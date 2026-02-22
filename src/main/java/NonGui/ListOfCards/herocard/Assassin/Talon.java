package NonGui.ListOfCards.herocard.Assassin;

import NonGui.BaseEntity.Cards.HeroCard.HeroCard;
import NonGui.BaseEntity.Properties.UnitClass;

public class Talon extends HeroCard {

    public Talon(){
        super(
                "Talon",
                "Live by the blade, die by the blade.",
                "Cutthroat: Roll 6+. Pull a card from another player's hand.",
                UnitClass.Assassin
        );
    }

    @Override
    public void useAbility() {
        //Roll 6+. Pull a card from another player's hand.
    }
}
