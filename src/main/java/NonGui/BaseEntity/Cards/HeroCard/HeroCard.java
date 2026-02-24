package NonGui.BaseEntity.Cards.HeroCard;

import NonGui.BaseEntity.Player;
import NonGui.BaseEntity.Properties.UnitClass;
import NonGui.BaseEntity.ActionCard;
import NonGui.BaseEntity.Cards.Itemcard.ItemCard;
import NonGui.BaseEntity.Properties.haveClass;

public abstract class HeroCard extends ActionCard implements haveClass {
    private UnitClass heroClass;
    private ItemCard Item;

    public HeroCard(){
        super("Dummy Hero", "For Demacia!!!", "No Ability, Pure POWER", "Hero Card");
        setItem(null);
    }

    public HeroCard(String name, String flavorText, String abilityDescription, UnitClass heroClass){
        super(name, flavorText, abilityDescription, "Hero Card");
        setItem(null);
        setUnitClass(heroClass);
    }

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

    public ItemCard getItem() { return Item; }
    public void setItem(ItemCard item) { Item = item; }

    @Override
    public UnitClass getUnitClass() { return heroClass; }
    @Override
    public void setUnitClass(UnitClass unitClass) { this.heroClass = unitClass; }
}
