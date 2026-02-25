package gui.board;

import NonGui.BaseEntity.Player;
import NonGui.ListOfLeader.Darius;
import gui.card.CardView;
import gui.card.LeaderCardView;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.collections.ListChangeListener;

public class LeftPlayerArea extends VBox {
    private TilePane seeMoreGrid;
    private Stage seeMoreStage;
    private final Player player;
    private final VBox handColumn;

    public LeftPlayerArea(Player player) {
        super(20);
        this.player = player;

        setPrefSize(144, 480);
        setMinSize(144, 480);
        setAlignment(Pos.CENTER);

        BackgroundFill areaFill = new BackgroundFill(Color.LIGHTYELLOW, CornerRadii.EMPTY, null);
        setBackground(new Background(areaFill));

        // Leader card
        LeaderCardView leaderCard = new LeaderCardView(player.getOwnedLeader());

        handColumn = new VBox(5);
        updateHandColumn();

        Button seeMore = null;
        if (player.getCardsInHand().size() > 5) {
            seeMore = new Button("See More");
            seeMore.setOnAction(e -> openSeeMoreWindow(areaFill));
        }

        if (seeMore != null) {
            getChildren().addAll(leaderCard, handColumn, seeMore);
        } else {
            getChildren().addAll(leaderCard, handColumn);
        }

        player.getCardsInHand().addListener((ListChangeListener.Change<?> change) -> {
            updateHandColumn();
            if (seeMoreGrid != null) updateSeeMoreGrid();
        });
    }

    private void updateHandColumn() {
        handColumn.getChildren().clear();
        int displayCount = Math.min(player.getCardsInHand().size(), 5);
        for (int i = 0; i < displayCount; i++) {
            handColumn.getChildren().add(new CardView(player.getCardsInHand().get(i), i));
        }
    }

    private void openSeeMoreWindow(BackgroundFill areaFill) {
        seeMoreGrid = new TilePane();
        seeMoreGrid.setHgap(10);
        seeMoreGrid.setVgap(10);
        seeMoreGrid.setAlignment(Pos.CENTER);
        seeMoreGrid.setBackground(new Background(areaFill));

        updateSeeMoreGrid();

        seeMoreStage = new Stage();
        seeMoreStage.setAlwaysOnTop(true);
        Scene scene = new Scene(seeMoreGrid, 600, 400);
        seeMoreStage.setTitle(player.getName() + "'s Hand");
        seeMoreStage.setScene(scene);
        seeMoreStage.show();
    }

    private void updateSeeMoreGrid() {
        seeMoreGrid.getChildren().clear();
        for (int i = 0; i < player.getCardsInHand().size(); i++) {
            seeMoreGrid.getChildren().add(new CardView(player.getCardsInHand().get(i), i));
        }
    }

}
