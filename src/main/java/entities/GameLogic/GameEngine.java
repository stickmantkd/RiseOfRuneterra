package entities.GameLogic;

import entities.baseObject.LeaderCard;
import entities.baseObject.Objective;
import entities.baseObject.Player;
import entities.baseObject.baseCard;
import entities.baseObject.Cards.HeroCard.HeroCard;
import entities.baseObject.Cards.HeroCard.UnitClass;
import entities.baseObject.Cards.ItemCard;
import entities.baseObject.Cards.MagicCard;
import entities.objective.BaronNashor;

import java.util.ArrayList;
import java.util.Scanner;

public class GameEngine {
    private static Scanner keyBoard;

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
                System.out.println("It's Player" + (PlayerNumber+1) + "'s turn");
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
                        baseCard selectedCard = currentPlayer.getCardInHand(selectedCardNumber);

                        switch (selectedCard){
                            case HeroCard heroCard -> {
                                currentPlayer.playHero(heroCard);
                            }
                            case MagicCard magicCard -> {
                                currentPlayer.playMagic(magicCard);
                            }
                            case ItemCard itemCard -> {
                                int selectedHeroNumber = selectHeroCard(currentPlayer);
                                HeroCard selectedHero = currentPlayer.getHeroCard(selectedHeroNumber);
                                currentPlayer.playItem(itemCard, selectedHero);
                            }
                            default -> throw new IllegalStateException("Unexpected value: " + selectedCard);
                        }
                    }
                    case (3) -> {
                        //now it only cose 1 action Point
                        int selectedObjective = selectObjective();
                        objectives[selectedObjective].tryToComplete(currentPlayer);
                    }
                }
                System.out.println("=============================");
                currentPlayer.setActionPoint(currentPlayer.getActionPoint() - 1);
            }
            if(currentPlayer.isWinning()){
                System.out.println("Player" + (PlayerNumber+1) + " Wins");
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

    //Setups
    private static void initializePlayer(){
        for(int i = 0; i < 4; ++i){
            System.out.println("Enter player" + i + "'s name.");
            String playerName = keyBoard.nextLine();
            players[i] = new Player(playerName);
        }
    }

    private static void initializeObjective(){
        //To be implemented
        objectives = new BaronNashor[3];
    }

    //get Choices
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
        int CardNumber = 0;
        for(baseCard card : player.getCardsInHand()){
            CardNumber++;
            System.out.println(CardNumber + " : " + card);
        }

        int choice = getChoice();
        if(choice < 1 || choice > CardNumber){
            System.out.println("Invalid card number");
            choice = selectHeroCard(player);
        }

        return choice;
    }

    private static int selectObjective(){
        System.out.println("Select Objective number");
        int ObjectiveNumber = 0;
        for(Objective objective : objectives){
            ObjectiveNumber++;
            System.out.println(ObjectiveNumber + " : " + objective);
        }

        int choice = getChoice();
        if(choice < 1 || choice > ObjectiveNumber){
            System.out.println("Invalid card number");
            choice = selectObjective();
        }

        return choice;
    }

    private static int selectPlayer(Player[] players){
        System.out.println("Select Player number");
        int PlayerNumber = 1;
        for(Player player : players){
            PlayerNumber++;
            System.out.println(PlayerNumber + " : "+ player.toString());
        }

        int choice = getChoice();
        if(choice < 1 || choice > PlayerNumber){
            System.out.println("Invalid card number");
            choice = selectPlayer(players);
        }

        return choice;
    }

    private static int selectHeroCard(Player Player){
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

    //Gameplay
    public static void SacrificeHero(Player player, int number){
        for(int i = 0; i < number; ++i){
            int selectedHero = selectHeroCard(player);
            if(player.boardIsEmpty()){
                break;
            }
            else if(!player.removeHeroCard(selectedHero)){
                System.out.println("Please selected NOT NULL hero");
                --i;
            }
        }
    }


    //Presets
    private static void RandomObjective(Objective[] objectiveList) {
        //To be implemented
    }
}
