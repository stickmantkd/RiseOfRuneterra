package gui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class GameUI extends Application {

    @Override
    public void start(Stage primaryStage) {
        BoardView boardView = new BoardView();

        Scene scene = new Scene(boardView,1366, 768);

        primaryStage.setTitle("Rise of Runeterra");
        primaryStage.setScene(scene);

        primaryStage.setMinWidth(1366);
        primaryStage.setMinHeight(768);
        primaryStage.setResizable(false);

        primaryStage.show();
    }
}
