package nongui.listofcards.herocard.Tank;

import nongui.baseentity.BaseCard;
import nongui.baseentity.cards.HeroCard.HeroCard;
import nongui.baseentity.cards.ModifierCard.ModifierCard;
import nongui.baseentity.Player;
import nongui.baseentity.properties.UnitClass;
import nongui.gamelogic.GameEngine;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceDialog;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Represents the "Nautilus" Hero Card.
 * <p>
 * <b>Ability: Dredge Line</b><br>
 * Requirement: Roll 6+<br>
 * Effect: Search the discard pile for a Modifier card and add it to your hand.
 * <p>
 * <i>Nautilus reaches into the depths of the discard pile to recover essential tactical modifiers.</i>
 */
public class Nautilus extends HeroCard {

    /**
     * Constructs Nautilus with his base stats and deep-sea flavor text.
     */
    public Nautilus() {
        super(
                "Nautilus",
                "Beware the depths.",
                "Dredge Line: Roll 6+. Search the discard pile for a Modifier card and add it to your hand.",
                UnitClass.Tank,
                6
        );
    }

    /**
     * Executes the Dredge Line ability.
     * <p>
     * Logic Flow:
     * 1. Scans the global discard pile for instances of ModifierCard.
     * 2. If none are found, informs the player via an Alert.
     * 3. If found, displays a ChoiceDialog for the player to select one.
     * 4. Transfers the selected card from the discard pile to the player's hand.
     * * @param player The player who activated Nautilus's ability.
     */
    @Override
    public void useAbility(Player player) {
        System.out.println("⚓ Nautilus uses Dredge Line!");

        // 1. Fetch and filter discard pile for Modifiers
        List<BaseCard> availableModifiers = GameEngine.deck.getDiscardPile().stream()
                .filter(card -> card instanceof ModifierCard)
                .collect(Collectors.toList());

        // 2. Empty state handling
        if (availableModifiers.isEmpty()) {
            System.out.println("🌊 The depths are empty... (No Modifier cards in discard pile)");
            Alert alert = new Alert(
                    Alert.AlertType.INFORMATION,
                    "No Modifier cards found in the discard pile!",
                    javafx.scene.control.ButtonType.OK
            );
            alert.setTitle("Dredge Line");
            alert.setHeaderText(null);
            alert.showAndWait();
            return;
        }

        // 3. UI Setup for selection
        List<String> options = availableModifiers.stream()
                .map(BaseCard::getName)
                .collect(Collectors.toList());

        ChoiceDialog<String> dialog = new ChoiceDialog<>(options.get(0), options);
        dialog.setTitle("Nautilus Ability: Dredge Line");
        dialog.setHeaderText("Select a Modifier card to retrieve from the depths");
        dialog.setContentText("Choose your card:");

        Optional<String> result = dialog.showAndWait();

        // 4. Data Transfer logic

        if (result.isPresent()) {
            String selectedName = result.get();

            BaseCard pickedCard = availableModifiers.stream()
                    .filter(c -> c.getName().equals(selectedName))
                    .findFirst()
                    .orElse(null);

            if (pickedCard != null) {
                // Transfer card
                GameEngine.deck.getDiscardPile().remove(pickedCard);
                player.addCardToHand(pickedCard);

                System.out.println("⚓ Nautilus dragged " + pickedCard.getName() + " back to surface!");

                // Synchronize View
                try {
                    gui.BoardView.refresh();
                } catch (Exception e) {}
            }
        }
    }
}