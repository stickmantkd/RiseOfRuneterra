package nongui.listofcards.itemcard.CurseItem;

import nongui.baseentity.cards.HeroCard.HeroCard;
import nongui.baseentity.cards.Itemcard.ItemCard;

/**
 * Represents the "Frozen Heart" Item Card.
 * <p>
 * Its chill slows more than your enemies.
 * Each time you roll to use this Hero card's effect, -2 to your roll.
 */
public class FrozenHeart extends ItemCard {

    /**
     * Constructs a new Frozen Heart with its identity and penalty effect text.
     */
    public FrozenHeart() {
        super(
                "Frozen Heart",
                "Its chill slows more than your enemies.",
                "Each time you roll to use this Hero card's effect, -2 to your roll."
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