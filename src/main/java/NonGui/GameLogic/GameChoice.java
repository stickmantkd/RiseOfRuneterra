package NonGui.GameLogic;

import NonGui.BaseEntity.Cards.ChallengeCard.ChallengeCard;
import NonGui.BaseEntity.Cards.HeroCard.HeroCard;
import NonGui.BaseEntity.Cards.ModifierCard.ModifierCard;
import NonGui.BaseEntity.*;

import java.util.ArrayList;
import java.util.List;

import static NonGui.GameLogic.GameEngine.*;

public class GameChoice {
     static int getChoice() {
        System.out.print(">> ");
        String input = keyBoard.nextLine();
        try{
            return Integer.parseInt(input);
        }catch(NumberFormatException e){
            return -1;
        }
    }

    public static int selectCardsInHand(Player player){
        System.out.println("Select Card number");
        int CardNumber = 0;
        for(BaseCard card : player.getCardsInHand()){
            CardNumber++;
            System.out.println(CardNumber + " : " + card);
        }

        int choice = getChoice();
        if(choice < 1 || choice > CardNumber){
            System.out.println("Invalid Card number");
            choice = selectCardsInHand(player);
        }

        return choice - 1;
    }

    public static int selectObjective(){
        System.out.println("Select Objective number");
        int ObjectiveNumber = 0;
        for(Objective objective : objectives){
            ObjectiveNumber++;
            System.out.println(ObjectiveNumber + " : " + objective);
        }

        int choice = getChoice();
        if(choice < 1 || choice > ObjectiveNumber){
            System.out.println("Invalid Objective number");
            choice = selectObjective();
        }

        return choice - 1;
    }

    public static int selectPlayer(Player[] players){
        System.out.println("Select Player number");
        int PlayerNumber = 0;
        for(Player player : players){
            PlayerNumber++;
            System.out.println(PlayerNumber + " : "+ player.toString());
        }

        int choice = getChoice();
        if(choice < 1 || choice > PlayerNumber){
            System.out.println("Invalid Player number");
            choice = selectPlayer(players);
        }

        return choice - 1;
    }

    public static int selectHeroCard(Player player){
        HeroCard[] heroCards = player.getOwnedHero();
        List<Integer> validIndexes = new ArrayList<>();

        System.out.println("Select Hero card number");
        int displayNumber = 1;
        for(int i = 0; i < heroCards.length; i++){
            if(heroCards[i] != null){
                System.out.println(displayNumber + " : " + heroCards[i]);
                validIndexes.add(i); // เก็บ index จริงของ Hero
                displayNumber++;
            }
        }

        // ถ้าไม่มี Hero เลย
        if(validIndexes.isEmpty()){
            System.out.println("You don’t have any Hero to select.");
            return -1; // ป้องกัน crash
        }

        int choice = getChoice();
        if(choice < 1 || choice > validIndexes.size()){
            System.out.println("Invalid Hero number");
            return selectHeroCard(player); // ให้เลือกใหม่
        }

        return validIndexes.get(choice - 1); // คืนค่า index จริง
    }

    public static int selectModifierEffect(ModifierCard modifier){
        System.out.println("Select an effect to apply");
        System.out.println("1 : Give + " + modifier.getPositiveModifier() + " to a roll.");
        System.out.println("2 : Give - " + modifier.getNegativeModifier() + " to a roll.");

        int choice = getChoice();
        if(choice < 1 || choice > 2){
            System.out.println("Invalid Choice");
            choice = selectModifierEffect(modifier);
        }

        return choice - 1;
    }

    public static int selectedModifierCard(Player player){
        System.out.println("Select Card number");
        int CardNumber = 0;
        System.out.println(CardNumber + " : Pass");
        for(BaseCard card : player.getCardsInHand()){
            CardNumber++;
            if(card instanceof ModifierCard) System.out.println(CardNumber + " : " + card);
        }

        int choice = getChoice();
        if(choice < 0 || choice > CardNumber){
            System.out.println("Invalid Card number");
            choice = selectedModifierCard(player);
        }

        return choice - 1;
    }

    public static int selectedChallengeCard(Player player){
        System.out.println("Select Card number");
        int CardNumber = 0;
        System.out.println(CardNumber + " : Pass");
        for(BaseCard card : player.getCardsInHand()){
            CardNumber++;
            if(card instanceof ChallengeCard) System.out.println(CardNumber + " : " + card);
        }

        int choice = getChoice();
        if(choice < 0 || choice > CardNumber){
            System.out.println("Invalid Card number");
            choice = selectedChallengeCard(player);
        }

        return choice - 1;
    }
}
