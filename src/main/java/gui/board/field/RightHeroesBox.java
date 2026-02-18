package gui.board.field;

import entities.card.BaseCard;
import entities.herocard.Minion;
import gui.card.BaseCardView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.geometry.Pos;

public class RightHeroesBox extends VBox {
    public RightHeroesBox() {
        super(10);
        setAlignment(Pos.CENTER);
        setPrefSize(160, 335);
        setMinSize(160, 335);

        VBox col1 = new VBox(10);
        for (int i = 0; i < 3; i++) col1.getChildren().add(new BaseCardView(new Minion()));

        VBox col2 = new VBox(10);
        col2.setAlignment(Pos.CENTER);
        for (int i = 0; i < 2; i++) col2.getChildren().add(new BaseCardView(new Minion()));

        HBox split = new HBox(10, col1, col2);
        split.setAlignment(Pos.CENTER);

        getChildren().add(split);
    }
}
