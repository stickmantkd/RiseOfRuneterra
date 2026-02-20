package entities.Cards.herocard;

import entities.GameLogic.GameUtils;
import entities.baseObject.Player;
import entities.baseObject.baseCards.HeroCard.HeroCard;
import entities.baseObject.baseCards.HeroCard.UnitClass;
import entities.GameLogic.GameUtils;

import java.io.IOException;

public class Minion extends HeroCard {
    public Minion() {
        // name, heroClass, rollRequirement
        super("Minion", "I'm a minion, not Neeko", UnitClass.Fighter);
        setAbilityDescription("Destroy 1 hero card");
    }



    @Override
    public void useAbility(Player[] PlayerList) throws IOException {
        GameUtils.Destroy(PlayerList);
    }
}
