package gui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import NonGui.GameLogic.GameEngine;

public class GameUI extends Application {

    @Override
    public void start(Stage primaryStage) {
        // --- Initialize game state before building the board ---
        GameEngine.startGame();

        // --- Build the board view ---
        BoardView boardView = new BoardView();
        Scene scene = new Scene(boardView, 1366, 768);

        // --- Configure stage ---
        primaryStage.setTitle("Rise of Runeterra");
        primaryStage.setScene(scene);

        primaryStage.setMinWidth(1366);
        primaryStage.setMinHeight(768);
        primaryStage.setResizable(false);

        primaryStage.show();
    }

    // --- Entry point ---
    public static void main(String[] args) {
        launch(args); // starts JavaFX Application Thread
    }
}
