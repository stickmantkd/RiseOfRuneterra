package NonGui.ListOfCards.modifiercard;

import NonGui.BaseEntity.Cards.ModifierCard.ModifierCard;

/**
 * Represents the "Flash Freeze" Modifier Card.
 * <p>
 * "Frozen steel strikes no blow."
 * Effect: Provides a minor positive modifier or a significant negative modifier.
 */
public class FlashFreeze extends ModifierCard {

    /**
     * Constructs a new Flash Freeze card with its identity and modifier values.
     */
    public FlashFreeze() {
        super(
                "Flash Freeze",
                "Frozen steel strikes no blow.",
                1,
                -3
        );
    }
}