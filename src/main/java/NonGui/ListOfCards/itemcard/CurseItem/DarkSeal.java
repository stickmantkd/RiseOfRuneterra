package NonGui.ListOfCards.itemcard.CurseItem;

import NonGui.BaseEntity.Cards.HeroCard.HeroCard;
import NonGui.BaseEntity.Cards.Itemcard.ItemCard;

/**
 * Represents the "Dark Seal" Item Card.
 * <p>
 * The seal drinks deeper than your victories—it drinks you.
 * If you successfully roll to use this Hero card's effect, DISCARD a card.
 */
public class DarkSeal extends ItemCard {

    /**
     * Constructs a new Dark Seal with its identity and curse effect text.
     */
    public DarkSeal() {
        super(
                "Dark Seal",
                "The seal drinks deeper than your victories—it drinks you.",
                "If you successfully roll to use this Hero card's effect, DISCARD a card."
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