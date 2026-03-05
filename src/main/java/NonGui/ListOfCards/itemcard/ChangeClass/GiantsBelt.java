package NonGui.ListOfCards.itemcard.ChangeClass;

import NonGui.BaseEntity.Cards.Itemcard.ClassChangingItemCard;
import NonGui.BaseEntity.Properties.UnitClass;

/**
 * Represents the "Giants Belt" Item Card.
 * <p>
 * A belt that once held the trousers of a mountain giant.
 * Change class to Tank.
 */
public class GiantsBelt extends ClassChangingItemCard {

    /**
     * Constructs a new Giants Belt with its identity, effect text, and target class.
     */
    public GiantsBelt() {
        super(
                "Giants Belt",
                "A belt that once held the trousers of a mountain giant.",
                "Change class to Tank",
                UnitClass.Tank
        );
    }
}