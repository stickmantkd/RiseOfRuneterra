package NonGui.ListOfCards.herocard.Support;

import NonGui.BaseEntity.Cards.HeroCard.HeroCard;
import NonGui.BaseEntity.Properties.UnitClass;

public class TahmKench extends HeroCard {
    public TahmKench() {
        super(
                "Tahm Kench",
                "Call me king, call me demon.",
                "An Acquired Taste: Roll 9+. Trade hands with another player.",
                UnitClass.Support
        );
    }

    @Override
    public void useAbility() {
        //Roll 9+. Trade hands with another player.
    }
}
