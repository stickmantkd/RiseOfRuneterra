package gui;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

/**
 * Handles the visual animation and display of dice rolls on the game board.
 * Supports both standard rolls and ability checks with success/fail indicators.
 * <p>
 * Uses a separate thread for timing animations to keep the UI responsive,
 * communicating back to the JavaFX Application Thread via {@link Platform#runLater}.
 * * @author GeminiCollaborator
 */
public class DiceView {

    // --- Layout Constants ---
    private static final double OVERLAY_WIDTH = 900;
    private static final double OVERLAY_HEIGHT = 480;
    private static final double PANEL_MAX_WIDTH = 340;
    private static final double DIE_SIZE = 100;

    // --- Animation Constants ---
    private static final int SHUFFLE_STEPS = 14;
    private static final int SHUFFLE_DELAY_MS = 75;
    private static final int RESULT_PAUSE_MS = 2000;
    private static final int ABILITY_PAUSE_MS = 2200;

    // --- Style Constants ---
    private static final String PANEL_STYLE =
            "-fx-background-color: linear-gradient(to bottom, #1c0d00, #2e1800, #1c0d00);" +
                    "-fx-border-color: #8B6914; -fx-border-width: 2;" +
                    "-fx-border-radius: 10; -fx-background-radius: 10;" +
                    "-fx-effect: dropshadow(gaussian, #000000, 24, 0.8, 0, 0);";

    private static final String BREAKDOWN_STYLE =
            "-fx-font-family: 'Georgia'; -fx-font-size: 14; -fx-font-weight: bold;" +
                    "-fx-text-fill: #FFD700;" +
                    "-fx-effect: dropshadow(gaussian, #FF8C00, 5, 0.5, 0, 0);";

    private static final String TARGET_STYLE =
            "-fx-font-family: 'Georgia'; -fx-font-size: 12;" +
                    "-fx-text-fill: #C8A870;";

    private static final String SUCCESS_BADGE_STYLE =
            "-fx-font-family: 'Georgia'; -fx-font-size: 16; -fx-font-weight: bold;" +
                    "-fx-text-fill: #50C878;" +
                    "-fx-effect: dropshadow(gaussian, #50C878, 8, 0.6, 0, 0);";

    private static final String FAIL_BADGE_STYLE =
            "-fx-font-family: 'Georgia'; -fx-font-size: 16; -fx-font-weight: bold;" +
                    "-fx-text-fill: #E05050;" +
                    "-fx-effect: dropshadow(gaussian, #E05050, 8, 0.6, 0, 0);";

    // --- Public Interface ---

    /**
     * Displays a standard dice roll animation and shows the final calculation.
     *
     * @param dice1     Value of the first die (1-6).
     * @param dice2     Value of the second die (1-6).
     * @param breakdown The string representation of the score calculation (e.g., "3 + 5 + 2 = 10").
     */
    public static void showDiceRoll(int dice1, int dice2, String breakdown) {
        new Thread(() -> {
            try {
                DiceWidgets w = new DiceWidgets(false);
                Platform.runLater(() -> BoardView.showOverlay(w.overlay));

                animateDice(w, dice1, dice2);

                Platform.runLater(() -> {
                    w.breakdownLabel.setText(breakdown);
                    w.breakdownLabel.setVisible(true);
                });

                Thread.sleep(RESULT_PAUSE_MS);
                Platform.runLater(BoardView::clearOverlay);

            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }).start();
    }

    /**
     * Displays an ability check roll animation, including target score and success/failure status.
     *
     * @param dice1       Value of the first die.
     * @param dice2       Value of the second die.
     * @param breakdown   The detailed calculation string.
     * @param total       The final calculated total.
     * @param targetScore The difficulty threshold to meet or exceed.
     */
    public static void showAbilityRoll(int dice1, int dice2, String breakdown, int total, int targetScore) {
        new Thread(() -> {
            try {
                DiceWidgets w = new DiceWidgets(true);
                Platform.runLater(() -> BoardView.showOverlay(w.overlay));

                animateDice(w, dice1, dice2);

                // Wait briefly so players can see the final dice result before the result text pops up
                Thread.sleep(600);

                boolean success = total >= targetScore;

                Platform.runLater(() -> {
                    w.breakdownLabel.setText(breakdown);
                    w.breakdownLabel.setVisible(true);
                    w.targetLabel.setText("Target: " + targetScore);
                    w.targetLabel.setVisible(true);

                    if (success) {
                        w.resultBadge.setText("✔  SUCCESS");
                        w.resultBadge.setStyle(SUCCESS_BADGE_STYLE);
                    } else {
                        w.resultBadge.setText("✘  FAILED");
                        w.resultBadge.setStyle(FAIL_BADGE_STYLE);
                    }
                    w.resultBadge.setVisible(true);
                });

                Thread.sleep(ABILITY_PAUSE_MS);
                Platform.runLater(BoardView::clearOverlay);

            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }).start();
    }

    /** Legacy support for rolls without a breakdown string. */
    public static void showDiceRoll(int dice1, int dice2) {
        showDiceRoll(dice1, dice2, dice1 + " + " + dice2 + "  =  " + (dice1 + dice2));
    }

    // --- Private Animation Logic ---

    /**
     * Executes the dice shuffle animation where faces change rapidly before landing.
     */
    private static void animateDice(DiceWidgets w, int finalD1, int finalD2) throws InterruptedException {
        for (int i = 0; i < SHUFFLE_STEPS; i++) {
            final int t1 = (int)(Math.random() * 6) + 1;
            final int t2 = (int)(Math.random() * 6) + 1;
            Platform.runLater(() -> {
                w.faceText1.setText(getDiceUnicode(t1));
                w.faceText2.setText(getDiceUnicode(t2));
            });
            Thread.sleep(SHUFFLE_DELAY_MS);
        }

        Platform.runLater(() -> {
            w.faceText1.setText(getDiceUnicode(finalD1));
            w.faceText2.setText(getDiceUnicode(finalD2));
        });
        Thread.sleep(300);
    }

    // --- Internal Widget Helper Class ---

    /**
     * Container class for all UI nodes used in the dice overlay.
     */
    private static class DiceWidgets {
        final StackPane overlay;
        final Text faceText1;
        final Text faceText2;
        final Label breakdownLabel;
        final Label targetLabel;
        final Label resultBadge;

        DiceWidgets(boolean abilityMode) {
            Rectangle backdrop = new Rectangle(OVERLAY_WIDTH, OVERLAY_HEIGHT, Color.color(0, 0, 0, 0.75));

            faceText1 = new Text(getDiceUnicode(1));
            faceText1.setStyle("-fx-font-size: 52;");
            faceText2 = new Text(getDiceUnicode(1));
            faceText2.setStyle("-fx-font-size: 52;");

            HBox diceRow = new HBox(28, buildDieFaceContainer(faceText1), buildDieFaceContainer(faceText2));
            diceRow.setAlignment(Pos.CENTER);

            Rectangle separator = new Rectangle(220, 1);
            separator.setFill(Color.web("#8B6914", 0.6));

            breakdownLabel = createLabel(BREAKDOWN_STYLE, true);
            targetLabel = createLabel(TARGET_STYLE, false);
            resultBadge = createLabel("", false);

            VBox panel = new VBox(abilityMode ? 12 : 14, diceRow, separator, breakdownLabel);
            if (abilityMode) panel.getChildren().addAll(targetLabel, resultBadge);

            panel.setAlignment(Pos.CENTER);
            panel.setPadding(new Insets(26, 36, 26, 36));
            panel.setStyle(PANEL_STYLE);
            panel.setMaxWidth(PANEL_MAX_WIDTH);

            overlay = new StackPane(backdrop, panel);
            overlay.setAlignment(Pos.CENTER);
        }

        private Label createLabel(String style, boolean wrap) {
            Label label = new Label();
            label.setStyle(style);
            label.setWrapText(wrap);
            if (wrap) {
                label.setMaxWidth(300);
                label.setAlignment(Pos.CENTER);
            }
            label.setVisible(false);
            return label;
        }
    }

    // --- Helper Methods ---

    private static StackPane buildDieFaceContainer(Text faceText) {
        Rectangle face = new Rectangle(DIE_SIZE, DIE_SIZE);
        face.setFill(Color.web("#f8f0d8"));
        face.setStroke(Color.web("#8B6914"));
        face.setStrokeWidth(3);
        face.setArcWidth(14);
        face.setArcHeight(14);
        face.setStyle("-fx-effect: dropshadow(gaussian, #000, 8, 0.5, 2, 2);");

        return new StackPane(face, faceText);
    }

    /** Maps an integer value to its corresponding Unicode dice character. */
    private static String getDiceUnicode(int val) {
        return switch (val) {
            case 1 -> "⚀";
            case 2 -> "⚁";
            case 3 -> "⚂";
            case 4 -> "⚃";
            case 5 -> "⚄";
            case 6 -> "⚅";
            default -> "🎲";
        };
    }
}