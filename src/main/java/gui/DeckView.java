package gui;

import NonGui.GameLogic.GameEngine;
import gui.card.CardView;
import javafx.collections.ListChangeListener;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 * Represents the graphical interface for viewing the game deck.
 * <p>
 * Displays all cards currently in the game deck using a scrollable tile grid.
 * It automatically updates when the underlying deck data changes.
 */
public class DeckView {

    private TilePane deckGrid;
    private Stage deckStage;

    /**
     * Constructs a new DeckView, initializing the scrollable grid and setting up
     * listeners to reflect real-time changes from the GameEngine's deck.
     */
    public DeckView() {
        deckGrid = new TilePane();
        deckGrid.setHgap(10);
        deckGrid.setVgap(10);
        deckGrid.setAlignment(Pos.CENTER);
        deckGrid.setBackground(new Background(
                new BackgroundFill(Color.LIGHTGRAY, CornerRadii.EMPTY, Insets.EMPTY)
        ));

        ScrollPane scrollPane = new ScrollPane(deckGrid);
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);
        scrollPane.setPannable(true);

        updateDeckGrid();

        deckStage = new Stage();
        deckStage.setAlwaysOnTop(true);
        deckStage.setTitle("Game Deck");
        deckStage.setScene(new Scene(scrollPane, 600, 400));

        GameEngine.deck.getGameDeck().addListener((ListChangeListener.Change<?> change) -> {
            updateDeckGrid();
        });
    }

    /**
     * Displays the deck window to the user.
     */
    public void show() {
        deckStage.show();
    }

    /**
     * Refreshes the grid contents by clearing the current view and
     * reloading all cards from the game deck.
     */
    private void updateDeckGrid() {
        if (deckGrid == null) return;

        deckGrid.getChildren().clear();
        for (int i = 0; i < GameEngine.deck.getGameDeck().size(); i++) {
            deckGrid.getChildren().add(new CardView(GameEngine.deck.getGameDeck().get(i), i));
        }
    }
}