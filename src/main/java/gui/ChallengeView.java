package gui;

import NonGui.BaseEntity.Player;
import NonGui.BaseEntity.BaseCard;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ChallengeView extends Stage {

    private static Label resultLabel; // static so ChallengeUtils can update it

    public ChallengeView(Player challenger, Player challenged, BaseCard card) {
        // Challenger side
        VBox challengerBox = new VBox(10);
        challengerBox.setAlignment(Pos.CENTER);
        Label challengerLabel = new Label("PLAYER 1: " + challenger.getName());
        Label rollLabel1 = new Label("Roll");
        Label challengerRollLabel = new Label("?");
        challenger.currentRollProperty().addListener((obs, oldVal, newVal) -> {
            challengerRollLabel.setText(newVal.intValue() >= 0 ? String.valueOf(newVal) : "?");
        });
        challengerBox.getChildren().addAll(challengerLabel, rollLabel1, challengerRollLabel);

        // Challenge symbol in center
        VBox challengeBox = new VBox(10);
        challengeBox.setAlignment(Pos.CENTER);
        Label challengeTitle = new Label("CHALLENGE");
        Label cardLabel = new Label("Card: " + card.getName());
        Image challengeImage = new Image("card/base/challenge card/ChallengeCard.png");
        ImageView challengeView = new ImageView(challengeImage);
        challengeView.setFitWidth(100);
        challengeView.setFitHeight(100);
        resultLabel = new Label("");
        challengeBox.getChildren().addAll(challengeTitle, cardLabel, challengeView, resultLabel);

        // Challenged side
        VBox challengedBox = new VBox(10);
        challengedBox.setAlignment(Pos.CENTER);
        Label challengedLabel = new Label("PLAYER 2: " + challenged.getName());
        Label rollLabel2 = new Label("Roll");
        Label challengedRollLabel = new Label("?");
        challenged.currentRollProperty().addListener((obs, oldVal, newVal) -> {
            challengedRollLabel.setText(newVal.intValue() >= 0 ? String.valueOf(newVal) : "?");
        });
        challengedBox.getChildren().addAll(challengedLabel, rollLabel2, challengedRollLabel);

        // Layout
        HBox root = new HBox(50);
        root.setAlignment(Pos.CENTER);
        root.getChildren().addAll(challengerBox, challengeBox, challengedBox);

        Scene scene = new Scene(root, 600, 400);
        this.setTitle("Challenge Phase");
        this.setScene(scene);
    }

    // Show two-line result
    public static void showResult(boolean success, String challengerName, String heroMsg) {
        String line1 = success
                ? ">>> Challenge SUCCESS by " + challengerName + " <<<"
                : ">>> Challenge FAILED by " + challengerName + " <<<";
        String combined = line1 + "\n" + heroMsg;

        if (resultLabel != null) {
            Platform.runLater(() -> resultLabel.setText(combined));
        }
    }
}
