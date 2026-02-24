package gui.board;

import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;

public class StatusBar extends ScrollPane {
    private static StatusBar instance;        // singleton reference
    private VBox messageBox = new VBox(5);

    public StatusBar() {
        instance = this;                      // assign when constructed
        setPrefHeight(144);                   // fixed height
        setMinHeight(144);
        setMaxHeight(144);

        setFitToWidth(true);
        setContent(messageBox);

        messageBox.setAlignment(Pos.TOP_LEFT);
        messageBox.setStyle("-fx-background-color: #333; -fx-padding: 5;");
    }

    public static void showMessage(String msg) {
        if (instance == null) return;
        Platform.runLater(() -> {
            Label label = new Label(msg);
            label.setStyle("-fx-text-fill: white;");
            instance.messageBox.getChildren().add(label); // append instead of overwrite

            // auto-scroll to bottom
            instance.setVvalue(1.0);
        });
    }
}
