package NonGui.GameUtils;

import NonGui.BaseEntity.*;

import static NonGui.GameLogic.GameChoice.*;
import static NonGui.GameLogic.GameEngine.*;
import static NonGui.GameUtils.DiceUtils.getRoll;
import static NonGui.GameUtils.GenerationsUtils.*;

public class GameplayUtils {
    //Sacrifice n Destroy
    public static void SacrificeHero(Player player, int number){
        for(int i = 0; i < number; ++i){
            if(player.boardIsEmpty()){
                break;
            }

            int selectedHero = selectHeroCard(player);
            if(!player.removeHeroCard(selectedHero)){
                System.out.println("Please selected NOT NULL hero");
                --i;
            }
        }
    }

    //Rotate objective
    public static void rotateObjective(int objectiveIndex){
        objectives[objectiveIndex] = generateRandomObjective();
    }

    //Challenge
    public static boolean beginChallenge() {
        int userRoll = 0,oppRoll = 0;
        while (userRoll == oppRoll){
            userRoll = getRoll();
            oppRoll = getRoll();
        }
        return userRoll > oppRoll;
    }
}