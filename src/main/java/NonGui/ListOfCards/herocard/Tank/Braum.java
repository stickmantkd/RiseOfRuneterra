package NonGui.ListOfCards.herocard.Tank;

import NonGui.BaseEntity.Cards.HeroCard.HeroCard;
import NonGui.BaseEntity.Player;
import NonGui.BaseEntity.Properties.UnitClass;

public class Braum extends HeroCard {
    public Braum() {
        super(
                "Braum",
                "Stand behind Braum!",
                "Unbreakable: Roll 8+. Cards you play cannot be challenged for the rest of your turn.",
                UnitClass.Tank
        );
    }

    @Override
    public void useAbility(Player player) {
        //Roll 8+. Cards you play cannot be challenged for the rest of your turn.
    }
}