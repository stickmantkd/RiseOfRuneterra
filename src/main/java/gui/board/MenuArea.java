package gui.board;

import NonGui.GameLogic.GameEngine;
import NonGui.BaseEntity.Player;
import NonGui.GameUtils.DiceUtils;
import NonGui.GameLogic.GameChoice;
import gui.BoardView;
import gui.card.CardView;
import javafx.collections.ListChangeListener;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class MenuArea extends VBox {

    private static Label turnLabel; // shows current player + AP

    // Keep references so we can update them
    private TilePane deckGrid;
    private Stage deckStage;
    private TilePane discardGrid;
    private Stage discardStage;

    public MenuArea() {
        setPrefSize(178, 624);
        setMinSize(178, 624);
        setBackground(new Background(new BackgroundFill(Color.DARKGRAY, CornerRadii.EMPTY, Insets.EMPTY)));
        setSpacing(10);
        setAlignment(Pos.CENTER);

        Label menuLabel = new Label("Menu");
        menuLabel.setTextFill(Color.WHITE);

        // Turn info label
        turnLabel = new Label();
        turnLabel.setTextFill(Color.YELLOW);
        updateTurnLabel(); // initial fill

        // Draw Card button
        Button drawButton = new Button("Draw Card");
        drawButton.setOnAction(e -> {
            Player current = GameEngine.getCurrentPlayer();
            GameEngine.drawCard(current);
            updateTurnLabel();
            BoardView.refresh();
        });

        // Play Card button (uses GameChoice)
        Button playButton = new Button("Play Card");
        playButton.setOnAction(e -> {
            Player current = GameEngine.getCurrentPlayer();
            if (!current.HandIsEmpty()) {
                int handIndex = GameChoice.selectCardsInHand(current);
                if (handIndex >= 0) {
                    GameEngine.playCard(current, handIndex);
                }
            }
            updateTurnLabel();
            BoardView.refresh();
        });

        // Use Hero Ability button (uses GameChoice)
        Button abilityButton = new Button("Use Hero Ability");
        abilityButton.setOnAction(e -> {
            Player current = GameEngine.getCurrentPlayer();
            int heroIndex = GameChoice.selectHeroCard(current);
            if (heroIndex >= 0) {
                GameEngine.useHeroAbility(current, heroIndex);
            }
            updateTurnLabel();
            BoardView.refresh();
        });

        // Try Objective button (uses GameChoice)
        Button objectiveButton = new Button("Try Objective");
        objectiveButton.setOnAction(e -> {
            Player current = GameEngine.getCurrentPlayer();
            int objIndex = GameChoice.selectObjective();
            if (objIndex >= 0) {
                GameEngine.tryObjective(current, objIndex);
            }
            updateTurnLabel();
            BoardView.refresh();
        });

        // End Turn button
        Button endTurnButton = new Button("End Turn");
        endTurnButton.setOnAction(e -> {
            GameEngine.nextTurn();
            updateTurnLabel();
            BoardView.refresh();
        });

        // Roll Dice button
        Button diceButton = new Button("Roll Dice");
        diceButton.setOnAction(e -> {
            int result = DiceUtils.getRoll(); // triggers animation + returns result
            System.out.println("Dice result: " + result); // optional debug log
        });

        // See Deck button
        Button seeDeckButton = new Button("See Deck");
        seeDeckButton.setOnAction(e -> openDeckWindow());

        // See Discard Pile button
        Button seeDiscardButton = new Button("See Discard Pile");
        seeDiscardButton.setOnAction(e -> openDiscardWindow());

        getChildren().addAll(menuLabel, turnLabel, drawButton, playButton, abilityButton,
                objectiveButton, endTurnButton, diceButton, seeDeckButton, seeDiscardButton);
    }

    // --- Helper to update turn info ---
    public static void updateTurnLabel() {
        if (!GameEngine.isGameActive()) {
            turnLabel.setText("Game Over");
            return;
        }
        Player current = GameEngine.getCurrentPlayer();
        turnLabel.setText(current.getName() + "'s Turn | AP: " + current.getActionPoint());
    }

    // --- Deck Window ---
    private void openDeckWindow() {
        deckGrid = new TilePane();
        deckGrid.setHgap(10);
        deckGrid.setVgap(10);
        deckGrid.setAlignment(Pos.CENTER);
        deckGrid.setBackground(new Background(new BackgroundFill(Color.LIGHTGRAY, CornerRadii.EMPTY, Insets.EMPTY)));

        updateDeckGrid(); // initial fill

        deckStage = new Stage();
        deckStage.setAlwaysOnTop(true);
        deckStage.setTitle("Game Deck");
        deckStage.setScene(new Scene(deckGrid, 600, 400));
        deckStage.show();

        // Listen for changes in deck
        GameEngine.deck.getGameDeck().addListener((ListChangeListener.Change<?> change) -> {
            updateDeckGrid();
        });
    }

    private void updateDeckGrid() {
        if (deckGrid == null) return;
        deckGrid.getChildren().clear();
        for (int i = 0; i < GameEngine.deck.getGameDeck().size(); i++) {
            deckGrid.getChildren().add(new CardView(GameEngine.deck.getGameDeck().get(i), i));
        }
    }

    // --- Discard Window ---
    private void openDiscardWindow() {
        discardGrid = new TilePane();
        discardGrid.setHgap(10);
        discardGrid.setVgap(10);
        discardGrid.setAlignment(Pos.CENTER);
        discardGrid.setBackground(new Background(new BackgroundFill(Color.LIGHTGRAY, CornerRadii.EMPTY, Insets.EMPTY)));

        updateDiscardGrid(); // initial fill

        discardStage = new Stage();
        discardStage.setAlwaysOnTop(true);
        discardStage.setTitle("Discard Pile");
        discardStage.setScene(new Scene(discardGrid, 600, 400));
        discardStage.show();

        // Listen for changes in discard pile
        GameEngine.deck.getDiscardPile().addListener((ListChangeListener.Change<?> change) -> {
            updateDiscardGrid();
        });
    }

    private void updateDiscardGrid() {
        if (discardGrid == null) return;
        discardGrid.getChildren().clear();
        for (int i = 0; i < GameEngine.deck.getDiscardPile().size(); i++) {
            discardGrid.getChildren().add(new CardView(GameEngine.deck.getDiscardPile().get(i), i));
        }
    }
}
