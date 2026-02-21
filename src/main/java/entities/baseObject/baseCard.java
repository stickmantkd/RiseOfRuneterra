package entities.baseObject;

public class baseCard {
    //fields
    private String name;
    private String flavorText;

    //constructor
    public baseCard(){
        setName("Dummy");
        setFlavorText("Ello, Ello...");
    }

    public baseCard(String name, String flavorText){
        setName(name);
        setFlavorText(flavorText);
    }

    //function

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
