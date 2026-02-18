package gui.board;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

public class MenuArea extends HBox {
    public MenuArea() {
        setPrefSize(178, 768);
        setMinSize(178, 768);
        setBackground(new Background(new BackgroundFill(Color.DARKGRAY, CornerRadii.EMPTY, Insets.EMPTY)));

        Label menuLabel = new Label("Menu Area");
        menuLabel.setTextFill(Color.BLACK);
        setAlignment(Pos.CENTER);
        getChildren().add(menuLabel);
    }
}
