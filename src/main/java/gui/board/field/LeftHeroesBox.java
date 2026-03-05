package gui.board.field;

import NonGui.BaseEntity.Player;
import NonGui.BaseEntity.Cards.HeroCard.HeroCard;
import gui.card.CardView;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class LeftHeroesBox extends HBox {
    public LeftHeroesBox(Player player) {
        super(8);
        setAlignment(Pos.CENTER);
        setPrefSize(250, 250);
        setPadding(new Insets(5));

        VBox col2 = new VBox(8); // 2-slot column
        col2.setAlignment(Pos.CENTER);

        VBox col3 = new VBox(8); // 3-slot column
        col3.setAlignment(Pos.CENTER);

        int count = 0;

        for (HeroCard hero : player.getOwnedHero()) {
            if (hero != null && count < 5) {
                CardView card = new CardView(hero, -1);
                if (count < 3) col3.getChildren().add(card);
                else           col2.getChildren().add(card);
                count++;
            }
        }

        while (count < 5) {
            StackPane ph = buildPlaceholder();
            if (count < 3) col3.getChildren().add(ph);
            else           col2.getChildren().add(ph);
            count++;
        }

        getChildren().addAll(col2, col3);
    }

    private StackPane buildPlaceholder() {
        Rectangle rect = new Rectangle(75, 105);
        rect.setFill(Color.web("#0d1a0d", 0.6));
        rect.setStroke(Color.web("#2a4020"));
        rect.setStrokeWidth(1);
        rect.setArcWidth(4);
        rect.setArcHeight(4);
        rect.setMouseTransparent(true);

        Label lbl = new Label("—");
        lbl.setStyle("-fx-text-fill: #2a4020; -fx-font-size: 18;");
        lbl.setMouseTransparent(true);

        StackPane sp = new StackPane(rect, lbl);
        sp.setMouseTransparent(true);
        return sp;
    }
}