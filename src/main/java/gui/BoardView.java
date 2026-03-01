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

public class BoardView extends GridPane {

    private static BoardView instance; // singleton reference for refresh
    private static StatusBar statusBar = new StatusBar(); // keep one instance

    // Overlay container (for dice animation, etc.)
    private static StackPane overlayPane = new StackPane();

    public BoardView() {
        setPrefSize(1366, 768);

        // Initial layout
        add(new MenuArea(), 0, 0, 1, 2);
        add(statusBar, 0, 2, 1, 1); // add status bar

        instance = this;
        refresh(); // build initial state
    }

    // --- Refresh method ---
    public static void refresh() {
        if (instance == null) return;

        instance.getChildren().clear();

        // Menu area
        instance.add(new MenuArea(), 0, 0, 1, 2);

        // Add status bar at bottom
        instance.add(statusBar, 0, 2, 1, 1);

        // Redraw each player area
        for (int i = 0; i < GameEngine.players.length; i++) {
            Player p = GameEngine.players[i];
            Region playerArea = switch (i) {
                case 0 -> new TopPlayerArea(p);
                case 1 -> new RightPlayerArea(p);
                case 2 -> new BottomPlayerArea(p);
                case 3 -> new LeftPlayerArea(p);
                default -> null;
            };

            // Highlight if this is the current player
            if (i == GameEngine.getCurrentPlayerIndex()) {
                playerArea.setStyle("-fx-border-color: black; -fx-border-width: 4;");
            }

            // Add to grid
            switch (i) {
                case 0 -> instance.add(playerArea, 2, 0);
                case 1 -> instance.add(playerArea, 3, 0, 1, 3);
                case 2 -> instance.add(playerArea, 2, 2);
                case 3 -> instance.add(playerArea, 1, 0, 1, 3);
            }
        }

        // Objectives in center
        VBox objectiveBox = new VBox(20);
        Objective[] objectives = GameEngine.getObjectives();
        for (int i = 0; i < objectives.length; i++) {
            objectiveBox.getChildren().add(new ObjectiveView(objectives[i], i));
        }
        instance.add(new FieldTableView(), 2, 1, 1, 1);

        // Add overlay pane on top (empty by default)
        instance.add(overlayPane, 2, 1);
        // In BoardView.refresh() after adding overlayPane
        overlayPane.setMouseTransparent(true);

    }

    // --- Overlay controls ---
    public static void showOverlay(StackPane overlay) {
        overlayPane.getChildren().clear();
        overlayPane.getChildren().add(overlay);
        overlayPane.setMouseTransparent(false); // allow interaction
    }

    public static void clearOverlay() {
        overlayPane.getChildren().clear();
        overlayPane.setMouseTransparent(true); // pass clicks through
    }

}
