package NonGui.GameUtils;

import NonGui.BaseEntity.BaseCard;
import NonGui.BaseEntity.Cards.ChallengeCard.ChallengeCard;
import NonGui.BaseEntity.Objective;
import NonGui.ListOfCards.herocard.Minion;
import NonGui.BaseEntity.Properties.UnitClass;
import NonGui.BaseEntity.LeaderCard;
import NonGui.BaseEntity.ActionCard;
import NonGui.ListOfCards.itemcard.BFSword;
import NonGui.ListOfCards.magiccard.FinalSpark;
import NonGui.ListOfCards.modifiercard.ElixirOfWrath;
import NonGui.ListOfObjective.BaronNashor;

import java.security.interfaces.ECKey;
import java.util.Random;


public class GenerationsUtils {
    public static Objective generateRandomObjective(){
        //To be implemented
        return (new BaronNashor());
    }

    public static BaseCard GenerateRandomCard(){
        //currently just draw a minion
        int rand = new Random().nextInt(5);
        return switch (rand) {
            case 0 -> new Minion();
            case 1 -> new ChallengeCard();
            case 2 -> new BFSword();
            case 3 -> new ElixirOfWrath();
            default -> new FinalSpark();
        };
    }

    public static LeaderCard GenerateRandomLeader(){
        //currently just generate a dumbo
        return new LeaderCard("dumbo","Dumbass", UnitClass.Fighter);
    }
}
