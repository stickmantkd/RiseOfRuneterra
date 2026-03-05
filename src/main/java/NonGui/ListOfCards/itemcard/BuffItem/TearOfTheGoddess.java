package NonGui.ListOfCards.itemcard.BuffItem;

import NonGui.BaseEntity.Cards.HeroCard.HeroCard;
import NonGui.BaseEntity.Cards.Itemcard.ItemCard;

/**
 * Represents the "Tear of the Goddess" Item Card.
 * <p>
 * A relic that rewards persistence with wisdom.
 * If you unsuccessfully roll to use this Hero card's effect, DRAW a card.
 */
public class TearOfTheGoddess extends ItemCard {

    /**
     * Constructs a new Tear of the Goddess with its identity and effect text.
     */
    public TearOfTheGoddess() {
        super(
                "Tear of the Goddess",
                "A relic that rewards persistence with wisdom.",
                "If you unsuccessfully roll to use this Hero card's effect, DRAW a card."
        );
    }

    @Override
    public void onEquip(HeroCard hero) {
        // Empty by design
    }

    @Override
    public void onUnEquip(HeroCard hero) {
        // Empty by design
    }
}