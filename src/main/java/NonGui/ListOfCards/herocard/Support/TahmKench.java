package NonGui.ListOfCards.herocard.Support;

import NonGui.BaseEntity.Cards.HeroCard.HeroCard;
import NonGui.BaseEntity.Player;
import NonGui.BaseEntity.Properties.UnitClass;
import NonGui.BaseEntity.BaseCard;
import javafx.collections.ObservableList;

import static NonGui.GameLogic.GameEngine.players;

public class TahmKench extends HeroCard {
    public TahmKench() {
        super(
                "Tahm Kench",
                "Call me king, call me demon.",
                "An Acquired Taste: Roll 9+. Trade hands with another player.",
                UnitClass.Support,
                9
        );
    }

    @Override
    public void useAbility(Player player) {
        System.out.println(this.getName() + " uses their ability! (Trade hands with another player)");

        // 1. Collect valid targets (exclude self)
        java.util.ArrayList<Player> validTargetsList = new java.util.ArrayList<>();
        for (Player p : players) {
            if (p != player) {
                validTargetsList.add(p);
            }
        }

        if (validTargetsList.isEmpty()) {
            System.out.println("No other players available to trade hands with!");
            return;
        }

        Player[] validTargetsArray = validTargetsList.toArray(new Player[0]);

        // 2. Let player choose a target
        System.out.println(player.getName() + ", choose a player to TRADE hands with:");
        int targetIndex = NonGui.GameLogic.GameChoice.selectPlayer(validTargetsArray);
        Player targetPlayer = validTargetsArray[targetIndex];

        // 3. Work directly with ObservableList
        ObservableList<BaseCard> myHand = player.getCardsInHand();
        ObservableList<BaseCard> targetHand = targetPlayer.getCardsInHand();

        // 4. Swap hands
        player.setCardsInHand(targetHand);
        targetPlayer.setCardsInHand(myHand);

        System.out.println("SUCCESS! " + player.getName() + " and " + targetPlayer.getName() + " have traded their hands!");
        System.out.println(player.getName() + " now has " + player.getCardsInHand().size() + " cards.");
        System.out.println(targetPlayer.getName() + " now has " + targetPlayer.getCardsInHand().size() + " cards.");
    }
}
