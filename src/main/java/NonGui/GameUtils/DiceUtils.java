package NonGui.GameUtils;

import NonGui.BaseEntity.Cards.HeroCard.HeroCard;
import NonGui.BaseEntity.Player;
import NonGui.ListOfCards.itemcard.BlueBuff;
import NonGui.ListOfCards.itemcard.SnakesEmbrace;

import java.util.Random;
import java.util.Scanner;

import static NonGui.GameUtils.TriggerUtils.modifierUtils.TriggerModifier;;

public class DiceUtils {
    public static int getRoll(){
        Random rand = new Random();

        int roll = rand.nextInt(6) + 1;
        System.out.println("You rolled a " + roll);
        System.out.println("Does anyone wanted to use a Modifier?");
        roll = TriggerModifier(roll);

        return roll;
    }

    public static boolean rollForAbility(HeroCard card, int targetScore) {
        System.out.println("\n🎲 " + card.getName() + " is rolling to activate an ability! (Needs " + targetScore + "+)");
        System.out.println("Press ENTER to roll the dice...");

        Random rand = new Random();
        int dice1 = rand.nextInt(6) + 1; // สุ่ม 1-6
        int dice2 = rand.nextInt(6) + 1; // สุ่ม 1-6
        int total = dice1 + dice2;
        if(card.getItem() instanceof BlueBuff) {
            total+=2;
            System.out.println("Result: [ " + dice1 + " ] + [ " + dice2 + " ] + [ " + 2 + "] = " + total);
        }
        else if(card.getItem() instanceof SnakesEmbrace){
            total-=2;
            System.out.println("Result: [ " + dice1 + " ] + [ " + dice2 + " ] - [ " + 2 + "] = " + total);
        }
        else System.out.println("Result: [ " + dice1 + " ] + [ " + dice2 + " ] = " + total);

        // TODO ในอนาคต: ถ้ามีการ์ด Modifier (+/- แต้มเต๋า) สามารถแทรกระบบให้ผู้เล่นคนอื่นลงการ์ดขัดขวางได้ตรงนี้ครับ

        if (total >= targetScore) {
            System.out.println("✅ SUCCESS! The ability activates.");
            return true;
        } else {
            System.out.println("❌ FAILED! The roll was too low. The ability does not activate.");
            return false;
        }
    }
}
