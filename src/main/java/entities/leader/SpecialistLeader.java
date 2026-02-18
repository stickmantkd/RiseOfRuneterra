package entities.leader;

import entities.card.LeaderCard;

public class SpecialistLeader extends LeaderCard {
    public SpecialistLeader() {
        super("SpecialistLeader", "Specialist");
    }

    @Override
    public void activateSkill() {
        System.out.println("Bard inspires: once per turn, reduce another player’s roll by 1.");
    }
}
