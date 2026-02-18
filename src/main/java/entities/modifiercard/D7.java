package entities.modifiercard;

import entities.basecard.ModifierCard;

public class D7 extends ModifierCard {
    public D7() {
        // name, optionA, optionB
        super("D7", 7, -7);
    }

    @Override
    public String toString() {
        return getType() + ": " + getName() + " (Options: " + getOptionA() + " / " + getOptionB() + ")";
    }
}
