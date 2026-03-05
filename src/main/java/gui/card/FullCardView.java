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

/**
 * Represents the detailed, full-sized view of a card.
 * <p>
 * This class creates a standalone window (Stage) that displays the card's
 * high-resolution artwork alongside its complete details, such as type,
 * flavor text, abilities, and specific hero attributes.
 */
public class FullCardView {

    // --- Styles ---
    private static final String TYPE_STYLE =
            "-fx-font-family: 'Georgia'; -fx-font-size: 12; -fx-font-weight: bold; " +
                    "-fx-text-fill: #FFD700; -fx-padding: 4 0 4 0;";

    private static final String NAME_STYLE =
            "-fx-font-family: 'Georgia'; -fx-font-weight: bold; -fx-font-size: 15; " +
                    "-fx-text-fill: #F5DEB3;";

    private static final String FLAVOR_STYLE =
            "-fx-font-size: 11; -fx-text-fill: #8a7050; -fx-font-style: italic;";

    private static final String ABILITY_STYLE =
            "-fx-font-family: 'Georgia'; -fx-font-size: 11; -fx-text-fill: #C8A870;";

    private static final String CLASS_STYLE =
            "-fx-font-family: 'Georgia'; -fx-font-size: 11; -fx-font-weight: bold; " +
                    "-fx-text-fill: #FFD700;";

    private static final String ITEM_STYLE =
            "-fx-font-family: 'Georgia'; -fx-font-size: 11; -fx-font-weight: bold; " +
                    "-fx-text-fill: #50C878;";

    private final BaseCard card;

    /**
     * Constructs a FullCardView for the specified card.
     *
     * @param card The base card entity to display.
     */
    public FullCardView(BaseCard card) {
        this.card = card;
    }

    /**
     * Builds and displays the full card details in a new Stage.
     *
     * @return The generated Stage containing the full card UI.
     */
    public Stage show() {
        Stage stage = new Stage();

        StackPane imagePane = buildImagePane();
        VBox infoPanel = buildInfoPanel();

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

    /**
     * Builds the pane containing the full-resolution card image or a fallback placeholder.
     */
    private StackPane buildImagePane() {
        Image img = ImageCache.get(cardPath(card.getType(), card.getName()));
        StackPane imagePane;

        if (img != null) {
            ImageView iv = new ImageView(img);
            iv.setFitWidth(FULL_W);
            iv.setFitHeight(FULL_H);
            iv.setPreserveRatio(false);
            iv.setSmooth(true);
            iv.setCache(true);

            imagePane = new StackPane(iv);
        } else {
            Rectangle rect = new Rectangle(FULL_W, FULL_H, Color.web("#1c0d00"));
            rect.setStroke(Color.web("#8B6914"));
            rect.setStrokeWidth(2);

            Label placeholder = new Label(card.getName());
            placeholder.setStyle("-fx-font-family: 'Georgia'; -fx-font-size: 13; -fx-text-fill: #C8A870;");
            placeholder.setWrapText(true);
            placeholder.setMaxWidth(FULL_W - 16);

            imagePane = new StackPane(rect, placeholder);
        }

        imagePane.setAlignment(Pos.CENTER);
        return imagePane;
    }

    /**
     * Assembles the entire information panel (top and bottom sections).
     */
    private VBox buildInfoPanel() {
        VBox topInfo = buildTopInfo();
        StackPane bottomInfo = buildBottomInfo();

        VBox.setVgrow(bottomInfo, Priority.ALWAYS);

        VBox infoPanel = new VBox(0, topInfo, bottomInfo);
        infoPanel.setStyle("-fx-border-color: #5a3a10; -fx-border-width: 1;");

        return infoPanel;
    }

    /**
     * Builds the upper section of the info panel, containing Type, Name, and Flavor Text.
     */
    private VBox buildTopInfo() {
        Label typeLabel = new Label(card.getType().toUpperCase());
        typeLabel.setStyle(TYPE_STYLE);
        typeLabel.setBackground(new Background(
                new BackgroundFill(Color.web("#2e1800"), CornerRadii.EMPTY, Insets.EMPTY)
        ));
        typeLabel.setMaxWidth(Double.MAX_VALUE);
        typeLabel.setAlignment(Pos.CENTER);

        Label nameLabel = new Label(card.getName());
        nameLabel.setStyle(NAME_STYLE);
        nameLabel.setWrapText(true);

        Label flavorLabel = new Label(card.getFlavorText());
        flavorLabel.setStyle(FLAVOR_STYLE);
        flavorLabel.setWrapText(true);

        VBox topInfo = new VBox(5, typeLabel, nameLabel, flavorLabel);
        topInfo.setPadding(new Insets(10));
        topInfo.setStyle(
                "-fx-background-color: linear-gradient(to bottom, #2e1800, #1c0d00);" +
                        "-fx-border-color: #5a3a10;" +
                        "-fx-border-width: 0 0 1 0;"
        );

        return topInfo;
    }

    /**
     * Builds the lower section of the info panel, containing abilities and hero-specific info.
     */
    private StackPane buildBottomInfo() {
        Label abilityLabel = new Label(card.getAbilityDescription());
        abilityLabel.setStyle(ABILITY_STYLE);
        abilityLabel.setWrapText(true);

        VBox abilityBox = new VBox(abilityLabel);
        abilityBox.setAlignment(Pos.TOP_LEFT);

        StackPane bottomInfo = new StackPane(abilityBox);

        // Append hero-specific information if applicable
        if (card instanceof HeroCard hero) {
            Label classLabel = new Label("Class: " + hero.getUnitClass());
            classLabel.setStyle(CLASS_STYLE);
            StackPane.setAlignment(classLabel, Pos.BOTTOM_RIGHT);
            bottomInfo.getChildren().add(classLabel);

            if (hero.getItem() != null) {
                Label itemLabel = new Label("⚔ Equipped: " + hero.getItem().getName());
                itemLabel.setStyle(ITEM_STYLE);
                StackPane.setAlignment(itemLabel, Pos.BOTTOM_LEFT);
                bottomInfo.getChildren().add(itemLabel);
            }
        }

        bottomInfo.setPadding(new Insets(10));
        bottomInfo.setStyle("-fx-background-color: linear-gradient(to bottom, #1c0d00, #120800);");

        return bottomInfo;
    }
}