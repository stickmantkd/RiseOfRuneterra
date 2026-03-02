package gui;

import NonGui.BaseEntity.Player;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import NonGui.GameLogic.GameEngine;
import javax.swing.*;

import java.util.Optional;

import static gui.board.MenuArea.updateTurnLabel;

public class GameUI extends Application {
    private boolean gameOver;

    @Override
    public void start(Stage primaryStage) {
        // --- Initialize game state before building the board ---
        GameEngine.reStartGame();
        gameOver = false;

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

    private void endTurn(Player currentPlayer){
        if(currentPlayer.isWinning()){
            gameOver = true;
            showWinningDialog(currentPlayer);

            return;
        }

        GameEngine.nextTurn();
        updateTurnLabel();
        BoardView.refresh();
    }


    private void showWinningDialog(Player currentPlayer) {
        // Ensure dialog runs on the FX Application Thread
        if (!Platform.isFxApplicationThread()) {
            Platform.runLater(() -> showWinningDialog(currentPlayer));
            return;
        }

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Game Over");
        alert.setHeaderText(currentPlayer.getName() + " wins!");
        alert.setContentText("Choose an option:");

        ButtonType restartBtn = new ButtonType("Restart", ButtonBar.ButtonData.OK_DONE);
        ButtonType exitBtn = new ButtonType("Exit", ButtonBar.ButtonData.CANCEL_CLOSE);

        alert.getButtonTypes().setAll(restartBtn, exitBtn);

        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == restartBtn) {
            restartGame();
        } else {
            exitGame();
        }
    }

        private void restartGame() {
        // Reset flags
        gameOver = false;

        // Your engine & UI resets
        GameEngine.reStartGame();
        BoardView.reStartGame(); //To be implemented
        updateTurnLabel();
        BoardView.refresh();
    }

    private void exitGame() {
        // Option A: Clean exit of the application
        System.exit(0);

        // Option B: If you are in a multi-window app and want to just close the game window:
        // parentFrame.dispose();
    }
}
