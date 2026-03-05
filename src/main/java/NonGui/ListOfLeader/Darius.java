package NonGui.ListOfLeader;

import NonGui.BaseEntity.LeaderCard;
import NonGui.BaseEntity.Properties.UnitClass;

/**
 * Represents "Darius" Leader Card.
 * <p>
 * Noxian general who executes weakened enemies.
 * Class: Fighter
 */
public class Darius extends LeaderCard {

    /**
     * Constructs a new Darius Leader Card with Fighter class and challenge bonus ability.
     */
    public Darius() {
        super(
                "Darius",
                "Noxian general who executes weakened enemies.",
                UnitClass.Fighter
        );
        setAbilityDescription("Add +2 to your roll whenever you Challenge another player.");
    }
}