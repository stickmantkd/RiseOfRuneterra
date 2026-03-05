package gui.card;

import NonGui.BaseEntity.Objective;
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

import static gui.card.ImageCache.*;

public class ObjectiveView extends StackPane {

    private static final DropShadow HOVER_GLOW = new DropShadow(12, Color.web("#FFD700"));
    static { HOVER_GLOW.setSpread(0.3); }

    private final Objective objective;

    public ObjectiveView(Objective objective, int index) {
        this.objective = objective;

        buildThumbnail();

        setAlignment(Pos.CENTER);
        setCursor(Cursor.HAND);

        setOnMouseEntered(e -> { setEffect(HOVER_GLOW); setScaleX(1.05); setScaleY(1.05); });
        setOnMouseExited(e  -> { setEffect(null);        setScaleX(1.0);  setScaleY(1.0);  });

        setOnMouseClicked(e -> showFullObjectiveWindow());
    }

    private void buildThumbnail() {
        Image img = ImageCache.get(objectivePath(objective.getName()), OBJ_THUMB_W, OBJ_THUMB_H);

        if (img != null) {
            ImageView iv = new ImageView(img);
            iv.setFitWidth(OBJ_THUMB_W);
            iv.setFitHeight(OBJ_THUMB_H);
            iv.setPreserveRatio(false);
            iv.setSmooth(true);
            getChildren().addAll(iv, CardView.goldBorder(OBJ_THUMB_W, OBJ_THUMB_H, 2, 4));
        } else {
            Rectangle rect = new Rectangle(OBJ_THUMB_W, OBJ_THUMB_H, Color.web("#1a0a00"));
            rect.setStroke(Color.web("#8B6914"));
            rect.setStrokeWidth(2);
            rect.setArcWidth(4);
            rect.setArcHeight(4);

            Label lbl = new Label("🏆\n" + objective.getName());
            lbl.setStyle(
                    "-fx-font-family: 'Georgia'; -fx-font-size: 11; -fx-font-weight: bold;" +
                            "-fx-text-fill: #FFD700; -fx-text-alignment: center;");
            lbl.setWrapText(true);
            lbl.setAlignment(Pos.CENTER);
            lbl.setMaxWidth(OBJ_THUMB_W - 12);

            getChildren().addAll(rect, lbl);
        }
    }

    private void showFullObjectiveWindow() {
        Stage stage = new Stage();
        Image img = ImageCache.get(objectivePath(objective.getName()), OBJ_FULL_W, OBJ_FULL_H);

        StackPane imagePane;
        if (img != null) {
            ImageView iv = new ImageView(img);
            iv.setFitWidth(OBJ_FULL_W);
            iv.setFitHeight(OBJ_FULL_H);
            iv.setPreserveRatio(false);
            iv.setSmooth(true);
            imagePane = new StackPane(iv);
        } else {
            Rectangle rect = new Rectangle(OBJ_FULL_W, OBJ_FULL_H, Color.web("#1a0a00"));
            rect.setStroke(Color.web("#8B6914"));
            rect.setStrokeWidth(3);
            Label ph = new Label("🏆\n" + objective.getName());
            ph.setStyle(
                    "-fx-font-family: 'Georgia'; -fx-font-size: 14; -fx-text-fill: #FFD700; -fx-text-alignment: center;");
            ph.setWrapText(true);
            imagePane = new StackPane(rect, ph);
        }
        imagePane.setAlignment(Pos.CENTER);

        // ── Info panel ──────────────────────────────────────────────────────

        Label nameLabel = new Label(objective.getName());
        nameLabel.setStyle(
                "-fx-font-family: 'Georgia'; -fx-font-weight: bold; -fx-font-size: 16; -fx-text-fill: #F5DEB3;");
        nameLabel.setWrapText(true);

        Label flavorLabel = new Label(objective.getFlavorText());
        flavorLabel.setStyle("-fx-font-size: 12; -fx-text-fill: #8a7050; -fx-font-style: italic;");
        flavorLabel.setWrapText(true);

        VBox topInfo = new VBox(6, nameLabel, flavorLabel);
        topInfo.setPadding(new Insets(10));
        topInfo.setAlignment(Pos.TOP_CENTER);
        topInfo.setStyle(
                "-fx-background-color: linear-gradient(to bottom, #2e1800, #1c0d00);" +
                        "-fx-border-color: #5a3a10; -fx-border-width: 0 0 1 0;");

        Label reqLabel = new Label("📋  Requirement: " + objective.getRequirementDescription());
        reqLabel.setStyle("-fx-font-size: 12; -fx-text-fill: #C8A870; -fx-font-family: 'Georgia';");
        reqLabel.setWrapText(true);

        Label prizeLabel = new Label("✨  Prize: " + objective.getPrizeDescription());
        prizeLabel.setStyle("-fx-font-size: 12; -fx-text-fill: #50C878; -fx-font-family: 'Georgia';");
        prizeLabel.setWrapText(true);

        Label punishLabel = new Label("💀  Punishment: " + objective.getPunishmentDescription());
        punishLabel.setStyle("-fx-font-size: 12; -fx-text-fill: #E05050; -fx-font-family: 'Georgia';");
        punishLabel.setWrapText(true);

        VBox bottomInfo = new VBox(10, reqLabel, prizeLabel, punishLabel);
        bottomInfo.setPadding(new Insets(12));
        bottomInfo.setAlignment(Pos.TOP_LEFT);
        bottomInfo.setStyle("-fx-background-color: linear-gradient(to bottom, #1c0d00, #120800);");
        VBox.setVgrow(bottomInfo, Priority.ALWAYS);

        VBox infoPanel = new VBox(0, topInfo, bottomInfo);
        infoPanel.setStyle("-fx-border-color: #5a3a10; -fx-border-width: 1;");

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

    public Objective getObjective() { return objective; }
}