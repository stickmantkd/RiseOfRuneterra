package NonGui.ListOfCards.herocard.Fighter;

import NonGui.BaseEntity.Cards.HeroCard.HeroCard;
import NonGui.BaseEntity.Player;
import NonGui.BaseEntity.Properties.UnitClass;
import NonGui.GameLogic.GameChoice;

import java.util.ArrayList;

import static NonGui.GameLogic.GameEngine.players;

public class Volibear extends HeroCard {

    public Volibear(){
        super(
                "Volibear",
                "I am the storm!",
                "Thundering Smash: Roll 5+. Choose a player. That player must DISCARD 2 cards.",
                UnitClass.Fighter
        );
    }

    @Override
    public void useAbility(Player player) {
        System.out.println(this.getName() + " uses Thundering Smash!");

        // 1. กรองผู้เล่นที่ใช้สกิลออกไปจากตัวเลือก
        ArrayList<Player> validTargetsList = new ArrayList<>();
        for (Player p : players) {
            if (p != player) { // ถ้าไม่ใช่คนที่ร่ายสกิล ให้เพิ่มเข้า List
                validTargetsList.add(p);
            }
        }

        // เช็คเผื่อกรณีเล่นคนเดียว หรือไม่มีเป้าหมายเหลือแล้ว
        if (validTargetsList.isEmpty()) {
            System.out.println("No other players available to target!");
            return;
        }

        // แปลง List กลับเป็น Array เพื่อส่งให้ GameChoice.selectPlayer
        Player[] validTargetsArray = validTargetsList.toArray(new Player[0]);

        // 2. เลือกผู้เล่นเป้าหมาย (จากรายชื่อที่กรองแล้ว)
        System.out.println(player.getName() + ", choose a player to discard 2 cards:");
        int targetIndex = GameChoice.selectPlayer(validTargetsArray);

        Player targetPlayer = validTargetsArray[targetIndex - 1];

        System.out.println(targetPlayer.getName() + " is targeted and must discard 2 cards!");

        // 3. บังคับเป้าหมายทิ้งการ์ด 2 ใบ
        for (int i = 0; i < 2; i++) {
            if (targetPlayer.HandIsEmpty()) {
                System.out.println(targetPlayer.getName() + " has no more cards in hand!");
                break;
            }

            System.out.println(targetPlayer.getName() + ", please select a card to DISCARD (" + (i + 1) + "/2):");
            int cardIndex = GameChoice.selectCardsInHand(targetPlayer);

            targetPlayer.getCardInHand(cardIndex);
            System.out.println("Card discarded.");
        }
    }
}