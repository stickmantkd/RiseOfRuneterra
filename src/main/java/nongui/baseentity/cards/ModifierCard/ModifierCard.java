package nongui.baseentity.cards.ModifierCard;

import nongui.baseentity.TriggerCard;

import static gui.ModifierView.selectModifierEffect;

/**
 * Represents a Modifier Card in the game.
 * This card acts as a trigger that can alter the outcome of a roll or an event
 * by applying either a positive or a negative modifier based on the player's choice.
 */
public class ModifierCard extends TriggerCard {

    // ==========================================
    // Fields
    // ==========================================
    private int positiveModifier;
    private int negativeModifier;

    // ==========================================
    // Constructors
    // ==========================================

    /**
     * Default constructor for ModifierCard.
     * Initializes the card with default positive (+2) and negative (-2) modifier values.
     */
    public ModifierCard() {
        super("Modifier Card", "Can grant or take power!", "Choose effect", "Modifier Card");
        setPositiveModifier(2);
        setNegativeModifier(2);
    }

    /**
     * Parameterized constructor to create a specific ModifierCard.
     *
     * @param name             The name of the modifier card.
     * @param flavorText       The lore or background text.
     * @param positiveModifier The value applied for a positive effect.
     * @param negativeModifier The value applied for a negative effect.
     */
    public ModifierCard(String name, String flavorText, int positiveModifier, int negativeModifier) {
        super(name, flavorText, "Choose effect", "Modifier Card");
        setPositiveModifier(positiveModifier);
        setNegativeModifier(negativeModifier);
    }

    // ==========================================
    // Core Logic
    // ==========================================

    /**
     * Prompts the user via the GUI to choose between the positive or negative effect.
     *
     * @return The chosen modifier value (positive, negative, or 0 if canceled/invalid).
     */
    public int useModifier() {
        int selectedEffect = selectModifierEffect(this);
        return switch (selectedEffect) {
            case 0 -> positiveModifier;
            case 1 -> negativeModifier;
            default -> 0;
        };
    }

    // ==========================================
    // Getters and Setters
    // ==========================================

    public int getPositiveModifier() { return positiveModifier; }
    public void setPositiveModifier(int positiveModifier) { this.positiveModifier = positiveModifier; }

    public int getNegativeModifier() { return negativeModifier; }
    public void setNegativeModifier(int negativeModifier) { this.negativeModifier = negativeModifier; }
}