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
 * Represents the graphical interface for dice rolls and animations.
 * <p>
 * Displays a temporary overlay with animated dice faces, roll breakdowns,
 * and success/failure results for ability checks.
 */
public class DiceView {

    // --- Styles ---
    private static final String BREAKDOWN_STYLE =
            "-fx-font-family: 'Georgia'; " +
                    "-fx-font-size: 14; " +
                    "-fx-font-weight: bold;" +
                    "-fx-text-fill: #FFD700;" +
                    "-fx-effect: dropshadow(gaussian, #FF8C00, 5, 0.5, 0, 0);";

    private static final String TARGET_STYLE =
            "-fx-font-family: 'Georgia'; " +
                    "-fx-font-size: 12;" +
                    "-fx-text-fill: #C8A870;";

    private static final String SUCCESS_BADGE_STYLE =
            "-fx-font-family: 'Georgia'; " +
                    "-fx-font-size: 16; " +
                    "-fx-font-weight: bold;" +
                    "-fx-text-fill: #50C878;" +
                    "-fx-effect: dropshadow(gaussian, #50C878, 8, 0.6, 0, 0);";

    private static final String FAIL_BADGE_STYLE =
            "-fx-font-family: 'Georgia'; " +
                    "-fx-font-size: 16; " +
                    "-fx-font-weight: bold;" +
                    "-fx-text-fill: #E05050;" +
                    "-fx-effect: dropshadow(gaussian, #E05050, 8, 0.6, 0, 0);";

    /**
     * Displays a standard dice roll animation and overlay.
     *
     * @param dice1     The final value of the first die (1–6).
     * @param dice2     The final value of the second die (1–6).
     * @param breakdown The full formula string (e.g., "3 + 5 + 2 (Fighter) = 10").
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

                Thread.sleep(2000);
                Platform.runLater(BoardView::clearOverlay);

            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }).start();
    }

    /**
     * Displays an ability roll overlay, including the target score and success/failure badge.
     *
     * @param dice1       The final value of the first die (1–6).
     * @param dice2       The final value of the second die (1–6).
     * @param breakdown   The full formula string indicating roll and bonuses.
     * @param total       The final calculated total after bonuses.
     * @param targetScore The score required to succeed.
     */
    public static void showAbilityRoll(int dice1, int dice2, String breakdown, int total, int targetScore) {
        new Thread(() -> {
            try {
                DiceWidgets w = new DiceWidgets(true);
                Platform.runLater(() -> BoardView.showOverlay(w.overlay));

                animateDice(w, dice1, dice2);

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

                Thread.sleep(2200);
                Platform.runLater(BoardView::clearOverlay);

            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }).start();
    }

    /**
     * Legacy overload for standard dice rolls without a custom breakdown text.
     *
     * @param dice1 The final value of the first die.
     * @param dice2 The final value of the second die.
     */
    public static void showDiceRoll(int dice1, int dice2) {
        showDiceRoll(dice1, dice2, dice1 + " + " + dice2 + "  =  " + (dice1 + dice2));
    }

    /**
     * Runs the shuffling animation for the dice faces before landing on the final values.
     * * @param w       The DiceWidgets container.
     * @param finalD1 The target value for the first die.
     * @param finalD2 The target value for the second die.
     */
    private static void animateDice(DiceWidgets w, int finalD1, int finalD2) throws InterruptedException {
        for (int i = 0; i < 14; i++) {
            final int t1 = (int) (Math.random() * 6) + 1;
            final int t2 = (int) (Math.random() * 6) + 1;
            Platform.runLater(() -> {
                w.faceText1.setText(diceFace(t1));
                w.faceText2.setText(diceFace(t2));
            });
            Thread.sleep(75);
        }

        Platform.runLater(() -> {
            w.faceText1.setText(diceFace(finalD1));
            w.faceText2.setText(diceFace(finalD2));
        });
        Thread.sleep(300);
    }

    private static StackPane buildDieFace(Text faceText) {
        Rectangle face = new Rectangle(100, 100);
        face.setFill(Color.web("#f8f0d8"));
        face.setStroke(Color.web("#8B6914"));
        face.setStrokeWidth(3);
        face.setArcWidth(14);
        face.setArcHeight(14);
        face.setStyle("-fx-effect: dropshadow(gaussian, #000, 8, 0.5, 2, 2);");

        StackPane pane = new StackPane(face, faceText);
        pane.setAlignment(Pos.CENTER);
        return pane;
    }

    private static String diceFace(int val) {
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

    /**
     * A helper class holding the UI components for the dice overlay.
     */
    private static class DiceWidgets {
        final StackPane overlay;
        final Text faceText1;
        final Text faceText2;
        final Label breakdownLabel;
        final Label targetLabel;
        final Label resultBadge;

        DiceWidgets(boolean abilityMode) {
            Rectangle backdrop = new Rectangle(900, 480, Color.color(0, 0, 0, 0.75));

            faceText1 = new Text(diceFace(1));
            faceText1.setStyle("-fx-font-size: 52;");
            faceText2 = new Text(diceFace(1));
            faceText2.setStyle("-fx-font-size: 52;");

            HBox diceRow = new HBox(28, buildDieFace(faceText1), buildDieFace(faceText2));
            diceRow.setAlignment(Pos.CENTER);

            Rectangle sep = new Rectangle(220, 1);
            sep.setFill(Color.web("#8B6914", 0.6));

            breakdownLabel = new Label();
            breakdownLabel.setStyle(BREAKDOWN_STYLE);
            breakdownLabel.setWrapText(true);
            breakdownLabel.setMaxWidth(300);
            breakdownLabel.setAlignment(Pos.CENTER);
            breakdownLabel.setVisible(false);

            targetLabel = new Label();
            targetLabel.setStyle(TARGET_STYLE);
            targetLabel.setVisible(false);

            resultBadge = new Label();
            resultBadge.setVisible(false);

            VBox panel;
            if (abilityMode) {
                panel = new VBox(12, diceRow, sep, breakdownLabel, targetLabel, resultBadge);
            } else {
                panel = new VBox(14, diceRow, sep, breakdownLabel);
            }

            panel.setAlignment(Pos.CENTER);
            panel.setPadding(new Insets(26, 36, 26, 36));
            panel.setStyle(
                    "-fx-background-color: linear-gradient(to bottom, #1c0d00, #2e1800, #1c0d00);" +
                            "-fx-border-color: #8B6914; -fx-border-width: 2;" +
                            "-fx-border-radius: 10; -fx-background-radius: 10;" +
                            "-fx-effect: dropshadow(gaussian, #000000, 24, 0.8, 0, 0);"
            );
            panel.setMaxWidth(340);

            overlay = new StackPane(backdrop, panel);
            overlay.setAlignment(Pos.CENTER);
        }
    }
}