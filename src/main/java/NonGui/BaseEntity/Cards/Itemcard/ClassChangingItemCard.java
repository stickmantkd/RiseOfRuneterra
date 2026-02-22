package NonGui.BaseEntity.Cards.Itemcard;

import NonGui.BaseEntity.Cards.HeroCard.HeroCard;
import NonGui.BaseEntity.Properties.UnitClass;

public class ClassChangingItemCard extends ItemCard{
    //Fields
    private UnitClass oldClass;
    private UnitClass newClass;

    //constructor
    public ClassChangingItemCard(String name,
                                 String flavorText,
                                 String AbilityDescription,
                                 UnitClass newClass){
        super(name, flavorText, AbilityDescription);
        setNewClass(newClass);
    }

    //Functions
    @Override
    public void onEquip(HeroCard hero) {
        setOldClass(hero.getUnitClass());
        hero.setUnitClass(newClass);
    }
    @Override
    public void onUnEquip(HeroCard hero) {
        hero.setUnitClass(oldClass);
    }

    public UnitClass getOldClass() {
        return oldClass;
    }
    public void setOldClass(UnitClass oldClass) {
        this.oldClass = oldClass;
    }

    public UnitClass getNewClass() {
        return newClass;
    }
    public void setNewClass(UnitClass newClass) {
        this.newClass = newClass;
    }
}
