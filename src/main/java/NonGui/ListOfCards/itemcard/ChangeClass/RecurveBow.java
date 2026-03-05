package NonGui.ListOfCards.itemcard.ChangeClass;

import NonGui.BaseEntity.Cards.Itemcard.ClassChangingItemCard;
import NonGui.BaseEntity.Properties.UnitClass;

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