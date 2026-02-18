package entities.leader;

import entities.card.LeaderCard;

public class MageLeader extends LeaderCard {
    public MageLeader() {
        super("MageLeader", "Mage");
    }

    @Override
    public void activateSkill() {
        System.out.println("Ahri charms enemies: draw a card when you play a Magic card.");
    }
}
