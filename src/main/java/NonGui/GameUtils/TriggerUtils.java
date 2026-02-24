package NonGui.GameUtils;

import NonGui.BaseEntity.BaseCard;
import NonGui.BaseEntity.Cards.ChallengeCard.ChallengeCard;
import NonGui.BaseEntity.Cards.ModifierCard.ModifierCard;
import NonGui.BaseEntity.Player;

import static NonGui.GameLogic.GameChoice.*;
import static NonGui.GameLogic.GameEngine.players;
import static NonGui.GameUtils.GameplayUtils.beginChallenge;

public class TriggerUtils {

    public static class modifierUtils{
        private static final boolean[] confirmPass = new boolean[4];
        public static int TriggerModifier ( int roll){
            resetConfirmPass();
            while (!allConfirmPass()) {
                for (int i = 0; i < 4; ++i) {
                    System.out.println("It's " + players[i] + " action.");
                    roll += playModifierCard(i);
                }
            }
            return roll;
        }

        private static void resetConfirmPass () {
            for (int i = 0; i < 4; ++i) {
                confirmPass[i] = false;
            }
        }

        private static boolean allConfirmPass () {
            for (boolean pass : confirmPass) {
                if (!pass) return false;
            }
            return true;
        }

        private static int playModifierCard ( int playerIndex){
            while (true) {
                Player currentPlayer = players[playerIndex];
                int modifierIndex = selectedModifierCard(currentPlayer);
                if (modifierIndex == -1) {
                    confirmPass[playerIndex] = true;
                    return 0;
                } else {
                    BaseCard modifier = currentPlayer.getCardInHand(modifierIndex);
                    if (!(modifier instanceof ModifierCard)) {
                        System.out.println("Invalid action: that was not a Modifier Card.");
                        continue;
                    }
                    return ((ModifierCard) modifier).useModifier();
                }
            }
        }
    }

    public static class challengeUtils{
        private static final boolean[] confirmPass = new boolean[4];
        public static boolean ChallengerWin(){
            resetConfirmPass();
            while (!allConfirmPass()) {
                for (int i = 0; i < 4; ++i) {
                    System.out.println("It's " + players[i] + " action.");
                    if(playChallenge(i)) return true;
                }
            }
            return false;
        }

        private static void resetConfirmPass () {
            for (int i = 0; i < 4; ++i) {
                confirmPass[i] = false;
            }
        }

        private static boolean allConfirmPass () {
            for (boolean pass : confirmPass) {
                if (!pass) return false;
            }
            return true;
        }

        private static boolean playChallenge ( int playerIndex){
            while (true) {
                Player currentPlayer = players[playerIndex];
                int challengeIndex = selectedChallengeCard(currentPlayer);
                if (challengeIndex == -1) {
                    confirmPass[playerIndex] = true;
                    return false;
                } else {
                    BaseCard modifier = currentPlayer.getCardInHand(challengeIndex);
                    if (!(modifier instanceof ChallengeCard)) {
                        System.out.println("Invalid action: that was not a Challenge Card.");
                        continue;
                    }
                    return beginChallenge();
                }
            }
        }
    }
}