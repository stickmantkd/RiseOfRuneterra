package NonGui.ListOfCards.itemcard.CurseItem;

import NonGui.BaseEntity.Cards.HeroCard.HeroCard;
import NonGui.BaseEntity.Cards.Itemcard.ItemCard;

public class CursedDoubloon extends ItemCard {
    public CursedDoubloon() {
        super("Cursed Doubloon", "Fortune comes at a heavy price.",
                "If you successfully roll to use this Hero card's effect, DISCARD a card.");
    }
    @Override public void onEquip(HeroCard hero) { /* Logic for discard on success */ }
    @Override public void onUnEquip(HeroCard hero) { /* Remove logic */ }
}
