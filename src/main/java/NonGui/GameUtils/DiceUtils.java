package NonGui.GameUtils;

import java.util.Random;

public class DiceUtils {
    public static int getRoll(){
        Random rand = new Random();

        int roll = rand.nextInt(6) + 1;
        System.out.println("You rolled a " + roll);
        System.out.println("Does anyone wanted to use a Modifier?");
        roll = TriggerModifier(roll);

        return roll;
    }
}
