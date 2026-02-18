package entities.basecard;

import entities.card.BaseCard;

public class MagicCard extends BaseCard {
    public MagicCard(String name) {
        super(name, "Magic");
    }

    public void cast() {
        System.out.println("Casting magic: " + getName());
    }
}
