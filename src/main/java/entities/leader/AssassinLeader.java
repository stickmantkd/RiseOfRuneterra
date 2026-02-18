package entities.leader;

import entities.card.LeaderCard;

public class AssassinLeader extends LeaderCard {
    public AssassinLeader() {
        super("AssassinLeader", "Assassin");
    }

    @Override
    public void activateSkill() {
        System.out.println("Yasuo strikes twice: reroll once per failed attack.");
    }
}
