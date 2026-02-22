package NonGui.BaseEntity.Cards.MagicCard;

import NonGui.BaseEntity.BaseCard;
import NonGui.BaseEntity.Player;

public abstract class MagicCard extends BaseCard {
    public MagicCard(String name, String flavorText, String abilityDescription){
        super(name,flavorText,abilityDescription);
    }

    @Override
    public abstract boolean playCard(Player player);
}
