package NonGui.ListOfObjective;

import NonGui.BaseEntity.Objective;
import NonGui.BaseEntity.Player;

public class RiftHerald extends Objective {
    public RiftHerald() {
        super("Rift Herald", "Shelly is ready to charge and shatter their defenses.", 8, 12);
        setRequirementDescription("Requires 1 Hero and 1 Assassin.");
        setPrizeDescription("Item cards you play cannot be challenged.");
        setPunishmentDescription("DISCARD 2 cards.");
    }

    @Override
    public void grantPrize(Player player) {
        System.out.println("Slayed Rift Herald! " + getPrizeDescription());
        // TODO: Implement logic to prevent the player's Item cards from being challenged
    }

    @Override
    public void grantPunishment(Player player) {
        System.out.println("Shelly headbutts you! " + getPunishmentDescription());
        // TODO: Implement logic to force the player to discard 2 cards
    }
}
