package gui.board;

import nongui.baseentity.Player;
import gui.card.CardView;
import gui.card.LeaderCardView;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.layout.TilePane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.collections.ListChangeListener;

/**
 * Represents the right area of the game board for a specific player.
 * <p>
 * Displays the player's name, their Leader card, and a vertical column of up to
 * a maximum number of cards currently in their hand. Includes a dynamic "More"
 * button to view the full hand if it exceeds the display limit.
 */
public class RightPlayerArea extends VBox {

    // --- Layout Constants ---
    private static final double AREA_WIDTH = 144;
    private static final double AREA_HEIGHT = 480;
    private static final int SPACING = 8;
    private static final int HAND_SPACING = 5;
    private static final int MAX_DISPLAY_CARDS = 5;

    // --- Window Constants ---
    private static final double WINDOW_WIDTH = 620;
    private static final double WINDOW_HEIGHT = 420;

    // --- Style Constants ---
    private static final String BORDER_STYLE = "-fx-border-color: #501030; -fx-border-width: 0 0 0 2;";
    private static final String GRID_BG = "-fx-background-color: linear-gradient(to left, #2d0d1a, #401226);";
    private static final String SCENE_BG = "#2d0d1a";

    // --- Instance Variables ---
    private final Player player;
    private final Label nameLabel;
    private final LeaderCardView leaderCard;
    private final VBox handColumn;
    private final Button seeMoreBtn;

    private TilePane seeMoreGrid;
    private Stage seeMoreStage;

    /**
     * Constructs the RightPlayerArea for the given player.
     *
     * @param player The player whose data will be displayed on the right.
     */
    public RightPlayerArea(Player player) {
        super(SPACING);
        this.player = player;

        // 1. Initialize core UI components
        this.nameLabel = buildNameLabel();
        this.leaderCard = new LeaderCardView(player.getOwnedLeader(), player);
        this.handColumn = buildHandColumn();
        this.seeMoreBtn = buildSeeMoreButton();

        // 2. Setup Layout and Data
        setupLayout();
        refreshArea(); // Initial render
        setupListeners();
    }

    /**
     * Configures the basic layout, sizing, and styling of the right area.
     */
    private void setupLayout() {
        setPrefSize(AREA_WIDTH, AREA_HEIGHT);
        setMinSize(AREA_WIDTH, AREA_HEIGHT);
        setAlignment(Pos.CENTER);
        setPadding(new Insets(10, 8, 10, 8));

        // Assumes Theme.RIGHT_BG is accessible
        setStyle(Theme.RIGHT_BG + BORDER_STYLE);
    }

    // --- UI Builders ---

    private Label buildNameLabel() {
        Label label = new Label("🔥 " + player.getName());
        label.setStyle(Theme.NAME_LABEL);
        label.setAlignment(Pos.CENTER);
        label.setWrapText(true);
        return label;
    }

    private VBox buildHandColumn() {
        VBox box = new VBox(HAND_SPACING);
        box.setAlignment(Pos.CENTER);
        return box;
    }

    private Button buildSeeMoreButton() {
        Button btn = new Button("◀ More");
        btn.setStyle(Theme.SEE_MORE_BUTTON);
        btn.setMaxWidth(Double.MAX_VALUE); // Ensures the button stretches to match VBox width
        btn.setOnAction(e -> openSeeMoreWindow());
        return btn;
    }

    // --- Dynamic Logic & Listeners ---

    /**
     * Attaches a listener to the player's hand to update UI dynamically when cards change.
     */
    private void setupListeners() {
        player.getCardsInHand().addListener((ListChangeListener.Change<?> change) -> {
            refreshArea();

            // Update the separate window only if it is currently open
            if (seeMoreGrid != null && seeMoreStage != null && seeMoreStage.isShowing()) {
                updateSeeMoreGrid();
            }
        });
    }

    /**
     * Refreshes the visual state of this vertical area based on current hand size.
     * Toggles the "More" button visibility dynamically.
     */
    private void refreshArea() {
        handColumn.getChildren().clear();
        int handSize = player.getCardsInHand().size();
        int displayCount = Math.min(handSize, MAX_DISPLAY_CARDS);

        for (int i = 0; i < displayCount; i++) {
            handColumn.getChildren().add(new CardView(player.getCardsInHand().get(i), i));
        }

        // Reassemble the VBox layout based on the 5-card threshold
        getChildren().clear();
        if (handSize > MAX_DISPLAY_CARDS) {
            getChildren().addAll(nameLabel, leaderCard, handColumn, seeMoreBtn);
        } else {
            getChildren().addAll(nameLabel, leaderCard, handColumn);
        }
    }

    // --- "See More" Window Logic ---

    /**
     * Opens a new Stage displaying all cards currently in the player's hand.
     */
    private void openSeeMoreWindow() {
        // Prevent opening multiple instances of the same window
        if (seeMoreStage != null && seeMoreStage.isShowing()) {
            seeMoreStage.toFront();
            return;
        }

        seeMoreGrid = new TilePane();
        seeMoreGrid.setHgap(10);
        seeMoreGrid.setVgap(10);
        seeMoreGrid.setAlignment(Pos.CENTER);
        seeMoreGrid.setPadding(new Insets(14));
        seeMoreGrid.setStyle(GRID_BG);

        updateSeeMoreGrid();

        seeMoreStage = new Stage();
        seeMoreStage.setAlwaysOnTop(true);

        Scene scene = new Scene(seeMoreGrid, WINDOW_WIDTH, WINDOW_HEIGHT);
        scene.setFill(Color.web(SCENE_BG));

        seeMoreStage.setTitle(player.getName() + "'s Hand");
        seeMoreStage.setScene(scene);
        seeMoreStage.show();
    }

    /**
     * Refreshes the grid items inside the "See More" window.
     */
    private void updateSeeMoreGrid() {
        seeMoreGrid.getChildren().clear();
        int handSize = player.getCardsInHand().size();

        for (int i = 0; i < handSize; i++) {
            seeMoreGrid.getChildren().add(new CardView(player.getCardsInHand().get(i), i));
        }
    }
}