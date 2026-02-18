package entities.leader;

import entities.card.LeaderCard;

public class TankLeader extends LeaderCard {
    public TankLeader() {
        super("TankLeader", "Tank");
    }

    @Override
    public void activateSkill() {
        System.out.println("Leona shields allies: prevent one Hero from being destroyed.");
    }
}
