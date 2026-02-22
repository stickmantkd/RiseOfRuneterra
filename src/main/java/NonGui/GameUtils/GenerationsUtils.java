package NonGui.GameUtils;

import NonGui.BaseEntity.Objective;
import NonGui.ListOfCards.herocard.Minion;
import NonGui.BaseEntity.Properties.UnitClass;
import NonGui.BaseEntity.LeaderCard;
import NonGui.BaseEntity.BaseCard;
import NonGui.ListOfObjective.BaronNashor;


public class GenerationsUtils {
    public static Objective generateRandomObjective(){
        //To be implemented
        return (new BaronNashor());
    }

    public static BaseCard GenerateRandomCard(){
        //currently just draw a minion
        return new Minion();
    }

    public static LeaderCard GenerateRandomLeader(){
        //currently just generate a dumbo
        return new LeaderCard("dumbo","Dumbass", UnitClass.Fighter);
    }
}
