package NonGui.GameLogic;

import NonGui.BaseEntity.*;

import java.util.Scanner;

import static NonGui.GameLogic.GameChoice.*;
import static NonGui.GameLogic.GameSetup.*;

public class GameEngine {
    public static Scanner keyBoard;
    public static Player[] players = new Player[4];
    public static Objective[] objectives = new Objective[3];

    public static void main(String[] args) {
        System.out.println("Launching Rise of Runeterra...");

        keyBoard = new Scanner(System.in);
        while(true){
            System.out.println("=============================");
            System.out.println("Welcome to Rise of Runeterra");
            System.out.println("=============================");
            System.out.println("What are you doing?");
            System.out.println("1) Start Game");
            System.out.println("2) Quit");
            System.out.println("=============================");
            int results = getChoice();

            if(results==1) {
                System.out.println("=============================");
                startGame();
            }else if(results==2){
                break;
            }else {
                System.out.println("Invalid Input, Terminate the game.");
                break;
            }
        }
    }


    private static void startGame(){
        //Fields
        initializePlayer();
        initializeObjective();

        boolean isGameActive = true;

        int PlayerNumber = 0;
        while(isGameActive) {
            Player currentPlayer = players[PlayerNumber];
            while(currentPlayer.getActionPoint() > 0){
                System.out.println("It's " + currentPlayer + "'s turn");
                System.out.println("=============================");
                System.out.println("Action Point left: " + currentPlayer.getActionPoint());
                System.out.println("Choose your action");
                System.out.println("1 : Draw a card");
                System.out.println("2 : Play a card");
                System.out.println("3 : Use Hero's ability");
                System.out.println("4 : Try to complete the objective");

                switch (getChoice()) {
                    case (1) -> {
                        currentPlayer.DrawRandomCard();
                        currentPlayer.decreaseActionPoint(1);
                    }
                    case (2) -> {
                        if(currentPlayer.HandIsEmpty()) {
                            System.out.println("Invalid action: Hand Is Empty!");
                            continue;
                        }
                        int cardIndex = selectCardsInHand(currentPlayer);

                        BaseCard selectedCard = currentPlayer.getCardInHand(cardIndex);
                        if(!(selectedCard instanceof ActionCard)){
                            System.out.println("Invalid action: You can't play a Trigger Card");
                            continue;
                        }
                        if(!((ActionCard) selectedCard).playCard(currentPlayer)){
                            System.out.println("Invalid action: Board Is Full!");
                            currentPlayer.addCardToHand(selectedCard);
                            continue;
                        }

                        currentPlayer.decreaseActionPoint(1);
                    }
                    case (3) -> {
                        int heroIndex = selectHeroCard(currentPlayer);
                        if(!currentPlayer.getHeroCard(heroIndex).tryUseAbility()){
                            System.out.println("Invalid action: Ability on cooldown");
                            continue;
                        }
                        currentPlayer.decreaseActionPoint(1);
                    }
                    case (4) -> {
                        if(currentPlayer.getActionPoint() < 2){
                            System.out.println("Invalid: You need 2 AP to attempt on an Objective");
                            continue;
                        }
                        int objectiveIndex = selectObjective();
                        objectives[objectiveIndex].tryToComplete(objectiveIndex, currentPlayer);
                        currentPlayer.decreaseActionPoint(2);
                    }
                    default -> {
                        System.out.println("Invalid choice");
                    }

                }
                System.out.println("=============================");
            }
            if(currentPlayer.isWinning()){
                System.out.println(currentPlayer + " Wins");
                System.out.println("Good Game Go Next");
                System.out.println("=============================");

                isGameActive = false;
            }

            currentPlayer.refillActionPoint();
            players[PlayerNumber] = currentPlayer;
            PlayerNumber = (PlayerNumber + 1)%4;
        }
    }
}
