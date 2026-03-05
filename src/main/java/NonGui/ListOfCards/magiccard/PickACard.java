package NonGui.ListOfCards.magiccard;

import NonGui.BaseEntity.Cards.MagicCard.MagicCard;
import NonGui.BaseEntity.Player;
import NonGui.BaseEntity.BaseCard;
import NonGui.BaseEntity.Properties.UnitClass;
import NonGui.GameLogic.GameEngine;
import gui.board.StatusBar;
import javafx.scene.control.ChoiceDialog;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Represents the "Pick a Card" Magic Card.
 * <p>
 * Lady Luck is smilin'.
 * Effect: DRAW 3 cards and DISCARD a card.
 */
public class PickACard extends MagicCard {

    /**
     * Constructs a new Pick a Card card with its identity and effect text.
     */
    public PickACard() {
        super(
                "Pick a Card",
                "Lady Luck is smilin'.",
                "DRAW 3 cards and DISCARD a card."
        );
    }

    @Override
    public boolean playCard(Player player) {
        System.out.println("\n🎴 " + player.getName() + " casts " + this.getName() + "!");

        System.out.println(player.getName() + " draws 3 cards...");
        for (int i = 0; i < 3; i++) {
            player.drawRandomCard();
        }

        try {
            gui.BoardView.refresh();
        } catch (Exception e) {}

        if (player.getCardsInHand().isEmpty()) {
            System.out.println("No cards in hand to discard.");
            return true;
        }

        List<String> handOptions = player.getCardsInHand().stream()
                .map(BaseCard::getName)
                .collect(Collectors.toList());

        ChoiceDialog<String> dialog = new ChoiceDialog<>(handOptions.get(0), handOptions);
        dialog.setTitle("Pick a Card: Discard Phase");
        dialog.setHeaderText(player.getName() + ", choose 1 card to DISCARD");
        dialog.setContentText("Select card:");

        Optional<String> result = dialog.showAndWait();

        if (result.isPresent()) {
            String selectedName = result.get();
            BaseCard toDiscard = player.getCardsInHand().stream()
                    .filter(c -> c.getName().equals(selectedName))
                    .findFirst().orElse(null);

            if (toDiscard != null) {
                player.getCardsInHand().remove(toDiscard);
                GameEngine.deck.discardCard(toDiscard);
                System.out.println("🗑️ " + toDiscard.getName() + " has been DISCARDED.");
            }
        } else {
            BaseCard forcedDiscard = player.getCardsInHand().get(0);
            player.getCardsInHand().remove(forcedDiscard);
            GameEngine.deck.discardCard(forcedDiscard);
            System.out.println("⚠️ No card selected. Forced discard: " + forcedDiscard.getName());
        }

        try {
            gui.BoardView.refresh();
        } catch (Exception e) {}

        if (player.getOwnedLeader().getUnitClass() == UnitClass.Mage) {
            player.drawRandomCard();
            StatusBar.showMessage("Mage Leader: Magic used! Drawing an extra card.");
        }

        return true;
    }
}