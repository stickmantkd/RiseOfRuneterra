package NonGui.ListOfLeader;

import NonGui.BaseEntity.LeaderCard;
import NonGui.BaseEntity.Properties.UnitClass;

public class Teemo extends LeaderCard {
    public Teemo() {
        super("Teemo", "Cunning scout who turns battlefields into traps.", UnitClass.Maskman);
        setAbilityDescription("Add +1 to your roll when attempting to complete an Objective.");
    }
}
