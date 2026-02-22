package NonGui.ListOfObjective;

import NonGui.BaseEntity.Objective;
import NonGui.BaseEntity.Player;

public class RedBrambleback extends Objective {
    public RedBrambleback() {
        super("Red Brambleback", "The Crest of Cinders empowers your strikes with burning fury.", 8, 12);
        setRequirementDescription("Requires 1 Hero and 1 Tank.");
        setPrizeDescription("Each time you DRAW a Modifier card, you may reveal it and DRAW a second card.");
        setPunishmentDescription("DISCARD 2 cards.");
    }

    @Override
    public void grantPrize(Player player) {
        System.out.println("Slayed Red Brambleback! " + getPrizeDescription());
        // TODO: Implement logic to allow an extra draw when a Modifier card is drawn
    }

    @Override
    public void grantPunishment(Player player) {
        System.out.println("You got burned by the cinders! " + getPunishmentDescription());
        // TODO: Implement logic to force the player to discard 2 cards
    }
}
