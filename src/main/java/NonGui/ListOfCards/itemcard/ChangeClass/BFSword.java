package NonGui.ListOfCards.itemcard.ChangeClass;

import NonGui.BaseEntity.Cards.Itemcard.ClassChangingItemCard;
import NonGui.BaseEntity.Properties.UnitClass;

/**
 * Represents the "BFSword" Item Card.
 * <p>
 * A heavy blade that changes the equipped hero's class to Fighter.
 * "clank! clank!"
 */
public class BFSword extends ClassChangingItemCard {

    /**
     * Constructs a new BFSword with its identity, effect text, and target class.
     */
    public BFSword() {
        super(
                "BFSword",
                "clank! clank!",
                "Change class to Fighter",
                UnitClass.Fighter
        );
    }
}