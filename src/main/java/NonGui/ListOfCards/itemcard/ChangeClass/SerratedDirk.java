package NonGui.ListOfCards.itemcard.ChangeClass;

import NonGui.BaseEntity.Cards.Itemcard.ClassChangingItemCard;
import NonGui.BaseEntity.Properties.UnitClass;

public class SerratedDirk extends ClassChangingItemCard {
    public SerratedDirk() {
        super("Serrated Dirk",
                "Sharp enough to cut through shadows themselves.",
                "Change class to Assassin",
                UnitClass.Assassin
        );
    }
}
