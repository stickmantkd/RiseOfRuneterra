package gui;

import NonGui.GameLogic.GameEngine;
import NonGui.BaseEntity.Player;
import NonGui.BaseEntity.Objective;
import gui.board.*;
import gui.card.ObjectiveView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

/**
 * Represents the main game board layout.
 * <p>
 * This class handles the rendering of player areas, the central field,
 * the status bar, and any overlays using a JavaFX GridPane.
 */
public class BoardView extends GridPane {

    private static BoardView instance;
    private static StatusBar statusBar = new StatusBar();
    private static StackPane overlayPane = new StackPane();

    /**
     * Constructs the BoardView, setting up its initial size, styling, and core components.
     */
    public BoardView() {
        setPrefSize(1366, 768);
        setStyle(
                "-fx-background-color: linear-gradient(to bottom right, #1a0a00, #2d1500, #1a0a00);" +
                        "-fx-border-color: #8B6914; " +
                        "-fx-border-width: 2;"
        );

        add(new MenuArea(), 0, 0, 1, 2);
        add(statusBar, 0, 2, 1, 1);

        instance = this;
        refresh();
    }

    /**
     * Refreshes the entire board view.
     * Clears current elements, redraws player areas, highlights the active player,
     * and re-adds the field and overlay layers.
     */
    public static void refresh() {
        if (instance == null) return;

        instance.getChildren().clear();
        instance.setStyle("-fx-background-color: linear-gradient(to bottom right, #1a0a00, #2d1500, #1a0a00);");

        instance.add(new MenuArea(), 0, 0, 1, 3);

        for (int i = 0; i < GameEngine.players.length; i++) {
            Player p = GameEngine.players[i];
            Region playerArea = switch (i) {
                case 0 -> new TopPlayerArea(p);
                case 1 -> new RightPlayerArea(p);
                case 2 -> new BottomPlayerArea(p);
                case 3 -> new LeftPlayerArea(p);
                default -> null;
            };

            // Highlight the current player's area
            if (i == GameEngine.getCurrentPlayerIndex() && playerArea != null) {
                playerArea.setStyle(
                        "-fx-border-color: #FFD700; " +
                                "-fx-border-width: 3;" +
                                "-fx-effect: dropshadow(gaussian, #FFD700, 15, 0.6, 0, 0);"
                );
            }

            if (playerArea != null) {
                switch (i) {
                    case 0 -> instance.add(playerArea, 2, 0);
                    case 1 -> instance.add(playerArea, 3, 0, 1, 3);
                    case 2 -> instance.add(playerArea, 2, 2);
                    case 3 -> instance.add(playerArea, 1, 0, 1, 3);
                }
            }
        }

        instance.add(new FieldTableView(), 2, 1, 1, 1);

        instance.add(overlayPane, 2, 1);
        overlayPane.setMouseTransparent(true);
    }

    /**
     * Displays a UI overlay on top of the main board.
     *
     * @param overlay The StackPane layout to be shown.
     */
    public static void showOverlay(StackPane overlay) {
        overlayPane.getChildren().clear();
        overlayPane.getChildren().add(overlay);
        overlayPane.setMouseTransparent(false);
    }

    /**
     * Clears the current overlay and restores interaction with the underlying board.
     */
    public static void clearOverlay() {
        overlayPane.getChildren().clear();
        overlayPane.setMouseTransparent(true);
    }

    /**
     * Restarts the game and resets the board state.
     */
    public static void reStartGame() {
        // To be implemented
    }
}