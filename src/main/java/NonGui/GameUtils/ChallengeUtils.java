package NonGui.GameUtils;

import NonGui.BaseEntity.BaseCard;
import NonGui.BaseEntity.Cards.ChallengeCard.ChallengeCard;
import NonGui.BaseEntity.Cards.HeroCard.HeroCard;
import NonGui.BaseEntity.Cards.Itemcard.ItemCard;
import NonGui.BaseEntity.Player;
import NonGui.GameLogic.GameEngine;
import gui.ChallengeView;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.StageStyle;

import java.util.concurrent.atomic.AtomicBoolean;

import static NonGui.GameLogic.GameEngine.players;

public class ChallengeUtils {

    public static boolean resolveChallenge(int challengedPlayerIndex, Player challengedPlayer, BaseCard card) {
        if (challengedPlayer.isUnchallengeable() || (card instanceof ItemCard && challengedPlayer.isUnchallengeable())) {
            System.out.println("✨ [BRAUM EFFECT] " + challengedPlayer.getName() + " is Unbreakable! This card cannot be challenged.");
            return false;
        }

        for (int i = 0; i < players.length; i++) {
            if (i == challengedPlayerIndex) continue;

            Player challenger = players[i];
            if (!hasChallengeCard(challenger)) continue;

            boolean wantsToPlay = askChallengeCard(challenger, challengedPlayer, card);
            if (!wantsToPlay) continue;

            ChallengeView view = new ChallengeView(challenger, challengedPlayer, card);
            view.show();

            int challengerRoll = DiceUtils.rollForChallenge(challenger);
            int challengedRoll  = DiceUtils.rollForChallenge(challengedPlayer);

            challenger.setCurrentRoll(challengerRoll);
            challengedPlayer.setCurrentRoll(challengedRoll);

            boolean success = challengerRoll >= challengedRoll;

            String heroMsg = success
                    ? "Challenge SUCCESS: " + card.getName() + " discarded."
                    : challengedPlayer.getName() + " successfully played hero " + card.getName();

            System.out.println(heroMsg);
            ChallengeView.showResult(success, challenger.getName(), heroMsg);

            if (success) {
                GameEngine.deck.discardCard(card);
                return true;
            } else {
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

    // ── Helpers ───────────────────────────────────────────────────────────────

    private static boolean hasChallengeCard(Player player) {
        for (BaseCard card : player.getCardsInHand()) {
            if (card instanceof ChallengeCard) return true;
        }
        return false;
    }

    /**
     * Styled YES / NO challenge prompt that matches the dark fantasy theme.
     */
    private static boolean askChallengeCard(Player challenger, Player challenged, BaseCard card) {
        if (!hasChallengeCard(challenger)) return false;

        AtomicBoolean answer = new AtomicBoolean(false);

        // ── Layout ────────────────────────────────────────────────────────────

        // Icon / flavour row
        Label icon = new Label("⚔");
        icon.setStyle("-fx-font-size: 32; -fx-text-fill: #FFD700;");

        Label titleLabel = new Label("Challenge Declared!");
        titleLabel.setStyle(
                "-fx-font-family: 'Georgia'; -fx-font-size: 16; -fx-font-weight: bold;" +
                        "-fx-text-fill: #FFD700;" +
                        "-fx-effect: dropshadow(gaussian, #FF8C00, 6, 0.5, 0, 0);"
        );

        HBox titleRow = new HBox(12, icon, titleLabel);
        titleRow.setAlignment(Pos.CENTER);

        // Separator
        Region sep = new Region();
        sep.setPrefHeight(1);
        sep.setMaxWidth(Double.MAX_VALUE);
        sep.setStyle("-fx-background-color: linear-gradient(to right, transparent, #8B6914, transparent);");
        VBox.setMargin(sep, new Insets(4, 0, 4, 0));

        // Body text
        Label bodyLabel = new Label(
                challenger.getName() + ", do you want to challenge\n" +
                        challenged.getName() + "'s card  \"" + card.getName() + "\"?"
        );
        bodyLabel.setStyle(
                "-fx-font-family: 'Georgia'; -fx-font-size: 12; -fx-text-fill: #F5DEB3;" +
                        "-fx-text-alignment: center;"
        );
        bodyLabel.setWrapText(true);
        bodyLabel.setAlignment(Pos.CENTER);

        // Buttons
        Button yesBtn = new Button("⚔  Challenge!");
        yesBtn.setStyle(
                "-fx-background-color: linear-gradient(to bottom, #6a3800, #3a1e00);" +
                        "-fx-text-fill: #FFD700; -fx-font-family: 'Georgia'; -fx-font-size: 12; -fx-font-weight: bold;" +
                        "-fx-border-color: #8B6914; -fx-border-width: 1;" +
                        "-fx-border-radius: 4; -fx-background-radius: 4;" +
                        "-fx-padding: 7 22 7 22; -fx-cursor: hand;"
        );
        yesBtn.setOnMouseEntered(e -> yesBtn.setStyle(
                "-fx-background-color: linear-gradient(to bottom, #9a5000, #5a2e00);" +
                        "-fx-text-fill: #FFFACD; -fx-font-family: 'Georgia'; -fx-font-size: 12; -fx-font-weight: bold;" +
                        "-fx-border-color: #FFD700; -fx-border-width: 1;" +
                        "-fx-border-radius: 4; -fx-background-radius: 4;" +
                        "-fx-padding: 7 22 7 22; -fx-cursor: hand;" +
                        "-fx-effect: dropshadow(gaussian, #FFD700, 6, 0.4, 0, 0);"
        ));

        Button noBtn = new Button("🛡  Pass");
        noBtn.setStyle(
                "-fx-background-color: linear-gradient(to bottom, #2a1a0a, #1a0d00);" +
                        "-fx-text-fill: #8a7050; -fx-font-family: 'Georgia'; -fx-font-size: 12;" +
                        "-fx-border-color: #3a2510; -fx-border-width: 1;" +
                        "-fx-border-radius: 4; -fx-background-radius: 4;" +
                        "-fx-padding: 7 22 7 22; -fx-cursor: hand;"
        );
        noBtn.setOnMouseEntered(e -> noBtn.setStyle(
                "-fx-background-color: linear-gradient(to bottom, #3a2a1a, #2a1a0a);" +
                        "-fx-text-fill: #C8A870; -fx-font-family: 'Georgia'; -fx-font-size: 12;" +
                        "-fx-border-color: #5a3a10; -fx-border-width: 1;" +
                        "-fx-border-radius: 4; -fx-background-radius: 4;" +
                        "-fx-padding: 7 22 7 22; -fx-cursor: hand;"
        ));

        HBox btnRow = new HBox(14, yesBtn, noBtn);
        btnRow.setAlignment(Pos.CENTER);

        VBox root = new VBox(12, titleRow, sep, bodyLabel, btnRow);
        root.setPadding(new Insets(22, 28, 22, 28));
        root.setAlignment(Pos.CENTER);
        root.setStyle(
                "-fx-background-color: linear-gradient(to bottom, #1c0d00, #2e1800);" +
                        "-fx-border-color: #8B6914; -fx-border-width: 2;" +
                        "-fx-border-radius: 8; -fx-background-radius: 8;"
        );
        root.setEffect(new DropShadow(24, Color.color(0, 0, 0, 0.85)));
        root.setPrefWidth(340);

        // ── Dialog wiring ─────────────────────────────────────────────────────

        Dialog<Boolean> dialog = new Dialog<>();
        dialog.initStyle(StageStyle.TRANSPARENT);
        dialog.setTitle("Challenge Phase");

        DialogPane dp = dialog.getDialogPane();
        dp.setContent(root);
        dp.setStyle("-fx-background-color: transparent; -fx-padding: 0;");
        dp.getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
        dp.lookupButton(ButtonType.OK).setVisible(false);
        dp.lookupButton(ButtonType.OK).setManaged(false);
        dp.lookupButton(ButtonType.CANCEL).setVisible(false);
        dp.lookupButton(ButtonType.CANCEL).setManaged(false);

        yesBtn.setOnMouseExited(e -> yesBtn.setStyle(
                "-fx-background-color: linear-gradient(to bottom, #6a3800, #3a1e00);" +
                        "-fx-text-fill: #FFD700; -fx-font-family: 'Georgia'; -fx-font-size: 12; -fx-font-weight: bold;" +
                        "-fx-border-color: #8B6914; -fx-border-width: 1;" +
                        "-fx-border-radius: 4; -fx-background-radius: 4;" +
                        "-fx-padding: 7 22 7 22; -fx-cursor: hand;"
        ));
        noBtn.setOnMouseExited(e -> noBtn.setStyle(
                "-fx-background-color: linear-gradient(to bottom, #2a1a0a, #1a0d00);" +
                        "-fx-text-fill: #8a7050; -fx-font-family: 'Georgia'; -fx-font-size: 12;" +
                        "-fx-border-color: #3a2510; -fx-border-width: 1;" +
                        "-fx-border-radius: 4; -fx-background-radius: 4;" +
                        "-fx-padding: 7 22 7 22; -fx-cursor: hand;"
        ));

        yesBtn.setOnAction(e -> { answer.set(true);  dialog.setResult(true);  dialog.close(); });
        noBtn.setOnAction( e -> { answer.set(false); dialog.setResult(false); dialog.close(); });

        dialog.showAndWait();
        return answer.get();
    }
}