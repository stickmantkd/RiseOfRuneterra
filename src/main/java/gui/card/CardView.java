package gui.card;

import entities.card.Card;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * Base GUI card view.
 * Renders a Card entity as a rectangle + label.
 */
public class CardView extends StackPane {

    private final Card card;

    public CardView(Card card, double width, double height, Color color) {
        this.card = card;

        Rectangle rect = new Rectangle(width, height, color);
        rect.setStroke(Color.BLACK);
        rect.setStrokeWidth(1);

        Label label = new Label(card.getName());
        label.setTextFill(Color.BLACK);

        setAlignment(Pos.CENTER);
        getChildren().addAll(rect, label);
    }

    public Card getCard() {
        return card;
    }
}