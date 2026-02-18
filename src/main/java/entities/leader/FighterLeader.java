package entities.leader;

import entities.card.LeaderCard;

public class FighterLeader extends LeaderCard {
    public FighterLeader() {
        super("FighterLeader", "Fighter");
    }

    @Override
    public void activateSkill() {
        System.out.println("Darius rallies fighters: +1 to attack rolls!");
    }
}
