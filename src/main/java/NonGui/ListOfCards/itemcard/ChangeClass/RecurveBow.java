package NonGui.ListOfCards.itemcard.ChangeClass;

import NonGui.BaseEntity.Cards.Itemcard.ClassChangingItemCard;
import NonGui.BaseEntity.Properties.UnitClass;

public class RecurveBow extends ClassChangingItemCard {
    public RecurveBow() {
        super("Recurve Bow",
                "Tightly strung and ready to whistle through the air.",
                "Change class to Marksman",
                UnitClass.Maskman
        );
    }
}