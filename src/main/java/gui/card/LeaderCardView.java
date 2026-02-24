package gui.card;

import NonGui.BaseEntity.LeaderCard;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class LeaderCardView extends StackPane {

    private final LeaderCard leader;

    public LeaderCardView(LeaderCard leader) {
        this.leader = leader;

        double width = 90;
        double height = 126;

        Rectangle rect = new Rectangle(width, height, Color.LIGHTBLUE);
        rect.setStroke(Color.BLACK);
        rect.setStrokeWidth(1);

        Label nameLabel = new Label(leader.getName());
        nameLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 12;");
        nameLabel.setWrapText(true);
        nameLabel.setMaxWidth(width - 6);

        Label flavorLabel = new Label(leader.getFlavorText());
        flavorLabel.setStyle("-fx-font-size: 10; -fx-text-fill: gray;");
        flavorLabel.setWrapText(true);
        flavorLabel.setMaxWidth(width - 6);

        Label abilityLabel = new Label(leader.getAbilityDescription());
        abilityLabel.setStyle("-fx-font-size: 10;");
        abilityLabel.setWrapText(true);
        abilityLabel.setMaxWidth(width - 6);

        VBox textBox = new VBox(4, nameLabel, flavorLabel, abilityLabel);
        textBox.setAlignment(Pos.TOP_CENTER);
        textBox.setMaxWidth(width - 4);
        textBox.setMaxHeight(height - 4);

        setAlignment(Pos.CENTER);
        getChildren().addAll(rect, textBox);

        Rectangle clip = new Rectangle(width - 4, height - 4);
        textBox.setClip(clip);

        // Click to show full leader card
        setOnMouseClicked(e -> showFullLeaderWindow());
    }

    private void showFullLeaderWindow() {
        Stage stage = new Stage();

        // Left side: leader image placeholder (240 × 336 resolution)
        Rectangle leaderImage = new Rectangle(240, 336, Color.LIGHTBLUE);
        leaderImage.setStroke(Color.BLACK);
        leaderImage.setStrokeWidth(2);
        HBox imageBox = new HBox(leaderImage);
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
        Label abilityLabel = new Label(leader.getAbilityDescription());
        abilityLabel.setStyle("-fx-font-size: 12;");
        abilityLabel.setWrapText(true);

        Label classLabel = new Label("Class: " + leader.getUnitClass());
        classLabel.setStyle("-fx-font-size: 12; -fx-font-weight: bold; -fx-text-fill: black;");

        // Use a StackPane so classLabel can be anchored bottom-right
        StackPane bottomInfoContent = new StackPane();

        VBox abilityBox = new VBox(abilityLabel);
        abilityBox.setAlignment(Pos.TOP_LEFT);

        StackPane.setAlignment(classLabel, Pos.BOTTOM_RIGHT);

        bottomInfoContent.getChildren().addAll(abilityBox, classLabel);
        bottomInfoContent.setPadding(new Insets(10));
        bottomInfoContent.setStyle("-fx-border-color: black; -fx-border-width: 1; -fx-background-color: lightgray;");
        VBox.setVgrow(bottomInfoContent, Priority.ALWAYS);

        // Right side: outer border containing both top and bottom boxes
        VBox innerInfo = new VBox(0, topInfoContent, bottomInfoContent);
        innerInfo.setAlignment(Pos.TOP_LEFT);
        innerInfo.setStyle("-fx-border-color: black; -fx-border-width: 1;");

        // Root layout: image + info side by side
        HBox root = new HBox(20, imageBox, innerInfo);
        root.setAlignment(Pos.CENTER);

        Scene scene = new Scene(root, 520, 336); // match height to image resolution

        stage.setTitle(leader.getName());
        stage.setScene(scene);
        stage.setAlwaysOnTop(true);
        stage.show();
    }

    public LeaderCard getLeader() {
        return leader;
    }
}
