package NonGui.ListOfLeader;

import NonGui.BaseEntity.LeaderCard;
import NonGui.BaseEntity.Properties.UnitClass;

/**
 * Represents "Garen" Leader Card.
 * <p>
 * Commander who stands for Demacian honor.
 * Class: Tank
 */
public class Garen extends LeaderCard {

    /**
     * Constructs a new Garen Leader Card with Tank class and modifier enhancement ability.
     */
    public Garen() {
        super(
                "Garen",
                "Commander who stands for Demacian honor.",
                UnitClass.Tank
        );
        setAbilityDescription("When playing a Modifier card, you may increase its bonus by +1 or its penalty by -1.");
    }
}