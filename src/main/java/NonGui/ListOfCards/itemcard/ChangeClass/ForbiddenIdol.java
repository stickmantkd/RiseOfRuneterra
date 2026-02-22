package NonGui.ListOfCards.itemcard.ChangeClass;

import NonGui.BaseEntity.Cards.Itemcard.ClassChangingItemCard;
import NonGui.BaseEntity.Properties.UnitClass;

public class ForbiddenIdol extends ClassChangingItemCard {
    public ForbiddenIdol() {
        super("Forbidden Idol",
                "A relic that whispers ancient hymns of protection.",
                "Change class to Support",
                UnitClass.Support
        );
    }
}
