package NonGui.ListOfCards.herocard.Support;

import NonGui.BaseEntity.Cards.HeroCard.HeroCard;
import NonGui.BaseEntity.Properties.UnitClass;

public class Bard extends HeroCard {
    public Bard() {
        super(
                "Bard",
                "*Mystical chime noises*", // เสียงกระดิ่งของ Bard
                "Traveler's Call: Roll 7+. DRAW 2 cards.",
                UnitClass.Support
        );
    }

    @Override
    public void useAbility() {
        //Roll 7+. DRAW 2 cards.
    }
}
