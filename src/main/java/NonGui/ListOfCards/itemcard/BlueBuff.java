package NonGui.ListOfCards.itemcard;

import NonGui.BaseEntity.Cards.HeroCard.HeroCard;
import NonGui.BaseEntity.Cards.Itemcard.ItemCard;

public class BlueBuff extends ItemCard {
    public BlueBuff() {
        super("Blue Buff", "A crest of ancient stone that grants infinite clarity.",
                "Each time you roll to use this Hero card's effect, +2 to your roll.");
    }
    @Override public void onEquip(HeroCard hero) { /* Logic for +2 roll bonus */ }
    @Override public void onUnEquip(HeroCard hero) { /* Remove bonus */ }
}
