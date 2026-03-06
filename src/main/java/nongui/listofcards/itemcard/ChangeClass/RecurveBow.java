package nongui.listofcards.itemcard.ChangeClass;

import nongui.baseentity.cards.Itemcard.ClassChangingItemCard;
import nongui.baseentity.properties.UnitClass;

/**
 * Represents the "Recurve Bow" Item Card.
 * <p>
 * Tightly strung and ready to whistle through the air.
 * Change class to Marksman.
 */
public class RecurveBow extends ClassChangingItemCard {

    /**
     * Constructs a new Recurve Bow with its identity, effect text, and target class.
     */
    public RecurveBow() {
        super(
                "Recurve Bow",
                "Tightly strung and ready to whistle through the air.",
                "Change class to Marksman",
                UnitClass.Maskman
        );
    }
}