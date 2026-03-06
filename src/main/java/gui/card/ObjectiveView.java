package gui.card;

import nongui.baseentity.Objective;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import gui.SoundManager;

import static gui.card.ImageCache.*;

/**
 * Represents a graphical thumbnail view of an Objective card.
 * <p>
 * Handles rendering the objective's image, applying hover animations, and responding
 * to click events to display the full-sized objective details (requirements, prizes, and punishments).
 */
public class ObjectiveView extends StackPane {

    // --- Shared Effects & Styles ---
    private static final DropShadow HOVER_GLOW = new DropShadow(12, Color.web("#FFD700"));
    static {
        HOVER_GLOW.setSpread(0.3);
    }

    private static final String NAME_STYLE =
            "-fx-font-family: 'Georgia'; -fx-font-weight: bold; -fx-font-size: 16; -fx-text-fill: #F5DEB3;";

    private static final String FLAVOR_STYLE =
            "-fx-font-size: 12; -fx-text-fill: #8a7050; -fx-font-style: italic;";

    private static final String REQ_STYLE =
            "-fx-font-size: 12; -fx-text-fill: #C8A870; -fx-font-family: 'Georgia';";

    private static final String PRIZE_STYLE =
            "-fx-font-size: 12; -fx-text-fill: #50C878; -fx-font-family: 'Georgia';";

    private static final String PUNISH_STYLE =
            "-fx-font-size: 12; -fx-text-fill: #E05050; -fx-font-family: 'Georgia';";

    private final Objective objective;

    /**
     * Constructs an ObjectiveView thumbnail.
     *
     * @param objective The Objective entity to display.
     * @param index     The index of this objective in the UI (optional use for layout).
     */
    public ObjectiveView(Objective objective, int index) {
        this.objective = objective;

        buildThumbnail();

        setAlignment(Pos.CENTER);
        setCursor(Cursor.HAND);

        setupEventHandlers();
    }

    /**
     * Initializes mouse interaction events (hover glow, scaling, and clicking).
     */
    private void setupEventHandlers() {
        setOnMouseEntered(e -> {
            SoundManager.hover();   // 🔊 hover sound

            setEffect(HOVER_GLOW);
            setScaleX(1.05);
            setScaleY(1.05);
        });

        setOnMouseExited(e -> {
            setEffect(null);
            setScaleX(1.0);
            setScaleY(1.0);
        });

        setOnMouseClicked(e -> {
            SoundManager.click();   // 🔊 click sound\

            showFullObjectiveWindow();
        });
    }

    /**
     * Builds the visual representation of the objective thumbnail.
     */
    private void buildThumbnail() {
        Image img = ImageCache.get(objectivePath(objective.getName()));

        if (img != null) {
            ImageView iv = new ImageView(img);
            iv.setFitWidth(OBJ_THUMB_W);
            iv.setFitHeight(OBJ_THUMB_H);
            iv.setPreserveRatio(false);
            iv.setSmooth(true);
            iv.setCache(true); // GPU cache for better performance

            getChildren().addAll(iv, CardView.createGoldBorder(OBJ_THUMB_W, OBJ_THUMB_H, 2, 4));
        } else {
            Rectangle rect = new Rectangle(OBJ_THUMB_W, OBJ_THUMB_H, Color.web("#1a0a00"));
            rect.setStroke(Color.web("#8B6914"));
            rect.setStrokeWidth(2);
            rect.setArcWidth(4);
            rect.setArcHeight(4);

            Label lbl = new Label("🏆\n" + objective.getName());
            lbl.setStyle(
                    "-fx-font-family: 'Georgia'; -fx-font-size: 11; " +
                            "-fx-font-weight: bold; -fx-text-fill: #FFD700; -fx-text-alignment: center;"
            );
            lbl.setWrapText(true);
            lbl.setAlignment(Pos.CENTER);
            lbl.setMaxWidth(OBJ_THUMB_W - 12);

            getChildren().addAll(rect, lbl);
        }
    }

    /**
     * Creates and shows a new window displaying the full-resolution objective card and its details.
     */
    private void showFullObjectiveWindow() {
        Stage stage = new Stage();

        StackPane imagePane = buildFullImagePane();
        VBox infoPanel = buildInfoPanel();

        HBox root = new HBox(0, imagePane, infoPanel);
        root.setStyle("-fx-background-color: #1a0a00;");

        Scene scene = new Scene(root, 760, (int) OBJ_FULL_H);
        scene.setFill(Color.web("#1a0a00"));

        stage.setTitle("🏆 " + objective.getName());
        stage.setScene(scene);
        stage.setAlwaysOnTop(true);
        stage.setResizable(false);
        stage.show();
    }

    /**
     * Builds the pane containing the full-resolution objective image or a fallback placeholder.
     */
    private StackPane buildFullImagePane() {
        Image img = ImageCache.get(objectivePath(objective.getName()));
        StackPane imagePane;

        if (img != null) {
            ImageView iv = new ImageView(img);
            iv.setFitWidth(OBJ_FULL_W);
            iv.setFitHeight(OBJ_FULL_H);
            iv.setPreserveRatio(false);
            iv.setSmooth(true);
            iv.setCache(true);

            imagePane = new StackPane(iv);
        } else {
            Rectangle rect = new Rectangle(OBJ_FULL_W, OBJ_FULL_H, Color.web("#1a0a00"));
            rect.setStroke(Color.web("#8B6914"));
            rect.setStrokeWidth(3);

            Label placeholder = new Label("🏆\n" + objective.getName());
            placeholder.setStyle(
                    "-fx-font-family: 'Georgia'; -fx-font-size: 14; " +
                            "-fx-text-fill: #FFD700; -fx-text-alignment: center;"
            );
            placeholder.setWrapText(true);

            imagePane = new StackPane(rect, placeholder);
        }

        imagePane.setAlignment(Pos.CENTER);
        return imagePane;
    }

    /**
     * Assembles the information panel containing top (name/flavor) and bottom (requirements/effects) details.
     */
    private VBox buildInfoPanel() {
        VBox topInfo = buildTopInfo();
        VBox bottomInfo = buildBottomInfo();

        VBox.setVgrow(bottomInfo, Priority.ALWAYS);

        VBox infoPanel = new VBox(0, topInfo, bottomInfo);
        infoPanel.setStyle("-fx-border-color: #5a3a10; -fx-border-width: 1;");

        return infoPanel;
    }

    /**
     * Builds the top section of the info panel with the objective's name and flavor text.
     */
    private VBox buildTopInfo() {
        Label nameLabel = new Label(objective.getName());
        nameLabel.setStyle(NAME_STYLE);
        nameLabel.setWrapText(true);

        Label flavorLabel = new Label(objective.getFlavorText());
        flavorLabel.setStyle(FLAVOR_STYLE);
        flavorLabel.setWrapText(true);

        VBox topInfo = new VBox(6, nameLabel, flavorLabel);
        topInfo.setPadding(new Insets(10));
        topInfo.setAlignment(Pos.TOP_CENTER);
        topInfo.setStyle(
                "-fx-background-color: linear-gradient(to bottom, #2e1800, #1c0d00);" +
                        "-fx-border-color: #5a3a10; -fx-border-width: 0 0 1 0;"
        );

        return topInfo;
    }

    /**
     * Builds the bottom section of the info panel detailing requirements, prizes, and punishments.
     */
    private VBox buildBottomInfo() {
        Label reqLabel = new Label("📋  Requirement: " + objective.getRequirementDescription());
        reqLabel.setStyle(REQ_STYLE);
        reqLabel.setWrapText(true);

        Label prizeLabel = new Label("✨  Prize: " + objective.getPrizeDescription());
        prizeLabel.setStyle(PRIZE_STYLE);
        prizeLabel.setWrapText(true);

        Label punishLabel = new Label("💀  Punishment: " + objective.getPunishmentDescription());
        punishLabel.setStyle(PUNISH_STYLE);
        punishLabel.setWrapText(true);

        VBox bottomInfo = new VBox(10, reqLabel, prizeLabel, punishLabel);
        bottomInfo.setPadding(new Insets(12));
        bottomInfo.setAlignment(Pos.TOP_LEFT);
        bottomInfo.setStyle("-fx-background-color: linear-gradient(to bottom, #1c0d00, #120800);");

        return bottomInfo;
    }

    /**
     * Gets the underlying Objective data.
     *
     * @return The Objective entity.
     */
    public Objective getObjective() {
        return objective;
    }
}