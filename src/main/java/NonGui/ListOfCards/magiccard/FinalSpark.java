package NonGui.ListOfCards.magiccard;

import NonGui.BaseEntity.Cards.MagicCard.MagicCard;
import NonGui.BaseEntity.Player;
import NonGui.BaseEntity.BaseCard;
import NonGui.GameLogic.GameChoice;

import java.util.ArrayList;
import java.util.Scanner;

import static NonGui.GameLogic.GameEngine.players;

public class FinalSpark extends MagicCard {

    public FinalSpark() {
        super(
                "Final Spark",
                "DEMACIA!!!",
                "DISCARD a card, then DESTROY a Hero card."
        );
    }

    @Override
    public boolean playCard(Player player) {
        System.out.println("\n✨ " + player.getName() + " casts " + this.getName() + "! (DISCARD 1 card to DESTROY a Hero)");

        // ==========================================
        // 1. ขั้นตอนการ DISCARD (ทิ้งการ์ดบนมือ 1 ใบ)
        // ==========================================

        if (player.HandIsEmpty()) {
            System.out.println("You have no other cards to DISCARD! The spell fizzles and fails.");
            return false;
        }

        System.out.println(player.getName() + ", choose a card from your hand to DISCARD:");
        ArrayList<BaseCard> hand = player.getCardsInHand();

        for (int i = 0; i < hand.size(); i++) {
            System.out.println((i + 1) + ". " + hand.get(i).getName());
        }

        Scanner scanner = new Scanner(System.in);
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
        player.getCardsInHand().remove(discardedCard);
        System.out.println("🗑️ " + discardedCard.getName() + " has been DISCARDED to power up the laser.");


        // ==========================================
        // 2. ขั้นตอนการ DESTROY (ทำลายฮีโร่)
        // ==========================================

        ArrayList<Player> validTargetsList = new ArrayList<>();
        for (Player p : players) {
            if (!p.boardIsEmpty()) {
                validTargetsList.add(p);
            }
        }

        if (validTargetsList.isEmpty()) {
            System.out.println("There are no heroes on the board to DESTROY! (The spell fires into the sky)");
            return true;
        }

        Player[] validTargetsArray = validTargetsList.toArray(new Player[0]);

        // เลือกผู้เล่นเป้าหมาย (เอา -1 ออกแล้ว)
        System.out.println("\nChoose a player to blast their hero:");
        int targetIndex = GameChoice.selectPlayer(validTargetsArray);
        Player targetPlayer = validTargetsArray[targetIndex];

        // เลือกฮีโร่และทำลาย (เอา -1 ออกแล้ว)
        System.out.println("Select a hero from " + targetPlayer.getName() + "'s board to DESTROY:");
        int heroIndex = GameChoice.selectHeroCard(targetPlayer);

        targetPlayer.removeHeroCard(heroIndex);
        System.out.println("💥 BZZZT! A hero from " + targetPlayer.getName() + "'s board has been completely vaporized by Final Spark!");

        return true;
    }
}
