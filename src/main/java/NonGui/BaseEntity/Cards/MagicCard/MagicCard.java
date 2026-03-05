package NonGui.BaseEntity.Cards.MagicCard;

import NonGui.BaseEntity.ActionCard;
import NonGui.BaseEntity.Player;

/**
 * An abstract base class representing a Magic Card in the game.
 * Magic cards typically have one-time spell effects that alter the game state,
 * buff allies, or disrupt opponents when played.
 */
public abstract class MagicCard extends ActionCard {

    // ==========================================
    // Constructor
    // ==========================================

    /**
     * Constructs a new MagicCard with the specified details.
     * The card type is automatically set to "Magic Card".
     *
     * @param name               The name of the magic spell.
     * @param flavorText         The lore or incantation text of the card.
     * @param abilityDescription The description of the spell's mechanical effect.
     */
    public MagicCard(String name, String flavorText, String abilityDescription) {
        super(name, flavorText, abilityDescription, "Magic Card");
    }

    // ==========================================
    // Core Gameplay Logic
    // ==========================================

    /**
     * Executes the specific effect of the magic card when played by a player.
     * Subclasses must implement this method to define the spell's unique behavior.
     *
     * @param player The player casting the magic card.
     * @return true if the spell was successfully cast and resolved, false if canceled or blocked.
     */
    @Override
    public abstract boolean playCard(Player player);
}