package NonGui.BaseEntity;

public abstract class BaseCard {
    //fields
    private String name;
    private String flavorText;
    private String abilityDescription;

    //constructor
    public BaseCard(){
        setName("Dummy");
        setFlavorText("Ello, Ello...");
    }

    public BaseCard(String name, String flavorText, String abilityDescription){
        setName(name);
        setFlavorText(flavorText);
        setAbilityDescription(abilityDescription);
    }

    //function
    public abstract boolean playCard(Player player);

    @Override
    public String toString() {
        return name;
    }

    //getters n setters
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

    public String getAbilityDescription() {
        return abilityDescription;
    }
    public void setAbilityDescription(String abilityDescription) {
        this.abilityDescription = abilityDescription;
    }
}
