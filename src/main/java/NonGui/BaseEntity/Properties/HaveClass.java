package NonGui.BaseEntity.Properties;

/**
 * An interface for entities that possess a specific unit class.
 * Implementing this interface ensures that the entity (such as a Hero or Leader)
 * can be categorized and interact with class-specific mechanics in the game.
 */
public interface HaveClass {

    // ==========================================
    // Methods
    // ==========================================

    /**
     * Gets the unit class of the entity.
     * @return The current UnitClass assigned to this entity.
     */
    public UnitClass getUnitClass();

    /**
     * Sets or updates the unit class of the entity.
     * @param unitClass The new UnitClass to assign.
     */
    public void setUnitClass(UnitClass unitClass);
}