package NonGui.ListOfLeader;

import NonGui.BaseEntity.LeaderCard;
import NonGui.BaseEntity.Properties.UnitClass;

public class Darius extends LeaderCard {
    public Darius() {
        super("Darius", "Noxian general who executes weakened enemies.", UnitClass.Fighter);
        setAbilityDescription("Add +2 to your roll whenever you Challenge another player.");
    }
}
