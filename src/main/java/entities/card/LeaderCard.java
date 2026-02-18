package entities.card;

public abstract class LeaderCard extends Card {
    private final String leaderClass;

    public LeaderCard(String name, String leaderClass) {
        super(name, "Leader");
        this.leaderClass = leaderClass;
    }

    public String getLeaderClass() { return leaderClass; }

    // Each leader has a unique skill
    public abstract void activateSkill();
}
