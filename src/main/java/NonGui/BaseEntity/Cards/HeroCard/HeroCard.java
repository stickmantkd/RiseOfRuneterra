package NonGui.BaseEntity.Cards.HeroCard;

import NonGui.BaseEntity.Player;
import NonGui.BaseEntity.Properties.*;
import NonGui.BaseEntity.ActionCard;
import NonGui.BaseEntity.Cards.Itemcard.*;

public abstract class HeroCard extends ActionCard implements haveClass {
    //Fields
    private UnitClass heroClass;
    private ItemCard Item;

    //Constructor
    public HeroCard(){
        super("Dummy Hero", "For Demacia!!!","No Ability, Pure POWER");
        setItem(null);
    }

    public HeroCard(String name, String flavorText,  String abilityDescription, UnitClass heroClass){
        super(name,flavorText,abilityDescription);
        setItem(null);
        setUnitClass(heroClass);
    }

    //On Play
    @Override
    public boolean playCard(Player player) {
        HeroCard[] ownedHero = player.getOwnedHero();
        for (int i = 0; i < ownedHero.length; i++) {
            if (ownedHero[i] == null) {
                ownedHero[i] = this;
                this.useAbility();
                player.setOwnedHero(ownedHero);
                return true;
            }
        }
        return false;
    }

    //Functions
    public boolean EquipItem(ItemCard Item){
        if(getItem() != null) return false;
        setItem(Item);
        Item.onEquip(this);
        return true;
    }

    public boolean unEquipItem(){
        if(getItem() == null) return false;
        Item.onUnEquip(this);
        setItem(null);
        return true;
    }

    public abstract void useAbility();

    //getters n setters
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
