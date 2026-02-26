package NonGui.BaseEntity.Cards.HeroCard;

import NonGui.BaseEntity.Player;
import NonGui.BaseEntity.Properties.*;
import NonGui.BaseEntity.ActionCard;
import NonGui.BaseEntity.Cards.Itemcard.*;

import static NonGui.GameUtils.DiceUtils.rollForAbility;
import static NonGui.GameUtils.TriggerUtils.challengeUtils.ChallengerWin;

public abstract class HeroCard extends ActionCard implements haveClass {
    //Fields
    private UnitClass heroClass;
    private ItemCard Item;
    private boolean canUseAbility;
    private int rollTarget;

    //Constructor
    public HeroCard(){
        super("Dummy Hero", "For Demacia!!!","No Ability, Pure POWER");
        setItem(null);
    }

    public HeroCard(String name, String flavorText,  String abilityDescription, UnitClass heroClass,int rollTarget){
        super(name,flavorText,abilityDescription);
        setItem(null);
        setUnitClass(heroClass);
        setCanUseAbility(true);
        setRollTarget(rollTarget);
    }

    //On Play
    @Override
    public boolean playCard(Player player) {
        HeroCard[] ownedHero = player.getOwnedHero();
        for (int i = 0; i < ownedHero.length; i++) {
            if (ownedHero[i] == null) {
                System.out.println("Does anyone wanted to challenge");
                if(ChallengerWin()) return true;
                ownedHero[i] = this;
                this.tryUseAbility(player);
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

    public boolean tryUseAbility(Player player){
        if(canUseAbility && rollForAbility(this,getRollTarget())){
            useAbility(player);
            setCanUseAbility(false);
            return true;
        }
        return  false;
    }
    public abstract void useAbility(Player player);

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

    public boolean CanUseAbility() {
        return canUseAbility;
    }
    public void setCanUseAbility(boolean canUseAbility) {
        this.canUseAbility = canUseAbility;
    }

    public int getRollTarget() {
        return rollTarget;
    }

    public void setRollTarget(int rollTarget) {
        this.rollTarget = rollTarget;
    }
}
