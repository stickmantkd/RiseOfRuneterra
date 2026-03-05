package NonGui.ListOfLeader;

import NonGui.BaseEntity.LeaderCard;
import NonGui.BaseEntity.Properties.UnitClass;

/**
 * Represents "Teemo" Leader Card.
 * <p>
 * Cunning scout who turns battlefields into traps.
 * Class: Marksman
 */
public class Teemo extends LeaderCard {

    /**
     * Constructs a new Teemo Leader Card with Marksman class and objective bonus ability.
     */
    public Teemo() {
        super(
                "Teemo",
                "Cunning scout who turns battlefields into traps.",
                UnitClass.Maskman
        );
        setAbilityDescription("Add +1 to your roll when attempting to complete an Objective.");
    }
}