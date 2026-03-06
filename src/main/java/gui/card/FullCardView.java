package gui.card;

import nongui.baseentity.BaseCard;
import nongui.baseentity.cards.HeroCard.HeroCard;
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

/**
 * Provides a detailed, full-screen pop-up view for a single card.
 * <p>
 * This view displays the high-resolution artwork alongside comprehensive data,
 * including flavor text, ability descriptions, and dynamic hero states such
 * as equipped items and class types.
 * * @author GeminiCollaborator
 */
public class FullCardView {

    private final BaseCard card;

    // --- Layout Constants ---
    private static final double VIEW_WIDTH = 480;
    private static final String FONT_GEORGIA = "Georgia";
    private static final String COLOR_GOLD = "#FFD700";
    private static final String COLOR_WHEAT = "#F5DEB3";
    private static final String COLOR_BRONZE = "#C8A870";
    private static final String COLOR_FLAVOR = "#8a7050";

    /**
     * Constructs a FullCardView for the specified card.
     * @param card The card data to be displayed in detail.
     */
    public FullCardView(BaseCard card) {
        this.card = card;
    }

    /**
     * Creates and shows a new Stage (window) displaying the card's details.
     * @return The Stage object created for this view.
     */
    public Stage show() {
        Stage stage = new Stage();

        // --- 1. Artwork Section ---
        StackPane imagePane = buildImagePane();

        // --- 2. Information Panel Section ---
        VBox infoPanel = buildInfoPanel();

        // --- 3. Root Assembly ---
        HBox root = new HBox(0, imagePane, infoPanel);
        root.setStyle("-fx-background-color: #1a0a00;");

        Scene scene = new Scene(root, VIEW_WIDTH, FULL_H);
        scene.setFill(Color.web("#1a0a00"));

        stage.setTitle(card.getName());
        stage.setScene(scene);
        stage.setAlwaysOnTop(true);
        stage.setResizable(false);
        stage.show();

        return stage;
    }

    /**
     * Builds the container for the card's artwork, including a placeholder if the image fails to load.
     */
    private StackPane buildImagePane() {
        Image img = ImageCache.get(cardPath(card.getType(), card.getName()));
        StackPane pane;

        if (img != null) {
            ImageView iv = new ImageView(img);
            iv.setFitWidth(FULL_W);
            iv.setFitHeight(FULL_H);
            iv.setPreserveRatio(false);
            iv.setSmooth(true);
            pane = new StackPane(iv);
        } else {
            Rectangle rect = new Rectangle(FULL_W, FULL_H, Color.web("#1c0d00"));
            rect.setStroke(Color.web("#8B6914"));
            rect.setStrokeWidth(2);

            Label placeholder = new Label(card.getName());
            placeholder.setStyle("-fx-font-family: '" + FONT_GEORGIA + "'; -fx-font-size: 13; -fx-text-fill: " + COLOR_BRONZE + ";");
            placeholder.setWrapText(true);
            placeholder.setMaxWidth(FULL_W - 16);
            placeholder.setAlignment(Pos.CENTER);

            pane = new StackPane(rect, placeholder);
        }
        pane.setAlignment(Pos.CENTER);
        return pane;
    }

    /**
     * Assembles the multi-layered info panel containing card statistics and descriptions.
     */
    private VBox buildInfoPanel() {
        // Top Header: Type, Name, and Flavor
        Label typeLabel = new Label(card.getType().toUpperCase());
        typeLabel.setStyle("-fx-font-family: '" + FONT_GEORGIA + "'; -fx-font-size: 12; -fx-font-weight: bold; " +
                "-fx-text-fill: " + COLOR_GOLD + "; -fx-padding: 4 0 4 0;");
        typeLabel.setBackground(new Background(new BackgroundFill(Color.web("#2e1800"), CornerRadii.EMPTY, Insets.EMPTY)));
        typeLabel.setMaxWidth(Double.MAX_VALUE);
        typeLabel.setAlignment(Pos.CENTER);

        Label nameLabel = new Label(card.getName());
        nameLabel.setStyle("-fx-font-family: '" + FONT_GEORGIA + "'; -fx-font-weight: bold; -fx-font-size: 15; -fx-text-fill: " + COLOR_WHEAT + ";");
        nameLabel.setWrapText(true);

        Label flavorLabel = new Label(card.getFlavorText());
        flavorLabel.setStyle("-fx-font-size: 11; -fx-text-fill: " + COLOR_FLAVOR + "; -fx-font-style: italic;");
        flavorLabel.setWrapText(true);

        VBox topInfo = new VBox(5, typeLabel, nameLabel, flavorLabel);
        topInfo.setPadding(new Insets(10));
        topInfo.setStyle("-fx-background-color: linear-gradient(to bottom, #2e1800, #1c0d00); " +
                "-fx-border-color: #5a3a10; -fx-border-width: 0 0 1 0;");

        // Bottom Section: Abilities and Dynamic Stats
        Label abilityLabel = new Label(card.getAbilityDescription());
        abilityLabel.setStyle("-fx-font-size: 11; -fx-text-fill: " + COLOR_BRONZE + "; -fx-font-family: '" + FONT_GEORGIA + "';");
        abilityLabel.setWrapText(true);

        StackPane bottomInfo = new StackPane();
        VBox abilityBox = new VBox(abilityLabel);
        abilityBox.setAlignment(Pos.TOP_LEFT);
        bottomInfo.getChildren().add(abilityBox);

        // Hero-specific overlays (Class and Equipped Items)
        if (card instanceof HeroCard hero) {
            appendHeroDetails(bottomInfo, hero);
        }

        bottomInfo.setPadding(new Insets(10));
        bottomInfo.setStyle("-fx-background-color: linear-gradient(to bottom, #1c0d00, #120800);");
        VBox.setVgrow(bottomInfo, Priority.ALWAYS);

        VBox infoPanel = new VBox(0, topInfo, bottomInfo);
        infoPanel.setStyle("-fx-border-color: #5a3a10; -fx-border-width: 1;");
        return infoPanel;
    }

    /**
     * Helper to inject specific Hero attributes like Class and Item status into the view.
     */
    private void appendHeroDetails(StackPane container, HeroCard hero) {
        Label classLabel = new Label("Class: " + hero.getUnitClass());
        classLabel.setStyle("-fx-font-family: '" + FONT_GEORGIA + "'; -fx-font-size: 11; -fx-font-weight: bold; -fx-text-fill: " + COLOR_GOLD + ";");
        StackPane.setAlignment(classLabel, Pos.BOTTOM_RIGHT);

        container.getChildren().add(classLabel);

        if (hero.getItem() != null) {
            Label itemLabel = new Label("⚔ Equipped: " + hero.getItem().getName());
            itemLabel.setStyle("-fx-font-family: '" + FONT_GEORGIA + "'; -fx-font-size: 11; -fx-font-weight: bold; -fx-text-fill: #50C878;");
            StackPane.setAlignment(itemLabel, Pos.BOTTOM_LEFT);
            container.getChildren().add(itemLabel);
        }
    }
}