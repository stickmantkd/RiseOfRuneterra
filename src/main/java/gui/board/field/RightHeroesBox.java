package gui.board.field;

import NonGui.BaseEntity.Player;
import NonGui.BaseEntity.Cards.HeroCard.HeroCard;
import gui.card.CardView;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * Represents the hero field for the right player.
 * <p>
 * Displays up to 5 heroes in a staggered formation: a 3-slot column closer to
 * the center of the board, and a 2-slot column on the far right. Empty slots
 * are filled with placeholder graphics.
 */
public class RightHeroesBox extends HBox {

    // --- Layout Constants ---
    private static final double BOX_SIZE = 250;
    private static final int SPACING = 8;
    private static final int PADDING = 5;

    // --- Capacity Constants ---
    private static final int MAX_HEROES = 5;
    private static final int COL1_CAPACITY = 3;

    // --- Placeholder Constants ---
    private static final double PH_WIDTH = 75;
    private static final double PH_HEIGHT = 105;
    private static final String PH_FILL_COLOR = "#0d1a0d";
    private static final double PH_FILL_OPACITY = 0.6;
    private static final String PH_STROKE_COLOR = "#2a4020";
    private static final String PH_LABEL_STYLE = "-fx-text-fill: " + PH_STROKE_COLOR + "; -fx-font-size: 18;";

    /**
     * Constructs the hero field box for the given right player.
     *
     * @param player The right player whose heroes will be displayed.
     */
    public RightHeroesBox(Player player) {
        super(SPACING);
        setupLayout();
        populateHeroes(player);
    }

    /**
     * Configures the basic layout properties of the main HBox.
     */
    private void setupLayout() {
        setAlignment(Pos.CENTER);
        setPrefSize(BOX_SIZE, BOX_SIZE);
        setPadding(new Insets(PADDING));
    }

    /**
     * Reads the player's owned heroes and populates the 3-slot and 2-slot columns.
     * Fills remaining capacity with placeholders.
     */
    private void populateHeroes(Player player) {
        VBox col1 = createColumn(); // Inner column (3 slots, closer to center)
        VBox col2 = createColumn(); // Outer column (2 slots, closer to right edge)

        int count = 0;

        // 1. Fill with actual heroes
        for (HeroCard hero : player.getOwnedHero()) {
            if (hero != null && count < MAX_HEROES) {
                CardView card = new CardView(hero, -1);

                if (count < COL1_CAPACITY) {
                    col1.getChildren().add(card);
                } else {
                    col2.getChildren().add(card);
                }
                count++;
            }
        }

        // 2. Fill the rest with placeholders
        while (count < MAX_HEROES) {
            StackPane placeholder = buildPlaceholder();

            if (count < COL1_CAPACITY) {
                col1.getChildren().add(placeholder);
            } else {
                col2.getChildren().add(placeholder);
            }
            count++;
        }

        // Add columns to the HBox (col1 on the left/center, col2 on the right/edge)
        getChildren().addAll(col1, col2);
    }

    /**
     * Helper to create a vertically centered column.
     */
    private VBox createColumn() {
        VBox col = new VBox(SPACING);
        col.setAlignment(Pos.CENTER);
        return col;
    }

    /**
     * Builds an empty placeholder slot for missing heroes.
     */
    private StackPane buildPlaceholder() {
        Rectangle rect = new Rectangle(PH_WIDTH, PH_HEIGHT);
        rect.setFill(Color.web(PH_FILL_COLOR, PH_FILL_OPACITY));
        rect.setStroke(Color.web(PH_STROKE_COLOR));
        rect.setStrokeWidth(1);
        rect.setArcWidth(4);
        rect.setArcHeight(4);
        rect.setMouseTransparent(true);

        Label lbl = new Label("—");
        lbl.setStyle(PH_LABEL_STYLE);
        lbl.setMouseTransparent(true);

        StackPane sp = new StackPane(rect, lbl);
        sp.setMouseTransparent(true); // Ensures clicks pass through to parent if needed
        return sp;
    }
}