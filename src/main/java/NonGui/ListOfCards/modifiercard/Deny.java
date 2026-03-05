package NonGui.ListOfCards.modifiercard;

import NonGui.BaseEntity.Cards.ModifierCard.ModifierCard;

/**
 * Represents the "Deny" Modifier Card.
 * <p>
 * "No."
 * Effect: A powerful modifier that significantly decreases a roll's outcome.
 */
public class Deny extends ModifierCard {

    /**
     * Constructs a new Deny card with its identity and modifier values.
     */
    public Deny() {
        super(
                "Deny",
                "\"No.\"",
                0,
                -4
        );
    }
}