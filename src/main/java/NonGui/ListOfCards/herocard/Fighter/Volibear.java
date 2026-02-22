package NonGui.ListOfCards.herocard.Fighter;

import NonGui.BaseEntity.Cards.HeroCard.HeroCard;
import NonGui.BaseEntity.Properties.UnitClass;

public class Volibear extends HeroCard {

    public Volibear(){
        super(
                "Volibear",
                "I am the storm!",
                "Thundering Smash: Roll 5+. Choose a player. That player must DISCARD 2 cards.",
                UnitClass.Fighter
        );
    }

    @Override
    public void useAbility() {

    }
}
