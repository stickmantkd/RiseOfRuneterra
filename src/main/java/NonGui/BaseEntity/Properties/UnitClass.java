package NonGui.BaseEntity.Properties;

/**
 * Represents the various character classes or roles available in the game.
 * These classes determine the playstyle, synergies, and requirements for Heroes, Leaders, and Objectives.
 */
public enum UnitClass {

    // ==========================================
    // Enum Constants
    // ==========================================

    /** A class focused on high burst damage and eliminating priority targets. */
    Assassin,

    /** A balanced melee class capable of dealing consistent damage and taking hits. */
    Fighter,

    /** A magic-wielding class specializing in powerful spells and area-of-effect damage. */
    Mage,

    /** A ranged physical damage dealer class. */
    Maskman,

    /** A utility class focused on healing, buffing allies, or disrupting enemies. */
    Support,

    /** A highly durable class designed to absorb damage and protect the team. */
    Tank
}