package NonGui.ListOfObjective;

import NonGui.BaseEntity.Objective;
import NonGui.BaseEntity.Player;

public class GreaterMurkWolf extends Objective {
    public GreaterMurkWolf() {
        super("Greater Murk Wolf", "A vicious, two-headed apex predator stalking the jungle.", 8, 12);
        setRequirementDescription("Requires 1 Hero and 1 Mage.");
        setPrizeDescription("Each time you DRAW a Magic card, you may play it immediately.");
        setPunishmentDescription("DISCARD 2 cards.");
    }

    @Override
    public void grantPrize(Player player) {
        System.out.println("Slayed Greater Murk Wolf! " + getPrizeDescription());
        // TODO: Implement logic to allow playing a Magic card immediately upon drawing
    }

    @Override
    public void grantPunishment(Player player) {
        System.out.println("Bitten by the ferocious wolves! " + getPunishmentDescription());
        // TODO: Implement logic to force the player to discard 2 cards
    }
}