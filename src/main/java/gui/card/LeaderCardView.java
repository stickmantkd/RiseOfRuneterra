package gui.card;

import NonGui.BaseEntity.LeaderCard;
import NonGui.BaseEntity.Objective;
import NonGui.BaseEntity.Player;
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

public class LeaderCardView extends StackPane {

    private static final DropShadow HOVER_GLOW = new DropShadow(14, Color.web("#FFD700"));
    static { HOVER_GLOW.setSpread(0.4); }

    private final LeaderCard leader;
    private final Player owner;

    public LeaderCardView(LeaderCard leader, Player owner) {
        this.leader = leader;
        this.owner  = owner;

        buildThumbnail();

        setAlignment(Pos.CENTER);
        setCursor(Cursor.HAND);

        setOnMouseEntered(e -> { setEffect(HOVER_GLOW); setScaleX(1.06); setScaleY(1.06); });
        setOnMouseExited(e  -> { setEffect(null);        setScaleX(1.0);  setScaleY(1.0);  });

        setOnMouseClicked(e -> {
            showFullLeaderWindow();
            if (owner != null) showOwnedObjectivesWindow(owner);
        });
    }

    private void buildThumbnail() {
        Image img = ImageCache.get(leaderPath(leader.getName()));

        if (img != null) {
            ImageView iv = new ImageView(img);
            iv.setFitWidth(LEADER_W);
            iv.setFitHeight(LEADER_H);
            iv.setPreserveRatio(false);
            iv.setSmooth(true);
            getChildren().addAll(iv, CardView.goldBorder(LEADER_W, LEADER_H, 2, 4));
        } else {
            Rectangle rect = new Rectangle(LEADER_W, LEADER_H, Color.web("#1a0a00"));
            rect.setStroke(Color.web("#FFD700"));
            rect.setStrokeWidth(2);
            rect.setArcWidth(4);
            rect.setArcHeight(4);

            Label lbl = new Label(leader.getName());
            lbl.setStyle(
                    "-fx-font-family: 'Georgia'; -fx-font-size: 9; -fx-font-weight: bold; -fx-text-fill: #FFD700;");
            lbl.setWrapText(true);
            lbl.setAlignment(Pos.CENTER);

            getChildren().addAll(rect, lbl);
        }
    }

    private void showFullLeaderWindow() {
        Stage stage = new Stage();
        Image img = ImageCache.get(leaderPath(leader.getName()));

        StackPane imagePane;
        if (img != null) {
            ImageView iv = new ImageView(img);
            iv.setFitWidth(FULL_LEADER_W);
            iv.setFitHeight(FULL_LEADER_H);
            iv.setPreserveRatio(false);
            iv.setSmooth(true);
            imagePane = new StackPane(iv);
        } else {
            Rectangle rect = new Rectangle(FULL_LEADER_W, FULL_LEADER_H, Color.web("#1a0a00"));
            rect.setStroke(Color.web("#FFD700"));
            rect.setStrokeWidth(2);
            imagePane = new StackPane(rect);
        }
        imagePane.setAlignment(Pos.CENTER);

        Label typeLabel = new Label("LEADER CARD");
        typeLabel.setStyle(
                "-fx-font-family: 'Georgia'; -fx-font-size: 12; -fx-font-weight: bold;" +
                        "-fx-text-fill: #FFD700; -fx-padding: 4 0 4 0;");
        typeLabel.setBackground(new Background(new BackgroundFill(
                Color.web("#2e1800"), CornerRadii.EMPTY, Insets.EMPTY)));
        typeLabel.setMaxWidth(Double.MAX_VALUE);
        typeLabel.setAlignment(Pos.CENTER);

        Label nameLabel = new Label(leader.getName());
        nameLabel.setStyle(
                "-fx-font-family: 'Georgia'; -fx-font-weight: bold; -fx-font-size: 15; -fx-text-fill: #F5DEB3;");
        nameLabel.setWrapText(true);

        Label flavorLabel = new Label(leader.getFlavorText());
        flavorLabel.setStyle("-fx-font-size: 11; -fx-text-fill: #8a7050; -fx-font-style: italic;");
        flavorLabel.setWrapText(true);

        VBox topInfo = new VBox(5, typeLabel, nameLabel, flavorLabel);
        topInfo.setPadding(new Insets(10));
        topInfo.setStyle(
                "-fx-background-color: linear-gradient(to bottom, #2e1800, #1c0d00);" +
                        "-fx-border-color: #5a3a10; -fx-border-width: 0 0 1 0;");

        Label abilityLabel = new Label("Ability: " + leader.getAbilityDescription());
        abilityLabel.setStyle("-fx-font-size: 11; -fx-text-fill: #C8A870; -fx-font-family: 'Georgia';");
        abilityLabel.setWrapText(true);

        Label classLabel = new Label("Class: " + leader.getUnitClass());
        classLabel.setStyle(
                "-fx-font-family: 'Georgia'; -fx-font-size: 11; -fx-font-weight: bold; -fx-text-fill: #FFD700;");

        StackPane bottomInfo = new StackPane();
        VBox abilityBox = new VBox(abilityLabel);
        abilityBox.setAlignment(Pos.TOP_LEFT);
        StackPane.setAlignment(classLabel, Pos.BOTTOM_RIGHT);
        bottomInfo.getChildren().addAll(abilityBox, classLabel);
        bottomInfo.setPadding(new Insets(10));
        bottomInfo.setStyle("-fx-background-color: linear-gradient(to bottom, #1c0d00, #120800);");
        VBox.setVgrow(bottomInfo, Priority.ALWAYS);

        VBox infoPanel = new VBox(0, topInfo, bottomInfo);
        infoPanel.setStyle("-fx-border-color: #5a3a10; -fx-border-width: 1;");

        HBox root = new HBox(0, imagePane, infoPanel);
        root.setStyle("-fx-background-color: #1a0a00;");

        Scene scene = new Scene(root, 520, (int) FULL_LEADER_H);
        scene.setFill(Color.web("#1a0a00"));

        stage.setTitle(leader.getName());
        stage.setScene(scene);
        stage.setAlwaysOnTop(true);
        stage.setResizable(false);
        stage.show();
    }

    private void showOwnedObjectivesWindow(Player player) {
        Stage stage = new Stage();
        HBox root = new HBox(12);
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(16));
        root.setStyle("-fx-background-color: linear-gradient(to bottom, #1c0d00, #2e1800);");

        Objective[] objs = player.getOwnedObjectives();
        for (int i = 0; i < 3; i++) {
            if (objs[i] != null) {
                root.getChildren().add(new ObjectiveView(objs[i], i));
            } else {
                StackPane blank = new StackPane();
                Rectangle r = new Rectangle(OBJ_THUMB_W, OBJ_THUMB_H, Color.web("#1a0a00"));
                r.setStroke(Color.web("#5a3a10"));
                r.setStrokeWidth(1.5);
                r.setArcWidth(4);
                r.setArcHeight(4);
                Label empty = new Label("Empty Slot");
                empty.setStyle("-fx-font-family: 'Georgia'; -fx-font-size: 10; -fx-text-fill: #5a3a10;");
                blank.getChildren().addAll(r, empty);
                root.getChildren().add(blank);
            }
        }

        Scene scene = new Scene(root, 530, 270);
        scene.setFill(Color.web("#1c0d00"));
        stage.setTitle(player.getName() + " — Objectives");
        stage.setScene(scene);
        stage.setAlwaysOnTop(true);
        stage.setResizable(false);
        stage.show();
    }

    public LeaderCard getLeader() { return leader; }
}