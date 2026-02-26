package NonGui.ListOfCards.herocard.Support;

import NonGui.BaseEntity.Cards.HeroCard.HeroCard;
import NonGui.BaseEntity.Player;
import NonGui.BaseEntity.Properties.UnitClass;

import static NonGui.GameLogic.GameEngine.players;

public class TahmKench extends HeroCard {
    public TahmKench() {
        super(
                "Tahm Kench",
                "Call me king, call me demon.",
                "An Acquired Taste: Roll 9+. Trade hands with another player.",
                UnitClass.Support,
                9
        );
    }

    @Override
    public void useAbility(Player player) {
        System.out.println(this.getName() + " uses their ability! (Trade hands with another player)");

        // 1. กรองผู้เล่นที่ใช้สกิลออกไปจากตัวเลือก (เพื่อไม่ให้สลับการ์ดกับตัวเอง)
        java.util.ArrayList<Player> validTargetsList = new java.util.ArrayList<>();
        // ต้องมั่นใจว่ามีการ import static NonGui.GameLogic.GameEngine.players; ไว้ด้านบนของคลาส
        for (Player p : players) {
            if (p != player) {
                validTargetsList.add(p);
            }
        }

        // เช็คเผื่อกรณีเล่นคนเดียว
        if (validTargetsList.isEmpty()) {
            System.out.println("No other players available to trade hands with!");
            return;
        }

        Player[] validTargetsArray = validTargetsList.toArray(new Player[0]);

        // 2. เลือกผู้เล่นเป้าหมายที่จะสลับการ์ดบนมือ
        System.out.println(player.getName() + ", choose a player to TRADE hands with:");
        int targetIndex = NonGui.GameLogic.GameChoice.selectPlayer(validTargetsArray);
        Player targetPlayer = validTargetsArray[targetIndex];

        // 3. ดึงการ์ดบนมือของทั้งสองฝ่ายมาเก็บไว้ชั่วคราว
        java.util.ArrayList<NonGui.BaseEntity.BaseCard> myHand = player.getCardsInHand();
        java.util.ArrayList<NonGui.BaseEntity.BaseCard> targetHand = targetPlayer.getCardsInHand();

        // 4. สลับการ์ดกันโดยใช้ Setter
        player.setCardsInHand(targetHand);
        targetPlayer.setCardsInHand(myHand);

        System.out.println("SUCCESS! " + player.getName() + " and " + targetPlayer.getName() + " have traded their hands!");
        System.out.println(player.getName() + " now has " + player.getCardsInHand().size() + " cards.");
        System.out.println(targetPlayer.getName() + " now has " + targetPlayer.getCardsInHand().size() + " cards.");
    }
}