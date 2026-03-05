package NonGui.ListOfLeader;

import NonGui.BaseEntity.LeaderCard;
import NonGui.BaseEntity.Properties.UnitClass;

public class Soraka extends LeaderCard {
    public Soraka() {
        super("Soraka", "Starlit guardian who heals without hesitation.", UnitClass.Support);
        setAbilityDescription("Add +1 to your roll when activating a Hero card's ability.");
    }
}
