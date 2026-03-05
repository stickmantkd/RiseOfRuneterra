package gui.card;

import NonGui.BaseEntity.BaseCard;
import NonGui.BaseEntity.Cards.HeroCard.HeroCard;
import NonGui.BaseEntity.Cards.Itemcard.ItemCard;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import static gui.card.ImageCache.*;

public class CardView extends StackPane {

    // One shared DropShadow object reused by every CardView instance.
    // DropShadow is read-only after construction so sharing is safe.
    private static final DropShadow HOVER_GLOW = new DropShadow(12, Color.web("#FFD700"));
    static { HOVER_GLOW.setSpread(0.3); }

    private final BaseCard card;

    public CardView(BaseCard card, int handIndex) {
        this.card = card;
        buildThumbnail();

        setPrefSize(THUMB_W, THUMB_H);
        setMaxSize(THUMB_W, THUMB_H);
        setAlignment(Pos.CENTER);
        setCursor(Cursor.HAND);

        setOnMouseEntered(e -> { setEffect(HOVER_GLOW); setScaleX(1.08); setScaleY(1.08); });
        setOnMouseExited(e  -> { setEffect(null);        setScaleX(1.0);  setScaleY(1.0);  });

        setOnMouseClicked(e -> {
            Stage heroStage = new FullCardView(card).show();
            if (card instanceof HeroCard hero && hero.getItem() != null) {
                Stage itemStage = new FullCardView(hero.getItem()).show();
                itemStage.setX(heroStage.getX() + heroStage.getWidth() + 10);
                itemStage.setY(heroStage.getY());
            }
        });
    }

    private void buildThumbnail() {
        // Image is fetched from cache (or loaded once and cached) at thumbnail
        // size so the decoder never allocates a full-res buffer for a 75x105 view.
        Image img = ImageCache.get(cardPath(card.getType(), card.getName()), THUMB_W, THUMB_H);

        if (img != null) {
            ImageView iv = new ImageView(img);
            iv.setFitWidth(THUMB_W);
            iv.setFitHeight(THUMB_H);
            iv.setPreserveRatio(false);
            iv.setSmooth(true);

            getChildren().addAll(iv, goldBorder(THUMB_W, THUMB_H, 2, 4));

            if (card instanceof HeroCard hero && hero.getItem() != null) {
                addItemOverlay(hero.getItem());
            }
        } else {
            addFallbackLabel();
        }
    }

    private void addItemOverlay(ItemCard item) {
        Image img = ImageCache.get(itemPath(item.getName()), ITEM_W, ITEM_H);
        if (img == null) return;

        ImageView iv = new ImageView(img);
        iv.setFitWidth(ITEM_W);
        iv.setFitHeight(ITEM_H);
        iv.setPreserveRatio(false);

        Rectangle border = new Rectangle(ITEM_W, ITEM_H, Color.TRANSPARENT);
        border.setStroke(Color.web("#FFD700"));
        border.setStrokeWidth(1.5);
        border.setArcWidth(3);
        border.setArcHeight(3);
        border.setMouseTransparent(true);

        StackPane overlay = new StackPane(iv, border);
        overlay.setPrefSize(ITEM_W, ITEM_H);
        StackPane.setAlignment(overlay, Pos.BOTTOM_RIGHT);
        getChildren().add(overlay);
    }

    private void addFallbackLabel() {
        Rectangle bg = new Rectangle(THUMB_W, THUMB_H, Color.web("#1c0d00"));
        bg.setStroke(Color.web("#8B6914"));
        bg.setStrokeWidth(1.5);
        bg.setArcWidth(4);
        bg.setArcHeight(4);

        Label lbl = new Label(card.getName());
        lbl.setTextFill(Color.web("#C8A870"));
        lbl.setStyle("-fx-font-family: 'Georgia'; -fx-font-size: 9; -fx-font-weight: bold;");
        lbl.setWrapText(true);
        lbl.setAlignment(Pos.CENTER);
        lbl.setMaxWidth(THUMB_W - 8);

        getChildren().addAll(bg, lbl);
    }

    /** Shared factory for the thin gold border overlay used on every card. */
    static Rectangle goldBorder(double w, double h, double strokeW, double arc) {
        Rectangle r = new Rectangle(w, h, Color.TRANSPARENT);
        r.setStroke(Color.web("#8B6914"));
        r.setStrokeWidth(strokeW);
        r.setArcWidth(arc);
        r.setArcHeight(arc);
        r.setMouseTransparent(true);
        return r;
    }

    public BaseCard getCard() { return card; }
}