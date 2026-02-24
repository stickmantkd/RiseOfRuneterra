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

    public MenuArea() {
        setPrefSize(178, 624);
        setMinSize(178, 624);
        setBackground(new Background(new BackgroundFill(Color.DARKGRAY, CornerRadii.EMPTY, Insets.EMPTY)));
        setSpacing(10);
        setAlignment(Pos.CENTER);

        Label menuLabel = new Label("Menu");
        menuLabel.setTextFill(Color.WHITE);

        // Draw Card button
        Button drawButton = new Button("Draw Card");
        drawButton.setOnAction(e -> {
            Player current = GameEngine.getCurrentPlayer();
            current.DrawRandomCard();
            current.decreaseActionPoint(1);
            BoardView.refresh(); // custom method to redraw GUI
        });

        // Play Card button
        Button playButton = new Button("Play Card");
        playButton.setOnAction(e -> {
            Player current = GameEngine.getCurrentPlayer();
            if (!current.HandIsEmpty()) {
                ActionCard card = (ActionCard) current.getCardInHand(0); // example: first card
                if (!card.playCard(current)) {
                    current.addCardToHand(card); // put back if invalid
                } else {
                    current.decreaseActionPoint(1);
                }
            }
            BoardView.refresh();
        });

        // Try Objective button
        Button objectiveButton = new Button("Try Objective");
        objectiveButton.setOnAction(e -> {
            Player current = GameEngine.getCurrentPlayer();
            if (current.getActionPoint() >= 2) {
                Objective obj = GameEngine.objectives[0]; // example: first objective
                obj.tryToComplete(0, current);
                current.decreaseActionPoint(2);
            }
            BoardView.refresh();
        });

        getChildren().addAll(menuLabel, drawButton, playButton, objectiveButton);
    }
}
