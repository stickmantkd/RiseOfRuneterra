package entities.card;

public class BaseCard extends Card {
    public BaseCard() {
        super("Blank Card", "none");
    }

    public BaseCard(String name, String type) {
        super(name, type);
    }
}
