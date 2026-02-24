package NonGui.BaseEntity;

public abstract class BaseCard {
    // Fields
    private String name;
    private String flavorText;
    private String abilityDescription;
    private String type; // <-- new field

    // Constructor
    BaseCard(String name, String flavorText, String abilityDescription, String type){
        setName(name);
        setFlavorText(flavorText);
        setAbilityDescription(abilityDescription);
        setType(type);
    }

    // toString
    @Override
    public String toString() {
        return name;
    }

    // Getters & setters
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getFlavorText() { return flavorText; }
    public void setFlavorText(String flavorText) { this.flavorText = flavorText; }

    public String getAbilityDescription() { return abilityDescription; }
    public void setAbilityDescription(String abilityDescription) { this.abilityDescription = abilityDescription; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
}
