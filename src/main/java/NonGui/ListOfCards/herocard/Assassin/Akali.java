package NonGui.ListOfCards.herocard.Assassin;

import NonGui.BaseEntity.Cards.HeroCard.HeroCard;
import NonGui.BaseEntity.Properties.UnitClass;

public class Akali extends HeroCard {

    public Akali(){
        super(
                "Akali",
                "Fear the assassin with no master.",
                "Perfect Execution: Roll 8+. DESTROY a Hero card, then DRAW a card.",
                UnitClass.Assassin
        );
    }

    @Override
    public void useAbility() {
        //Roll 8+. DESTROY a Hero card, then DRAW a card.
    }
}
