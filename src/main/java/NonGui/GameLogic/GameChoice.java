package NonGui.GameLogic;

import NonGui.BaseEntity.Cards.HeroCard.HeroCard;
import NonGui.BaseEntity.Cards.ModifierCard.ModifierCard;
import NonGui.BaseEntity.Objective;
import NonGui.BaseEntity.Player;
import NonGui.BaseEntity.ActionCard;

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
        for(ActionCard card : player.getCardsInHand()){
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
        HeroCard[] heroCards =player.getOwnedHero();
        System.out.println("Select Hero card number");
        int HeroCardNumber = 0;
        for(HeroCard hero : heroCards){
            HeroCardNumber++;
            if(hero == null) continue;
            System.out.println(HeroCardNumber + " : "+ hero);
        }

        int choice = getChoice();
        if(choice < 1 || choice > HeroCardNumber){
            System.out.println("Invalid Hero number");
            choice = selectHeroCard(player);
        }

        return choice - 1;
    }

    public static int selectModifierEffect(ModifierCard modifier){
        System.out.println("Select an effect to apply");
        System.out.println("1 : Give + " + modifier.getPositiveModifier() + " to a roll.");
        System.out.println("1 : Give - " + modifier.getNegativeModifier() + " to a roll.");

        int choice = getChoice();
        if(choice < 1 || choice > 2){
            System.out.println("Invalid Choice");
            choice = selectModifierEffect(modifier);
        }

        return choice - 1;
    }
}
