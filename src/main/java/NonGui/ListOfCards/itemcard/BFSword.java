package NonGui.ListOfCards.itemcard;

import NonGui.BaseEntity.Cards.Itemcard.ClassChangingItemCard;
import NonGui.BaseEntity.Properties.UnitClass;

public class BFSword extends ClassChangingItemCard {
    public BFSword(){
        super("BFSword","clank! clank!","Change class to Fighter");
        setNewClass(UnitClass.Fighter);
    }
}
