package NonGui.BaseEntity.Cards.MagicCard;

import NonGui.BaseEntity.ActionCard;
import NonGui.BaseEntity.Player;

public abstract class MagicCard extends ActionCard {
    public MagicCard(String name, String flavorText, String abilityDescription){
        super(name, flavorText, abilityDescription, "Magic Card");
    }

    @Override
    public abstract boolean playCard(Player player);
}
