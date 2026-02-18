package gui.board;

import entities.card.LeaderCard;
import entities.card.BaseCard;
import entities.herocard.Minion;
import entities.itemcard.BFSword;
import entities.leader.TankLeader;
import gui.card.LeaderCardView;
import gui.card.BaseCardView;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;

public class TopPlayerArea extends HBox {
    public TopPlayerArea() {
        super(20);
        setPrefSize(900, 144);
        setMinSize(900, 144);
        setAlignment(Pos.CENTER);

        // Background
        setBackground(new Background(new BackgroundFill(Color.LIGHTBLUE, CornerRadii.EMPTY, null)));

        // Leader card
        LeaderCardView leaderCard = new LeaderCardView(new TankLeader());

        // Player cards (5 heroes left to right)
        HBox heroRow = new HBox(10);
        heroRow.setAlignment(Pos.CENTER);
        for (int i = 0; i < 5; i++) {
            heroRow.getChildren().add(new BaseCardView(new BFSword()));
        }

        // See More button
        Button seeMore = new Button("See More");

        getChildren().addAll(leaderCard, heroRow, seeMore);
    }
}
