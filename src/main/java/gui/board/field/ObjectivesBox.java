package gui.board.field;

import NonGui.BaseEntity.Objective;
import gui.card.ObjectiveView;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class ObjectivesBox extends HBox {

    public ObjectivesBox(Objective[] objectives) {
        super(12);
        setAlignment(Pos.CENTER);
        setPadding(new Insets(6));

        for (int i = 0; i < objectives.length; i++) {
            if (objectives[i] != null) {
                getChildren().add(new ObjectiveView(objectives[i], i));
            } else {
                getChildren().add(buildPlaceholder());
            }
        }
    }

    private StackPane buildPlaceholder() {
        Rectangle rect = new Rectangle(150, 210);
        rect.setFill(Color.web("#0d1a0d", 0.5));
        rect.setStroke(Color.web("#3a4a20"));
        rect.setStrokeWidth(1.5);
        rect.setArcWidth(4);
        rect.setArcHeight(4);
        rect.setMouseTransparent(true);

        Label lbl = new Label("🏆\nEmpty");
        lbl.setStyle(
                "-fx-text-fill: #3a4a20; -fx-font-family: 'Georgia';" +
                        "-fx-font-size: 12; -fx-text-alignment: center;"
        );
        lbl.setMouseTransparent(true);

        StackPane sp = new StackPane(rect, lbl);
        sp.setMouseTransparent(true);
        return sp;
    }
}