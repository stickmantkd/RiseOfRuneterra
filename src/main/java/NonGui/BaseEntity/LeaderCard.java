package NonGui.BaseEntity;

import NonGui.BaseEntity.Properties.HaveClass;
import NonGui.BaseEntity.Properties.UnitClass;

/**
 * Represents a Leader Card in the game.
 * Each player owns one Leader Card which dictates their starting class
 * and provides unique passive abilities or team synergies.
 */
public class LeaderCard implements HaveClass {

    // ==========================================
    // Fields
    // ==========================================
    private String name;
    private String flavorText;
    private UnitClass leaderClass;
    private String abilityDescription;

    // ==========================================
    // Constructor
    // ==========================================

    /**
     * Constructs a new LeaderCard with the specified details.
     * Note: abilityDescription can be set separately using its setter.
     *
     * @param name       The name of the leader.
     * @param flavorText The lore or background text for the leader.
     * @param unitClass  The specific class (UnitClass) this leader belongs to.
     */
    public LeaderCard(String name, String flavorText, UnitClass unitClass) {
        this.setName(name);
        this.setFlavorText(flavorText);
        this.setUnitClass(unitClass);
    }

    // ==========================================
    // Core Methods
    // ==========================================

    /**
     * Returns the string representation of the Leader Card.
     * * @return The name of the leader.
     */
    @Override
    public String toString() {
        return name;
    }

    // ==========================================
    // Getters and Setters
    // ==========================================

    /**
     * Gets the name of the leader.
     * @return The leader's name.
     */
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the flavor text (lore) of the leader.
     * @return The flavor text.
     */
    public String getFlavorText() {
        return flavorText;
    }

    public void setFlavorText(String flavorText) {
        this.flavorText = flavorText;
    }

    /**
     * Gets the unit class of this leader.
     * Overridden from the haveClass interface.
     * * @return The UnitClass of the leader.
     */
    @Override
    public UnitClass getUnitClass() {
        return leaderClass;
    }

    /**
     * Sets the unit class of this leader.
     * Overridden from the haveClass interface.
     * * @param unitClass The new UnitClass to assign.
     */
    @Override
    public void setUnitClass(UnitClass unitClass) {
        this.leaderClass = unitClass;
    }

    /**
     * Gets the description of the leader's unique ability.
     * @return The ability description.
     */
    public String getAbilityDescription() {
        return abilityDescription;
    }

    public void setAbilityDescription(String abilityDescription) {
        this.abilityDescription = abilityDescription;
    }
}