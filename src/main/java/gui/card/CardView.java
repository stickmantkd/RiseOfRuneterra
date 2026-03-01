package gui.card;

import NonGui.BaseEntity.BaseCard;
import NonGui.BaseEntity.Cards.HeroCard.HeroCard;
import NonGui.BaseEntity.Cards.Itemcard.ItemCard;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
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

            // If HeroCard has an item, overlay it bottom-right (25x35 fixed)
            if (card instanceof HeroCard hero && hero.getItem() != null) {
                ItemCard item = hero.getItem();
                String itemPath = "/card/base/item card/" + item.getName().replaceAll("\\s+", "") + ".png";
                java.net.URL itemRes = getClass().getResource(itemPath);
                if (itemRes != null) {
                    ImageView itemThumb = new ImageView(new Image(itemRes.toExternalForm()));
                    itemThumb.setFitWidth(25);
                    itemThumb.setFitHeight(35);
                    itemThumb.setPreserveRatio(false);

                    // Add a border rectangle behind the item for clarity
                    Rectangle border = new Rectangle(25, 35, Color.TRANSPARENT);
                    border.setStroke(Color.WHITE);
                    border.setStrokeWidth(2);

                    StackPane itemPane = new StackPane(border, itemThumb);
                    itemPane.setPrefSize(25, 35);

                    // Align bottom-right inside the same StackPane
                    StackPane.setAlignment(itemPane, Pos.BOTTOM_RIGHT);
                    getChildren().add(itemPane);
                }
            }
        } else {
            // Blank card with border and fill
            Rectangle rect = new Rectangle(thumbWidth, thumbHeight, Color.WHITESMOKE);
            rect.setStroke(Color.BLACK);
            rect.setStrokeWidth(1);

            // Add card name label
            javafx.scene.control.Label nameLabel = new javafx.scene.control.Label(card.getName());
            nameLabel.setTextFill(Color.BLACK);
            nameLabel.setStyle("-fx-font-size: 10; -fx-font-weight: bold;"); // small bold text
            nameLabel.setWrapText(true);
            nameLabel.setAlignment(Pos.CENTER);

            StackPane textPane = new StackPane(nameLabel);
            textPane.setPrefSize(thumbWidth, thumbHeight);

            getChildren().addAll(rect, textPane);
        }

        setPrefSize(thumbWidth, thumbHeight);
        setAlignment(Pos.CENTER);

        // On click: show TWO FullCardView windows (hero + item)
        setOnMouseClicked(e -> {
            Stage heroStage = new FullCardView(card).show();

            if (card instanceof HeroCard hero && hero.getItem() != null) {
                Stage itemStage = new FullCardView(hero.getItem()).show();
                // Position item window slightly to the right of hero window
                itemStage.setX(heroStage.getX() + heroStage.getWidth() + 10);
                itemStage.setY(heroStage.getY());
            }
        });
    }

    public BaseCard getCard() {
        return card;
    }
}
