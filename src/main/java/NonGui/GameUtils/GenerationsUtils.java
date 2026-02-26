package NonGui.GameUtils;

import NonGui.BaseEntity.BaseCard;
import NonGui.BaseEntity.Cards.ChallengeCard.ChallengeCard;
import NonGui.BaseEntity.Objective;
import NonGui.ListOfCards.itemcard.BlueBuff;
import NonGui.ListOfCards.itemcard.SnakesEmbrace;
import NonGui.ListOfCards.magiccard.FinalSpark;
import NonGui.ListOfCards.herocard.Assassin.Akali;
import NonGui.ListOfCards.herocard.Assassin.Shaco;
import NonGui.ListOfCards.herocard.Assassin.Talon;
import NonGui.ListOfCards.herocard.Fighter.Fiora;
import NonGui.ListOfCards.herocard.Fighter.Olaf;
import NonGui.ListOfCards.herocard.Fighter.Volibear;
import NonGui.ListOfCards.herocard.Mage.Veigar;
import NonGui.ListOfCards.herocard.Mage.Zoe;
import NonGui.ListOfCards.herocard.Maskman.Ezreal;
import NonGui.ListOfCards.herocard.Minion;
import NonGui.BaseEntity.Properties.UnitClass;
import NonGui.BaseEntity.LeaderCard;
import NonGui.BaseEntity.ActionCard;
import NonGui.ListOfCards.itemcard.BFSword;
import NonGui.ListOfCards.magiccard.Charm;
import NonGui.ListOfCards.magiccard.HowlingGale;
import NonGui.ListOfCards.magiccard.PickACard;
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
            case 0 -> new BlueBuff();
            case 1 -> new SnakesEmbrace();
            //case 2 -> new HowlingGale();
            //case 3 -> new PickACard();
            default -> new Ezreal();
        };
    }

    public static LeaderCard GenerateRandomLeader(){
        //currently just generate a dumbo
        return new LeaderCard("dumbo","Dumbass", UnitClass.Fighter);
    }
}
