package gui.board;

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
        grid.setHgap(20);
        grid.setVgap(20);
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
        grid.add(new TopHeroesBox(), 1, 0, 5, 1);
        grid.add(new BottomHeroesBox(), 1, 2, 5, 1);
        grid.add(new LeftHeroesBox(), 0, 0, 1, 3);
        grid.add(new RightHeroesBox(), 6, 0, 1, 3);
        grid.add(new ObjectivesBox(), 2, 1, 3, 1);

        getChildren().addAll(background,grid);
    }
}
