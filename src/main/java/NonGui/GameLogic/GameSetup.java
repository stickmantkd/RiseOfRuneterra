package NonGui.GameLogic;

import NonGui.BaseEntity.Player;
import NonGui.ListOfObjective.BaronNashor;

import static NonGui.GameLogic.GameEngine.*;

public class GameSetup {
    static void initializePlayer(){
        for(int i = 0; i < 4; ++i){
            System.out.println("Enter player" + (i+1) + "'s name.");
            String playerName = keyBoard.nextLine();
            players[i] = new Player(playerName);
        }
    }

    static void initializeObjective(){
        //To be implemented
        objectives = new BaronNashor[3];
    }
}
