package nongui.listofcards.itemcard.BuffItem;

import nongui.baseentity.cards.HeroCard.HeroCard;
import nongui.baseentity.cards.Itemcard.ItemCard;

/**
 * Represents the "Blue Buff" Item Card.
 * <p>
 * A crest of ancient stone that grants infinite clarity.
 * Each time you roll to use this Hero card's effect, +2 to your roll.
 */
public class BlueBuff extends ItemCard {

    /**
     * Constructs a new Blue Buff with its identity and effect text.
     */
    public BlueBuff() {
        super(
                "Blue Buff",
                "A crest of ancient stone that grants infinite clarity.",
                "Each time you roll to use this Hero card's effect, +2 to your roll."
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