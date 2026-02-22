package NonGui.ListOfObjective;

import NonGui.BaseEntity.Objective;
import NonGui.BaseEntity.Player;

public class BlueSentinel extends Objective {
    public BlueSentinel() {
        super("Blue Sentinel", "The Crest of Insight grants you ultimate clarity and magical prowess.", 8, 12);
        setRequirementDescription("Requires 1 Hero and 1 Support.");
        setPrizeDescription("Each time you roll for a Hero card's effect, +1 to your roll.");
        setPunishmentDescription("DISCARD 2 cards.");
    }

    @Override
    public void grantPrize(Player player) {
        System.out.println("Slayed Blue Sentinel! " + getPrizeDescription());
        // TODO: Implement logic to add +1 to the player's Hero card effect rolls
    }

    @Override
    public void grantPunishment(Player player) {
        System.out.println("Crushed by the stone golem! " + getPunishmentDescription());
        // TODO: Implement logic to force the player to discard 2 cards
    }
}
