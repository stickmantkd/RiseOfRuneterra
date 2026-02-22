package NonGui.ListOfCards.herocard.Mage;

import NonGui.BaseEntity.Cards.HeroCard.HeroCard;
import NonGui.BaseEntity.Properties.UnitClass;

public class Zoe extends HeroCard {

    public Zoe(){
        super(
                "Zoe",
                "More sparkles! More!",
                "Spell Thief: Roll 6+. DRAW a card. If it is a Magic card, you may play it immediately and DRAW a second card.",
                UnitClass.Mage
        );
    }

    @Override
    public void useAbility() {
        //Roll 6+. DRAW a card. If it is a Magic card, you may play it immediately and DRAW a second card.
    }
}
