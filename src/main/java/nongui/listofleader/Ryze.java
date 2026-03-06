package nongui.listofleader;

import nongui.baseentity.LeaderCard;
import nongui.baseentity.properties.UnitClass;

/**
 * Represents "Ryze" Leader Card.
 * <p>
 * Ancient archmage who safeguards the World Runes.
 * Class: Mage
 */
public class Ryze extends LeaderCard {

    /**
     * Constructs a new Ryze Leader Card with Mage class and magic-draw synergy.
     */
    public Ryze() {
        super(
                "Ryze",
                "Ancient archmage who safeguards the World Runes.",
                UnitClass.Mage
        );
        setAbilityDescription("Every time you play a Magic card, DRAW 1 card from the deck.");
    }
}