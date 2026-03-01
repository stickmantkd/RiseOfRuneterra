package NonGui.ListOfObjective;

import NonGui.BaseEntity.Objective;
import NonGui.BaseEntity.Player;

public class TestObjective extends Objective {

    // Constructor
    public TestObjective() {
        super("Test Objective",
                "A simple test objective with minimal requirements.",
                0, 12); // requirementValue = 0, prizeValue = 1
        setRequirementDescription("No requirements");
        setPrizeDescription("Gain +1 Action Point");
        setPunishmentDescription("No punishment");
    }

    @Override
    public void grantPrize(Player player) {
        System.out.println("Test Objective completed: +1 Action Point");
        player.setOwnedObjective(player.getOwnedObjective()+1);
        player.setMaxActionPoint(player.getMaxActionPoint()+ 1);
        player.setActionPoint(player.getActionPoint() + 1);
    }

    @Override
    public void grantPunishment(Player player) {
        System.out.println("Test Objective failed: no punishment applied");
        // No punishment logic
    }
}
