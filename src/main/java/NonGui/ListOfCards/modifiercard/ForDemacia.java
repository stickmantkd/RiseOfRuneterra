package NonGui.ListOfCards.modifiercard;

import NonGui.BaseEntity.Cards.ModifierCard.ModifierCard;

/**
 * Represents the "For Demacia" Modifier Card.
 * <p>
 * "Stand together. Strike as one."
 * Effect: Provides a significant positive modifier to a roll.
 */
public class ForDemacia extends ModifierCard {

    /**
     * Constructs a new For Demacia card with its identity and modifier values.
     */
    public ForDemacia() {
        super(
                "For Demacia",
                "Stand together. Strike as one.",
                4,
                0
        );
    }
}