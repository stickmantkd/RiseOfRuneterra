package gui.board.field;

import entities.card.BaseCard;
import entities.herocard.Minion;
import gui.card.BaseCardView;
import javafx.scene.layout.HBox;
import javafx.geometry.Pos;

public class BottomHeroesBox extends HBox {
    public BottomHeroesBox() {
        super(10);
        setAlignment(Pos.CENTER);
        setPrefSize(395, 105);
        setMinSize(395, 105);

        for (int i = 0; i < 5; i++) {
            getChildren().add(new BaseCardView(new Minion()));
        }
    }
}
