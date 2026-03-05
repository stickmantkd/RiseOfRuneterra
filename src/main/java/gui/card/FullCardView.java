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

import static gui.card.ImageCache.*;

public class FullCardView {

    private final BaseCard card;

    public FullCardView(BaseCard card) {
        this.card = card;
    }

    public Stage show() {
        Stage stage = new Stage();

        // Load cached image (high resolution but reused)
        Image img = ImageCache.get(cardPath(card.getType(), card.getName()), FULL_W, FULL_H);

        StackPane imagePane;

        if (img != null) {
            ImageView iv = new ImageView(img);

            iv.setFitWidth(FULL_W);
            iv.setFitHeight(FULL_H);

            iv.setPreserveRatio(false);

            // important for quality
            iv.setSmooth(true);
            iv.setCache(true);

            imagePane = new StackPane(iv);
        } else {

            Rectangle rect = new Rectangle(FULL_W, FULL_H, Color.web("#1c0d00"));
            rect.setStroke(Color.web("#8B6914"));
            rect.setStrokeWidth(2);

            Label ph = new Label(card.getName());
            ph.setStyle(
                    "-fx-font-family: 'Georgia';" +
                            "-fx-font-size: 13;" +
                            "-fx-text-fill: #C8A870;"
            );

            ph.setWrapText(true);
            ph.setMaxWidth(FULL_W - 16);

            imagePane = new StackPane(rect, ph);
        }

        imagePane.setAlignment(Pos.CENTER);

        /* ---------- INFO PANEL ---------- */

        Label typeLabel = new Label(card.getType().toUpperCase());
        typeLabel.setStyle(
                "-fx-font-family: 'Georgia';" +
                        "-fx-font-size: 12;" +
                        "-fx-font-weight: bold;" +
                        "-fx-text-fill: #FFD700;" +
                        "-fx-padding: 4 0 4 0;"
        );

        typeLabel.setBackground(new Background(
                new BackgroundFill(Color.web("#2e1800"), CornerRadii.EMPTY, Insets.EMPTY)
        ));

        typeLabel.setMaxWidth(Double.MAX_VALUE);
        typeLabel.setAlignment(Pos.CENTER);

        Label nameLabel = new Label(card.getName());
        nameLabel.setStyle(
                "-fx-font-family: 'Georgia';" +
                        "-fx-font-weight: bold;" +
                        "-fx-font-size: 15;" +
                        "-fx-text-fill: #F5DEB3;"
        );

        nameLabel.setWrapText(true);

        Label flavorLabel = new Label(card.getFlavorText());
        flavorLabel.setStyle(
                "-fx-font-size: 11;" +
                        "-fx-text-fill: #8a7050;" +
                        "-fx-font-style: italic;"
        );

        flavorLabel.setWrapText(true);

        VBox topInfo = new VBox(5, typeLabel, nameLabel, flavorLabel);

        topInfo.setPadding(new Insets(10));
        topInfo.setStyle(
                "-fx-background-color: linear-gradient(to bottom, #2e1800, #1c0d00);" +
                        "-fx-border-color: #5a3a10;" +
                        "-fx-border-width: 0 0 1 0;"
        );

        Label abilityLabel = new Label(card.getAbilityDescription());

        abilityLabel.setStyle(
                "-fx-font-size: 11;" +
                        "-fx-text-fill: #C8A870;" +
                        "-fx-font-family: 'Georgia';"
        );

        abilityLabel.setWrapText(true);

        StackPane bottomInfo = new StackPane();

        VBox abilityBox = new VBox(abilityLabel);
        abilityBox.setAlignment(Pos.TOP_LEFT);

        bottomInfo.getChildren().add(abilityBox);

        /* ---------- HERO EXTRA INFO ---------- */

        if (card instanceof HeroCard hero) {

            Label classLabel = new Label("Class: " + hero.getUnitClass());

            classLabel.setStyle(
                    "-fx-font-family: 'Georgia';" +
                            "-fx-font-size: 11;" +
                            "-fx-font-weight: bold;" +
                            "-fx-text-fill: #FFD700;"
            );

            StackPane.setAlignment(classLabel, Pos.BOTTOM_RIGHT);

            bottomInfo.getChildren().add(classLabel);

            if (hero.getItem() != null) {

                Label itemLabel = new Label("⚔ Equipped: " + hero.getItem().getName());

                itemLabel.setStyle(
                        "-fx-font-family: 'Georgia';" +
                                "-fx-font-size: 11;" +
                                "-fx-font-weight: bold;" +
                                "-fx-text-fill: #50C878;"
                );

                StackPane.setAlignment(itemLabel, Pos.BOTTOM_LEFT);

                bottomInfo.getChildren().add(itemLabel);
            }
        }

        bottomInfo.setPadding(new Insets(10));
        bottomInfo.setStyle(
                "-fx-background-color: linear-gradient(to bottom, #1c0d00, #120800);"
        );

        VBox.setVgrow(bottomInfo, Priority.ALWAYS);

        VBox infoPanel = new VBox(0, topInfo, bottomInfo);

        infoPanel.setStyle(
                "-fx-border-color: #5a3a10;" +
                        "-fx-border-width: 1;"
        );

        HBox root = new HBox(0, imagePane, infoPanel);

        root.setStyle("-fx-background-color: #1a0a00;");

        Scene scene = new Scene(root, 480, (int) FULL_H);
        scene.setFill(Color.web("#1a0a00"));

        stage.setTitle(card.getName());
        stage.setScene(scene);

        stage.setAlwaysOnTop(true);
        stage.setResizable(false);

        stage.show();

        return stage;
    }
}