package NonGui.ListOfCards.herocard.Mage;

import NonGui.BaseEntity.Cards.HeroCard.HeroCard;
import NonGui.BaseEntity.Player;
import NonGui.BaseEntity.Properties.UnitClass;

public class Zilean extends HeroCard {

    public Zilean() {
        super(
                "Zilean",
                "I knew you would do that.",
                "Rewind: Roll 5+. Search the discard pile for a Magic card and add it to your hand.",
                UnitClass.Mage
        );
    }

    @Override
    public void useAbility(Player player) {
        //Roll 5+. Search the discard pile for a Magic card and add it to your hand.
    }
}