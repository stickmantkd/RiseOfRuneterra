package NonGui.ListOfCards.herocard.Fighter;

import NonGui.BaseEntity.Cards.HeroCard.HeroCard;
import NonGui.BaseEntity.Properties.UnitClass;

public class Olaf extends HeroCard {

    public Olaf(){
        super(
                "Olaf",
                "Leave nothing behind!",
                "Ragnarok: Roll 10+. DISCARD up to 3 cards. For each card discarded, DESTROY a Hero card.",
                UnitClass.Fighter
        );
    }

    @Override
    public void useAbility() {
        //Roll 10+. DISCARD up to 3 cards. For each card discarded, DESTROY a Hero card.
    }
}
