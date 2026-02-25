package NonGui.GameUtils;

import java.util.Random;
import gui.board.StatusBar; // <-- import StatusBar
import static NonGui.GameUtils.TriggerUtils.modifierUtils.TriggerModifier;;

public class DiceUtils {
    public static int getRoll(){
        Random rand = new Random();

        int roll = rand.nextInt(6) + 1;
        StatusBar.showMessage("You rolled a " + roll);
        StatusBar.showMessage("Does anyone wanted to use a Modifier?");
        System.out.println("You rolled a " + roll);
        System.out.println("Does anyone wanted to use a Modifier?");
        //roll = TriggerModifier(roll);

        return roll;
    }
}