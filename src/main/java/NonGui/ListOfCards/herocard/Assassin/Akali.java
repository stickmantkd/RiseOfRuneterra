package NonGui.ListOfCards.herocard.Assassin;

import NonGui.BaseEntity.BaseCard;
import NonGui.BaseEntity.Cards.HeroCard.HeroCard;
import NonGui.BaseEntity.Player;
import NonGui.BaseEntity.Properties.UnitClass;

import java.util.Random;

import static NonGui.GameLogic.GameEngine.players;

public class Akali extends HeroCard {

    public Akali(){
        super(
                "Akali",
                "Fear the assassin with no master.",
                "Perfect Execution: Roll 8+. DESTROY a Hero card, then DRAW a card.",
                UnitClass.Assassin,
                7
        );
    }

    @Override
    public void useAbility(Player player) {
        System.out.println(this.getName() + " uses their ability! (Pull cards from players with a Thief)");

        boolean stoleAnyCard = false;
        Random rand = new Random();

        // 1. วนลูปเช็คผู้เล่นทุกคนในเกม
        for (Player targetPlayer : players) {
            // ข้ามตัวเอง ไม่ต้องขโมยการ์ดตัวเอง
            if (targetPlayer == player) {
                continue;
            }

            // 2. เช็คว่าผู้เล่นคนนี้มีฮีโร่อาชีพ Thief บนบอร์ดหรือไม่
            boolean hasThief = false;
            for (HeroCard hero : targetPlayer.getOwnedHero()) {
                if (hero != null && hero.getUnitClass() == UnitClass.Assassin) {
                    hasThief = true;
                    break; // เจอ Thief แค่ 1 ตัวก็ถือว่าเข้าเงื่อนไขแล้ว
                }
            }

            // 3. ถ้าเข้าเงื่อนไข (มี Thief และ มีการ์ดบนมือให้ขโมย)
            if (hasThief) {
                if (targetPlayer.HandIsEmpty()) {
                    System.out.println(targetPlayer.getName() + " has a Thief, but no cards in hand to steal!");
                } else {
                    // หาจำนวนการ์ดบนมือเป้าหมาย เพื่อนำมาสุ่ม index
                    int handSize = targetPlayer.getCardsInHand().size();

                    // สุ่มตัวเลขตั้งแต่ 1 ถึง handSize (เพราะ getCardInHand ของคุณใช้ index - 1)
                    int randomCardIndex = rand.nextInt(handSize) + 1;

                    // ดึงการ์ดออกจากมือเป้าหมาย
                    BaseCard stolenCard = targetPlayer.getCardInHand(randomCardIndex);

                    // นำการ์ดที่ขโมยมา เพิ่มเข้ามือของเรา
                    player.addCardToHand(stolenCard);

                    System.out.println("SUCCESS! " + player.getName() + " pulled a card from " + targetPlayer.getName() + "'s hand!");
                    stoleAnyCard = true;
                }
            }
        }

        // แจ้งเตือนกรณีไม่มีใครเข้าเงื่อนไขเลย
        if (!stoleAnyCard) {
            System.out.println("No one else had a Thief in their party, or they had no cards. Nothing happened.");
        }
    }
}