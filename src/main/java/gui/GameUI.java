package gui;

import NonGui.BaseEntity.Player;
import NonGui.GameLogic.GameEngine;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.Optional;

import static gui.board.MenuArea.updateTurnLabel;

/**
 * The main entry point for the "Rise of Runeterra" graphical user interface.
 * <p>
 * This class initializes the JavaFX application, manages the primary stage,
 * handles turn transitions, and displays the victory dialog when a player wins.
 */
public class GameUI extends Application {

    private static boolean gameOver;

    /**
     * The main method that launches the JavaFX application.
     *
     * @param args Command-line arguments.
     */
    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Initializes and displays the primary stage and main game board.
     *
     * @param primaryStage The primary window for the JavaFX application.
     */
    @Override
    public void start(Stage primaryStage) {
        GameEngine.reStartGame();
        gameOver = false;

        BoardView boardView = new BoardView();
        Scene scene = new Scene(boardView, 1366, 768);
        scene.setFill(Color.web("#1a0a00"));

        // Apply global dark theme to default controls (dialogs, etc.)
        String cssUrl = getClass().getResource("/gui/dark-theme.css") != null
                ? getClass().getResource("/gui/dark-theme.css").toExternalForm()
                : "";
        if (!cssUrl.isEmpty()) {
            scene.getStylesheets().add(cssUrl);
        }

        primaryStage.setTitle("⚔ Rise of Runeterra ⚔");
        primaryStage.setScene(scene);
        primaryStage.setMinWidth(1366);
        primaryStage.setMinHeight(768);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    /**
     * Evaluates the state of the game at the end of an action or turn.
     * Checks for victory conditions, progresses to the next player if action points are depleted,
     * and refreshes the board UI.
     *
     * @param currentPlayer The player whose turn is currently active.
     */
    public static void endTurn(Player currentPlayer) {
        if (currentPlayer.isWinning()) {
            gameOver = true;
            showWinningDialog(currentPlayer);
            return;
        }

        if (currentPlayer.getActionPoint() == 0) {
            GameEngine.nextTurn();
        }

        updateTurnLabel();
        BoardView.refresh();
    }

    /**
     * Displays the victory dialog when a player achieves the winning condition.
     *
     * @param currentPlayer The player who won the game.
     */
    private static void showWinningDialog(Player currentPlayer) {
        if (!Platform.isFxApplicationThread()) {
            Platform.runLater(() -> showWinningDialog(currentPlayer));
            return;
        }

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("⚔ Rise of Runeterra — Victory ⚔");
        alert.setHeaderText("🏆  " + currentPlayer.getName() + "  claims victory!");
        alert.setContentText("A legend is born. What will you do next?");

        // Style the dialog pane
        DialogPane dp = alert.getDialogPane();
        dp.setStyle(
                "-fx-background-color: linear-gradient(to bottom, #1c0d00, #2e1800);" +
                        "-fx-border-color: #8B6914; " +
                        "-fx-border-width: 2;" +
                        "-fx-font-family: 'Georgia';"
        );

        Label header = (Label) dp.lookup(".header-panel .label");
        if (header != null) {
            header.setStyle("-fx-text-fill: #FFD700; -fx-font-size: 16; -fx-font-weight: bold;");
        }

        ButtonType restartBtn = new ButtonType("⚔  Play Again", ButtonBar.ButtonData.OK_DONE);
        ButtonType exitBtn    = new ButtonType("✖  Exit",       ButtonBar.ButtonData.CANCEL_CLOSE);
        alert.getButtonTypes().setAll(restartBtn, exitBtn);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == restartBtn) {
            restartGame();
        } else {
            exitGame();
        }
    }

    /**
     * Restarts the game by resetting the game engine and UI state.
     */
    private static void restartGame() {
        gameOver = false;
        GameEngine.reStartGame();
        BoardView.reStartGame();
        updateTurnLabel();
        BoardView.refresh();
    }

    /**
     * Exits the application entirely.
     */
    private static void exitGame() {
        System.exit(0);
    }
}