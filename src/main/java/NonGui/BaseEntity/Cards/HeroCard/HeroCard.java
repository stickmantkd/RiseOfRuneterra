package NonGui.BaseEntity.Cards.HeroCard;

import NonGui.BaseEntity.Properties.haveClass;
import NonGui.BaseEntity.baseCard;

public abstract class HeroCard extends baseCard implements haveClass {
    //Fields
    private UnitClass heroClass;
    private String abilityDescription;

    //Constructor
    public HeroCard(){
        super("Dummy Hero", "For Demacia!!!");
        setAbilityDescription("No Ability, Pure POWER");
    }

    public HeroCard(String name, String flavorText, UnitClass heroClass){
        super(name,flavorText);
        setUnitClass(heroClass);
    }

    public abstract void useAbility();

    //getters n setters
    public void setAbilityDescription(String description){
        this.abilityDescription = description;
    }
    public String getAbilityDescription(){
        return abilityDescription;
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
