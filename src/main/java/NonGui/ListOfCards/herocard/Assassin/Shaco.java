package NonGui.ListOfCards.herocard.Assassin;

import NonGui.BaseEntity.Cards.HeroCard.HeroCard;
import NonGui.BaseEntity.Player;
import NonGui.BaseEntity.Properties.UnitClass;

public class Shaco extends HeroCard {

    public Shaco(){
        super(
                "Shaco",
                "The joke's on you!",
                "Deceive: Roll 7+. STEAL an Item card equipped to another player's Hero.",
                UnitClass.Assassin
        );
    }

    @Override
    public void useAbility(Player player) {

    }
}
