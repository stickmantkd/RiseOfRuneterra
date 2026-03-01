package NonGui.GameUtils;

import NonGui.BaseEntity.BaseCard;
import NonGui.BaseEntity.Cards.ModifierCard.ModifierCard;
import NonGui.BaseEntity.Player;
import NonGui.GameLogic.GameEngine;
import gui.ModifierView;

import static NonGui.GameLogic.GameEngine.players;

public class ModifierUtils {
    private static final boolean[] confirmPass = new boolean[4];

    // Each call modifies only the roll passed in, and updates that player's property
    public static int TriggerModifier(int roll, Player targetPlayer) {
        resetConfirmPass();
        while (!allConfirmPass()) {
            for (int i = 0; i < players.length; ++i) {
                if (!hasModifierCard(players[i])) {
                    confirmPass[i] = true;
                    continue;
                }

                System.out.println("It's " + players[i].getName() + "'s action.");
                int effect = playModifierCard(i, targetPlayer); // pass targetPlayer
                roll += effect;

                // Update the target player's roll property immediately
                targetPlayer.setCurrentRoll(roll);

                System.out.println("Current roll after modifiers: " + roll);
            }
        }
        return roll;
    }

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

    private static int playModifierCard(int playerIndex, Player targetPlayer) {
        while (true) {
            Player currentPlayer = players[playerIndex];
            int modifierIndex = ModifierView.selectModifierCard(currentPlayer, targetPlayer);

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
                currentPlayer.removeCardFromHand(modifier);
                GameEngine.deck.discardCard(modifier);
                System.out.println("Modifier card " + modifier.getName() + " applied effect: " + effect +
                        " to " + targetPlayer.getName());
                return effect;
            }
        }
    }

    private static boolean hasModifierCard(Player player) {
        for (BaseCard card : player.getCardsInHand()) {
            if (card instanceof ModifierCard) return true;
        }
        return false;
    }
}
