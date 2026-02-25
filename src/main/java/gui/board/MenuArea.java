package gui.board;

import NonGui.GameLogic.GameEngine;
import NonGui.BaseEntity.Player;
import NonGui.BaseEntity.ActionCard;
import NonGui.BaseEntity.Objective;

import gui.BoardView;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

public class MenuArea extends VBox {

    private static Label turnLabel; // shows current player + AP

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

        // Play Card button
        Button playButton = new Button("Play Card");
        playButton.setOnAction(e -> {
            Player current = GameEngine.getCurrentPlayer();
            if (!current.HandIsEmpty()) {
                ActionCard card = (ActionCard) current.getCardInHand(0); // example: first card
                GameEngine.playCard(current, 0);
            }
            updateTurnLabel();
            BoardView.refresh();
        });

        // Try Objective button
        Button objectiveButton = new Button("Try Objective");
        objectiveButton.setOnAction(e -> {
            Player current = GameEngine.getCurrentPlayer();
            GameEngine.tryObjective(current, 0); // example: first objective
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

        getChildren().addAll(menuLabel, turnLabel, drawButton, playButton, objectiveButton, endTurnButton);
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
}
