package NonGui.ListOfCards.itemcard.CurseItem;

import NonGui.BaseEntity.Cards.HeroCard.HeroCard;
import NonGui.BaseEntity.Cards.Itemcard.ItemCard;

public class DarkSeal extends ItemCard {
    public DarkSeal() {
        super("Dark Seal", "The seal drinks deeper than your victories—it drinks you.",
                "If you successfully roll to use this Hero card's effect, DISCARD a card.");
    }
    @Override public void onEquip(HeroCard hero) { /* Logic for discard on success */ }
    @Override public void onUnEquip(HeroCard hero) { /* Remove logic */ }
}
