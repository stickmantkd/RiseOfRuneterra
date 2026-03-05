package NonGui.ListOfCards.itemcard.ChangeClass;

import NonGui.BaseEntity.Cards.Itemcard.ClassChangingItemCard;
import NonGui.BaseEntity.Properties.UnitClass;

/**
 * Represents the "Serrated Dirk" Item Card.
 * <p>
 * Sharp enough to cut through shadows themselves.
 * Change class to Assassin.
 */
public class SerratedDirk extends ClassChangingItemCard {

    /**
     * Constructs a new Serrated Dirk with its identity, effect text, and target class.
     */
    public SerratedDirk() {
        super(
                "Serrated Dirk",
                "Sharp enough to cut through shadows themselves.",
                "Change class to Assassin",
                UnitClass.Assassin
        );
    }
}