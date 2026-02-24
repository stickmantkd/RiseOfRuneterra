package gui.card;

import NonGui.BaseEntity.Objective;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class ObjectiveView extends StackPane {

    private final Objective objective;

    public ObjectiveView(Objective objective, int index) {
        this.objective = objective;

        double width = 150;
        double height = 210;

        Rectangle rect = new Rectangle(width, height, Color.LIGHTGREEN);
        rect.setStroke(Color.BLACK);
        rect.setStrokeWidth(2);

        Label nameLabel = new Label(objective.getName());
        nameLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 14;");
        nameLabel.setWrapText(true);
        nameLabel.setMaxWidth(width - 8);

        Label flavorLabel = new Label(objective.getFlavorText());
        flavorLabel.setStyle("-fx-font-size: 12; -fx-text-fill: gray;");
        flavorLabel.setWrapText(true);
        flavorLabel.setMaxWidth(width - 8);

        Label requirementLabel = new Label(objective.getRequirementDescription());
        requirementLabel.setStyle("-fx-font-size: 12;");
        requirementLabel.setWrapText(true);
        requirementLabel.setMaxWidth(width - 8);

        Label prizeLabel = new Label("Prize: " + objective.getPrizeDescription());
        prizeLabel.setStyle("-fx-font-size: 12; -fx-text-fill: darkgreen;");
        prizeLabel.setWrapText(true);
        prizeLabel.setMaxWidth(width - 8);

        Label punishmentLabel = new Label("Punishment: " + objective.getPunishmentDescription());
        punishmentLabel.setStyle("-fx-font-size: 12; -fx-text-fill: darkred;");
        punishmentLabel.setWrapText(true);
        punishmentLabel.setMaxWidth(width - 8);

        VBox textBox = new VBox(6, nameLabel, flavorLabel, requirementLabel, prizeLabel, punishmentLabel);
        textBox.setAlignment(Pos.TOP_CENTER);
        textBox.setMaxWidth(width - 6);
        textBox.setMaxHeight(height - 6);

        setAlignment(Pos.CENTER);
        getChildren().addAll(rect, textBox);

        Rectangle clip = new Rectangle(width - 6, height - 6);
        textBox.setClip(clip);

        // Clicking now only shows full objective window, no game logic
        setOnMouseClicked(e -> showFullObjectiveWindow());
    }

    private void showFullObjectiveWindow() {
        Stage stage = new Stage();

        // Left side: objective image placeholder (400 × 560 resolution)
        Rectangle objectiveImage = new Rectangle(400, 560, Color.LIGHTGREEN);
        objectiveImage.setStroke(Color.BLACK);
        objectiveImage.setStrokeWidth(2);
        HBox imageBox = new HBox(objectiveImage);
        imageBox.setAlignment(Pos.CENTER);

        // Top info box: name + flavor
        Label nameLabel = new Label(objective.getName());
        nameLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 18;");
        nameLabel.setWrapText(true);

        Label flavorLabel = new Label(objective.getFlavorText());
        flavorLabel.setStyle("-fx-font-size: 14; -fx-text-fill: gray;");
        flavorLabel.setWrapText(true);

        VBox topInfoContent = new VBox(5, nameLabel, flavorLabel);
        topInfoContent.setAlignment(Pos.TOP_CENTER);
        topInfoContent.setPadding(new Insets(10));
        topInfoContent.setStyle("-fx-border-color: black; -fx-border-width: 1; -fx-background-color: lightgray;");

        // Bottom info box: requirement, prize, punishment
        Label requirementLabel = new Label("Requirement: " + objective.getRequirementDescription());
        requirementLabel.setStyle("-fx-font-size: 14;");
        requirementLabel.setWrapText(true);

        Label prizeLabel = new Label("Prize: " + objective.getPrizeDescription());
        prizeLabel.setStyle("-fx-font-size: 14; -fx-text-fill: darkgreen;");
        prizeLabel.setWrapText(true);

        Label punishmentLabel = new Label("Punishment: " + objective.getPunishmentDescription());
        punishmentLabel.setStyle("-fx-font-size: 14; -fx-text-fill: darkred;");
        punishmentLabel.setWrapText(true);

        VBox bottomInfoContent = new VBox(8, requirementLabel, prizeLabel, punishmentLabel);
        bottomInfoContent.setAlignment(Pos.TOP_LEFT);
        bottomInfoContent.setPadding(new Insets(10));
        bottomInfoContent.setStyle("-fx-border-color: black; -fx-border-width: 1; -fx-background-color: lightgray;");
        VBox.setVgrow(bottomInfoContent, Priority.ALWAYS);

        // Right side: outer border containing both top and bottom boxes
        VBox innerInfo = new VBox(0, topInfoContent, bottomInfoContent);
        innerInfo.setAlignment(Pos.TOP_LEFT);
        innerInfo.setStyle("-fx-border-color: black; -fx-border-width: 1;");

        // Root layout: image + info side by side
        HBox root = new HBox(0, imageBox, innerInfo);
        root.setAlignment(Pos.CENTER);

        Scene scene = new Scene(root, 800, 560); // match height to image resolution

        stage.setTitle(objective.getName());
        stage.setScene(scene);
        stage.setAlwaysOnTop(true);
        stage.show();
    }

    public Objective getObjective() {
        return objective;
    }
}
