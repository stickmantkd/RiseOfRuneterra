package gui.board.field;

import NonGui.BaseEntity.Player;
import NonGui.BaseEntity.Cards.HeroCard.HeroCard;
import gui.card.CardView;
import javafx.scene.layout.HBox;
import javafx.geometry.Pos;
import javafx.scene.shape.Rectangle;
import javafx.scene.paint.Color;

public class TopHeroesBox extends HBox {
    public TopHeroesBox(Player player) {
        super(10);
        setAlignment(Pos.CENTER);
        setPrefSize(395, 105);
        setMinSize(395, 105);

        int count = 0;

        // Loop through player's heroes
        for (HeroCard hero : player.getOwnedHero()) {
            if (hero != null) {
                getChildren().add(new CardView(hero, -1));
                count++;
            }
        }

        // Fill remaining slots with blank placeholders until we have 5
        while (count < 5) {
            Rectangle placeholder = new Rectangle(75, 105); // BaseCard small size
            placeholder.setFill(Color.LIGHTGRAY);
            placeholder.setStroke(Color.GRAY);
            placeholder.setStrokeWidth(1);
            getChildren().add(placeholder);
            count++;
        }
    }
}
