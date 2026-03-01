package NonGui.GameUtils;

import NonGui.BaseEntity.BaseCard;
import NonGui.BaseEntity.Objective;
import NonGui.ListOfCards.itemcard.BlueBuff;
import NonGui.ListOfCards.itemcard.SnakesEmbrace;
import NonGui.ListOfCards.herocard.Maskman.Ezreal;
import NonGui.BaseEntity.Properties.UnitClass;
import NonGui.BaseEntity.LeaderCard;
import NonGui.ListOfCards.magiccard.HowlingGale;
import NonGui.ListOfCards.magiccard.PickACard;
import NonGui.ListOfObjective.BaronNashor;
import NonGui.ListOfObjective.TestObjective;

import java.util.Random;


public class GenerationsUtils {
    public static Objective generateRandomObjective(){
        //To be implemented
        return (new TestObjective());
    }

    public static BaseCard generateRandomCard(){
        //currently just draw a minion
        int rand = new Random().nextInt(5);
        return switch (rand) {
            case 0 -> new BlueBuff();
            case 1 -> new SnakesEmbrace();
            case 2 -> new HowlingGale();
            case 3 -> new PickACard();
            default -> new Ezreal();
        };
    }

    public static LeaderCard generateRandomLeader(){
        //currently just generate a dumbo
        return new LeaderCard("dumbo","Dumbass", UnitClass.Fighter);
    }
}