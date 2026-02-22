package NonGui.ListOfCards.herocard.Support;

import NonGui.BaseEntity.Cards.HeroCard.HeroCard;
import NonGui.BaseEntity.Properties.UnitClass;

public class Neeko extends HeroCard {
    public Neeko() {
        super(
                "Neeko",
                "Neeko is not a sad tomato. She is a strong tomato!",
                "Inherent Glamour: Roll 6+. Choose a player. STEAL a Hero from that player and move this card to their Party.",
                UnitClass.Support
        );
    }

    @Override
    public void useAbility() {
        //Roll 6+. Choose a player. STEAL a Hero from that player and move this card to their Party.
    }
}