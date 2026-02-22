package NonGui.GameUtils;

import NonGui.ListOfCards.herocard.Fighter.Fiora;
import NonGui.BaseEntity.Properties.UnitClass;
import NonGui.BaseEntity.LeaderCard;
import NonGui.BaseEntity.BaseCard;


public class GenerationsUtils {


    public static BaseCard GenerateRandomCard(){
        //currently just draw a minion
        return new Fiora();
    }

    public static LeaderCard GenerateRandomLeader(){
        //currently just generate a dumbo
        return new LeaderCard("dumbo","Dumbass", UnitClass.Fighter);
    }
}
