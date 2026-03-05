package gui.board;

import NonGui.GameLogic.GameEngine;
import gui.card.CardView;
import javafx.collections.ListChangeListener;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.control.ScrollPane;

/**
 * DeckView with scrollable grid.
 */
public class DeckView {

    private TilePane deckGrid;
    private Stage deckStage;

    public DeckView() {
        deckGrid = new TilePane();
        deckGrid.setHgap(10);
        deckGrid.setVgap(10);
        deckGrid.setAlignment(Pos.CENTER);
        deckGrid.setBackground(new Background(
                new BackgroundFill(Color.LIGHTGRAY, CornerRadii.EMPTY, Insets.EMPTY)
        ));

        // Wrap grid in a ScrollPane
        ScrollPane scrollPane = new ScrollPane(deckGrid);
        scrollPane.setFitToWidth(true);   // makes grid expand horizontally
        scrollPane.setFitToHeight(true);  // makes grid expand vertically
        scrollPane.setPannable(true);     // allows click-and-drag scrolling

        updateDeckGrid(); // initial fill

        deckStage = new Stage();
        deckStage.setAlwaysOnTop(true);
        deckStage.setTitle("Game Deck");
        deckStage.setScene(new Scene(scrollPane, 600, 400));

        // Listen for changes in deck
        GameEngine.deck.getGameDeck().addListener((ListChangeListener.Change<?> change) -> {
            updateDeckGrid();
        });
    }

    /** Show the deck window */
    public void show() {
        deckStage.show();
    }

    /** Refresh deck grid contents */
    private void updateDeckGrid() {
        if (deckGrid == null) return;
        deckGrid.getChildren().clear();
        for (int i = 0; i < GameEngine.deck.getGameDeck().size(); i++) {
            deckGrid.getChildren().add(new CardView(GameEngine.deck.getGameDeck().get(i), i));
        }
    }
}
