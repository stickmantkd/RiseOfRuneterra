package NonGui.ListOfLeader;

import NonGui.BaseEntity.LeaderCard;
import NonGui.BaseEntity.Properties.UnitClass;

public class Garen extends LeaderCard {
    public Garen() {
        super("Garen", "Commander who stands for Demacian honor.", UnitClass.Tank);
        setAbilityDescription("When playing a Modifier card, you may increase its bonus by +1 or its penalty by -1.");
    }
}
