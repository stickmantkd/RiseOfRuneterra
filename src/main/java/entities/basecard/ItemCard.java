package entities.basecard;

import entities.card.BaseCard;

public class ItemCard extends BaseCard {
    private final boolean cursed;

    public ItemCard(String name, boolean cursed) {
        super(name, "Item");
        this.cursed = cursed;
    }

    public boolean isCursed() { return cursed; }
}
