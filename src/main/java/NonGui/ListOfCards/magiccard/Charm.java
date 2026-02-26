package NonGui.ListOfCards.magiccard;

import NonGui.BaseEntity.Cards.MagicCard.MagicCard;
import NonGui.BaseEntity.Player;
import NonGui.BaseEntity.BaseCard;
import NonGui.BaseEntity.Cards.HeroCard.HeroCard;
import NonGui.GameLogic.GameChoice;

import java.util.ArrayList;
import java.util.Scanner;

import static NonGui.GameLogic.GameEngine.players;

public class Charm extends MagicCard {

    public Charm() {
        super(
                "Charm",
                "Don't you trust me?", // คำพูดตอนใช้สกิล Charm ของ Ahri
                "DISCARD 2 cards, then STEAL a Hero card."
        );
    }

    @Override
    public boolean playCard(Player player) {
        System.out.println("\n💖 " + player.getName() + " casts " + this.getName() + "! (DISCARD 2 cards to STEAL a Hero)");

        // ==========================================
        // 0. ตรวจสอบเงื่อนไขก่อนร่าย (Safety Checks)
        // ==========================================

        // เช็คว่ามีการ์ดในมือพอให้ทิ้งไหม (ต้องมีอย่างน้อย 2 ใบ)
        if (player.getCardsInHand().size() < 2) {
            System.out.println("You don't have enough cards to DISCARD! (Need 2 cards). The spell fails.");
            return false;
        }

        // เช็คว่าบอร์ดของเรามีช่องว่างพอให้ขโมยฮีโร่มาใส่หรือไม่
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

        // เช็คว่ามีศัตรูให้ขโมยฮีโร่ไหม
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
        // 1. ขั้นตอนการ DISCARD (ทิ้งการ์ด 2 ใบ)
        // ==========================================
        Scanner scanner = new Scanner(System.in);

        // วนลูปให้เลือกทิ้งการ์ดทีละใบจนครบ 2 ใบ
        for (int count = 1; count <= 2; count++) {
            ArrayList<BaseCard> hand = player.getCardsInHand();
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

            // ลบ -1 ออกแค่ในระบบเลือกเป้าหมาย แต่ ArrayList บนมือยังต้องมี -1 อยู่นะครับ
            BaseCard discardedCard = hand.get(discardIndex - 1);
            player.getCardsInHand().remove(discardedCard);
            System.out.println("🗑️ " + discardedCard.getName() + " has been DISCARDED.");
        }


        // ==========================================
        // 2. ขั้นตอนการ STEAL (ขโมยฮีโร่)
        // ==========================================
        Player[] validTargetsArray = validTargetsList.toArray(new Player[0]);

        // เลือกผู้เล่นเป้าหมาย (ผมเอา -1 ออกแล้ว ตามที่คุณแจ้งไว้ก่อนหน้านี้ครับ)
        System.out.println("\nChoose a player to STEAL a Hero from:");
        int targetIndex = GameChoice.selectPlayer(validTargetsArray);
        Player targetPlayer = validTargetsArray[targetIndex];

        // เลือกฮีโร่เป้าหมายและดึงข้อมูลออกมา (ผมเอา -1 ออกแล้วเช่นกันครับ)
        System.out.println("Select a hero from " + targetPlayer.getName() + "'s board to STEAL:");
        int heroIndex = GameChoice.selectHeroCard(targetPlayer);

        HeroCard stolenHero = targetPlayer.getHeroCard(heroIndex);

        // ถอดฮีโร่ออกจากบอร์ดศัตรู
        targetPlayer.removeHeroCard(heroIndex);

        // เอาฮีโร่ที่ขโมยมา ใส่ลงในช่องว่างช่องแรกของบอร์ดเรา
        for (int i = 0; i < player.getOwnedHero().length; i++) {
            if (player.getOwnedHero()[i] == null) {
                player.getOwnedHero()[i] = stolenHero;
                break;
            }
        }

        System.out.println("💖 SUCCESS! " + player.getName() + " charmed and stole " + stolenHero.getName() + " from " + targetPlayer.getName() + "!");

        return true;
    }
}