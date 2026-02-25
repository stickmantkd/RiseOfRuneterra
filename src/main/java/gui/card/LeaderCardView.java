package gui.card;

import NonGui.BaseEntity.LeaderCard;
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

public class LeaderCardView extends StackPane {

    private final LeaderCard leader;

    public LeaderCardView(LeaderCard leader) {
        this.leader = leader;

        double thumbWidth = 90;
        double thumbHeight = 126;

        // Build resource path from leader name
        String resourcePath = "/card/leader/" + leader.getName().replaceAll("\\s+", "") + ".png";
        java.net.URL resource = getClass().getResource(resourcePath);

        if (resource != null) {
            ImageView thumbnail = new ImageView(new Image(resource.toExternalForm()));
            thumbnail.setFitWidth(thumbWidth);
            thumbnail.setFitHeight(thumbHeight);
            thumbnail.setPreserveRatio(false); // stretch to fill
            getChildren().add(thumbnail);
        } else {
            // Blank card with border and fill
            Rectangle rect = new Rectangle(thumbWidth, thumbHeight, Color.LIGHTBLUE);
            rect.setStroke(Color.BLACK);
            rect.setStrokeWidth(1);
            getChildren().add(rect);
        }

        setAlignment(Pos.CENTER);

        // Click to show full leader card
        setOnMouseClicked(e -> showFullLeaderWindow());
    }

    private void showFullLeaderWindow() {
        Stage stage = new Stage();

        double fullWidth = 240;
        double fullHeight = 336;

        String resourcePath = "/card/leader/" + leader.getName().replaceAll("\\s+", "") + ".png";
        java.net.URL resource = getClass().getResource(resourcePath);

        HBox imageBox;
        if (resource != null) {
            ImageView leaderImage = new ImageView(new Image(resource.toExternalForm()));
            leaderImage.setFitWidth(fullWidth);
            leaderImage.setFitHeight(fullHeight);
            leaderImage.setPreserveRatio(false); // stretch to fill
            imageBox = new HBox(leaderImage);
        } else {
            Rectangle rect = new Rectangle(fullWidth, fullHeight, Color.LIGHTBLUE);
            rect.setStroke(Color.BLACK);
            rect.setStrokeWidth(2);
            imageBox = new HBox(rect);
        }
        imageBox.setAlignment(Pos.CENTER);

        // Top info box: type + name + flavor
        Label typeLabel = new Label("Leader Card");
        typeLabel.setStyle("-fx-font-size: 14; -fx-font-weight: bold; -fx-text-fill: white;");
        typeLabel.setBackground(new Background(new BackgroundFill(Color.DARKBLUE, CornerRadii.EMPTY, Insets.EMPTY)));
        typeLabel.setMaxWidth(Double.MAX_VALUE);
        typeLabel.setAlignment(Pos.CENTER);

        Label nameLabel = new Label(leader.getName());
        nameLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 16;");
        nameLabel.setWrapText(true);

        Label flavorLabel = new Label(leader.getFlavorText());
        flavorLabel.setStyle("-fx-font-size: 12; -fx-text-fill: gray;");
        flavorLabel.setWrapText(true);

        VBox topInfoContent = new VBox(5, typeLabel, nameLabel, flavorLabel);
        topInfoContent.setAlignment(Pos.TOP_CENTER);
        topInfoContent.setPadding(new Insets(10));
        topInfoContent.setStyle("-fx-border-color: black; -fx-border-width: 1; -fx-background-color: lightgray;");

        // Bottom info box: ability + class
        Label abilityLabel = new Label("Ability: " + leader.getAbilityDescription());
        abilityLabel.setStyle("-fx-font-size: 12;");
        abilityLabel.setWrapText(true);

        Label classLabel = new Label("Class: " + leader.getUnitClass());
        classLabel.setStyle("-fx-font-size: 12; -fx-font-weight: bold; -fx-text-fill: black;");

        StackPane bottomInfoContent = new StackPane();
        VBox abilityBox = new VBox(abilityLabel);
        abilityBox.setAlignment(Pos.TOP_LEFT);

        StackPane.setAlignment(classLabel, Pos.BOTTOM_RIGHT);
        bottomInfoContent.getChildren().addAll(abilityBox, classLabel);

        bottomInfoContent.setPadding(new Insets(10));
        bottomInfoContent.setStyle("-fx-border-color: black; -fx-border-width: 1; -fx-background-color: lightgray;");
        VBox.setVgrow(bottomInfoContent, Priority.ALWAYS);

        VBox innerInfo = new VBox(0, topInfoContent, bottomInfoContent);
        innerInfo.setAlignment(Pos.TOP_LEFT);
        innerInfo.setStyle("-fx-border-color: black; -fx-border-width: 1;");

        HBox root = new HBox(0, imageBox, innerInfo);
        root.setAlignment(Pos.CENTER);

        Scene scene = new Scene(root, 520, 336);

        stage.setTitle(leader.getName());
        stage.setScene(scene);
        stage.setAlwaysOnTop(true);
        stage.show();
    }

    public LeaderCard getLeader() {
        return leader;
    }
}
