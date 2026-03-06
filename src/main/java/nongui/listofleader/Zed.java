package nongui.listofleader;

import nongui.baseentity.LeaderCard;
import nongui.baseentity.properties.UnitClass;

/**
 * Represents "Zed" Leader Card.
 * <p>
 * Supreme leader of the Order of Shadow who strikes without mercy.
 * Class: Assassin
 */
public class Zed extends LeaderCard {

    /**
     * Constructs a new Zed Leader Card with Assassin class and item-draw synergy.
     */
    public Zed() {
        super(
                "Zed",
                "supreme leader of the Order of Shadow who strikes without mercy.",
                UnitClass.Assassin
        );
        setAbilityDescription("Every time you equip an Item card to a Hero, DRAW 1 card from the deck.");
    }
}