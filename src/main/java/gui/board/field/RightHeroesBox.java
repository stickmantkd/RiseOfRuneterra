package gui.board.field;

import NonGui.BaseEntity.Player;
import NonGui.BaseEntity.Cards.HeroCard.HeroCard;
import gui.card.CardView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.geometry.Pos;
import javafx.scene.shape.Rectangle;
import javafx.scene.paint.Color;

public class RightHeroesBox extends HBox {
    public RightHeroesBox(Player player) {
        super(10); // spacing between the two columns
        setAlignment(Pos.CENTER);
        setPrefSize(250, 250); // adjust as needed

        VBox col1 = new VBox(10); // first column (3 slots)
        col1.setAlignment(Pos.CENTER);

        VBox col2 = new VBox(10); // second column (2 slots)
        col2.setAlignment(Pos.CENTER);

        int count = 0;

        // Add hero cards
        for (HeroCard hero : player.getOwnedHero()) {
            if (hero != null && count < 5) {
                CardView card = new CardView(hero, -1);
                if (count < 3) {
                    col1.getChildren().add(card);
                } else {
                    col2.getChildren().add(card);
                }
                count++;
            }
        }

        // Fill remaining slots with placeholders until we have 5
        while (count < 5) {
            Rectangle placeholder = new Rectangle(75, 105); // BaseCard small size
            placeholder.setFill(Color.LIGHTGRAY);
            placeholder.setStroke(Color.GRAY);
            placeholder.setStrokeWidth(1);

            if (count < 3) {
                col1.getChildren().add(placeholder);
            } else {
                col2.getChildren().add(placeholder);
            }
            count++;
        }

        getChildren().addAll(col1, col2);
    }
}
