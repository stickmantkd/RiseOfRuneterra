package gui.board.field;

import nongui.baseentity.Player;
import nongui.baseentity.cards.HeroCard.HeroCard;
import gui.card.CardView;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

// Import standard sizes from ImageCache to maintain consistency
import static gui.card.ImageCache.THUMB_H;
import static gui.card.ImageCache.THUMB_W;

/**
 * Represents the UI container for displaying a player's active heroes on the board.
 * <p>
 * This box holds up to a maximum number of heroes. Empty slots are visually represented
 * by placeholder graphics.
 */
public class BottomHeroesBox extends HBox {

    // --- Layout Constants ---
    private static final int MAX_HEROES = 5;
    private static final double BOX_WIDTH = 395;
    private static final double BOX_HEIGHT = 115;
    private static final int SPACING = 8;

    // --- Style Constants ---
    private static final Color PLACEHOLDER_FILL = Color.web("#0d1a0d", 0.6);
    private static final Color PLACEHOLDER_STROKE = Color.web("#2a4020");
    private static final String PLACEHOLDER_TEXT_STYLE = "-fx-text-fill: #2a4020; -fx-font-size: 18;";

    /**
     * Constructs a BottomHeroesBox for the specified player.
     *
     * @param player The player whose heroes will be displayed in this box.
     */
    public BottomHeroesBox(Player player) {
        super(SPACING);

        setupLayout();
        populateHeroes(player);
    }

    /**
     * Configures the layout properties of the HBox.
     */
    private void setupLayout() {
        setAlignment(Pos.CENTER);
        setPrefSize(BOX_WIDTH, BOX_HEIGHT);
        setMinSize(BOX_WIDTH, BOX_HEIGHT);
        setPadding(new Insets(5));
    }

    /**
     * Populates the box with the player's heroes and fills any remaining slots with placeholders.
     *
     * @param player The player providing the hero data.
     */
    private void populateHeroes(Player player) {
        int count = 0;

        // 1. Add CardViews for all currently owned heroes
        for (HeroCard hero : player.getOwnedHero()) {
            if (hero != null) {
                // -1 implies this is a field card, not a card in hand
                getChildren().add(new CardView(hero, -1));
                count++;
            }
        }

        // 2. Fill the rest of the capacity (up to MAX_HEROES) with empty placeholders
        for (int i = count; i < MAX_HEROES; i++) {
            getChildren().add(buildPlaceholder());
        }
    }

    /**
     * Creates a visual placeholder for an empty hero slot.
     *
     * @return A StackPane containing the styled placeholder graphics.
     */
    private StackPane buildPlaceholder() {
        Rectangle rect = new Rectangle(THUMB_W, THUMB_H);
        rect.setFill(PLACEHOLDER_FILL);
        rect.setStroke(PLACEHOLDER_STROKE);
        rect.setStrokeWidth(1);
        rect.setArcWidth(4);
        rect.setArcHeight(4);
        rect.setMouseTransparent(true);

        Label lbl = new Label("—");
        lbl.setStyle(PLACEHOLDER_TEXT_STYLE);
        lbl.setMouseTransparent(true);

        StackPane sp = new StackPane(rect, lbl);
        sp.setMouseTransparent(true); // Ensures clicks pass through if needed

        return sp;
    }
}