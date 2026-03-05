package gui.board.field;

import NonGui.BaseEntity.Player;
import NonGui.BaseEntity.Cards.HeroCard.HeroCard;
import gui.card.CardView;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class BottomHeroesBox extends HBox {
    public BottomHeroesBox(Player player) {
        super(8);
        setAlignment(Pos.CENTER);
        setPrefSize(395, 115);
        setMinSize(395, 115);
        setPadding(new Insets(5));

        int count = 0;

        for (HeroCard hero : player.getOwnedHero()) {
            if (hero != null) {
                getChildren().add(new CardView(hero, -1));
                count++;
            }
        }

        while (count < 5) {
            getChildren().add(buildPlaceholder());
            count++;
        }
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