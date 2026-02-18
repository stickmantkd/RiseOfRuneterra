package gui.board;

import entities.basecard.ChallengeCard;
import entities.card.LeaderCard;
import entities.card.BaseCard;
import entities.herocard.Minion;
import entities.leader.MarksmanLeader;
import gui.card.LeaderCardView;
import gui.card.BaseCardView;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;

public class LeftPlayerArea extends VBox {
    public LeftPlayerArea() {
        super(10);
        setPrefSize(144, 768);
        setMinSize(144, 768);
        setAlignment(Pos.CENTER);

        // Background
        setBackground(new Background(new BackgroundFill(Color.DEEPPINK, CornerRadii.EMPTY, null)));

        // Leader card
        LeaderCardView leaderCard = new LeaderCardView(new MarksmanLeader());

        // Player cards (5 heroes top to bottom)
        VBox heroColumn = new VBox(5);
        heroColumn.setAlignment(Pos.CENTER);
        for (int i = 0; i < 5; i++) {
            heroColumn.getChildren().add(new BaseCardView(new ChallengeCard()));
        }

        // See More button
        Button seeMore = new Button("See More");

        getChildren().addAll(leaderCard, heroColumn, seeMore);
    }
}
