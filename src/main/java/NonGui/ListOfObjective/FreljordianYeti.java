package NonGui.ListOfObjective;

import NonGui.BaseEntity.Objective;
import NonGui.BaseEntity.Player;

public class FreljordianYeti extends Objective {
    public FreljordianYeti() {
        super("Freljordian Yeti", "A massive, relentless beast from the Howling Abyss.", 8, 12);
        setRequirementDescription("Requires 1 Hero and 1 Marksman.");
        setPrizeDescription("Each time you DRAW an Item card, you may play it immediately.");
        setPunishmentDescription("DISCARD 2 cards.");
    }

    @Override
    public void grantPrize(Player player) {
        System.out.println("Slayed Freljordian Yeti! " + getPrizeDescription());
        // TODO: Implement logic to allow playing an Item card immediately upon drawing
    }

    @Override
    public void grantPunishment(Player player) {
        System.out.println("The yeti stomps the ground and freezes you! " + getPunishmentDescription());
        // TODO: Implement logic to force the player to discard 2 cards
    }
}