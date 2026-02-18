package gui;

import gui.board.*;

import javafx.scene.layout.GridPane;

public class BoardView extends GridPane {

    public BoardView() {
        setPrefSize(1366, 768);

        // Menu area
        add(new MenuArea(), 0, 0, 1, 3);

        // Top player area
        add(new TopPlayerArea(), 2, 0);

        // Bottom player area
        add(new BottomPlayerArea(), 2, 2);

        // Left player area
        add(new LeftPlayerArea(), 1, 0, 1, 3);

        // Right player area
        add(new RightPlayerArea(), 3, 0, 1, 3);

        // Center table area
        add(new FieldTableView(), 2, 1);
    }
}