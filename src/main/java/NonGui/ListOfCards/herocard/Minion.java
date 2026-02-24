package NonGui.ListOfCards.herocard;

import NonGui.BaseEntity.Cards.HeroCard.HeroCard;
import NonGui.BaseEntity.Player;
import NonGui.BaseEntity.Properties.UnitClass;

public class Minion extends HeroCard {
    public Minion() {
        // name, heroClass, rollRequirement
        super("Minion",
                "I'm a minion, not Neeko",
                "Destroy A Hero Card" ,
                UnitClass.Fighter);
    }

    @Override
    public void useAbility(Player player){

    }
}
