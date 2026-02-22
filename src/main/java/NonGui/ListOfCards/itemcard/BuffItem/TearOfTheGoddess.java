package NonGui.ListOfCards.itemcard.BuffItem;

import NonGui.BaseEntity.Cards.HeroCard.HeroCard;
import NonGui.BaseEntity.Cards.Itemcard.ItemCard;

public class TearOfTheGoddess extends ItemCard {
    public TearOfTheGoddess() {
        super("Tear of the Goddess", "A relic that rewards persistence with wisdom.",
                "If you unsuccessfully roll to use this Hero card's effect, DRAW a card.");
    }
    @Override public void onEquip(HeroCard hero) { /* Logic for draw on fail */ }
    @Override public void onUnEquip(HeroCard hero) { /* Remove logic */ }
}
