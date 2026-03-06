package gui;

import nongui.gamelogic.GameEngine;
import nongui.baseentity.Player;
import gui.board.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;

/**
 * The main container for the game's graphical user interface.
 * <p>
 * BoardView organizes player areas, the central battlefield (FieldTableView),
 * and the menu system using a {@link GridPane} layout. It also manages
 * the overlay layer for animations like dice rolls.
 * * @author GeminiCollaborator
 */
public class BoardView extends GridPane {

    private static BoardView instance;
    private static final StackPane overlayPane = new StackPane();

    // --- Style Constants ---
    private static final String BOARD_BG =
            "-fx-background-color: linear-gradient(to bottom right, #1a0a00, #2d1500, #1a0a00);" +
                    "-fx-border-color: #8B6914; -fx-border-width: 2;";

    private static final String ACTIVE_PLAYER_HIGHLIGHT =
            "-fx-border-color: #FFD700; -fx-border-width: 3;" +
                    "-fx-effect: dropshadow(gaussian, #FFD700, 15, 0.6, 0, 0);";

    /**
     * Initializes the BoardView with a fixed resolution and base styles.
     */
    public BoardView() {
        setPrefSize(1366, 768);
        setStyle(BOARD_BG);

        // Prevent overlay from intercepting clicks when empty
        overlayPane.setMouseTransparent(true);

        instance = this;
        refresh();
    }

    /**
     * Rebuilds the entire game board UI based on the current state in GameEngine.
     * This method clears existing components and re-adds them to their specific grid positions.
     */
    public static void refresh() {
        if (instance == null) return;

        instance.getChildren().clear();
        instance.setStyle(BOARD_BG);

        // Column 0: Menu and Navigation (Spans all rows)
        instance.add(new MenuArea(), 0, 0, 1, 3);

        // Add Players in their respective positions
        for (int i = 0; i < GameEngine.players.length; i++) {
            Player player = GameEngine.players[i];
            Region playerArea = createPlayerArea(i, player);

            if (playerArea == null) continue;

            // Highlight the player whose turn it is
            if (i == GameEngine.getCurrentPlayerIndex()) {
                playerArea.setStyle(playerArea.getStyle() + ACTIVE_PLAYER_HIGHLIGHT);
            }

            positionPlayerArea(i, playerArea);
        }

        // Central Battlefield: FieldTableView (Row 1, Column 2)
        instance.add(new FieldTableView(), 2, 1);

        // Overlay Layer: Sits directly on top of the FieldTableView
        instance.add(overlayPane, 2, 1);
    }

    /**
     * Factory method to create a player area based on their index.
     */
    private static Region createPlayerArea(int index, Player player) {
        return switch (index) {
            case 0 -> new TopPlayerArea(player);
            case 1 -> new RightPlayerArea(player);
            case 2 -> new BottomPlayerArea(player);
            case 3 -> new LeftPlayerArea(player);
            default -> null;
        };
    }

    /**
     * Places the player area into the correct GridPane coordinate.
     */
    private static void positionPlayerArea(int index, Region area) {
        switch (index) {
            case 0 -> instance.add(area, 2, 0);          // Top
            case 1 -> instance.add(area, 3, 0, 1, 3);   // Right (Tall)
            case 2 -> instance.add(area, 2, 2);          // Bottom
            case 3 -> instance.add(area, 1, 0, 1, 3);   // Left (Tall)
        }
    }

    /**
     * Displays a temporary overlay (e.g., DiceRoll, Card Selection) over the central field.
     * * @param overlay The StackPane containing the UI to be displayed.
     */
    public static void showOverlay(StackPane overlay) {
        overlayPane.getChildren().setAll(overlay);
        overlayPane.setMouseTransparent(false);
    }

    /**
     * Removes the current overlay and restores click interaction to the field below.
     */
    public static void clearOverlay() {
        overlayPane.getChildren().clear();
        overlayPane.setMouseTransparent(true);
    }

    /**
     * Resets the game state and UI. (Logic to be implemented).
     */
    public static void reStartGame() {
        // Implementation for game reset goes here
    }
}