package nongui.listofcards.itemcard.ChangeClass;

import nongui.baseentity.cards.Itemcard.ClassChangingItemCard;
import nongui.baseentity.properties.UnitClass;

/**
 * Represents the "Forbidden Idol" Item Card.
 * <p>
 * A relic that whispers ancient hymns of protection.
 * Change class to Support.
 */
public class ForbiddenIdol extends ClassChangingItemCard {

    /**
     * Constructs a new Forbidden Idol with its identity, effect text, and target class.
     */
    public ForbiddenIdol() {
        super(
                "Forbidden Idol",
                "A relic that whispers ancient hymns of protection.",
                "Change class to Support",
                UnitClass.Support
        );
    }
}