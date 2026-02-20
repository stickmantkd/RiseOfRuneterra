package entities.baseObject.baseCards.HeroCard;

import entities.baseObject.baseCards.Properties.haveClass;
import entities.baseObject.Player;
import entities.baseObject.baseEntity;

import java.io.IOException;

public abstract class HeroCard extends baseEntity implements haveClass {
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
    public abstract void useAbility(Player[] PlayerList) throws IOException;

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
