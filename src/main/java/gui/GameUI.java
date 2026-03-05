package gui;

import NonGui.BaseEntity.Player;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import NonGui.GameLogic.GameEngine;

import java.util.Optional;

import static gui.board.MenuArea.updateTurnLabel;

public class GameUI extends Application {
    private static boolean gameOver;

    @Override
    public void start(Stage primaryStage) {
        GameEngine.reStartGame();
        gameOver = false;

        BoardView boardView = new BoardView();
        Scene scene = new Scene(boardView, 1366, 768);
        scene.setFill(Color.web("#1a0a00"));

        // Apply global dark theme to default controls (dialogs, etc.)
        scene.getStylesheets().add(getClass().getResource("/gui/dark-theme.css") != null
                ? getClass().getResource("/gui/dark-theme.css").toExternalForm()
                : "");

        primaryStage.setTitle("⚔ Rise of Runeterra ⚔");
        primaryStage.setScene(scene);
        primaryStage.setMinWidth(1366);
        primaryStage.setMinHeight(768);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

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
                        "-fx-border-color: #8B6914; -fx-border-width: 2;" +
                        "-fx-font-family: 'Georgia';"
        );
        Label header = (Label) dp.lookup(".header-panel .label");
        if (header != null) header.setStyle("-fx-text-fill: #FFD700; -fx-font-size: 16; -fx-font-weight: bold;");

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

    private static void restartGame() {
        gameOver = false;
        GameEngine.reStartGame();
        BoardView.reStartGame();
        updateTurnLabel();
        BoardView.refresh();
    }

    private static void exitGame() {
        System.exit(0);
    }
}