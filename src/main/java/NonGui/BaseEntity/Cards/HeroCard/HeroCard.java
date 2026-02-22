package NonGui.BaseEntity.Cards.HeroCard;

import NonGui.BaseEntity.Properties.*;
import NonGui.BaseEntity.baseCard;
import NonGui.BaseEntity.Cards.Itemcard.*;

public abstract class HeroCard extends baseCard implements haveClass {
    //Fields
    private UnitClass heroClass;
    private String abilityDescription;
    private ItemCard Item;

    //Constructor
    public HeroCard(){
        super("Dummy Hero", "For Demacia!!!");
        setItem(null);
        setAbilityDescription("No Ability, Pure POWER");
    }

    public HeroCard(String name, String flavorText, UnitClass heroClass){
        super(name,flavorText);
        setItem(null);
        setUnitClass(heroClass);
    }

    //Functions
    public boolean EquipItem(ItemCard Item){
        if(getItem() != null) return false;
        setItem(Item);
        Item.enableAbility(this);
        return true;
    }

    public boolean unEquipItem(){
        if(getItem() == null) return false;
        Item.disableAbility(this);
        setItem(null);
        return true;
    }

    public abstract void useAbility();

    //getters n setters
    public void setAbilityDescription(String description){
        this.abilityDescription = description;
    }
    public String getAbilityDescription(){
        return abilityDescription;
    }
    public ItemCard getItem() {return Item;}

    public void setItem(ItemCard item) {
        Item = item;
    }

    @Override
    public UnitClass getUnitClass() {
        return heroClass;
    }

    @Override
    public void setUnitClass(UnitClass unitClass) {
        this.heroClass = unitClass;
    }
}
