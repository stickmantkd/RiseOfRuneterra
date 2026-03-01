package gui.board.field;

import NonGui.BaseEntity.Player;
import NonGui.BaseEntity.Cards.HeroCard.HeroCard;
import gui.card.CardView;
import javafx.scene.layout.HBox;
import javafx.geometry.Pos;
import javafx.scene.shape.Rectangle;
import javafx.scene.paint.Color;

public class BottomHeroesBox extends HBox {
    public BottomHeroesBox(Player player) {
        super(10);
        setAlignment(Pos.CENTER);
        setPrefSize(395, 105);
        setMinSize(395, 105);

        int count = 0;

        // Add hero cards (clickable via CardView)
        for (HeroCard hero : player.getOwnedHero()) {
            if (hero != null) {
                getChildren().add(new CardView(hero, -1)); // CardView has its own click handler
                count++;
            }
        }

        // Fill remaining slots with placeholders
        while (count < 5) {
            Rectangle placeholder = new Rectangle(75, 105);
            placeholder.setFill(Color.LIGHTGRAY);
            placeholder.setStroke(Color.GRAY);
            placeholder.setStrokeWidth(1);

            // Important: make placeholder transparent to mouse events
            placeholder.setMouseTransparent(true);

            getChildren().add(placeholder);
            count++;
        }
    }
}
