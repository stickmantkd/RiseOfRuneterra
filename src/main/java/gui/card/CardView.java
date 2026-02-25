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

public class CardView extends StackPane {

    private final BaseCard card;
    private final int handIndex;

    public CardView(BaseCard card, int handIndex) {
        this.card = card;
        this.handIndex = handIndex;

        double thumbWidth = 75;
        double thumbHeight = 105;

        // Build resource path from card name
        String resourcePath = "/card/base/" + card.getType().toLowerCase() + "/"
                + card.getName().replaceAll("\\s+", "") + ".png";
        java.net.URL resource = getClass().getResource(resourcePath);

        if (resource != null) {
            ImageView thumbnail = new ImageView(new Image(resource.toExternalForm()));
            thumbnail.setFitWidth(thumbWidth);
            thumbnail.setFitHeight(thumbHeight);
            thumbnail.setPreserveRatio(false); // stretch to fill
            getChildren().add(thumbnail);
        } else {
            // Blank card with border and fill
            Rectangle rect = new Rectangle(thumbWidth, thumbHeight, Color.WHITESMOKE);
            rect.setStroke(Color.BLACK);
            rect.setStrokeWidth(1);
            getChildren().add(rect);
        }

        setAlignment(Pos.CENTER);

        // On click: show full card in new window
        setOnMouseClicked(e -> showFullCardWindow());
    }

    private void showFullCardWindow() {
        Stage stage = new Stage();

        double fullWidth = 200;
        double fullHeight = 280;

        // Build resource path again
        String resourcePath = "/card/base/" + card.getType().toLowerCase() + "/"
                + card.getName().replaceAll("\\s+", "") + ".png";
        java.net.URL resource = getClass().getResource(resourcePath);

        HBox imageBox;
        if (resource != null) {
            ImageView cardImage = new ImageView(new Image(resource.toExternalForm()));
            cardImage.setFitWidth(fullWidth);
            cardImage.setFitHeight(fullHeight);
            cardImage.setPreserveRatio(false); // stretch to fill
            imageBox = new HBox(cardImage);
        } else {
            Rectangle rect = new Rectangle(fullWidth, fullHeight, Color.WHITESMOKE);
            rect.setStroke(Color.BLACK);
            rect.setStrokeWidth(2);
            imageBox = new HBox(rect);
        }
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

        if (card instanceof HeroCard hero) {
            Label classLabel = new Label("Class: " + hero.getUnitClass());
            classLabel.setStyle("-fx-font-size: 12; -fx-font-weight: bold; -fx-text-fill: black;");
            StackPane.setAlignment(classLabel, Pos.BOTTOM_RIGHT);
            bottomInfoContent.getChildren().add(classLabel);
        }

        bottomInfoContent.setPadding(new Insets(10));
        bottomInfoContent.setStyle("-fx-border-color: black; -fx-border-width: 1; -fx-background-color: lightgray;");
        VBox.setVgrow(bottomInfoContent, Priority.ALWAYS);

        VBox innerInfo = new VBox(0, topInfoContent, bottomInfoContent);
        innerInfo.setAlignment(Pos.TOP_LEFT);
        innerInfo.setStyle("-fx-border-color: black; -fx-border-width: 1;");

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
