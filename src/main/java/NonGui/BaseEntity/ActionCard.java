package NonGui.BaseEntity;

public abstract class ActionCard extends BaseCard{
    //fields
    private String abilityDescription;

    //constructor
    public ActionCard(){
        super("Dummy","Hi");
    }

    public ActionCard(String name, String flavorText, String abilityDescription){
        super(name, flavorText);
        setAbilityDescription(abilityDescription);
    }

    //function
    public abstract boolean playCard(Player player);


    //getters n setters
    public String getAbilityDescription() {
        return abilityDescription;
    }
    public void setAbilityDescription(String abilityDescription) {
        this.abilityDescription = abilityDescription;
    }
}
