package gui.card;

import NonGui.BaseEntity.BaseCard;
import NonGui.BaseEntity.Cards.HeroCard.HeroCard;
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

public class FullCardView {

    private final BaseCard card;

    public FullCardView(BaseCard card) {
        this.card = card;
    }

    public Stage show() {
        Stage stage = new Stage();

        double fullWidth  = 200;
        double fullHeight = 280;

        String resourcePath = "/card/base/" + card.getType().toLowerCase() + "/"
                + card.getName().replaceAll("\\s+", "") + ".png";
        java.net.URL resource = getClass().getResource(resourcePath);

        StackPane imagePane;
        if (resource != null) {
            ImageView cardImage = new ImageView(new Image(resource.toExternalForm()));
            cardImage.setFitWidth(fullWidth);
            cardImage.setFitHeight(fullHeight);
            cardImage.setPreserveRatio(false);
            imagePane = new StackPane(cardImage);
        } else {
            Rectangle rect = new Rectangle(fullWidth, fullHeight, Color.web("#1c0d00"));
            rect.setStroke(Color.web("#8B6914"));
            rect.setStrokeWidth(2);
            Label placeholder = new Label(card.getName());
            placeholder.setStyle("-fx-font-family: 'Georgia'; -fx-font-size: 13; -fx-text-fill: #C8A870;");
            placeholder.setWrapText(true);
            placeholder.setMaxWidth(fullWidth - 16);
            imagePane = new StackPane(rect, placeholder);
        }
        imagePane.setAlignment(Pos.CENTER);

        // Type label
        Label typeLabel = new Label(card.getType().toUpperCase());
        typeLabel.setStyle(
                "-fx-font-family: 'Georgia'; -fx-font-size: 12; -fx-font-weight: bold;" +
                        "-fx-text-fill: #FFD700; -fx-padding: 4 0 4 0;"
        );
        typeLabel.setBackground(new Background(new BackgroundFill(
                Color.web("#2e1800"), CornerRadii.EMPTY, Insets.EMPTY
        )));
        typeLabel.setMaxWidth(Double.MAX_VALUE);
        typeLabel.setAlignment(Pos.CENTER);

        // Name
        Label nameLabel = new Label(card.getName());
        nameLabel.setStyle(
                "-fx-font-family: 'Georgia'; -fx-font-weight: bold; -fx-font-size: 15;" +
                        "-fx-text-fill: #F5DEB3;"
        );
        nameLabel.setWrapText(true);

        // Flavor text
        Label flavorLabel = new Label(card.getFlavorText());
        flavorLabel.setStyle("-fx-font-size: 11; -fx-text-fill: #8a7050; -fx-font-style: italic;");
        flavorLabel.setWrapText(true);

        VBox topInfo = new VBox(5, typeLabel, nameLabel, flavorLabel);
        topInfo.setPadding(new Insets(10));
        topInfo.setAlignment(Pos.TOP_LEFT);
        topInfo.setStyle(
                "-fx-background-color: linear-gradient(to bottom, #2e1800, #1c0d00);" +
                        "-fx-border-color: #5a3a10; -fx-border-width: 0 0 1 0;"
        );

        // Ability
        Label abilityLabel = new Label(card.getAbilityDescription());
        abilityLabel.setStyle("-fx-font-size: 11; -fx-text-fill: #C8A870; -fx-font-family: 'Georgia';");
        abilityLabel.setWrapText(true);

        StackPane bottomInfo = new StackPane();
        VBox abilityBox = new VBox(abilityLabel);
        abilityBox.setAlignment(Pos.TOP_LEFT);
        bottomInfo.getChildren().add(abilityBox);

        if (card instanceof HeroCard hero) {
            Label classLabel = new Label("Class: " + hero.getUnitClass());
            classLabel.setStyle(
                    "-fx-font-family: 'Georgia'; -fx-font-size: 11; -fx-font-weight: bold; -fx-text-fill: #FFD700;"
            );
            StackPane.setAlignment(classLabel, Pos.BOTTOM_RIGHT);
            bottomInfo.getChildren().add(classLabel);

            if (hero.getItem() != null) {
                Label itemLabel = new Label("⚔ Equipped: " + hero.getItem().getName());
                itemLabel.setStyle(
                        "-fx-font-family: 'Georgia'; -fx-font-size: 11; -fx-font-weight: bold; -fx-text-fill: #50C878;"
                );
                StackPane.setAlignment(itemLabel, Pos.BOTTOM_LEFT);
                bottomInfo.getChildren().add(itemLabel);
            }
        }

        bottomInfo.setPadding(new Insets(10));
        bottomInfo.setStyle("-fx-background-color: linear-gradient(to bottom, #1c0d00, #120800);");
        VBox.setVgrow(bottomInfo, Priority.ALWAYS);

        VBox innerInfo = new VBox(0, topInfo, bottomInfo);
        innerInfo.setStyle("-fx-border-color: #5a3a10; -fx-border-width: 1;");

        HBox root = new HBox(0, imagePane, innerInfo);
        root.setAlignment(Pos.CENTER);
        root.setStyle("-fx-background-color: #1a0a00;");

        Scene scene = new Scene(root, 480, 280);
        scene.setFill(Color.web("#1a0a00"));

        stage.setTitle(card.getName());
        stage.setScene(scene);
        stage.setAlwaysOnTop(true);
        stage.show();

        return stage;
    }
}