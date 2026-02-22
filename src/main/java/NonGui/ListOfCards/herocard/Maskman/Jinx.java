package NonGui.ListOfCards.herocard.Maskman;

import NonGui.BaseEntity.Cards.HeroCard.HeroCard;
import NonGui.BaseEntity.Properties.UnitClass;

public class Jinx extends HeroCard {

    public Jinx(){
        super(
                "Jinx",
                "Rules are made to be broken... like buildings! Or people!",
                "Get Excited!: Roll 10+. DRAW cards until you have 7 cards in your hand.",
                UnitClass.Maskman
        );
    }

    @Override
    public void useAbility() {
        //Roll 10+. DRAW cards until you have 7 cards in your hand.
    }
}
