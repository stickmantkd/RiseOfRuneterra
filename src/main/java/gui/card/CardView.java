package gui.card;

import nongui.baseentity.BaseCard;
import nongui.baseentity.cards.HeroCard.HeroCard;
import nongui.baseentity.cards.Itemcard.ItemCard;
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

/**
 * A graphical thumbnail representation of a game card.
 * <p>
 * CardView handles hover animations, gold-bordered thumbnails, and
 * item overlays for Hero cards. When clicked, it triggers a {@link FullCardView}
 * to show detailed information.
 * * @author GeminiCollaborator
 */
public class CardView extends StackPane {

    // --- Shared Visual Constants ---
    private static final DropShadow HOVER_GLOW = new DropShadow(12, Color.web("#FFD700"));
    private static final String FONT_GEORGIA = "Georgia";
    private static final double HOVER_SCALE = 1.08;

    static {
        HOVER_GLOW.setSpread(0.3);
    }

    private final BaseCard card;

    /**
     * Constructs a thumbnail view for a card.
     * * @param card      The card data to display.
     * @param handIndex The position in hand (use -1 if the card is on the board).
     */
    public CardView(BaseCard card, int handIndex) {
        this.card = card;

        setupBaseLayout();
        buildThumbnail();
        setupInteraction();
    }

    /**
     * Configures dimensions, alignment, and basic styling for the StackPane.
     */
    private void setupBaseLayout() {
        setPrefSize(THUMB_W, THUMB_H);
        setMaxSize(THUMB_W, THUMB_H);
        setAlignment(Pos.CENTER);
        setCursor(Cursor.HAND);
    }

    /**
     * Defines mouse enter, exit, and click behaviors.
     */
    private void setupInteraction() {
        // Hover Effects
        setOnMouseEntered(e -> {
            setEffect(HOVER_GLOW);
            setScaleX(HOVER_SCALE);
            setScaleY(HOVER_SCALE);
        });

        setOnMouseExited(e -> {
            setEffect(null);
            setScaleX(1.0);
            setScaleY(1.0);
        });

        // Click Logic: Shows full card view (and item view if equipped)
        setOnMouseClicked(e -> {
            Stage mainStage = new FullCardView(card).show();

            // If it's a Hero with an item, pop up the item detail next to it
            if (card instanceof HeroCard hero && hero.getItem() != null) {
                Stage itemStage = new FullCardView(hero.getItem()).show();

                // Position item stage to the right of the hero stage
                itemStage.setX(mainStage.getX() + mainStage.getWidth() + 10);
                itemStage.setY(mainStage.getY());
            }
        });
    }

    /**
     * Fetches the image from cache and assembles the visual layers of the card.
     */
    private void buildThumbnail() {
        Image img = ImageCache.get(cardPath(card.getType(), card.getName()));

        if (img != null) {
            ImageView iv = new ImageView(img);
            iv.setFitWidth(THUMB_W);
            iv.setFitHeight(THUMB_H);
            iv.setPreserveRatio(false);
            iv.setSmooth(true);

            getChildren().addAll(iv, createGoldBorder(THUMB_W, THUMB_H, 2, 4));

            // Overlay item icon if hero has equipment
            if (card instanceof HeroCard hero && hero.getItem() != null) {
                addItemOverlay(hero.getItem());
            }
        } else {
            addFallbackDisplay();
        }
    }

    /**
     * Adds a small square icon in the bottom-right corner if an item is equipped.
     */
    private void addItemOverlay(ItemCard item) {
        Image img = ImageCache.get(itemPath(item.getName()));
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

    /**
     * Displays a text-based placeholder if the card image asset is missing.
     */
    private void addFallbackDisplay() {
        Rectangle bg = new Rectangle(THUMB_W, THUMB_H, Color.web("#1c0d00"));
        bg.setStroke(Color.web("#8B6914"));
        bg.setStrokeWidth(1.5);
        bg.setArcWidth(4);
        bg.setArcHeight(4);

        Label lbl = new Label(card.getName());
        lbl.setTextFill(Color.web("#C8A870"));
        lbl.setStyle("-fx-font-family: '" + FONT_GEORGIA + "'; -fx-font-size: 9; -fx-font-weight: bold;");
        lbl.setWrapText(true);
        lbl.setAlignment(Pos.CENTER);
        lbl.setMaxWidth(THUMB_W - 8);

        getChildren().addAll(bg, lbl);
    }

    /**
     * Helper factory for creating consistent gold borders across different card types.
     */
    static Rectangle createGoldBorder(double w, double h, double strokeW, double arc) {
        Rectangle r = new Rectangle(w, h, Color.TRANSPARENT);
        r.setStroke(Color.web("#8B6914"));
        r.setStrokeWidth(strokeW);
        r.setArcWidth(arc);
        r.setArcHeight(arc);
        r.setMouseTransparent(true);
        return r;
    }

    /** @return The base card data associated with this view. */
    public BaseCard getCard() {
        return card;
    }
}