package gui.card;

import NonGui.BaseEntity.Objective;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
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

        double thumbWidth  = 150;
        double thumbHeight = 210;

        String resourcePath = "/card/objective/" + objective.getName().replaceAll("\\s+", "") + ".png";
        java.net.URL resource = getClass().getResource(resourcePath);

        if (resource != null) {
            ImageView thumbnail = new ImageView(new Image(resource.toExternalForm()));
            thumbnail.setFitWidth(thumbWidth);
            thumbnail.setFitHeight(thumbHeight);
            thumbnail.setPreserveRatio(false);

            Rectangle border = new Rectangle(thumbWidth, thumbHeight);
            border.setFill(Color.TRANSPARENT);
            border.setStroke(Color.web("#8B6914"));
            border.setStrokeWidth(2);
            border.setArcWidth(4);
            border.setArcHeight(4);

            getChildren().addAll(thumbnail, border);
        } else {
            Rectangle rect = new Rectangle(thumbWidth, thumbHeight, Color.web("#1a0a00"));
            rect.setStroke(Color.web("#8B6914"));
            rect.setStrokeWidth(2);
            rect.setArcWidth(4);
            rect.setArcHeight(4);

            Label nameLabel = new Label("🏆\n" + objective.getName());
            nameLabel.setStyle(
                    "-fx-font-family: 'Georgia'; -fx-font-size: 11; -fx-font-weight: bold;" +
                            "-fx-text-fill: #FFD700; -fx-text-alignment: center;"
            );
            nameLabel.setWrapText(true);
            nameLabel.setAlignment(Pos.CENTER);
            nameLabel.setMaxWidth(thumbWidth - 12);

            StackPane textPane = new StackPane(nameLabel);
            textPane.setPrefSize(thumbWidth, thumbHeight);
            getChildren().addAll(rect, textPane);
        }

        setAlignment(Pos.CENTER);
        setCursor(javafx.scene.Cursor.HAND);

        DropShadow glow = new DropShadow(12, Color.web("#FFD700"));
        glow.setSpread(0.3);
        setOnMouseEntered(e -> { setEffect(glow); setScaleX(1.05); setScaleY(1.05); });
        setOnMouseExited(e  -> { setEffect(null);  setScaleX(1.0);  setScaleY(1.0);  });

        setOnMouseClicked(e -> showFullObjectiveWindow());
    }

    private void showFullObjectiveWindow() {
        Stage stage = new Stage();

        double fullWidth  = 360;
        double fullHeight = 504;

        String resourcePath = "/card/objective/" + objective.getName().replaceAll("\\s+", "") + ".png";
        java.net.URL resource = getClass().getResource(resourcePath);

        StackPane imagePane;
        if (resource != null) {
            ImageView objectiveImage = new ImageView(new Image(resource.toExternalForm()));
            objectiveImage.setFitWidth(fullWidth);
            objectiveImage.setFitHeight(fullHeight);
            objectiveImage.setPreserveRatio(false);
            imagePane = new StackPane(objectiveImage);
        } else {
            Rectangle rect = new Rectangle(fullWidth, fullHeight, Color.web("#1a0a00"));
            rect.setStroke(Color.web("#8B6914"));
            rect.setStrokeWidth(3);
            Label placeholder = new Label("🏆\n" + objective.getName());
            placeholder.setStyle("-fx-font-family: 'Georgia'; -fx-font-size: 14; -fx-text-fill: #FFD700; -fx-text-alignment: center;");
            placeholder.setWrapText(true);
            imagePane = new StackPane(rect, placeholder);
        }
        imagePane.setAlignment(Pos.CENTER);

        // Name & flavor
        Label nameLabel = new Label(objective.getName());
        nameLabel.setStyle(
                "-fx-font-family: 'Georgia'; -fx-font-weight: bold; -fx-font-size: 16; -fx-text-fill: #F5DEB3;"
        );
        nameLabel.setWrapText(true);

        Label flavorLabel = new Label(objective.getFlavorText());
        flavorLabel.setStyle("-fx-font-size: 12; -fx-text-fill: #8a7050; -fx-font-style: italic;");
        flavorLabel.setWrapText(true);

        VBox topInfo = new VBox(6, nameLabel, flavorLabel);
        topInfo.setPadding(new Insets(10));
        topInfo.setAlignment(Pos.TOP_CENTER);
        topInfo.setStyle(
                "-fx-background-color: linear-gradient(to bottom, #2e1800, #1c0d00);" +
                        "-fx-border-color: #5a3a10; -fx-border-width: 0 0 1 0;"
        );

        // Requirement / prize / punishment
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

        VBox innerInfo = new VBox(0, topInfo, bottomInfo);
        innerInfo.setStyle("-fx-border-color: #5a3a10; -fx-border-width: 1;");

        HBox root = new HBox(0, imagePane, innerInfo);
        root.setStyle("-fx-background-color: #1a0a00;");

        Scene scene = new Scene(root, 760, 504);
        scene.setFill(Color.web("#1a0a00"));

        stage.setTitle("🏆 " + objective.getName());
        stage.setScene(scene);
        stage.setAlwaysOnTop(true);
        stage.show();
    }

    public Objective getObjective() { return objective; }
}