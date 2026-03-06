package nongui.listofcards.herocard.Fighter;

import nongui.baseentity.cards.HeroCard.HeroCard;
import nongui.baseentity.Player;
import nongui.baseentity.properties.UnitClass;
import nongui.gamelogic.GameChoice;
import nongui.gamelogic.GameEngine;

/**
 * Represents the "Fiora" Hero Card.
 * <p>
 * <b>Ability: Riposte</b><br>
 * Requirement: Roll 8+<br>
 * Effect: Target any player and select one Hero card from their board to be DESTROYED.
 * <p>
 * <i>Fiora excels at removing key threats directly from the battlefield.</i>
 */
public class Fiora extends HeroCard {

    /**
     * Constructs Fiora with her base stats and duelist flavor text.
     */
    public Fiora() {
        super(
                "Fiora",
                "I demand satisfaction.",
                "Riposte: Roll 8+. DESTROY a Hero card.",
                UnitClass.Fighter,
                8
        );
    }

    /**
     * Executes Fiora's Riposte ability.
     * <p>
     * Logic Flow:
     * 1. Player selects a target opponent.
     * 2. System validates if the target has heroes on the board.
     * 3. Player selects a specific hero to destroy.
     * 4. The hero is removed from the board and sent to the discard pile.
     * * @param player The player who activated Fiora's ability.
     */
    @Override
    public void useAbility(Player player) {
        System.out.println(this.getName() + " uses Riposte!");

        // 1. Select a target player
        // Note: Using selectPlayer from GameChoice usually brings up a UI selection.
        int selectedPlayerIndex = GameChoice.selectPlayer(GameEngine.players);

        // Safety check for UI cancellation
        if (selectedPlayerIndex == -1) {
            System.out.println("Riposte canceled.");
            return;
        }

        Player targetPlayer = GameEngine.players[selectedPlayerIndex];

        // 2. Check if the target player even has any heroes to destroy
        if (targetPlayer.boardIsEmpty()) {
            System.out.println(targetPlayer.getName() + " has no Hero cards to destroy.");
            return;
        }

        // 3. Select which Hero card to destroy from that player's board
        int selectedHeroIndex = GameChoice.selectHeroCard(targetPlayer);

        // Safety check for hero selection cancellation
        if (selectedHeroIndex == -1) {
            System.out.println("No hero selected. Riposte fails.");
            return;
        }

        // 4. Capture reference for logging and discarding before removal
        HeroCard heroToDestroy = targetPlayer.getHeroCard(selectedHeroIndex);

        // 5. DESTROY! (Removing from their board)
        boolean success = targetPlayer.removeHeroCard(selectedHeroIndex);

        if (success && heroToDestroy != null) {
            // ✅ Polish: Ensure the destroyed card goes to the discard pile
            GameEngine.deck.discardCard(heroToDestroy);
            System.out.println("⚔️ En Garde! " + heroToDestroy.getName() + " from " + targetPlayer.getName() + " was destroyed.");
        } else {
            System.out.println("Failed to destroy the Hero card.");
        }

        // Refresh board UI to reflect the destruction
        try { gui.BoardView.refresh(); } catch (Exception e) {}
    }
}