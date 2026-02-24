package NonGui.ListOfCards.magiccard;

import NonGui.BaseEntity.Cards.MagicCard.MagicCard;
import NonGui.BaseEntity.Player;

public class ElixirOfSorcery extends MagicCard {
    public ElixirOfSorcery() {
        super(
                "Elixir of Sorcery",
                "True power comes to those who drink deep.",
                "+2 to all of your rolls until the end of your turn."
        );
    }

    @Override
    public boolean playCard(Player player) {
        System.out.println(player.getName() + " played Elixir of Sorcery!");
        // TODO: เพิ่มลอจิก บัฟ +2 ให้กับการทอยลูกเต๋าทั้งหมดของ Player จนจบเทิร์น
        return true;
    }
}
