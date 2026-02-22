package NonGui.ListOfCards.herocard.Tank;

import NonGui.BaseEntity.Cards.HeroCard.HeroCard;
import NonGui.BaseEntity.Properties.UnitClass;

public class Nautilus extends HeroCard {
    public Nautilus() {
        super(
                "Nautilus",
                "Beware the depths.",
                "Dredge Line: Roll 6+. Search the discard pile for a Modifier card and add it to your hand.",
                UnitClass.Tank
        );
    }

    @Override
    public void useAbility() {
        //Roll 6+. Search the discard pile for a Modifier card and add it to your hand.
    }
}
