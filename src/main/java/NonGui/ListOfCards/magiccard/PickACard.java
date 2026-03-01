package NonGui.ListOfCards.magiccard;

import NonGui.BaseEntity.Cards.MagicCard.MagicCard;
import NonGui.BaseEntity.Player;
import NonGui.BaseEntity.BaseCard;
import javafx.collections.ObservableList;

import java.util.Scanner;

public class PickACard extends MagicCard {

    public PickACard() {
        super(
                "Pick a Card",
                "Lady Luck is smilin'.", // Twisted Fate’s iconic line
                "DRAW 3 cards and DISCARD a card."
        );
    }

    @Override
    public boolean playCard(Player player) {
        System.out.println("\n🎴 " + player.getName() + " casts " + this.getName() + "! (DRAW 3 cards and DISCARD 1 card)");

        // ==========================================
        // 1. DRAW step (draw 3 cards)
        // ==========================================
        System.out.println(player.getName() + " is drawing 3 cards...");
        for (int i = 0; i < 3; i++) {
            player.DrawRandomCard();
        }
        System.out.println("Draw complete!");

        // ==========================================
        // 2. DISCARD step (discard 1 card)
        // ==========================================
        ObservableList<BaseCard> hand = player.getCardsInHand(); // <-- ObservableList

        if (hand.isEmpty()) {
            System.out.println("You have no cards in hand to DISCARD!");
            return true;
        }

        System.out.println("\n" + player.getName() + ", choose a card from your hand to DISCARD:");

        // Show cards in hand
        for (int i = 0; i < hand.size(); i++) {
            System.out.println((i + 1) + ". " + hand.get(i).getName());
        }

        Scanner scanner = new Scanner(System.in);
        int discardIndex = -1;

        // Loop until valid input
        while (discardIndex < 1 || discardIndex > hand.size()) {
            System.out.print("Enter number (1-" + hand.size() + "): ");
            try {
                discardIndex = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Try again.");
            }
        }

        // Discard selected card
        BaseCard discardedCard = hand.get(discardIndex - 1);
        hand.remove(discardedCard); // works directly on ObservableList
        System.out.println("🗑️ " + discardedCard.getName() + " has been DISCARDED.");

        return true; // spell successfully cast
    }
}
