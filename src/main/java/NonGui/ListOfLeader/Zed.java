package NonGui.ListOfLeader;

import NonGui.BaseEntity.LeaderCard;
import NonGui.BaseEntity.Properties.UnitClass;

public class Zed extends LeaderCard {
    public Zed() {
        super("Zed", "supreme leader of the Order of Shadow who strikes without mercy.", UnitClass.Assassin);
        setAbilityDescription("Every time you equip an Item card to a Hero, DRAW 1 card from the deck.");
    }
}
