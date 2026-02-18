package entities.herocard;

import entities.basecard.HeroCard;

public class Minion extends HeroCard {
    public Minion() {
        // name, heroClass, rollRequirement
        super("Minion", "Fighter", 5);
    }

    @Override
    public boolean attemptEffect(int roll) {
        // Minion gets no special bonus, just standard check
        return roll >= getRollRequirement();
    }
}
