package gui.card;

import NonGui.BaseEntity.BaseCard;
import NonGui.BaseEntity.Cards.HeroCard.HeroCard;
import NonGui.BaseEntity.Cards.Itemcard.ItemCard;
import javafx.geometry.Pos;
import javafx.scene.CacheHint;
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
 * Represents a graphical thumbnail view of a card in the game.
 * <p>
 * Handles rendering the card's image, applying hover animations, and responding
 * to click events to display the full-sized card (and its equipped item, if any).
 */
public class CardView extends StackPane {

    // --- Shared Effects ---
    private static final DropShadow HOVER_GLOW = new DropShadow(12, Color.web("#FFD700"));

    static {
        HOVER_GLOW.setSpread(0.3);
    }

    private final BaseCard card;

    /**
     * Constructs a new CardView thumbnail.
     *
     * @param card      The base card data to represent.
     * @param handIndex The index of this card in the player's hand (can be used for specific game logic).
     */
    public CardView(BaseCard card, int handIndex) {
        this.card = card;

        buildThumbnail();

        // Sizing and layout setup
        setPrefSize(THUMB_W, THUMB_H);
        setMaxSize(THUMB_W, THUMB_H);
        setAlignment(Pos.CENTER);
        setCursor(Cursor.HAND);

        // GPU cache for smoother scaling animations
        setCache(true);
        setCacheHint(CacheHint.SPEED);

        setupEventHandlers();
    }

    /**
     * Initializes mouse interaction events (hover glow, scaling, and clicking).
     */
    private void setupEventHandlers() {
        setOnMouseEntered(e -> {
            setEffect(HOVER_GLOW);
            setScaleX(1.08);
            setScaleY(1.08);
        });

        setOnMouseExited(e -> {
            setEffect(null);
            setScaleX(1.0);
            setScaleY(1.0);
        });

        setOnMouseClicked(e -> {
            Stage heroStage = new FullCardView(card).show();

            // If it's a HeroCard with an equipped item, show the item side-by-side
            if (card instanceof HeroCard hero && hero.getItem() != null) {
                Stage itemStage = new FullCardView(hero.getItem()).show();
                itemStage.setX(heroStage.getX() + heroStage.getWidth() + 10);
                itemStage.setY(heroStage.getY());
            }
        });
    }

    /**
     * Builds the visual representation of the card.
     * Tries to load the card image; if it fails, falls back to a text label.
     */
    private void buildThumbnail() {
        Image img = ImageCache.get(cardPath(card.getType(), card.getName()));

        if (img != null) {
            ImageView iv = new ImageView(img);
            iv.setFitWidth(THUMB_W);
            iv.setFitHeight(THUMB_H);
            iv.setPreserveRatio(false);
            iv.setSmooth(true);
            iv.setCache(true);

            getChildren().addAll(iv, goldBorder(THUMB_W, THUMB_H, 2, 4));

            // Overlay item icon if the hero has an item equipped
            if (card instanceof HeroCard hero && hero.getItem() != null) {
                addItemOverlay(hero.getItem());
            }
        } else {
            addFallbackLabel();
        }
    }

    /**
     * Adds a smaller item overlay to the bottom right of the hero card thumbnail.
     *
     * @param item The item card currently equipped by the hero.
     */
    private void addItemOverlay(ItemCard item) {
        Image img = ImageCache.get(itemPath(item.getName()));
        if (img == null) return;

        ImageView iv = new ImageView(img);
        iv.setFitWidth(ITEM_W);
        iv.setFitHeight(ITEM_H);
        iv.setPreserveRatio(false);
        iv.setSmooth(true);
        iv.setCache(true);

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
     * Creates a textual fallback UI when the card image cannot be found in the system.
     */
    private void addFallbackLabel() {
        Rectangle bg = new Rectangle(THUMB_W, THUMB_H, Color.web("#1c0d00"));
        bg.setStroke(Color.web("#8B6914"));
        bg.setStrokeWidth(1.5);
        bg.setArcWidth(4);
        bg.setArcHeight(4);

        Label lbl = new Label(card.getName());
        lbl.setTextFill(Color.web("#C8A870"));
        lbl.setStyle(
                "-fx-font-family: 'Georgia'; " +
                        "-fx-font-size: 9; " +
                        "-fx-font-weight: bold;"
        );
        lbl.setWrapText(true);
        lbl.setAlignment(Pos.CENTER);
        lbl.setMaxWidth(THUMB_W - 8);

        getChildren().addAll(bg, lbl);
    }

    /**
     * A shared utility factory for creating a gold border around cards and items.
     *
     * @param w       The width of the border.
     * @param h       The height of the border.
     * @param strokeW The stroke width.
     * @param arc     The corner radius arc.
     * @return A transparent Rectangle with a gold stroke.
     */
    static Rectangle goldBorder(double w, double h, double strokeW, double arc) {
        Rectangle r = new Rectangle(w, h, Color.TRANSPARENT);
        r.setStroke(Color.web("#8B6914"));
        r.setStrokeWidth(strokeW);
        r.setArcWidth(arc);
        r.setArcHeight(arc);
        r.setMouseTransparent(true);

        return r;
    }

    /**
     * Gets the base card data associated with this view.
     *
     * @return The underlying BaseCard instance.
     */
    public BaseCard getCard() {
        return card;
    }
}