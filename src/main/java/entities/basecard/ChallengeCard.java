package entities.basecard;

import entities.card.BaseCard;

public class ChallengeCard extends BaseCard {
    public ChallengeCard() {
        super("Challenge", "Challenge");
    }

    @Override
    public String toString() {
        return getType() + ": " + getName();
    }
}
