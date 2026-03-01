package NonGui.ListOfCards.magiccard;

import NonGui.BaseEntity.Cards.MagicCard.MagicCard;
import NonGui.BaseEntity.Player;
import NonGui.BaseEntity.BaseCard;
import NonGui.BaseEntity.Cards.HeroCard.HeroCard;
import NonGui.GameLogic.GameChoice;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.Scanner;

import static NonGui.GameLogic.GameEngine.players;

public class Charm extends MagicCard {

    public Charm() {
        super(
                "Charm",
                "Don't you trust me?", // Ahri’s quote when using Charm
                "DISCARD 2 cards, then STEAL a Hero card."
        );
    }

    @Override
    public boolean playCard(Player player) {
        System.out.println("\n💖 " + player.getName() + " casts " + this.getName() + "! (DISCARD 2 cards to STEAL a Hero)");

        // ==========================================
        // 0. Safety Checks
        // ==========================================

        // Must have at least 2 cards to discard
        if (player.getCardsInHand().size() < 2) {
            System.out.println("You don't have enough cards to DISCARD! (Need 2 cards). The spell fails.");
            return false;
        }

        // Check if player has space for a stolen hero
        boolean hasSpace = false;
        for (HeroCard h : player.getOwnedHero()) {
            if (h == null) {
                hasSpace = true;
                break;
            }
        }
        if (!hasSpace) {
            System.out.println(player.getName() + "'s party is full! You cannot steal any more heroes. The spell fails.");
            return false;
        }

        // Check if there are valid targets with heroes
        ArrayList<Player> validTargetsList = new ArrayList<>();
        for (Player p : players) {
            if (p != player && !p.boardIsEmpty()) {
                validTargetsList.add(p);
            }
        }
        if (validTargetsList.isEmpty()) {
            System.out.println("No other players have heroes available to STEAL! The spell fails.");
            return false;
        }

        // ==========================================
        // 1. DISCARD 2 cards
        // ==========================================
        Scanner scanner = new Scanner(System.in);

        for (int count = 1; count <= 2; count++) {
            ObservableList<BaseCard> hand = player.getCardsInHand(); // <-- ObservableList
            System.out.println("\n" + player.getName() + ", choose card #" + count + " from your hand to DISCARD:");

            for (int i = 0; i < hand.size(); i++) {
                System.out.println((i + 1) + ". " + hand.get(i).getName());
            }

            int discardIndex = -1;
            while (discardIndex < 1 || discardIndex > hand.size()) {
                System.out.print("Enter number (1-" + hand.size() + "): ");
                try {
                    discardIndex = Integer.parseInt(scanner.nextLine());
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input. Try again.");
                }
            }

            BaseCard discardedCard = hand.get(discardIndex - 1);
            hand.remove(discardedCard); // works directly on ObservableList
            System.out.println("🗑️ " + discardedCard.getName() + " has been DISCARDED.");
        }

        // ==========================================
        // 2. STEAL a Hero
        // ==========================================
        Player[] validTargetsArray = validTargetsList.toArray(new Player[0]);

        System.out.println("\nChoose a player to STEAL a Hero from:");
        int targetIndex = GameChoice.selectPlayer(validTargetsArray);
        Player targetPlayer = validTargetsArray[targetIndex];

        System.out.println("Select a hero from " + targetPlayer.getName() + "'s board to STEAL:");
        int heroIndex = GameChoice.selectHeroCard(targetPlayer);

        HeroCard stolenHero = targetPlayer.getHeroCard(heroIndex);

        // Remove hero from target
        targetPlayer.removeHeroCard(heroIndex);

        // Place stolen hero into first empty slot
        for (int i = 0; i < player.getOwnedHero().length; i++) {
            if (player.getOwnedHero()[i] == null) {
                player.getOwnedHero()[i] = stolenHero;
                break;
            }
        }

        System.out.println("💖 SUCCESS! " + player.getName() + " charmed and stole " + stolenHero.getName() + " from " + targetPlayer.getName() + "!");
        System.out.println(player.getName() + " now has " + player.getCardsInHand().size() + " cards.");
        System.out.println(targetPlayer.getName() + " now has " + targetPlayer.getCardsInHand().size() + " cards.");

        return true;
    }
}
