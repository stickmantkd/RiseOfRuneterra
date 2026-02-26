package NonGui.ListOfCards.herocard.Support;

import NonGui.BaseEntity.Cards.HeroCard.HeroCard;
import NonGui.BaseEntity.Player;
import NonGui.BaseEntity.Properties.UnitClass;

public class Bard extends HeroCard {
    public Bard() {
        super(
                "Bard",
                "*Mystical chime noises*", // เสียงกระดิ่งของ Bard
                "Traveler's Call: Roll 7+. DRAW 2 cards.",
                UnitClass.Support
        );
    }

    @Override
    public void useAbility(Player player) {
        System.out.println(this.getName() + " uses Traveler's Call! " + player.getName() + " draws 2 cards.");

        player.DrawRandomCard();
        player.DrawRandomCard();
    }
}