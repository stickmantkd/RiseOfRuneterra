package entities.GameLogic;

import java.util.Random;

public class DiceUtils {
    public static int getRoll(){
        Random rand = new Random();
        return rand.nextInt(6) + 1;
    }
}
