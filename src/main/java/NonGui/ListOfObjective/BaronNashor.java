package NonGui.ListOfObjective;

import NonGui.BaseEntity.Objective;
import NonGui.BaseEntity.Player;

import static NonGui.GameUtils.GameplayUtils.*;

public class BaronNashor extends Objective {
    //constructors
    public BaronNashor() {
        super("Baron Nashor",
                "His barony is rather small. Just large enough to accommodate only him, really.",
                8,12);
        setRequirementDescription("have 2 Heroes");
        setPrizeDescription("get +1 Action point");
        setPunishmentDescription("Sacrifice 2 Heroes");
    }

    //functions
    @Override
    public void grantPrize(Player player) {
        System.out.println("You've conquer the Baron Nashor : maxAP + 1");
        player.setOwnedObjective(player.getOwnedObjective()+1);

        player.setMaxActionPoint(player.getMaxActionPoint()+ 1);
        player.setActionPoint(player.getActionPoint()+1);
    }
    @Override
    public void grantPunishment(Player player) {
        System.out.println("You failed : Sacrifice 2 Heroes");
        SacrificeHero(player,2);
    }
}
