package NonGui.ListOfCards.magiccard;

import NonGui.BaseEntity.Cards.MagicCard.MagicCard;
import NonGui.BaseEntity.Player;

public class ArcaneInsight extends MagicCard {
    public ArcaneInsight() {
        super(
                "Arcane Insight",
                "A sudden rush of magical knowledge fills your mind.",
                "DRAW 3 cards and DISCARD a card."
        );
    }

    @Override
    public boolean playCard(Player player) {
        System.out.println(player.getName() + " played Arcane Insight!");
        // TODO: เพิ่มลอจิก จั่วการ์ด 3 ใบ และบังคับทิ้งการ์ด 1 ใบ
        return true;
    }
}
