package gui.board;

import nongui.gamelogic.GameEngine;
import gui.board.field.*;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.paint.CycleMethod;
import javafx.scene.shape.Rectangle;

/**
 * Represents the main playing field in the center of the game board.
 * <p>
 * This view acts as the visual "table" where heroes and objectives are placed.
 * It dynamically arranges the play areas for 4 players in a cross layout (Top, Bottom, Left, Right)
 * around a central objectives area.
 */
public class FieldTableView extends StackPane {

    // --- Layout Constants ---
    private static final double TABLE_WIDTH = 900;
    private static final double TABLE_HEIGHT = 480;
    private static final double GRID_GAP = 10;
    private static final double GRID_PADDING = 12;

    // --- Theme & Color Constants ---
    private static final String COLOR_FELT_DARK = "#0a1f0a";
    private static final String COLOR_FELT_LIGHT = "#122a12";
    private static final String COLOR_BORDER = "#4a7a30";

    /**
     * Constructs the main game field area.
     */
    public FieldTableView() {
        setupLayout();

        Rectangle background = buildTableBackground();
        GridPane grid = buildPlayGrid();

        getChildren().addAll(background, grid);
    }

    /**
     * Configures the fixed dimensions of the playing field.
     */
    private void setupLayout() {
        setPrefSize(TABLE_WIDTH, TABLE_HEIGHT);
        setMaxSize(TABLE_WIDTH, TABLE_HEIGHT);
        setMinSize(TABLE_WIDTH, TABLE_HEIGHT);
    }

    /**
     * Creates a styled background representing a dark green felt game table.
     *
     * @return A styled Rectangle bound to the dimensions of this pane.
     */
    private Rectangle buildTableBackground() {
        Rectangle background = new Rectangle();

        // Gradient creates a subtle lighting effect on the "table"
        background.setFill(new LinearGradient(
                0, 0, 1, 1, true, CycleMethod.NO_CYCLE,
                new Stop(0.0, Color.web(COLOR_FELT_DARK)),
                new Stop(0.5, Color.web(COLOR_FELT_LIGHT)),
                new Stop(1.0, Color.web(COLOR_FELT_DARK))
        ));

        background.setStroke(Color.web(COLOR_BORDER));
        background.setStrokeWidth(2);

        // Bind size to StackPane to ensure it always covers the background
        background.widthProperty().bind(widthProperty());
        background.heightProperty().bind(heightProperty());
        background.setMouseTransparent(true);

        return background;
    }

    /**
     * Assembles the grid containing the respective hero zones for all 4 players
     * and the central objective area.
     *
     * @return The populated GridPane.
     */
    private GridPane buildPlayGrid() {
        GridPane grid = new GridPane();
        grid.setHgap(GRID_GAP);
        grid.setVgap(GRID_GAP);
        grid.setAlignment(Pos.CENTER);
        grid.setPadding(new Insets(GRID_PADDING));

        /* * --- Grid Layout Structure ---
         * [ Left Area (RowSpan 3) ] | [ Top Area      ] | [ Right Area (RowSpan 3) ]
         * | [ Objectives    ] |
         * | [ Bottom Area   ] |
         */

        // Parameters: (Node, Column, Row, ColSpan, RowSpan)
        grid.add(new TopHeroesBox(GameEngine.players[0]),    1, 0, 1, 1); // Top
        grid.add(new ObjectivesBox(GameEngine.getObjectives()), 1, 1, 1, 1); // Center
        grid.add(new BottomHeroesBox(GameEngine.players[2]), 1, 2, 1, 1); // Bottom

        grid.add(new LeftHeroesBox(GameEngine.players[3]),   0, 0, 1, 3); // Left
        grid.add(new RightHeroesBox(GameEngine.players[1]),  2, 0, 1, 3); // Right

        return grid;
    }
}