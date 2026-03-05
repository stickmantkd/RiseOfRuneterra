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
                // ถ้าอยากให้ข้อความตรงกับโค้ด แนะนำให้แก้ Description ตรงนี้ครับ
                "Perfect Execution: Roll 8+. Pull a card from each player with an Assassin.",
                UnitClass.Assassin,
                8
        );
    }

    @Override
    public void useAbility(Player player) {
        System.out.println(this.getName() + " uses their ability! (Pull cards from players with an Assassin)");

        boolean stoleAnyCard = false;
        Random rand = new Random();

        // 1. วนลูปเช็คผู้เล่นทุกคนในเกม
        for (Player targetPlayer : players) {
            // ข้ามตัวเอง ไม่ต้องขโมยการ์ดตัวเอง
            if (targetPlayer == player) {
                continue;
            }

            // 2. เช็คว่าผู้เล่นคนนี้มีฮีโร่อาชีพ Assassin บนบอร์ดหรือไม่
            boolean hasAssassin = false;
            for (HeroCard hero : targetPlayer.getOwnedHero()) {
                if (hero != null && hero.getUnitClass() == UnitClass.Assassin) {
                    hasAssassin = true;
                    break; // เจอ Assassin แค่ 1 ตัวก็ถือว่าเข้าเงื่อนไขแล้ว
                }
            }

            // 3. ถ้าเข้าเงื่อนไข (มี Assassin และ มีการ์ดบนมือให้ขโมย)
            if (hasAssassin) {
                if (targetPlayer.handIsEmpty()) {
                    System.out.println(targetPlayer.getName() + " has an Assassin, but no cards in hand to steal!");
                } else {
                    // หาจำนวนการ์ดบนมือเป้าหมาย เพื่อนำมาสุ่ม index
                    int handSize = targetPlayer.getCardsInHand().size();

                    // 🛠️ แก้ไขบั๊ก IndexOutOfBounds: สุ่ม 0 ถึง handSize - 1
                    int randomCardIndex = rand.nextInt(handSize);

                    // ดึงการ์ดออกจากมือเป้าหมาย
                    BaseCard stolenCard = targetPlayer.getCardInHand(randomCardIndex);

                    // นำการ์ดที่ขโมยมา เพิ่มเข้ามือของเรา
                    player.addCardToHand(stolenCard);

                    System.out.println("SUCCESS! " + player.getName() + " pulled " + stolenCard.getName() + " from " + targetPlayer.getName() + "'s hand!");
                    stoleAnyCard = true;
                }
            }
        }

        // แจ้งเตือนกรณีไม่มีใครเข้าเงื่อนไขเลย
        if (!stoleAnyCard) {
            System.out.println("No one else had an Assassin in their party, or they had no cards. Nothing happened.");
        }
    }
}