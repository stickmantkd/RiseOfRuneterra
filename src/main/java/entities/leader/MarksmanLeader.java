package entities.leader;

import entities.card.LeaderCard;

public class MarksmanLeader extends LeaderCard {
    public MarksmanLeader() {
        super("MarksmanLeader", "Marksman");
    }

    @Override
    public void activateSkill() {
        System.out.println("Ashe scouts: gain +1 when attacking Monsters.");
    }
}
