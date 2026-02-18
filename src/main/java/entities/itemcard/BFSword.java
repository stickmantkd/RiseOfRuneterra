package entities.itemcard;

import entities.basecard.ItemCard;

public class BFSword extends ItemCard {
    public BFSword() {
        // name, cursed flag
        super("BFSword", false);
    }

    @Override
    public String toString() {
        return getType() + ": " + getName() + " (Attack Damage +40)";
    }
}
