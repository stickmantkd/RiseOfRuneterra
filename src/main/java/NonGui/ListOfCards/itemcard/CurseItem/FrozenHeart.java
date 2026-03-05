package NonGui.ListOfCards.itemcard.CurseItem;

import NonGui.BaseEntity.Cards.HeroCard.HeroCard;
import NonGui.BaseEntity.Cards.Itemcard.ItemCard;

public class FrozenHeart extends ItemCard {
    public FrozenHeart() {
        super("Frozen Heart", "Its chill slows more than your enemies.",
                "Each time you roll to use this Hero card's effect, -2 to your roll.");
    }
    @Override public void onEquip(HeroCard hero) { /* Logic for -2 roll penalty */ }
    @Override public void onUnEquip(HeroCard hero) { /* Remove penalty */ }
}