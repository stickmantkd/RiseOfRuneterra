package gui.board;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;

/**
 * Represents the status/log bar at the bottom of the game UI.
 * <p>
 * Displays system and game messages to the players. Keeps a limited history
 * of messages to prevent memory/performance degradation over time.
 */
public class StatusBar extends ScrollPane {

    // --- Layout Constants ---
    private static final double BAR_HEIGHT = 100;
    private static final int SPACING = 4;
    private static final int MAX_MESSAGES = 50; // Prevents infinite memory/UI node growth

    // --- Style Constants ---
    private static final String BG_COLOR = "#0d0600";
    private static final String BORDER_COLOR = "#5a3a10";
    private static final String TEXT_COLOR = "#C8A870";

    private static final String BAR_STYLE =
            "-fx-background: " + BG_COLOR + ";" +
                    "-fx-background-color: " + BG_COLOR + ";" +
                    "-fx-border-color: " + BORDER_COLOR + "; -fx-border-width: 1 0 0 0;";

    private static final String MSG_BOX_STYLE = "-fx-background-color: " + BG_COLOR + ";";

    private static final String LABEL_STYLE =
            "-fx-text-fill: " + TEXT_COLOR + ";" +
                    "-fx-font-family: 'Consolas', monospace;" +
                    "-fx-font-size: 11;";

    // --- Instance Variables ---
    private static StatusBar instance;
    private final VBox messageBox;

    /**
     * Constructs the global StatusBar.
     */
    public StatusBar() {
        instance = this;
        this.messageBox = new VBox(SPACING);

        setupLayout();
        setupContentBox();
    }

    /**
     * Configures the scrolling container's dimensions and style.
     */
    private void setupLayout() {
        setPrefHeight(BAR_HEIGHT);
        setMinHeight(BAR_HEIGHT);
        setMaxHeight(BAR_HEIGHT);
        setFitToWidth(true);

        setStyle(BAR_STYLE);
        setContent(messageBox);
    }

    /**
     * Configures the inner box that holds the actual text labels.
     */
    private void setupContentBox() {
        messageBox.setAlignment(Pos.TOP_LEFT);
        messageBox.setPadding(new Insets(6, 10, 6, 10));
        messageBox.setStyle(MSG_BOX_STYLE);

        // UX Fix: Auto-scroll to bottom perfectly when the box's height changes
        messageBox.heightProperty().addListener((observable, oldValue, newValue) -> {
            setVvalue(1.0);
        });
    }

    /**
     * Appends a new message to the status bar globally.
     * Safe to call from any background thread.
     *
     * @param msg The message to display.
     */
    public static void showMessage(String msg) {
        if (instance == null) return;

        // Ensures UI updates happen on the main JavaFX Application Thread
        Platform.runLater(() -> {
            Label label = new Label("▸ " + msg);
            label.setStyle(LABEL_STYLE);

            instance.messageBox.getChildren().add(label);

            // Limit memory usage: Remove the oldest message if we exceed the cap
            if (instance.messageBox.getChildren().size() > MAX_MESSAGES) {
                instance.messageBox.getChildren().remove(0);
            }
        });
    }
}