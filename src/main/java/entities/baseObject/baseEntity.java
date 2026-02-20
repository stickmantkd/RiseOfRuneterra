package entities.baseObject;

public class baseEntity {
    //fields
    private String name;
    private String flavorText;

    //constructor
    public baseEntity(){
        setName("Dummy");
        setFlavorText("Ello, Ello...");
    }

    public baseEntity(String name, String flavorText){
        setName(name);
        setFlavorText(flavorText);
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
