package entities.objective;

import entities.baseObject.Objective;
import entities.baseObject.Player;

public class BaronNashor extends Objective {
    //constructors
    public BaronNashor() {
        super("Baron Nashor",
                "His barony is rather small. Just large enough to accommodate only him, really.",
                8,12);
        setRequirementDescription("have 2 Heroes");
        setPrizeDescription("get +1 Action");
        setPunishmentDescription("Sacrifice 2 Heroes");
    }

    //functions
    @Override
    public void requirement(Player player) {
        //require 2 Heroes
    }
    @Override
    public void grantPrize(Player player) {
        player.setOwnedObjective(player.getOwnedObjective()+1);

        player.setMaxActionPoint(player.getMaxActionPoint()+ 1);
        player.setActionPoint(player.getActionPoint()+1);
    }
    @Override
    public void grantPunishment(Player player) {
        //player sacrifice 2 Heroes
    }
}
