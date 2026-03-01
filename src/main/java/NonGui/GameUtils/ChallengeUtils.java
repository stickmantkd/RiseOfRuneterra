package NonGui.GameUtils;

import NonGui.BaseEntity.BaseCard;
import NonGui.BaseEntity.Cards.ChallengeCard.ChallengeCard;
import NonGui.BaseEntity.Cards.HeroCard.HeroCard;
import NonGui.BaseEntity.Player;
import NonGui.GameLogic.GameEngine;
import gui.ChallengeView;

import static NonGui.GameLogic.GameEngine.players;

public class ChallengeUtils {

    public static boolean resolveChallenge(int challengedPlayerIndex, Player challengedPlayer, BaseCard card) {
        for (int i = 0; i < players.length; i++) {
            if (i == challengedPlayerIndex) continue;

            Player challenger = players[i];
            if (!hasChallengeCard(challenger)) continue;

            // Ask if challenger wants to play a challenge card
            boolean wantsToPlay = askChallengeCard(challenger);
            if (!wantsToPlay) continue;

            // Show challenge GUI
            ChallengeView view = new ChallengeView(challenger, challengedPlayer, card);
            view.show();

            // Rolls automatically trigger modifier phase
            int challengerRoll = DiceUtils.getRoll(challenger);
            int challengedRoll = DiceUtils.getRoll(challengedPlayer);

            challenger.setCurrentRoll(challengerRoll);
            challengedPlayer.setCurrentRoll(challengedRoll);

            // Compare rolls
            boolean success = challengerRoll >= challengedRoll;

            // Build hero message line
            String heroMsg = success
                    ? "Challenge SUCCESS: " + card.getName() + " discarded."
                    : challengedPlayer.getName() + " successfully played hero " + card.getName();

            // Console output
            System.out.println(heroMsg);

            // GUI output (two lines)
            ChallengeView.showResult(success, challenger.getName(), heroMsg);

            if (success) {
                // Challenge succeeded → discard card
                GameEngine.deck.discardCard(card);
                return true;
            } else {
                // Challenge failed → assign hero to challenged player
                if (card instanceof HeroCard hero) {
                    HeroCard[] ownedHero = challengedPlayer.getOwnedHero();
                    for (int j = 0; j < ownedHero.length; j++) {
                        if (ownedHero[j] == null) {
                            ownedHero[j] = hero;
                            hero.setOwner(challengedPlayer);
                            challengedPlayer.setOwnedHero(ownedHero);
                            hero.tryUseAbility(challengedPlayer);
                            break;
                        }
                    }
                }
                return false;
            }
        }
        return false;
    }

    // Check if player has a challenge card
    private static boolean hasChallengeCard(Player player) {
        for (BaseCard card : player.getCardsInHand()) {
            if (card instanceof ChallengeCard) return true;
        }
        return false;
    }

    // Ask if player wants to play a challenge card
    private static boolean askChallengeCard(Player player) {
        // Only show if player has challenge cards
        boolean hasChallenge = player.getCardsInHand().stream()
                .anyMatch(card -> card instanceof ChallengeCard);

        if (!hasChallenge) return false;

        javafx.scene.control.Alert alert = new javafx.scene.control.Alert(
                javafx.scene.control.Alert.AlertType.CONFIRMATION,
                player.getName() + " - Do you want to play a challenge card?",
                javafx.scene.control.ButtonType.YES,
                javafx.scene.control.ButtonType.NO
        );
        alert.setTitle("Challenge Phase");
        alert.setHeaderText("Challenge Card Option");

        javafx.scene.control.ButtonType result = alert.showAndWait().orElse(javafx.scene.control.ButtonType.NO);

        return result == javafx.scene.control.ButtonType.YES;
    }
}