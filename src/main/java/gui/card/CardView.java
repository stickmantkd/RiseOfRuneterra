package gui.card;

import NonGui.BaseEntity.BaseCard;
import NonGui.BaseEntity.Cards.HeroCard.HeroCard;
import NonGui.BaseEntity.Cards.Itemcard.ItemCard;
import javafx.geometry.Pos;
import javafx.scene.effect.DropShadow;
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

        double thumbWidth  = 75;
        double thumbHeight = 105;

        String resourcePath = "/card/base/" + card.getType().toLowerCase() + "/"
                + card.getName().replaceAll("\\s+", "") + ".png";
        java.net.URL resource = getClass().getResource(resourcePath);

        if (resource != null) {
            ImageView thumbnail = new ImageView(new Image(resource.toExternalForm()));
            thumbnail.setFitWidth(thumbWidth);
            thumbnail.setFitHeight(thumbHeight);
            thumbnail.setPreserveRatio(false);

            // Gold border rectangle
            Rectangle border = new Rectangle(thumbWidth, thumbHeight);
            border.setFill(Color.TRANSPARENT);
            border.setStroke(Color.web("#8B6914"));
            border.setStrokeWidth(2);
            border.setArcWidth(4);
            border.setArcHeight(4);

            getChildren().addAll(thumbnail, border);

            // Item overlay (bottom-right)
            if (card instanceof HeroCard hero && hero.getItem() != null) {
                ItemCard item = hero.getItem();
                String itemPath = "/card/base/item card/" + item.getName().replaceAll("\\s+", "") + ".png";
                java.net.URL itemRes = getClass().getResource(itemPath);
                if (itemRes != null) {
                    ImageView itemThumb = new ImageView(new Image(itemRes.toExternalForm()));
                    itemThumb.setFitWidth(26);
                    itemThumb.setFitHeight(36);
                    itemThumb.setPreserveRatio(false);

                    Rectangle itemBorder = new Rectangle(26, 36, Color.TRANSPARENT);
                    itemBorder.setStroke(Color.web("#FFD700"));
                    itemBorder.setStrokeWidth(1.5);
                    itemBorder.setArcWidth(3);
                    itemBorder.setArcHeight(3);

                    StackPane itemPane = new StackPane(itemBorder, itemThumb);
                    itemPane.setPrefSize(26, 36);
                    StackPane.setAlignment(itemPane, Pos.BOTTOM_RIGHT);
                    getChildren().add(itemPane);
                }
            }
        } else {
            // Fallback: styled blank card
            Rectangle rect = new Rectangle(thumbWidth, thumbHeight, Color.web("#1c0d00"));
            rect.setStroke(Color.web("#8B6914"));
            rect.setStrokeWidth(1.5);
            rect.setArcWidth(4);
            rect.setArcHeight(4);

            javafx.scene.control.Label nameLabel = new javafx.scene.control.Label(card.getName());
            nameLabel.setTextFill(Color.web("#C8A870"));
            nameLabel.setStyle("-fx-font-family: 'Georgia'; -fx-font-size: 9; -fx-font-weight: bold;");
            nameLabel.setWrapText(true);
            nameLabel.setAlignment(Pos.CENTER);
            nameLabel.setMaxWidth(thumbWidth - 8);

            StackPane textPane = new StackPane(nameLabel);
            textPane.setPrefSize(thumbWidth, thumbHeight);

            getChildren().addAll(rect, textPane);
        }

        setPrefSize(thumbWidth, thumbHeight);
        setAlignment(Pos.CENTER);
        setCursor(javafx.scene.Cursor.HAND);

        // Hover glow effect
        DropShadow glow = new DropShadow(12, Color.web("#FFD700"));
        glow.setSpread(0.3);

        setOnMouseEntered(e -> {
            setEffect(glow);
            setScaleX(1.08);
            setScaleY(1.08);
        });
        setOnMouseExited(e -> {
            setEffect(null);
            setScaleX(1.0);
            setScaleY(1.0);
        });

        // Click: show full card(s)
        setOnMouseClicked(e -> {
            Stage heroStage = new FullCardView(card).show();
            if (card instanceof HeroCard hero && hero.getItem() != null) {
                Stage itemStage = new FullCardView(hero.getItem()).show();
                itemStage.setX(heroStage.getX() + heroStage.getWidth() + 10);
                itemStage.setY(heroStage.getY());
            }
        });
    }

    public BaseCard getCard() { return card; }
}