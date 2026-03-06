package nongui.listofcards.herocard.Fighter;

import nongui.baseentity.BaseCard;
import nongui.baseentity.cards.HeroCard.HeroCard;
import nongui.baseentity.Player;
import nongui.baseentity.properties.UnitClass;
import nongui.gamelogic.GameChoice;
import nongui.gamelogic.GameEngine;

import java.util.ArrayList;

import static nongui.gamelogic.GameEngine.players;

/**
 * Represents the "Volibear" Hero Card.
 * <p>
 * <b>Ability: Thundering Smash</b><br>
 * Requirement: Roll 5+<br>
 * Effect: Target another player. That player must choose and DISCARD 2 cards from their hand.
 * <p>
 * <i>Volibear brings the storm to suppress his enemies' options early on.</i>
 */
public class Volibear extends HeroCard {

    /**
     * Constructs Volibear with his base stats and thunderous flavor text.
     */
    public Volibear(){
        super(
                "Volibear",
                "I am the storm!",
                "Thundering Smash: Roll 5+. Choose a player. That player must DISCARD 2 cards.",
                UnitClass.Fighter,
                5
        );
    }

    /**
     * Executes the Thundering Smash ability.
     * <p>
     * Logic Flow:
     * 1. Filter out the current player to identify valid opponents.
     * 2. Prompt the current player to select a target opponent.
     * 3. Force the target opponent to select and discard up to 2 cards via GameChoice.
     * * @param player The player who activated Volibear's ability.
     */
    @Override
    public void useAbility(Player player) {
        System.out.println(this.getName() + " uses Thundering Smash!");

        // 1. Identify valid targets (Excluding the owner)
        ArrayList<Player> validTargetsList = new ArrayList<>();
        for (Player p : players) {
            if (p != player) {
                validTargetsList.add(p);
            }
        }

        if (validTargetsList.isEmpty()) {
            System.out.println("No other players available to target!");
            return;
        }

        Player[] validTargetsArray = validTargetsList.toArray(new Player[0]);

        // 2. Select Target Player
        System.out.println(player.getName() + ", choose a player to discard 2 cards:");
        int targetIndex = GameChoice.selectPlayer(validTargetsArray);

        // Handle potential UI cancellation
        if (targetIndex == -1) {
            System.out.println("Thundering Smash canceled.");
            return;
        }

        Player targetPlayer = validTargetsArray[targetIndex];
        System.out.println("⚡ " + targetPlayer.getName() + " is targeted by the storm!");

        // 3. Forced Discard Phase (2 cards)

        for (int i = 0; i < 2; i++) {
            if (targetPlayer.handIsEmpty()) {
                System.out.println(targetPlayer.getName() + " has no more cards in hand!");
                break;
            }

            System.out.println(targetPlayer.getName() + ", please select a card to DISCARD (" + (i + 1) + "/2):");
            int cardIndex = GameChoice.selectCardsInHand(targetPlayer);

            if (cardIndex != -1) {
                // Ensure the card is removed and sent to the discard pile
                BaseCard discarded = targetPlayer.getCardsInHand().remove(cardIndex);
                GameEngine.deck.discardCard(discarded);
                System.out.println("🗑️ Card discarded: " + discarded.getName());
            } else {
                // If the player tries to skip a mandatory discard, force discard the first card
                BaseCard forcedDiscard = targetPlayer.getCardsInHand().remove(0);
                GameEngine.deck.discardCard(forcedDiscard);
                System.out.println("⚠️ Selection skipped. Auto-discarded: " + forcedDiscard.getName());
            }
        }

        try { gui.BoardView.refresh(); } catch (Exception e) {}
    }
}