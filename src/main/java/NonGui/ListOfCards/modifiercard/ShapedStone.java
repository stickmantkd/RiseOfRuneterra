package NonGui.ListOfCards.modifiercard;

import NonGui.BaseEntity.Cards.ModifierCard.ModifierCard;

/**
 * Represents the "Shaped Stone" Modifier Card.
 * <p>
 * "The desert shapes the strong."
 * Effect: Provides a strong positive modifier or a minor negative modifier.
 */
public class ShapedStone extends ModifierCard {

    /**
     * Constructs a new Shaped Stone card with its identity and modifier values.
     */
    public ShapedStone() {
        super(
                "Shaped Stone",
                "The desert shapes the strong.",
                3,
                -1
        );
    }
}