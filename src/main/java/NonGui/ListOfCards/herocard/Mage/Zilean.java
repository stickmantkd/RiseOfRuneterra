package NonGui.ListOfCards.herocard.Mage;

import NonGui.BaseEntity.BaseCard;
import NonGui.BaseEntity.Cards.HeroCard.HeroCard;
import NonGui.BaseEntity.Cards.MagicCard.MagicCard;
import NonGui.BaseEntity.Player;
import NonGui.BaseEntity.Properties.UnitClass;
import NonGui.GameLogic.GameEngine;
import javafx.scene.control.ChoiceDialog;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Represents the "Zilean" Hero Card.
 * <p>
 * <b>Ability: Rewind</b><br>
 * Requirement: Roll 5+<br>
 * Effect: Search the discard pile for a Magic card and add it back to your hand.
 * <p>
 * <i>Zilean allows players to recycle powerful spells, making him a cornerstone for Mage-heavy decks.</i>
 */
public class Zilean extends HeroCard {

    /**
     * Constructs Zilean with his base stats and time-bending flavor text.
     */
    public Zilean() {
        super(
                "Zilean",
                "I knew you would do that.",
                "Rewind: Roll 5+. Search the discard pile for a Magic card and add it to your hand.",
                UnitClass.Mage,
                5
        );
    }

    /**
     * Executes the Rewind ability.
     * <p>
     * Logic Flow:
     * 1. Access the global discard pile from GameEngine.
     * 2. Filter the pile to identify only MagicCard instances.
     * 3. Prompt the player with a ChoiceDialog to select a card from the past.
     * 4. Remove the selected card from the discard pile and add it to the player's hand.
     * * @param player The player who activated Zilean's ability.
     */
    @Override
    public void useAbility(Player player) {
        System.out.println("⏳ Zilean uses Rewind! Turning back time...");

        // 1. Filter the discard pile for Magic cards specifically
        List<BaseCard> magicInDiscard = GameEngine.deck.getDiscardPile().stream()
                .filter(card -> card instanceof MagicCard)
                .collect(Collectors.toList());

        // 2. Fail-safe: Check if any magic exists in the past
        if (magicInDiscard.isEmpty()) {
            System.out.println("⏳ Time is empty... No Magic cards in discard pile.");
            return;
        }

        // 3. Prepare the UI Selection
        List<String> options = magicInDiscard.stream()
                .map(BaseCard::getName)
                .collect(Collectors.toList());

        ChoiceDialog<String> dialog = new ChoiceDialog<>(options.get(0), options);
        dialog.setTitle("Zilean Ability: Rewind");
        dialog.setHeaderText("Select a Magic card to bring back to the present");
        dialog.setContentText("Choose your spell:");

        Optional<String> result = dialog.showAndWait();

        // 4. Time Paradox Resolution: Move the card back to hand
        result.ifPresent(selectedName -> {
            BaseCard pickedCard = magicInDiscard.stream()
                    .filter(c -> c.getName().equals(selectedName))
                    .findFirst()
                    .orElse(null);

            if (pickedCard != null) {
                // Remove from discard and add to hand
                GameEngine.deck.getDiscardPile().remove(pickedCard);
                player.addCardToHand(pickedCard);

                System.out.println("⏳ " + player.getName() + " retrieved " + pickedCard.getName() + " from the past!");

                // Synchronize board UI
                try {
                    gui.BoardView.refresh();
                } catch (Exception e) {}
            }
        });
    }
}