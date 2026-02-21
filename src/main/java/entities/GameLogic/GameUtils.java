package entities.GameLogic;

import entities.Cards.herocard.Minion;
import entities.baseObject.Cards.HeroCard.UnitClass;
import entities.baseObject.LeaderCard;
import entities.baseObject.baseCard;

import java.util.Objects;

public class GameUtils {
    public static boolean isSame(baseCard entity1, baseCard entity2){
        return Objects.equals(entity1.getName(), entity2.getName());
    }

    public static baseCard GenerateRandomCard(){
        //currently just draw a minion
        return new Minion();
    }

    public static LeaderCard GenerateRandomLeader(){
        //currently just generate a dumbo
        return new LeaderCard("dumbo","Dumbass", UnitClass.Fighter);
    }
}
