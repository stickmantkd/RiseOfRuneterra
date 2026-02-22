package NonGui.ListOfCards.itemcard.CurseItem;

import NonGui.BaseEntity.Cards.HeroCard.HeroCard;
import NonGui.BaseEntity.Cards.Itemcard.ItemCard;

public class VoidBinding extends ItemCard {
    public VoidBinding() {
        super("Void Binding", "Dark shackles that silence the strongest souls.",
                "You cannot use this Hero card's effect.");
    }
    @Override public void onEquip(HeroCard hero) { /* Disable Hero effect logic */ }
    @Override public void onUnEquip(HeroCard hero) { /* Re-enable Hero effect */ }
}
