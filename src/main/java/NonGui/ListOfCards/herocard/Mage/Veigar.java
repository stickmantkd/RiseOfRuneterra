package NonGui.ListOfCards.herocard.Mage;

import NonGui.BaseEntity.Cards.HeroCard.HeroCard;
import NonGui.BaseEntity.Properties.UnitClass;

public class Veigar extends HeroCard {

    public Veigar(){
        super(
                "Veigar",
                "Know that if the tables were turned, I would show you no mercy!",
                "Primordial Burst: Roll 7+. Choose a player. That player must SACRIFICE a Hero card.",
                UnitClass.Mage
        );
    }

    @Override
    public void useAbility() {
        //Roll 7+. Choose a player. That player must SACRIFICE a Hero card.
    }
}
