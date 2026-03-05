package NonGui.BaseEntity;

/**
 * An abstract base class for cards that can be actively played by a player to perform an action.
 * Any card that has an immediate playable effect (e.g., MagicCard, ItemCard, ModifierCard) should extend this class.
 */
public abstract class ActionCard extends BaseCard {

    // ==========================================
    // Constructor
    // ==========================================

    /**
     * Constructs a new ActionCard with the specified details.
     * This constructor is protected because ActionCard is an abstract class.
     *
     * @param name               The name of the action card.
     * @param flavorText         The lore or story text associated with the card.
     * @param abilityDescription The description of the card's mechanical effect.
     * @param type               The category/type of the card.
     */
    protected ActionCard(String name, String flavorText, String abilityDescription, String type) {
        super(name, flavorText, abilityDescription, type);
    }

    // ==========================================
    // Core Methods
    // ==========================================

    /**
     * Executes the specific logic or effect of this card when played.
     * Subclasses must implement this method to define what happens when the card is used.
     *
     * @param player The player who is attempting to play the card.
     * @return {@code true} if the card was played successfully (e.g., conditions met, effect resolved),
     * {@code false} if the action was canceled, failed, or blocked (e.g., insufficient AP, canceled via Challenge).
     */
    public abstract boolean playCard(Player player);
}