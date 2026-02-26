package NonGui.ListOfCards.magiccard;

import NonGui.BaseEntity.Cards.MagicCard.MagicCard;
import NonGui.BaseEntity.Player;
import NonGui.BaseEntity.BaseCard;

import java.util.ArrayList;
import java.util.Scanner;

public class PickACard extends MagicCard {

    public PickACard() {
        super(
                "Pick a Card",
                "Lady Luck is smilin'.", // คำพูดติดปากของ Twisted Fate
                "DRAW 3 cards and DISCARD a card."
        );
    }

    @Override
    public boolean playCard(Player player) {
        System.out.println("\n🎴 " + player.getName() + " casts " + this.getName() + "! (DRAW 3 cards and DISCARD 1 card)");

        // ==========================================
        // 1. ขั้นตอนการ DRAW (จั่วการ์ด 3 ใบ)
        // ==========================================
        System.out.println(player.getName() + " is drawing 3 cards...");
        for (int i = 0; i < 3; i++) {
            player.DrawRandomCard();
        }
        System.out.println("Draw complete!");

        // ==========================================
        // 2. ขั้นตอนการ DISCARD (เลือกทิ้งการ์ด 1 ใบ)
        // ==========================================
        ArrayList<BaseCard> hand = player.getCardsInHand();

        // เช็คกันเหนียว (ปกติไม่ควรว่างเพราะเพิ่งจั่วไป 3 ใบ)
        if (hand.isEmpty()) {
            System.out.println("You have no cards in hand to DISCARD!");
            return true;
        }

        System.out.println("\n" + player.getName() + ", choose a card from your hand to DISCARD:");

        // แสดงการ์ดในมือให้เลือกทิ้ง
        for (int i = 0; i < hand.size(); i++) {
            System.out.println((i + 1) + ". " + hand.get(i).getName());
        }

        Scanner scanner = new Scanner(System.in);
        int discardIndex = -1;

        // วนลูปรับค่าจนกว่าผู้เล่นจะกรอกตัวเลขที่ถูกต้อง
        while (discardIndex < 1 || discardIndex > hand.size()) {
            System.out.print("Enter number (1-" + hand.size() + "): ");
            try {
                discardIndex = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Try again.");
            }
        }

        // นำการ์ดที่เลือกไปทิ้ง (ใช้ -1 เพื่อแปลงจากตัวเลขที่พิมพ์ 1, 2, 3... ให้ตรงกับ Index 0, 1, 2... ของ ArrayList)
        BaseCard discardedCard = hand.get(discardIndex - 1);
        player.getCardsInHand().remove(discardedCard);
        System.out.println("🗑️ " + discardedCard.getName() + " has been DISCARDED.");

        return true; // ร่ายเวทมนตร์สำเร็จ
    }
}
