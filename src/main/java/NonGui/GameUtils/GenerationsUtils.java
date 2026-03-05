package NonGui.GameUtils;

import NonGui.BaseEntity.BaseCard;
import NonGui.BaseEntity.Objective;
import NonGui.ListOfCards.itemcard.BuffItem.BlueBuff;
import NonGui.ListOfCards.itemcard.CurseItem.FrozenHeart;
import NonGui.ListOfCards.herocard.Maskman.Ezreal;
import NonGui.BaseEntity.Properties.UnitClass;
import NonGui.BaseEntity.LeaderCard;
import NonGui.ListOfCards.magiccard.HowlingGale;
import NonGui.ListOfCards.magiccard.PickACard;

import java.util.Random;

import static NonGui.GameLogic.GameEngine.objectiveDeck;

/**
 * Utility class responsible for generating or drawing specific entities.
 * Acts as a factory for objectives, random cards (for testing), and leaders.
 */
public class GenerationsUtils {

    // ==========================================
    // Objective Generation
    // ==========================================

    /**
     * Draws the top objective from the global Objective Deck.
     * @return The first Objective in the deck, or null if the deck is empty.
     */
    public static Objective drawObjective() {
        if (objectiveDeck.isDeckEmpty()) {
            System.out.println("No objectives left in the deck!");
            return null;
        }
        // ✅ Always draw from the top of the deck (index 0)
        return objectiveDeck.getObjectiveDeck().removeFirst();
    }

    // ==========================================
    // Random Generation (Testing/Prototyping)
    // ==========================================

    /**
     * Generates a random card from a predefined subset for testing purposes.
     * @return A new instance of a BaseCard (Item, Magic, or Hero).
     */
    public static BaseCard generateRandomCard(){
        //currently just draw a minion
        int rand = new Random().nextInt(5);
        switch (rand) {
            case 0 -> {
                return new BlueBuff();
            }
            case 1 -> {
                return new FrozenHeart();
            }
            case 2 -> {
                return new HowlingGale();
            }
            case 3 -> {
                return new PickACard();
            }
            default -> {
                return new Ezreal();
            }
        }
    }

    /**
     * Generates a placeholder Leader card.
     * Used typically when a valid leader is not yet assigned.
     * @return A LeaderCard instance with placeholder "dumbo" data.
     */
    public static LeaderCard generateRandomLeader(){
        // currently just generate a placeholder for testing
        return new LeaderCard("dumbo", "Dumbass", UnitClass.Fighter);
    }
}