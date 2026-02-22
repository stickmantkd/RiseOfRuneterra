package NonGui.GameLogic;

import NonGui.BaseEntity.Cards.Itemcard.ItemCard;
import NonGui.BaseEntity.Cards.HeroCard.HeroCard;
import NonGui.BaseEntity.Cards.MagicCard.MagicCard;
import NonGui.BaseEntity.*;

import java.util.Scanner;

import static NonGui.GameLogic.GameChoice.*;
import static NonGui.GameLogic.GameSetup.*;

public class GameEngine {
    public static Scanner keyBoard;
    public static Player[] players = new Player[4];
    public static Objective[] objectives;

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
                System.out.println("3 : Try to complete the objective");
                switch (getChoice()) {
                    case (1) -> {
                        currentPlayer.DrawRandomCard();
                    }
                    case (2) -> {
                        int selectedCardNumber = selectCardsInHand(currentPlayer);
                        BaseCard selectedCard = currentPlayer.getCardInHand(selectedCardNumber);

                        if(!selectedCard.playCard(currentPlayer)){
                            System.out.println("Invalid: You cannot play that card that way!");
                            currentPlayer.addCardToHand(selectedCard);
                            currentPlayer.increaseActionPoint(1);
                        }
                    }
                    case (3) -> {
                        //now it only cose 1 action Point
                        int selectedObjective = selectObjective();
                        objectives[selectedObjective].tryToComplete(currentPlayer);
                    }
                }
                System.out.println("=============================");
                currentPlayer.decreaseActionPoint(1);
            }
            if(currentPlayer.isWinning()){
                System.out.println(currentPlayer + " Wins");
                System.out.println("Good Game Go Next");
                System.out.println("=============================");

                isGameActive = false;
            }

            if(currentPlayer.getActionPoint() == 0){
                currentPlayer.refillActionPoint();
                players[PlayerNumber] = currentPlayer;
                PlayerNumber = (PlayerNumber + 1)%4;
            }
        }
    }
}
