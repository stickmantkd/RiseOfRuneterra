package NonGui.ListOfLeader;

import NonGui.BaseEntity.LeaderCard;
import NonGui.BaseEntity.Properties.UnitClass;

/**
 * Represents "Soraka" Leader Card.
 * <p>
 * Starlit guardian who heals without hesitation.
 * Class: Support
 */
public class Soraka extends LeaderCard {

    /**
     * Constructs a new Soraka Leader Card with Support class and hero activation bonus.
     */
    public Soraka() {
        super(
                "Soraka",
                "Starlit guardian who heals without hesitation.",
                UnitClass.Support
        );
        setAbilityDescription("Add +1 to your roll when activating a Hero card's ability.");
    }
}