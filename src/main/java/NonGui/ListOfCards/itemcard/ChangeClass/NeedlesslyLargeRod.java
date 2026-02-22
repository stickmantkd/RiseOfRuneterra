package NonGui.ListOfCards.itemcard.ChangeClass;

import NonGui.BaseEntity.Cards.Itemcard.ClassChangingItemCard;
import NonGui.BaseEntity.Properties.UnitClass;

public class NeedlesslyLargeRod extends ClassChangingItemCard {
    public NeedlesslyLargeRod() {
        super("Needlessly Large Rod",
                "Pure magical power condensed into a heavy staff.",
                "Change class to Mage",
                UnitClass.Mage
        );
    }
}
