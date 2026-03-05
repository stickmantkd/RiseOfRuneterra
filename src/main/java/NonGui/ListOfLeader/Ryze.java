package NonGui.ListOfLeader;

import NonGui.BaseEntity.LeaderCard;
import NonGui.BaseEntity.Properties.UnitClass;

public class Ryze extends LeaderCard {
    public Ryze() {
        super("Ryze", "Ancient archmage who safeguards the World Runes.", UnitClass.Mage);
        setAbilityDescription("Every time you play a Magic card, DRAW 1 card from the deck.");
    }
}
