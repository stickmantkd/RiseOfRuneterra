package gui;

import NonGui.BaseEntity.Player;
import NonGui.BaseEntity.BaseCard;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class ChallengeView extends Stage {

    private static Label resultLabel;

    private static final String PANEL_STYLE =
            "-fx-background-color: linear-gradient(to bottom, #1c0d00, #2e1800);" +
                    "-fx-border-color: #8B6914; -fx-border-width: 1;" +
                    "-fx-border-radius: 6; -fx-background-radius: 6;" +
                    "-fx-padding: 14;";

    private static final String PLAYER_NAME_STYLE =
            "-fx-font-family: 'Georgia'; -fx-font-size: 14; -fx-font-weight: bold; -fx-text-fill: #FFD700;";

    private static final String ROLL_NUM_STYLE =
            "-fx-font-family: 'Georgia'; -fx-font-size: 42; -fx-font-weight: bold; -fx-text-fill: #F5DEB3;" +
                    "-fx-effect: dropshadow(gaussian, #FF8C00, 6, 0.5, 0, 0);";

    private static final String LABEL_STYLE =
            "-fx-font-family: 'Georgia'; -fx-font-size: 12; -fx-text-fill: #C8A870;";

    public ChallengeView(Player challenger, Player challenged, BaseCard card) {

        // ── Challenger panel ────────────────────────────────────────────────
        VBox challengerBox = new VBox(10);
        challengerBox.setAlignment(Pos.CENTER);
        challengerBox.setStyle(PANEL_STYLE);

        Label challengerLabel = new Label("⚔  " + challenger.getName());
        challengerLabel.setStyle(PLAYER_NAME_STYLE);

        Label roll1Title = new Label("Roll");
        roll1Title.setStyle(LABEL_STYLE);

        Label challengerRollLabel = new Label("?");
        challengerRollLabel.setStyle(ROLL_NUM_STYLE);

        challenger.currentRollProperty().addListener((obs, oldVal, newVal) ->
                challengerRollLabel.setText(newVal.intValue() >= 0 ? String.valueOf(newVal) : "?")
        );
        challengerBox.getChildren().addAll(challengerLabel, roll1Title, challengerRollLabel);

        // ── Centre panel (card info) ─────────────────────────────────────────
        VBox challengeBox = new VBox(10);
        challengeBox.setAlignment(Pos.CENTER);
        challengeBox.setStyle(PANEL_STYLE);

        Label challengeTitle = new Label("⚡ CHALLENGE ⚡");
        challengeTitle.setStyle(
                "-fx-font-family: 'Georgia'; -fx-font-size: 16; -fx-font-weight: bold;" +
                        "-fx-text-fill: #FFD700; -fx-effect: dropshadow(gaussian, #FF8C00, 8, 0.6, 0, 0);"
        );

        Label cardLabel = new Label("Card: " + card.getName());
        cardLabel.setStyle(LABEL_STYLE);

        // Card image or placeholder
        StackPane cardVisual = new StackPane();
        String imgPath = "card/base/challenge card/ChallengeCard.png";
        try {
            Image challengeImage = new Image(imgPath);
            ImageView challengeView = new ImageView(challengeImage);
            challengeView.setFitWidth(90);
            challengeView.setFitHeight(126);
            challengeView.setPreserveRatio(false);
            cardVisual.getChildren().add(challengeView);
        } catch (Exception ex) {
            Rectangle placeholder = new Rectangle(90, 126, Color.web("#3a2010"));
            placeholder.setStroke(Color.web("#8B6914"));
            placeholder.setStrokeWidth(2);
            cardVisual.getChildren().add(placeholder);
        }

        resultLabel = new Label("");
        resultLabel.setStyle(
                "-fx-font-family: 'Georgia'; -fx-font-size: 13; -fx-font-weight: bold;" +
                        "-fx-text-fill: #FFD700; -fx-wrap-text: true; -fx-text-alignment: center;" +
                        "-fx-effect: dropshadow(gaussian, #FF8C00, 4, 0.4, 0, 0);"
        );
        resultLabel.setMaxWidth(200);
        resultLabel.setWrapText(true);

        challengeBox.getChildren().addAll(challengeTitle, cardLabel, cardVisual, resultLabel);

        // ── Challenged panel ────────────────────────────────────────────────
        VBox challengedBox = new VBox(10);
        challengedBox.setAlignment(Pos.CENTER);
        challengedBox.setStyle(PANEL_STYLE);

        Label challengedLabel = new Label("🛡  " + challenged.getName());
        challengedLabel.setStyle(PLAYER_NAME_STYLE);

        Label roll2Title = new Label("Roll");
        roll2Title.setStyle(LABEL_STYLE);

        Label challengedRollLabel = new Label("?");
        challengedRollLabel.setStyle(ROLL_NUM_STYLE);

        challenged.currentRollProperty().addListener((obs, oldVal, newVal) ->
                challengedRollLabel.setText(newVal.intValue() >= 0 ? String.valueOf(newVal) : "?")
        );
        challengedBox.getChildren().addAll(challengedLabel, roll2Title, challengedRollLabel);

        // ── Layout ──────────────────────────────────────────────────────────
        HBox root = new HBox(24, challengerBox, challengeBox, challengedBox);
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(24));
        root.setStyle("-fx-background-color: linear-gradient(to bottom right, #1a0a00, #2d1500, #1a0a00);");

        Scene scene = new Scene(root, 650, 360);
        scene.setFill(Color.web("#1a0a00"));

        this.setTitle("⚔  Challenge Phase  ⚔");
        this.setScene(scene);
    }

    public static void showResult(boolean success, String challengerName, String heroMsg) {
        String icon = success ? "✅" : "❌";
        String line1 = icon + (success
                ? "  Challenge SUCCESS  —  " + challengerName
                : "  Challenge FAILED  —  " + challengerName);
        String combined = line1 + "\n" + heroMsg;

        if (resultLabel != null) {
            Platform.runLater(() -> resultLabel.setText(combined));
        }
    }
}