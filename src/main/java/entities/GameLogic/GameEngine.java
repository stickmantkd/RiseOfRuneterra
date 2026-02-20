package entities.GameLogic;

import entities.baseObject.LeaderCard.LeaderCard;
import entities.baseObject.Objective;
import entities.baseObject.Player;
import entities.baseObject.baseCard;
import entities.baseObject.baseCards.HeroCard.HeroCard;
import entities.baseObject.baseCards.HeroCard.UnitClass;
import entities.objective.BaronNashor;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class GameEngine {
    private static Scanner keyBoard;

    public static Objective[] objectiveList;

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
        Player[] playerList = new Player[4];
        for(int i = 0; i < 4; ++ i){
            playerList[i] = new Player("DumbAss");
            playerList[i].setOwnedLeader(new LeaderCard("Dummy","dumb", UnitClass.Fighter));
        }
        objectiveList = new BaronNashor[3];

        boolean isGameActive = true;

        int playerNumber = 0;
        while(isGameActive) {
            while(playerList[playerNumber].getActionPoint() > 0){
                System.out.println("It's Player" + (int) (playerNumber+1) + "'s turn");
                System.out.println("=============================");
                System.out.println("Action Point left: " + playerList[playerNumber].getActionPoint());
                System.out.println("Choose your action");
                System.out.println("1 : Draw a card");
                System.out.println("2 : Play a card");
                System.out.println("3 : Try to complete the objective");
                switch (getChoice()) {
                    case (1) -> {
                        //Implement DrawRandomCard();
                    }
                    case (2) -> {
                        ArrayList<baseCard> Hands = playerList[playerNumber].getCardsInHand();
                        int selectedCardNumber = selectCardsInHand(playerList[playerNumber]);
                        //-implement getCardsInHand(selectedCardNumber); in Player
                        //baseCard SelectedCard = PlayerList[playerNumber].getCardsInHand(selectedCardNumber);

                        //-implement playCard(SelectedCard) in HeroCard,MagicCard,ItemCard
                        //SelectedCard.playCard();
                    }
                    case (3) -> {
                        //now it only cose 1 action Point
                        int selectedObjective = SelectObjective();

                        //-implement attack() in player
                        //PlayerList[playerNumber].attack(ObjectiveList[selectedObjective]);
                    }
                }
                System.out.println("=============================");
                playerList[playerNumber].setActionPoint(playerList[playerNumber].getActionPoint() - 1);
            }
            if(playerList[playerNumber].isWinning()){
                System.out.println("Player" + (int) (playerNumber+1) + " Wins");
                System.out.println("Good Game Go Next");
                System.out.println("=============================");

                isGameActive = false;
            }

            if(playerList[playerNumber].getActionPoint() == 0){
                playerList[playerNumber].refillActionPoint();
                playerNumber = (playerNumber + 1)%4;
            }
        }
    }

    //getChoices
    private static int getChoice() {
        System.out.print(">> ");
        String input = keyBoard.nextLine();
        try{
            return Integer.parseInt(input);
        }catch(NumberFormatException e){
            return -1;
        }
    }


    private static int selectCardsInHand(Player player){
        System.out.println("Select Card number");
        int CardNumber = 1;
        for(baseCard card : player.getCardsInHand()){
            System.out.println(CardNumber + " : " + card);
            CardNumber++;
        }

        return getChoice();
    }

    private static int SelectObjective(){
        System.out.println("Select Objective number");
        int ObjectiveNumber = 1;
        for(Objective objective : objectiveList){
            System.out.println(ObjectiveNumber + " : " + objective);
            ObjectiveNumber++;
        }

        return getChoice();
    }

    private static int SelectPlayer(Player[] Players){
        System.out.println("Select Player number");
        int playerNumber = 1;
        for(Player player : Players){
            System.out.println(playerNumber + " : "+ player.toString());
            playerNumber++;
        }

        return getChoice();
    }

    private static int SelectHeroCard(Player Player){
        HeroCard[] HeroCards = Player.getOwnedHero();
        System.out.println("Select Hero card number");
        int HeroCardNumber = 1;
        for(HeroCard hero : HeroCards){
            if(hero == null) continue;
            System.out.println(HeroCardNumber + " : "+ hero);
            HeroCardNumber++;
        }

        return getChoice();
    }

    //Presets
    private static void RandomObjective(Objective[] objectiveList) {
        //To be implemented
    }
}
