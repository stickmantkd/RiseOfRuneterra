package nongui.baseentity;

/**
 * The abstract base class for all cards in the game.
 * Provides the fundamental properties that every card shares, such as name, flavor text, ability description, and card type.
 * Classes like HeroCard, ItemCard, and MagicCard should inherit from this class.
 */
public abstract class BaseCard {

    // ==========================================
    // Fields
    // ==========================================
    private String name;
    private String flavorText;
    private String abilityDescription;
    private String type;

    // ==========================================
    // Constructor
    // ==========================================

    /**
     * Constructs a new BaseCard with the specified details.
     * This constructor is protected because BaseCard is abstract and should only be called by its subclasses.
     * * @param name               The name of the card.
     * @param flavorText         The lore or story text associated with the card.
     * @param abilityDescription The description of the card's mechanical effect in the game.
     * @param type               The category/type of the card (e.g., "Hero", "Item", "Magic", "Modifier").
     */
    protected BaseCard(String name, String flavorText, String abilityDescription, String type) {
        this.setName(name);
        this.setFlavorText(flavorText);
        this.setAbilityDescription(abilityDescription);
        this.setType(type);
    }

    // ==========================================
    // Core Methods
    // ==========================================

    /**
     * Returns the string representation of the card, which is its name.
     * Useful for displaying the card in UI lists or debugging.
     * * @return The name of the card.
     */
    @Override
    public String toString() {
        return name;
    }

    // ==========================================
    // Getters and Setters
    // ==========================================

    /**
     * Gets the name of the card.
     * @return The card's name.
     */
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    /**
     * Gets the flavor text (lore) of the card.
     * @return The flavor text.
     */
    public String getFlavorText() { return flavorText; }
    public void setFlavorText(String flavorText) { this.flavorText = flavorText; }

    /**
     * Gets the ability description of the card.
     * @return The text describing the card's effect.
     */
    public String getAbilityDescription() { return abilityDescription; }
    public void setAbilityDescription(String abilityDescription) { this.abilityDescription = abilityDescription; }

    /**
     * Gets the type category of the card.
     * @return The type of the card.
     */
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
}