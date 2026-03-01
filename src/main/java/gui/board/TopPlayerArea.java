package gui.board;

import NonGui.BaseEntity.Player;
import gui.card.CardView;
import gui.card.LeaderCardView;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.collections.ListChangeListener;

public class TopPlayerArea extends HBox {
    private TilePane seeMoreGrid;   // grid for "See More" window
    private Stage seeMoreStage;     // window reference
    private final Player player;
    private final HBox handRow;     // inline hand row

    public TopPlayerArea(Player player) {
        super(20);
        this.player = player;

        setPrefSize(900, 144);
        setMinSize(900, 144);
        setAlignment(Pos.CENTER);

        // Background for this area
        BackgroundFill areaFill = new BackgroundFill(Color.LIGHTBLUE, CornerRadii.EMPTY, null);
        setBackground(new Background(areaFill));

        // Player name label
        Label nameLabel = new Label(player.getName());
        nameLabel.setStyle("-fx-font-size: 14; -fx-font-weight: bold; -fx-text-fill: black;");
        nameLabel.setAlignment(Pos.CENTER_LEFT);

        // Leader card
        LeaderCardView leaderCard = new LeaderCardView(player.getOwnedLeader(), player);

        // Player cards (show only up to 5)
        handRow = new HBox(5);
        handRow.setAlignment(Pos.CENTER);
        updateHandRow();

        // See More button (only if > 5 cards)
        Button seeMore = null;
        if (player.getCardsInHand().size() > 5) {
            seeMore = new Button("See More");
            seeMore.setOnAction(e -> openSeeMoreWindow(areaFill));
        }

        // Layout: name label on the left, then leader card, then hand row, then optional button
        if (seeMore != null) {
            getChildren().addAll(nameLabel, leaderCard, handRow, seeMore);
        } else {
            getChildren().addAll(nameLabel, leaderCard, handRow);
        }

        // Listen for changes in the hand and auto-update
        player.getCardsInHand().addListener((ListChangeListener.Change<?> change) -> {
            updateHandRow();
            if (seeMoreGrid != null) {
                updateSeeMoreGrid();
            }
        });
    }

    private void updateHandRow() {
        handRow.getChildren().clear();
        int displayCount = Math.min(player.getCardsInHand().size(), 5);
        for (int i = 0; i < displayCount; i++) {
            handRow.getChildren().add(new CardView(player.getCardsInHand().get(i), i));
        }
    }

    private void openSeeMoreWindow(BackgroundFill areaFill) {
        seeMoreGrid = new TilePane();
        seeMoreGrid.setHgap(10);
        seeMoreGrid.setVgap(10);
        seeMoreGrid.setAlignment(Pos.CENTER);
        seeMoreGrid.setBackground(new Background(areaFill));

        updateSeeMoreGrid(); // initial fill

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
