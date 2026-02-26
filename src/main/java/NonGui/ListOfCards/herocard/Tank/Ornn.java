package NonGui.ListOfCards.herocard.Tank;

import NonGui.BaseEntity.Cards.HeroCard.HeroCard;
import NonGui.BaseEntity.Player;
import NonGui.BaseEntity.Properties.UnitClass;

public class Ornn extends HeroCard {
    public Ornn() {
        super(
                "Ornn",
                "I have made this. It is good.",
                "Master Craftsman: Roll 9+. +5 to all of your rolls until the end of your turn.",
                UnitClass.Tank,
                9
        );
    }

    @Override
    public void useAbility(Player player) {
        //Roll 9+. +5 to all of your rolls until the end of your turn.
    }
}