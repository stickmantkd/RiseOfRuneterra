package NonGui.ListOfCards.itemcard.ChangeClass;

import NonGui.BaseEntity.Cards.Itemcard.ClassChangingItemCard;
import NonGui.BaseEntity.Properties.UnitClass;

/**
 * Represents the "Needlessly Large Rod" Item Card.
 * <p>
 * Pure magical power condensed into a heavy staff.
 * Change class to Mage.
 */
public class NeedlesslyLargeRod extends ClassChangingItemCard {

    /**
     * Constructs a new Needlessly Large Rod with its identity, effect text, and target class.
     */
    public NeedlesslyLargeRod() {
        super(
                "Needlessly Large Rod",
                "Pure magical power condensed into a heavy staff.",
                "Change class to Mage",
                UnitClass.Mage
        );
    }
}