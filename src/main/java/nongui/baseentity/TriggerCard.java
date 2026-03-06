package nongui.baseentity;

/**
 * An abstract base class for cards that trigger automatically based on specific game events or conditions.
 * Unlike ActionCards that are played actively, TriggerCards typically wait for a certain condition
 * to be met before their effects are activated.
 */
public abstract class TriggerCard extends BaseCard {

    // ==========================================
    // Constructor
    // ==========================================

    /**
     * Constructs a new TriggerCard with the specified details.
     *
     * @param name               The name of the trigger card.
     * @param flavorText         The lore or story text associated with the card.
     * @param abilityDescription The description of the card's trigger condition and its effect.
     * @param type               The category or specific type of the trigger card.
     */
    public TriggerCard(String name, String flavorText, String abilityDescription, String type) {
        super(name, flavorText, abilityDescription, type);
    }
}