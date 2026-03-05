package gui.board;

import NonGui.GameLogic.GameEngine;
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

public class FieldTableView extends StackPane {

    public FieldTableView() {
        setPrefSize(900, 480);
        setMaxSize(900, 480);
        setMinSize(900, 480);

        // Background: dark green felt / game table
        Rectangle background = new Rectangle();
        background.setFill(new LinearGradient(
                0, 0, 1, 1, true, CycleMethod.NO_CYCLE,
                new Stop(0.0, Color.web("#0a1f0a")),
                new Stop(0.5, Color.web("#122a12")),
                new Stop(1.0, Color.web("#0a1f0a"))
        ));
        background.setStroke(Color.web("#4a7a30"));
        background.setStrokeWidth(2);
        background.widthProperty().bind(widthProperty());
        background.heightProperty().bind(heightProperty());
        background.setMouseTransparent(true);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setAlignment(Pos.CENTER);
        grid.setPadding(new Insets(12));

        grid.add(new TopHeroesBox(GameEngine.players[0]),    1, 0, 1, 1);
        grid.add(new BottomHeroesBox(GameEngine.players[2]), 1, 2, 1, 1);
        grid.add(new LeftHeroesBox(GameEngine.players[3]),   0, 0, 1, 3);
        grid.add(new RightHeroesBox(GameEngine.players[1]),  2, 0, 1, 3);
        grid.add(new ObjectivesBox(GameEngine.getObjectives()), 1, 1, 1, 1);

        getChildren().addAll(background, grid);
    }
}