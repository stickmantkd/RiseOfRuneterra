package NonGui.ListOfCards.magiccard;

import NonGui.BaseEntity.Cards.MagicCard.MagicCard;
import NonGui.BaseEntity.Player;

public class HowlingGale extends MagicCard {
    public HowlingGale() {
        super(
                "Howling Gale",
                "The winds whisper secrets and strip away defenses.",
                "Return an Item card equipped to any player's Hero card to that player's hand, then DRAW a card."
        );
    }

    @Override
    public boolean playCard(Player player) {
        System.out.println(player.getName() + " played Howling Gale!");
        // TODO: เพิ่มลอจิก ถอด Item card กลับขึ้นมือเจ้าของ แล้วให้ Player ที่ร่ายจั่วการ์ด 1 ใบ
        return true;
    }
}
