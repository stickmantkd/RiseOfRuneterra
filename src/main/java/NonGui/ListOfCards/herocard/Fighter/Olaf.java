package NonGui.ListOfCards.herocard.Fighter;

import NonGui.BaseEntity.Cards.HeroCard.HeroCard;
import NonGui.BaseEntity.Player;
import NonGui.BaseEntity.Properties.UnitClass;
import NonGui.GameLogic.GameChoice;

import java.util.ArrayList;
import java.util.Scanner;

import static NonGui.GameLogic.GameEngine.players;

public class Olaf extends HeroCard {

    public Olaf(){
        super(
                "Olaf",
                "Leave nothing behind!",
                "Ragnarok: Roll 10+. DISCARD up to 3 cards. For each card discarded, DESTROY a Hero card.",
                UnitClass.Fighter
        );
    }

    @Override
    public void useAbility(Player player) {
        System.out.println(this.getName() + " uses Ragnarok!");

        // เช็คจำนวนการ์ดบนมือก่อน ว่ามีให้ทิ้งสูงสุดกี่ใบ (เผื่อมีบนมือไม่ถึง 3 ใบ)
        int maxDiscard = Math.min(3, player.getCardsInHand().size());
        if (maxDiscard == 0) {
            System.out.println(player.getName() + " has no cards to discard. Ragnarok ends.");
            return;
        }

        // 1. ถามผู้เล่นว่าจะทิ้งการ์ดกี่ใบ (ใช้ Scanner รับค่าชั่วคราวเพราะ GameChoice.getChoice ไม่รองรับการถามจำนวน)
        Scanner scanner = new Scanner(System.in);
        int numToDiscard = -1;
        while (numToDiscard < 0 || numToDiscard > maxDiscard) {
            System.out.print(player.getName() + ", how many cards do you want to DISCARD? (0 to " + maxDiscard + "): ");
            try {
                numToDiscard = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        }

        if (numToDiscard == 0) {
            System.out.println(player.getName() + " chose to discard 0 cards. Ragnarok ends.");
            return;
        }

        // 2. ทิ้งการ์ดตามจำนวนที่ระบุ
        for (int i = 0; i < numToDiscard; i++) {
            System.out.println(player.getName() + ", select a card to DISCARD (" + (i + 1) + "/" + numToDiscard + "):");
            int cardIndex = GameChoice.selectCardsInHand(player);
            player.getCardInHand(cardIndex); // เมธอดนี้ทำการ remove การ์ดจากมือให้อยู่แล้ว
            System.out.println("Card discarded.");
        }

        // 3. ทำลายฮีโร่ตามจำนวนการ์ดที่ทิ้งไป
        for (int i = 0; i < numToDiscard; i++) {
            // กรองหา "เป้าหมายที่ถูกต้อง" (ไม่ใช่ตัวเอง และ ต้องมีฮีโร่บนบอร์ดให้ทำลาย)
            ArrayList<Player> validTargetsList = new ArrayList<>();
            for (Player p : players) {
                if (p != player && !p.boardIsEmpty()) {
                    validTargetsList.add(p);
                }
            }

            // เช็คว่ามีฮีโร่ศัตรูเหลือให้ทำลายไหม (เผื่อทำลายจนหมดบอร์ดศัตรูทุกคนแล้ว)
            if (validTargetsList.isEmpty()) {
                System.out.println("No enemy heroes left to destroy!");
                break;
            }

            Player[] validTargetsArray = validTargetsList.toArray(new Player[0]);

            // เลือกผู้เล่นที่จะทำลายฮีโร่
            System.out.println(player.getName() + ", choose a player to DESTROY their hero (" + (i + 1) + "/" + numToDiscard + "):");
            int targetIndex = GameChoice.selectPlayer(validTargetsArray);
            Player targetPlayer = validTargetsArray[targetIndex - 1];

            // เลือกฮีโร่จากผู้เล่นคนนั้น
            System.out.println("Select a hero from " + targetPlayer.getName() + "'s board to DESTROY:");
            int heroIndex = GameChoice.selectHeroCard(targetPlayer);

            // นำฮีโร่ออกจากบอร์ดเป้าหมาย (heroIndex - 1 เพราะในเมนู GameChoice เริ่มที่ 1 แต่ index ของ Array เริ่มที่ 0)
            targetPlayer.removeHeroCard(heroIndex - 1);
            System.out.println("A hero from " + targetPlayer.getName() + " has been destroyed!");
        }
    }
}
