package nongui.listofcards.itemcard.CurseItem;

import nongui.baseentity.cards.HeroCard.HeroCard;
import nongui.baseentity.cards.Itemcard.ItemCard;

/**
 * Represents the "Abyssal Mask" Item Card.
 * <p>
 * The abyss does not protect you—it devours what remains.
 * Curse: You cannot use this Hero card's effect.
 */
public class AbyssalMask extends ItemCard {

    /**
     * Constructs a new Abyssal Mask with its identity and curse effect text.
     */
    public AbyssalMask() {
        super(
                "Abyssal Mask",
                "The abyss does not protect you—it devours what remains.",
                "You cannot use this Hero card's effect."
        );
    }

    @Override
    public void onEquip(HeroCard hero) {
        System.out.println("🌌 Abyssal Mask has silenced " + hero.getName() + "!");
        hero.setCanUseAbility(false);
    }

    @Override
    public void onUnEquip(HeroCard hero) {
        System.out.println("✨ The Abyssal Mask releases its grip on " + hero.getName());
        hero.setCanUseAbility(true);
    }
}