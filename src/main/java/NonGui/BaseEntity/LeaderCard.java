package NonGui.BaseEntity;

import NonGui.BaseEntity.Properties.haveClass;
import NonGui.BaseEntity.Properties.UnitClass;

public class LeaderCard implements haveClass {
    //Fields
    private String name;
    private String flavorText;
    private UnitClass leaderClass;
    private String abilityDescription;

    //constructors
    public LeaderCard(String name, String flavorText, UnitClass heroClass){
        setName(name);
        setFlavorText(flavorText);
        setUnitClass(heroClass);
    }

    //functions


    //getters and setters

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getFlavorText() {
        return flavorText;
    }
    public void setFlavorText(String flavorText) {
        this.flavorText = flavorText;
    }

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
}
