package NonGui.ListOfCards.modifiercard;

import NonGui.BaseEntity.Cards.ModifierCard.ModifierCard;

/**
 * Represents the "Twin Disciplines" Modifier Card.
 * <p>
 * "The body bends, the spirit does not."
 * Effect: Provides a balanced modifier of either +2 or -2 to a roll.
 */
public class TwinDisciplines extends ModifierCard {

    /**
     * Constructs a new Twin Disciplines card with its identity and modifier values.
     */
    public TwinDisciplines() {
        super(
                "Twin Disciplines",
                "The body bends, the spirit does not.",
                2,
                -2
        );
    }
}