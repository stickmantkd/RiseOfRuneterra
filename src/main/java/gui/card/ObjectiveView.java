package gui.card;

import NonGui.BaseEntity.Objective;
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

public class ObjectiveView extends StackPane {

    private final Objective objective;

    public ObjectiveView(Objective objective, int index) {
        this.objective = objective;

        double thumbWidth = 150;
        double thumbHeight = 210;

        String resourcePath = "/card/objective/" + objective.getName().replaceAll("\\s+", "") + ".png";
        java.net.URL resource = getClass().getResource(resourcePath);

        ImageView thumbnail = new ImageView();
        if (resource != null) {
            thumbnail.setImage(new Image(resource.toExternalForm()));
            thumbnail.setFitWidth(thumbWidth);
            thumbnail.setFitHeight(thumbHeight);
            thumbnail.setPreserveRatio(false); // stretch to fill
            getChildren().add(thumbnail);
        } else {
            // Blank card with border and fill
            Rectangle rect = new Rectangle(thumbWidth, thumbHeight, Color.LIGHTGRAY);
            rect.setStroke(Color.BLACK);
            rect.setStrokeWidth(2);
            getChildren().add(rect);
        }

        setAlignment(Pos.CENTER);

        // Clicking shows full objective window
        setOnMouseClicked(e -> showFullObjectiveWindow());
    }

    private void showFullObjectiveWindow() {
        Stage stage = new Stage();

        double fullWidth = 400;
        double fullHeight = 560;

        String resourcePath = "/card/objective/" + objective.getName().replaceAll("\\s+", "") + ".png";
        java.net.URL resource = getClass().getResource(resourcePath);

        ImageView objectiveImage = new ImageView();
        if (resource != null) {
            objectiveImage.setImage(new Image(resource.toExternalForm()));
            objectiveImage.setFitWidth(fullWidth);
            objectiveImage.setFitHeight(fullHeight);
            objectiveImage.setPreserveRatio(false); // stretch to fill
        } else {
            // Blank card with border and fill
            Rectangle rect = new Rectangle(fullWidth, fullHeight, Color.LIGHTGRAY);
            rect.setStroke(Color.BLACK);
            rect.setStrokeWidth(3);
            objectiveImage = new ImageView(); // empty image view
            HBox imageBox = new HBox(rect);
            imageBox.setAlignment(Pos.CENTER);
        }

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

        VBox innerInfo = new VBox(0, topInfoContent, bottomInfoContent);
        innerInfo.setAlignment(Pos.TOP_LEFT);
        innerInfo.setStyle("-fx-border-color: black; -fx-border-width: 1;");

        HBox root = new HBox(0, imageBox, innerInfo);
        root.setAlignment(Pos.CENTER);

        Scene scene = new Scene(root, 800, 560);

        stage.setTitle(objective.getName());
        stage.setScene(scene);
        stage.setAlwaysOnTop(true);
        stage.show();
    }

    public Objective getObjective() {
        return objective;
    }
}
