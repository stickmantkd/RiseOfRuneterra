package NonGui.ListOfCards.herocard.Maskman;

import NonGui.BaseEntity.Cards.HeroCard.HeroCard;
import NonGui.BaseEntity.Properties.UnitClass;

public class Caitlyn extends HeroCard {

    public Caitlyn(){
        super(
                "Caitlyn",
                "Meet the long gun of the law.",
                "Ace in the Hole: Roll 9+. DESTROY a Hero card and DRAW a card.",
                UnitClass.Maskman
        );
    }

    @Override
    public void useAbility() {
        //Roll 9+. DESTROY a Hero card and DRAW a card.
    }
}
