package gui.card;

import NonGui.BaseEntity.BaseCard;
import NonGui.BaseEntity.Cards.HeroCard.HeroCard;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class CardView extends StackPane {

    private final BaseCard card;
    private final int handIndex;

    public CardView(BaseCard card, int handIndex) {
        this.card = card;
        this.handIndex = handIndex;

        double width = 75;
        double height = 105;

        Rectangle rect = new Rectangle(width, height, Color.WHITESMOKE);
        rect.setStroke(Color.BLACK);
        rect.setStrokeWidth(1);

        Label nameLabel = new Label(card.getName());
        nameLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 10;");
        nameLabel.setWrapText(true);
        nameLabel.setMaxWidth(width - 6);

        Label flavorLabel = new Label(card.getFlavorText());
        flavorLabel.setStyle("-fx-font-size: 8; -fx-text-fill: gray;");
        flavorLabel.setWrapText(true);
        flavorLabel.setMaxWidth(width - 6);

        Label abilityLabel = new Label(card.getAbilityDescription());
        abilityLabel.setStyle("-fx-font-size: 8;");
        abilityLabel.setWrapText(true);
        abilityLabel.setMaxWidth(width - 6);

        VBox textBox = new VBox(2, nameLabel, flavorLabel, abilityLabel);
        textBox.setAlignment(Pos.TOP_CENTER);
        textBox.setMaxWidth(width - 4);
        textBox.setMaxHeight(height - 4);

        setAlignment(Pos.CENTER);
        getChildren().addAll(rect, textBox);

        Rectangle clip = new Rectangle(width - 4, height - 4);
        textBox.setClip(clip);

        // On click: show full card in new window
        setOnMouseClicked(e -> showFullCardWindow());
    }

    private void showFullCardWindow() {
        Stage stage = new Stage();

        // Left side: card image placeholder
        Rectangle cardImage = new Rectangle(200, 280, Color.WHITESMOKE);
        cardImage.setStroke(Color.BLACK);
        cardImage.setStrokeWidth(2);
        HBox imageBox = new HBox(cardImage);
        imageBox.setAlignment(Pos.CENTER);

        // Top info box: type + name + flavor
        Label typeLabel = new Label(card.getType());
        typeLabel.setStyle("-fx-font-size: 14; -fx-font-weight: bold; -fx-text-fill: white;");
        typeLabel.setBackground(new Background(new BackgroundFill(Color.DARKBLUE, CornerRadii.EMPTY, Insets.EMPTY)));
        typeLabel.setMaxWidth(Double.MAX_VALUE);
        typeLabel.setAlignment(Pos.CENTER);

        Label nameLabel = new Label(card.getName());
        nameLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 16;");
        nameLabel.setWrapText(true);

        Label flavorLabel = new Label(card.getFlavorText());
        flavorLabel.setStyle("-fx-font-size: 12; -fx-text-fill: gray;");
        flavorLabel.setWrapText(true);

        VBox topInfoContent = new VBox(5, typeLabel, nameLabel, flavorLabel);
        topInfoContent.setAlignment(Pos.TOP_CENTER);
        topInfoContent.setPadding(new Insets(10));
        topInfoContent.setStyle("-fx-border-color: black; -fx-border-width: 1; -fx-background-color: lightgray;");

        // Bottom info box: ability (+ class if HeroCard)
        Label abilityLabel = new Label(card.getAbilityDescription());
        abilityLabel.setStyle("-fx-font-size: 12;");
        abilityLabel.setWrapText(true);

        StackPane bottomInfoContent = new StackPane();
        VBox abilityBox = new VBox(abilityLabel);
        abilityBox.setAlignment(Pos.TOP_LEFT);

        bottomInfoContent.getChildren().add(abilityBox);

        // Only add class label if this card is a HeroCard
        if (card instanceof HeroCard hero) {
            Label classLabel = new Label("Class: " + hero.getUnitClass());
            classLabel.setStyle("-fx-font-size: 12; -fx-font-weight: bold; -fx-text-fill: black;");
            StackPane.setAlignment(classLabel, Pos.BOTTOM_RIGHT);
            bottomInfoContent.getChildren().add(classLabel);
        }

        bottomInfoContent.setPadding(new Insets(10));
        bottomInfoContent.setStyle("-fx-border-color: black; -fx-border-width: 1; -fx-background-color: lightgray;");
        VBox.setVgrow(bottomInfoContent, Priority.ALWAYS);

        // Right side: outer border containing both top and bottom boxes
        VBox innerInfo = new VBox(0, topInfoContent, bottomInfoContent);
        innerInfo.setAlignment(Pos.TOP_LEFT);
        innerInfo.setStyle("-fx-border-color: black; -fx-border-width: 1;");

        // Root layout: image + info side by side
        HBox root = new HBox(0, imageBox, innerInfo);
        root.setAlignment(Pos.CENTER);

        Scene scene = new Scene(root, 480, 280);

        stage.setTitle(card.getName());
        stage.setScene(scene);
        stage.setAlwaysOnTop(true);
        stage.show();
    }

    public BaseCard getCard() {
        return card;
    }
}
