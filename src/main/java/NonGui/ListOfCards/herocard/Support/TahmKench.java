package NonGui.ListOfCards.herocard.Support;

import NonGui.BaseEntity.BaseCard;
import NonGui.BaseEntity.Cards.HeroCard.HeroCard;
import NonGui.BaseEntity.Player;
import NonGui.BaseEntity.Properties.UnitClass;
import NonGui.GameLogic.GameChoice;
import javafx.collections.ObservableList;

import java.util.ArrayList;

import static NonGui.GameLogic.GameEngine.players;

/**
 * Represents the "Tahm Kench" Hero Card.
 * <p>
 * <b>Ability: An Acquired Taste</b><br>
 * Requirement: Roll 9+<br>
 * Effect: Choose another player. You and that player swap your entire hands.
 * <p>
 * <i>Tahm Kench offers a bargain that can leave opponents empty-handed and desperate.</i>
 */
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

    /**
     * Executes the An Acquired Taste ability.
     * <p>
     * Logic Flow:
     * 1. Target identification: Identify all players except the owner.
     * 2. Interaction: Owner selects a target player via GameChoice.
     * 3. Swap: Exchanges the ObservableList references for hands between the two players.
     * * @param player The player who activated Tahm Kench's ability.
     */
    @Override
    public void useAbility(Player player) {
        System.out.println("🐸 " + this.getName() + " invites you to a bargain... (Trade Hands)");

        // 1. Filter valid targets (excluding self)
        ArrayList<Player> validTargetsList = new ArrayList<>();
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

        // 2. Select the target player
        System.out.println(player.getName() + ", choose a player to TRADE hands with:");
        int targetIndex = GameChoice.selectPlayer(validTargetsArray);

        // Fail-safe for UI cancel
        if (targetIndex == -1) {
            System.out.println("The bargain was refused. (Action canceled)");
            return;
        }

        Player targetPlayer = validTargetsArray[targetIndex];

        // 3. Capture hand references

        ObservableList<BaseCard> myHand = player.getCardsInHand();
        ObservableList<BaseCard> targetHand = targetPlayer.getCardsInHand();

        // 4. Perform the swap
        // Note: Ensure your Player class handles setCardsInHand correctly to update UI listeners
        player.setCardsInHand(targetHand);
        targetPlayer.setCardsInHand(myHand);

        System.out.println("🤝 SUCCESS! " + player.getName() + " and " + targetPlayer.getName() + " have traded their hands!");
        System.out.println(player.getName() + " now has " + player.getCardsInHand().size() + " cards.");
        System.out.println(targetPlayer.getName() + " now has " + targetPlayer.getCardsInHand().size() + " cards.");

        // Sync GUI to reflect the new hands for both players
        try {
            gui.BoardView.refresh();
        } catch (Exception e) {}
    }
}