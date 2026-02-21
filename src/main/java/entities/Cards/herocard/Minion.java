package entities.Cards.herocard;

import entities.baseObject.Cards.HeroCard.HeroCard;
import entities.baseObject.Cards.HeroCard.UnitClass;

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
