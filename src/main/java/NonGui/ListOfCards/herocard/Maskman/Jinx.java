package NonGui.ListOfCards.herocard.Maskman;

import NonGui.BaseEntity.Cards.HeroCard.HeroCard;
import NonGui.BaseEntity.Player;
import NonGui.BaseEntity.Properties.UnitClass;

public class Jinx extends HeroCard {

    public Jinx(){
        super(
                "Jinx",
                "Rules are made to be broken... like buildings! Or people!",
                "Get Excited!: Roll 10+. DRAW cards until you have 7 cards in your hand.",
                UnitClass.Maskman
        );
    }

    @Override
    public void useAbility(Player player) {
        System.out.println(this.getName() + " uses their ability! (DRAW cards until you have 7 in your hand)");

        // 1. เช็คจำนวนการ์ดบนมือปัจจุบัน
        int currentHandSize = player.getCardsInHand().size();
        int targetHandSize = 7;

        // 2. ถ้ามี 7 ใบหรือมากกว่าอยู่แล้ว ก็ไม่ต้องจั่วเพิ่ม
        if (currentHandSize >= targetHandSize) {
            System.out.println(player.getName() + " already has " + currentHandSize + " cards. No cards drawn.");
            return;
        }

        // 3. คำนวณว่าต้องจั่วเพิ่มกี่ใบ
        int cardsToDraw = targetHandSize - currentHandSize;
        System.out.println(player.getName() + " has " + currentHandSize + " cards. Drawing " + cardsToDraw + " more cards...");

        // 4. ทำการจั่วการ์ดตามจำนวนที่ขาด
        for (int i = 0; i < cardsToDraw; i++) {
            player.DrawRandomCard();
        }

        System.out.println("SUCCESS! " + player.getName() + " now has " + player.getCardsInHand().size() + " cards in hand.");
    }
}
