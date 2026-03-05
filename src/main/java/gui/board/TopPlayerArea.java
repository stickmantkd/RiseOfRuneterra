package gui.board;

import NonGui.BaseEntity.Player;
import gui.card.CardView;
import gui.card.LeaderCardView;
import javafx.geometry.Insets;
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
    private TilePane seeMoreGrid;
    private Stage seeMoreStage;
    private final Player player;
    private final HBox handRow;

    public TopPlayerArea(Player player) {
        super(16);
        this.player = player;

        setPrefSize(900, 144);
        setMinSize(900, 144);
        setAlignment(Pos.CENTER);
        setPadding(new Insets(8, 14, 8, 14));

        setStyle(
                Theme.TOP_BG +
                        "-fx-border-color: #2a4060; -fx-border-width: 0 0 2 0;"
        );

        Label nameLabel = new Label("👑 " + player.getName());
        nameLabel.setStyle(Theme.NAME_LABEL);

        LeaderCardView leaderCard = new LeaderCardView(player.getOwnedLeader(), player);

        handRow = new HBox(6);
        handRow.setAlignment(Pos.CENTER);
        updateHandRow();

        Button seeMore = null;
        if (player.getCardsInHand().size() > 5) {
            seeMore = createSeeMoreButton();
        }

        if (seeMore != null) {
            getChildren().addAll(nameLabel, leaderCard, handRow, seeMore);
        } else {
            getChildren().addAll(nameLabel, leaderCard, handRow);
        }

        player.getCardsInHand().addListener((ListChangeListener.Change<?> change) -> {
            updateHandRow();
            if (seeMoreGrid != null) updateSeeMoreGrid();
        });
    }

    private Button createSeeMoreButton() {
        Button btn = new Button("▼ More");
        btn.setStyle(Theme.SEE_MORE_BUTTON);
        btn.setOnAction(e -> openSeeMoreWindow());
        return btn;
    }

    private void updateHandRow() {
        handRow.getChildren().clear();
        int displayCount = Math.min(player.getCardsInHand().size(), 5);
        for (int i = 0; i < displayCount; i++) {
            handRow.getChildren().add(new CardView(player.getCardsInHand().get(i), i));
        }
    }

    private void openSeeMoreWindow() {
        seeMoreGrid = new TilePane();
        seeMoreGrid.setHgap(10);
        seeMoreGrid.setVgap(10);
        seeMoreGrid.setAlignment(Pos.CENTER);
        seeMoreGrid.setPadding(new Insets(14));
        seeMoreGrid.setStyle("-fx-background-color: linear-gradient(to bottom, #0d1f2d, #162840);");
        updateSeeMoreGrid();

        seeMoreStage = new Stage();
        seeMoreStage.setAlwaysOnTop(true);
        Scene scene = new Scene(seeMoreGrid, 620, 420);
        scene.setFill(Color.web("#0d1f2d"));
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