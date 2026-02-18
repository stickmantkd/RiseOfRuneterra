package gui.board.field;

import entities.objective.BaronObjective;
import entities.objective.ElderDragonObjective;
import gui.card.ObjectiveCardView;
import javafx.scene.layout.HBox;
import javafx.geometry.Pos;

public class ObjectivesBox extends HBox {
    public ObjectivesBox() {
        super(10);
        setAlignment(Pos.CENTER);
        setPrefSize(470, 210);
        setMinSize(470, 210);

        getChildren().addAll(
                new ObjectiveCardView(new BaronObjective()),
                new ObjectiveCardView(new ElderDragonObjective()),
                new ObjectiveCardView(new BaronObjective())
        );
    }
}
