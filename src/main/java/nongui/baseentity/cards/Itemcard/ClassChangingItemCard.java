package nongui.baseentity.cards.Itemcard;

import nongui.baseentity.cards.HeroCard.HeroCard;
import nongui.baseentity.properties.UnitClass;

/**
 * Represents an item card that alters a hero's class when equipped.
 * It safely stores the hero's original class and restores it when unequipped.
 */
public class ClassChangingItemCard extends ItemCard {

    // ==========================================
    // Fields
    // ==========================================
    private UnitClass oldClass;
    private UnitClass newClass;

    // ==========================================
    // Constructor
    // ==========================================

    /**
     * Constructs a new ClassChangingItemCard with the specified details.
     *
     * @param name               The name of the item.
     * @param flavorText         The lore or background text.
     * @param abilityDescription The description of the item's effect.
     * @param newClass           The new UnitClass to apply to the hero when equipped.
     */
    public ClassChangingItemCard(String name, String flavorText, String abilityDescription, UnitClass newClass) {
        super(name, flavorText, abilityDescription);
        setNewClass(newClass);
    }

    // ==========================================
    // Item Equip / Unequip Logic
    // ==========================================

    /**
     * Triggers when the item is equipped to a hero.
     * Saves the hero's current class before overwriting it with the new class.
     *
     * @param hero The hero equipping the item.
     */
    @Override
    public void onEquip(HeroCard hero) {
        setOldClass(hero.getUnitClass());
        hero.setUnitClass(newClass);
    }

    /**
     * Triggers when the item is unequipped from a hero.
     * Restores the hero's class back to its original state.
     *
     * @param hero The hero unequipping the item.
     */
    @Override
    public void onUnEquip(HeroCard hero) {
        hero.setUnitClass(oldClass);
    }

    // ==========================================
    // Getters and Setters
    // ==========================================

    public UnitClass getOldClass() { return oldClass; }
    public void setOldClass(UnitClass oldClass) { this.oldClass = oldClass; }

    public UnitClass getNewClass() { return newClass; }
    public void setNewClass(UnitClass newClass) { this.newClass = newClass; }
}