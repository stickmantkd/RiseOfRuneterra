package NonGui.GameUtils;

import NonGui.BaseEntity.Objective;
import NonGui.BaseEntity.Properties.UnitClass;
import NonGui.BaseEntity.LeaderCard;
import NonGui.BaseEntity.ActionCard;
import NonGui.ListOfCards.herocard.Assassin.Akali;
import NonGui.ListOfObjective.BaronNashor;


public class GenerationsUtils {
    public static Objective generateRandomObjective(){
        //To be implemented
        return (new BaronNashor());
    }

    public static ActionCard GenerateRandomCard(){
        //currently just draw a minion
        return new Akali();
    }

    public static LeaderCard GenerateRandomLeader(){
        //currently just generate a dumbo
        return new LeaderCard("dumbo","Dumbass", UnitClass.Fighter);
    }
}
