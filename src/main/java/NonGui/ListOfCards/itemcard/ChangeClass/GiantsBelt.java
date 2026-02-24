package NonGui.ListOfCards.itemcard.ChangeClass;

import NonGui.BaseEntity.Cards.Itemcard.ClassChangingItemCard;
import NonGui.BaseEntity.Properties.UnitClass;

public class GiantsBelt extends ClassChangingItemCard {
    public GiantsBelt() {
        super("Giant's Belt",
                "A belt that once held the trousers of a mountain giant.",
                "Change class to Tank",
                UnitClass.Tank
        );
    }
}