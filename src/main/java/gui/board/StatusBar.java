package gui.board;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;

public class StatusBar extends ScrollPane {
    private static StatusBar instance;
    private VBox messageBox = new VBox(4);

    public StatusBar() {
        instance = this;
        setPrefHeight(100);
        setMinHeight(100);
        setMaxHeight(100);
        setFitToWidth(true);
        setContent(messageBox);

        setStyle(
                "-fx-background: #0d0600;" +
                        "-fx-background-color: #0d0600;" +
                        "-fx-border-color: #5a3a10; -fx-border-width: 1 0 0 0;"
        );

        messageBox.setAlignment(Pos.TOP_LEFT);
        messageBox.setPadding(new Insets(6, 10, 6, 10));
        messageBox.setStyle("-fx-background-color: #0d0600;");
    }

    public static void showMessage(String msg) {
        if (instance == null) return;
        Platform.runLater(() -> {
            Label label = new Label("▸ " + msg);
            label.setStyle(
                    "-fx-text-fill: #C8A870;" +
                            "-fx-font-family: 'Consolas', monospace;" +
                            "-fx-font-size: 11;"
            );
            instance.messageBox.getChildren().add(label);
            instance.setVvalue(1.0);
        });
    }
}