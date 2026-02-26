package NonGui.ListOfCards.herocard.Maskman;

import NonGui.BaseEntity.Cards.HeroCard.HeroCard;
import NonGui.BaseEntity.Player;
import NonGui.BaseEntity.Properties.UnitClass;

public class Ezreal extends HeroCard {

    public Ezreal() {
        super(
                "Ezreal",
                "Time for a true display of skill!",
                "Prodigal Explorer: Roll 8+. DRAW 2 cards. If at least one of these cards is an Item card, you may play one of them immediately.",
                UnitClass.Maskman
        );
    }

    @Override
    public void useAbility(Player player) {
        //Roll 8+. DRAW 2 cards. If at least one of these cards is an Item card, you may play one of them immediately.
    }
}