package NonGui.ListOfCards.herocard;

import NonGui.BaseEntity.Cards.HeroCard.HeroCard;
import NonGui.BaseEntity.Properties.UnitClass;

public class Minion extends HeroCard {
    public Minion() {
        // name, heroClass, rollRequirement
        super("Minion", "I'm a minion, not Neeko", UnitClass.Fighter);
        setAbilityDescription("Destroy 1 hero card");
    }

    @Override
    public void useAbility(){

    }
}
