package entities.baseObject.baseCards.LeaderCard;

import entities.baseObject.baseCards.HeroCard.UnitClass;
import entities.baseObject.baseCards.Properties.haveClass;
import entities.baseObject.baseEntity;

public class LeaderCard extends baseEntity implements haveClass {
    //Fields
    private UnitClass leaderClass;
    private String abilityDescription;
    private Specialties specialties;

    //constructors
    public LeaderCard(String name, String flavorText, UnitClass heroClass){
        super(name,flavorText);
        setUnitClass(heroClass);
    }

    //functions


    //getters and setters
    @Override
    public UnitClass getUnitClass() {
        return leaderClass;
    }
    @Override
    public void setUnitClass(UnitClass unitClass) {
        this.leaderClass = unitClass;
    }

    public String getAbilityDescription() {
        return abilityDescription;
    }
    public void setAbilityDescription(String abilityDescription) {
        this.abilityDescription = abilityDescription;
    }

    public Specialties getSpecialties() {
        return specialties;
    }
    public void setSpecialties(Specialties specialties) {
        this.specialties = specialties;
    }
}
