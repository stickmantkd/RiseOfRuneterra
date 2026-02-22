package NonGui.ListOfObjective;

import NonGui.BaseEntity.Objective;
import NonGui.BaseEntity.Player;

public class InfernalDrake extends Objective {
    public InfernalDrake() {
        super("Infernal Drake", "The flames of war fuel your power and destruction.", 8, 12);
        setRequirementDescription("Requires 1 Hero, 1 Fighter.");
        setPrizeDescription("Each time you roll for a Challenge card, +1 to your roll.");
        setPunishmentDescription("DISCARD 2 cards.");
    }

    @Override
    public void grantPrize(Player player) {
        System.out.println("Slayed Infernal Drake! " + getPrizeDescription());
        // TODO: Implement logic to add +1 to the player's Challenge card rolls
    }

    @Override
    public void grantPunishment(Player player) {
        System.out.println("Scorched to ashes by the drake! " + getPunishmentDescription());
        // TODO: Implement logic to force the player to discard 2 cards
    }
}
