package NonGui.ListOfCards.magiccard;

import NonGui.BaseEntity.Cards.MagicCard.MagicCard;
import NonGui.BaseEntity.Player;

import static NonGui.GameLogic.GameChoice.*;
import static NonGui.GameLogic.GameEngine.players;

public class FinalSpark extends MagicCard {
    public FinalSpark(){
        super("Final Spark",
                "Final light!",
                "Destroy A Hero Card"
        );
    }

    @Override
    public boolean playCard(Player player) {
        int playerNumber = selectPlayer(players);
        Player selectedPlayer = players[playerNumber];

        int heroNumber = selectHeroCard(selectedPlayer);

        return selectedPlayer.removeHeroCard(heroNumber);
    }
}
