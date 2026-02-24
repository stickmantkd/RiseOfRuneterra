package NonGui.ListOfObjective;

import NonGui.BaseEntity.Objective;
import NonGui.BaseEntity.Player;
import gui.board.StatusBar; // <-- import StatusBar

public class BlueSentinel extends Objective {
    public BlueSentinel() {
        super("Blue Sentinel",
                "The Crest of Insight grants you ultimate clarity and magical prowess.",
                8, 12);
        setRequirementDescription("Requires 1 Hero and 1 Support.");
        setPrizeDescription("Each time you roll for a Hero card's effect, +1 to your roll.");
        setPunishmentDescription("DISCARD 2 cards.");
    }

    @Override
    public void grantPrize(Player player) {
        // Update GUI instead of console
        StatusBar.showMessage("Slayed Blue Sentinel! " + getPrizeDescription());
        // TODO: Implement logic to add +1 to the player's Hero card effect rolls
    }

    @Override
    public void grantPunishment(Player player) {
        // Update GUI instead of console
        StatusBar.showMessage("Crushed by the stone golem! " + getPunishmentDescription());
        // TODO: Implement logic to force the player to discard 2 cards
    }
}
