package NonGui.GameUtils;

import NonGui.BaseEntity.BaseCard;
import NonGui.BaseEntity.Cards.ModifierCard.ModifierCard;
import NonGui.BaseEntity.Player;
import NonGui.BaseEntity.Properties.UnitClass;
import NonGui.GameLogic.GameEngine;
import gui.ModifierView;

import static NonGui.GameLogic.GameEngine.players;

/**
 * Utility class for handling Modifier card interactions during a roll phase.
 * It manages a priority loop where all players can contribute modifier cards
 * to adjust the final roll outcome.
 */
public class ModifierUtils {

    /**
     * Tracks which players have passed their opportunity to play a modifier in the current round.
     */
    private static final boolean[] confirmPass = new boolean[4];

    // ==========================================
    // Core Logic: The Modifier Loop
    // ==========================================

    /**
     * Triggers the interactive modifier phase.
     * Iterates through all players repeatedly until everyone passes consecutively.
     * * @param roll         The initial dice roll value.
     * @param targetPlayer The player whose roll is being modified.
     * @return The final roll value after all modifiers are applied.
     */
    public static int TriggerModifier(int roll, Player targetPlayer) {
        resetConfirmPass();

        // Loop continues until all players pass in a single round
        while (!allConfirmPass()) {
            for (int i = 0; i < players.length; ++i) {
                // Skip players who have no modifier cards to play
                if (!hasModifierCard(players[i])) {
                    confirmPass[i] = true;
                    continue;
                }

                System.out.println("It's " + players[i].getName() + "'s action.");
                int effect = playModifierCard(i, targetPlayer);
                roll += effect;

                // Update the target player's roll property immediately for GUI feedback
                targetPlayer.setCurrentRoll(roll);

                System.out.println("Current roll after modifiers: " + roll);
            }
        }
        return roll;
    }

    // ==========================================
    // Internal State Helpers
    // ==========================================

    private static void resetConfirmPass() {
        for (int i = 0; i < players.length; ++i) {
            confirmPass[i] = false;
        }
    }

    private static boolean allConfirmPass() {
        for (boolean pass : confirmPass) {
            if (!pass) return false;
        }
        return true;
    }

    // ==========================================
    // Card Execution Logic
    // ==========================================

    /**
     * Handles the specific action of a player choosing and playing a modifier card.
     * Applies class bonuses (e.g., Tank bonus) and manages card disposal.
     * * @return The integer value change (positive or negative) to be added to the roll.
     */
    private static int playModifierCard(int playerIndex, Player targetPlayer) {
        while (true) {
            Player currentPlayer = players[playerIndex];
            int modifierIndex = ModifierView.selectModifierCard(currentPlayer, targetPlayer);

            // -1 represents the "Pass" action in ModifierView
            if (modifierIndex == -1) {
                confirmPass[playerIndex] = true;
                return 0;
            } else {
                BaseCard modifier = currentPlayer.getCardInHand(modifierIndex);

                if (!(modifier instanceof ModifierCard)) {
                    System.out.println("Invalid action: that was not a Modifier Card.");
                    continue;
                }

                int effect = ((ModifierCard) modifier).useModifier();

                // 🛡️ Tank Passive: Increases the potency of Modifier cards by 1.
                if (currentPlayer.getOwnedLeader().getUnitClass() == UnitClass.Tank) {
                    if (effect > 0) effect += 1;
                    else effect -= 1;
                }

                // Process card consumption
                currentPlayer.removeCardFromHand(modifier);
                GameEngine.deck.discardCard(modifier);

                System.out.println("Modifier card " + modifier.getName() + " applied effect: " + effect +
                        " to " + targetPlayer.getName());

                // Reset other players' pass status because the roll has changed
                resetConfirmPass();
                confirmPass[playerIndex] = false; // Current player might want to play another one later

                return effect;
            }
        }
    }

    /**
     * Checks if a player has any ModifierCard in their hand.
     */
    private static boolean hasModifierCard(Player player) {
        for (BaseCard card : player.getCardsInHand()) {
            if (card instanceof ModifierCard) return true;
        }
        return false;
    }
}