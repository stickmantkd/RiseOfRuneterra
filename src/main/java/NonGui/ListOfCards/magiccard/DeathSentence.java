package NonGui.ListOfCards.magiccard;

import NonGui.BaseEntity.Cards.MagicCard.MagicCard;
import NonGui.BaseEntity.Player;

public class DeathSentence extends MagicCard {
    public DeathSentence() {
        super(
                "Death Sentence",
                "Nowhere to hide, nowhere to run.",
                "DISCARD 2 cards, then STEAL a Hero card."
        );
    }

    @Override
    public boolean playCard(Player player) {
        System.out.println(player.getName() + " played Death Sentence!");
        // TODO: เพิ่มลอจิก ทิ้งการ์ด 2 ใบ เพื่อเลือกขโมย Hero Card จาก Player คนอื่น
        return true;
    }
}
