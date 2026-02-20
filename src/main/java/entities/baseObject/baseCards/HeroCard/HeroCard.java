package entities.baseObject.baseCards.HeroCard;

import entities.baseObject.Properties.haveClass;
import entities.baseObject.Player;
import entities.baseObject.baseCard;

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

    //Functions
    @Override
    public void playCard(Player player) {
        //To be implemented
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
