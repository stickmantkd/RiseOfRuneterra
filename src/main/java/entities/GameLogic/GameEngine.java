package entities.GameLogic;

import entities.baseObject.Player;
public class GameEngine {

    public static void main(String[] args) {
        System.out.println("Launching Rise of Runeterra...");

        Player[] PlayerList = new Player[4];

        //Get Each player's name
        int playerNumber = 0;
        while (playerNumber < 4){
            System.out.print("Enter Player" + playerNumber+1 + "'s name");
            String playerName = System.in.toString();
            PlayerList[playerNumber] = new Player(playerName);
            playerNumber++;
        }
    }
}
