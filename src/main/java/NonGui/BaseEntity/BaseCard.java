package NonGui.BaseEntity;

public abstract class BaseCard {
    //Fields
    private String name;
    private String flavorText;

    //constructor
    BaseCard(String name, String flavorText){
        setName(name);
        setFlavorText(flavorText);
    }

    //to string
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
}
