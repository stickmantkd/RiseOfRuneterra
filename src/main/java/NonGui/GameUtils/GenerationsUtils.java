package NonGui.GameUtils;

import NonGui.ListOfCards.herocard.Minion;
import NonGui.BaseEntity.Cards.HeroCard.UnitClass;
import NonGui.BaseEntity.LeaderCard;
import NonGui.BaseEntity.baseCard;


public class GenerationsUtils {


    public static baseCard GenerateRandomCard(){
        //currently just draw a minion
        return new Minion();
    }

    public static LeaderCard GenerateRandomLeader(){
        //currently just generate a dumbo
        return new LeaderCard("dumbo","Dumbass", UnitClass.Fighter);
    }
}
