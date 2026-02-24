package gui.board.field;

import NonGui.BaseEntity.Objective;
import gui.card.ObjectiveView;
import javafx.geometry.Pos;
import javafx.scene.layout.HBox;

public class ObjectivesBox extends HBox {

    public ObjectivesBox(Objective[] objectives) {
        super(15); // spacing between objectives
        setAlignment(Pos.CENTER);

        // Loop through objectives and render them
        for (int i = 0; i < objectives.length; i++) {
            if (objectives[i] != null) {
                getChildren().add(new ObjectiveView(objectives[i], i));
            }
        }
    }
}
