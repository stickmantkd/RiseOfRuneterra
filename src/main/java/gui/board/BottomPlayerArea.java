package gui.board;

import NonGui.BaseEntity.Player;
import gui.card.CardView;
import gui.card.LeaderCardView;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.TilePane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.collections.ListChangeListener;

/**
 * Represents the bottom area of the game board for the local player.
 * <p>
 * Displays the player's name, their Leader card, and up to a maximum number
 * of cards currently in their hand. If the hand exceeds the limit, a "More"
 * button appears to open a secondary window viewing all cards.
 */
public class BottomPlayerArea extends HBox {

    // --- Layout Constants ---
    private static final double AREA_WIDTH = 900;
    private static final double AREA_HEIGHT = 144;
    private static final int SPACING = 16;
    private static final int HAND_SPACING = 6;
    private static final int MAX_DISPLAY_CARDS = 5;

    // --- Window Constants ---
    private static final double WINDOW_WIDTH = 620;
    private static final double WINDOW_HEIGHT = 420;

    // --- Style Constants ---
    private static final String BORDER_STYLE = "-fx-border-color: #2a4060; -fx-border-width: 2 0 0 0;";
    private static final String GRID_BG = "-fx-background-color: linear-gradient(to top, #0d1f2d, #162840);";
    private static final String SCENE_BG = "#0d1f2d";

    // --- Instance Variables ---
    private final Player player;
    private final Label nameLabel;
    private final LeaderCardView leaderCard;
    private final HBox handRow;
    private final Button seeMoreBtn;

    private TilePane seeMoreGrid;
    private Stage seeMoreStage;

    /**
     * Constructs the BottomPlayerArea for the given player.
     *
     * @param player The local player whose data will be displayed.
     */
    public BottomPlayerArea(Player player) {
        super(SPACING);
        this.player = player;

        // 1. Initialize core UI components
        this.nameLabel = buildNameLabel();
        this.leaderCard = new LeaderCardView(player.getOwnedLeader(), player);
        this.handRow = buildHandRow();
        this.seeMoreBtn = buildSeeMoreButton();

        // 2. Setup Layout and Data
        setupLayout();
        refreshArea(); // Initial render
        setupListeners();
    }

    /**
     * Configures the basic layout, sizing, and styling of the main area.
     */
    private void setupLayout() {
        setPrefSize(AREA_WIDTH, AREA_HEIGHT);
        setMinSize(AREA_WIDTH, AREA_HEIGHT);
        setAlignment(Pos.CENTER);
        setPadding(new Insets(8, 14, 8, 14));

        // Assumes Theme.BOTTOM_BG is accessible
        setStyle(Theme.BOTTOM_BG + BORDER_STYLE);
    }

    // --- UI Builders ---

    private Label buildNameLabel() {
        Label label = new Label("⚔ " + player.getName());
        label.setStyle(Theme.NAME_LABEL);
        return label;
    }

    private HBox buildHandRow() {
        HBox box = new HBox(HAND_SPACING);
        box.setAlignment(Pos.CENTER);
        return box;
    }

    private Button buildSeeMoreButton() {
        Button btn = new Button("▲ More");
        btn.setStyle(Theme.SEE_MORE_BUTTON);
        btn.setOnAction(e -> openSeeMoreWindow());
        return btn;
    }

    // --- Dynamic Logic & Listeners ---

    /**
     * Attaches a listener to the player's hand to update UI dynamically when cards are drawn/played.
     */
    private void setupListeners() {
        player.getCardsInHand().addListener((ListChangeListener.Change<?> change) -> {
            refreshArea();

            // Only update the extra window if it exists and is currently visible
            if (seeMoreGrid != null && seeMoreStage != null && seeMoreStage.isShowing()) {
                updateSeeMoreGrid();
            }
        });
    }

    /**
     * Completely refreshes the visual state of this area based on current hand size.
     * This ensures the "More" button toggles its visibility correctly.
     */
    private void refreshArea() {
        handRow.getChildren().clear();
        int handSize = player.getCardsInHand().size();
        int displayCount = Math.min(handSize, MAX_DISPLAY_CARDS);

        for (int i = 0; i < displayCount; i++) {
            handRow.getChildren().add(new CardView(player.getCardsInHand().get(i), i));
        }

        // Reassemble the layout based on the 5-card threshold
        getChildren().clear();
        if (handSize > MAX_DISPLAY_CARDS) {
            getChildren().addAll(nameLabel, leaderCard, handRow, seeMoreBtn);
        } else {
            getChildren().addAll(nameLabel, leaderCard, handRow);
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