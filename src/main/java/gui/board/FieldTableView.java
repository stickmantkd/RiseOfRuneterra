package gui.board;

import NonGui.GameLogic.GameEngine;
import gui.board.field.*;
import javafx.geometry.Pos;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class FieldTableView extends StackPane {

    public FieldTableView() {
        setPrefSize(900 , 480);
        setMaxSize(900 , 480);
        setMinSize(900 , 480);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setAlignment(Pos.CENTER);

        // Background rectangle
        Rectangle background = new Rectangle();
        background.setFill(Color.DARKSEAGREEN);
        background.setStroke(Color.BLACK);
        background.setStrokeWidth(1);
        // Bind background size to FieldTableView size
        background.widthProperty().bind(widthProperty());
        background.heightProperty().bind(heightProperty());

        // hero/objective boxes
        grid.add(new TopHeroesBox(GameEngine.players[0]), 1, 0, 1, 1);
        grid.add(new BottomHeroesBox(GameEngine.players[1]), 1, 2, 1, 1);
        grid.add(new LeftHeroesBox(GameEngine.players[2]), 0, 0, 1, 3);
        grid.add(new RightHeroesBox(GameEngine.players[3]), 2, 0, 1, 3);
        grid.add(new ObjectivesBox(GameEngine.getObjectives()), 1, 1, 1, 1);

        getChildren().addAll(background,grid);
    }
}
