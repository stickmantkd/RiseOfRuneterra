package entities.card;

/**
 * Root class for all card entities.
 * Provides shared properties and behavior.
 */
public abstract class Card {
    private final String name;
    private final String type;

    public Card(String name, String type) {
        this.name = name;
        this.type = type;
    }

    public String getName() { return name; }
    public String getType() { return type; }

    @Override
    public String toString() {
        return type + ": " + name;
    }
}
