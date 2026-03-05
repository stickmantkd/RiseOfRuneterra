package gui.board;

import NonGui.BaseEntity.Player;
import gui.card.CardView;
import gui.card.LeaderCardView;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.layout.TilePane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.collections.ListChangeListener;

public class RightPlayerArea extends VBox {
    private TilePane seeMoreGrid;
    private Stage seeMoreStage;
    private final Player player;
    private final VBox handColumn;

    public RightPlayerArea(Player player) {
        super(8);
        this.player = player;

        setPrefSize(144, 480);
        setMinSize(144, 480);
        setAlignment(Pos.CENTER);
        setPadding(new Insets(10, 8, 10, 8));

        setStyle(
                Theme.RIGHT_BG +
                        "-fx-border-color: #501030; -fx-border-width: 0 0 0 2;"
        );

        Label nameLabel = new Label("🔥 " + player.getName());
        nameLabel.setStyle(Theme.NAME_LABEL);
        nameLabel.setAlignment(Pos.CENTER);
        nameLabel.setWrapText(true);

        LeaderCardView leaderCard = new LeaderCardView(player.getOwnedLeader(), player);

        handColumn = new VBox(5);
        handColumn.setAlignment(Pos.CENTER);
        updateHandColumn();

        Button seeMore = null;
        if (player.getCardsInHand().size() > 5) {
            seeMore = createSeeMoreButton();
        }

        if (seeMore != null) {
            getChildren().addAll(nameLabel, leaderCard, handColumn, seeMore);
        } else {
            getChildren().addAll(nameLabel, leaderCard, handColumn);
        }

        player.getCardsInHand().addListener((ListChangeListener.Change<?> change) -> {
            updateHandColumn();
            if (seeMoreGrid != null) updateSeeMoreGrid();
        });
    }

    private Button createSeeMoreButton() {
        Button btn = new Button("◀ More");
        btn.setStyle(Theme.SEE_MORE_BUTTON);
        btn.setMaxWidth(Double.MAX_VALUE);
        btn.setOnAction(e -> openSeeMoreWindow());
        return btn;
    }

    private void updateHandColumn() {
        handColumn.getChildren().clear();
        int displayCount = Math.min(player.getCardsInHand().size(), 5);
        for (int i = 0; i < displayCount; i++) {
            handColumn.getChildren().add(new CardView(player.getCardsInHand().get(i), i));
        }
    }

    private void openSeeMoreWindow() {
        seeMoreGrid = new TilePane();
        seeMoreGrid.setHgap(10);
        seeMoreGrid.setVgap(10);
        seeMoreGrid.setAlignment(Pos.CENTER);
        seeMoreGrid.setPadding(new Insets(14));
        seeMoreGrid.setStyle("-fx-background-color: linear-gradient(to left, #2d0d1a, #401226);");
        updateSeeMoreGrid();

        seeMoreStage = new Stage();
        seeMoreStage.setAlwaysOnTop(true);
        Scene scene = new Scene(seeMoreGrid, 620, 420);
        scene.setFill(Color.web("#2d0d1a"));
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