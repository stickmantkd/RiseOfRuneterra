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

public class DiceView {

    // ── Public entry points ───────────────────────────────────────────────────

    /**
     * Basic roll (getRoll / rollForChallenge).
     *
     * @param dice1     first die value (1–6)
     * @param dice2     second die value (1–6)
     * @param breakdown full formula string, e.g. "3 + 5  +2 (Fighter)  = 10"
     */
    public static void showDiceRoll(int dice1, int dice2, String breakdown) {
        new Thread(() -> {
            try {
                DiceWidgets w = new DiceWidgets(false);
                Platform.runLater(() -> BoardView.showOverlay(w.overlay));

                animateDice(w, dice1, dice2);

                // Show breakdown
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
     * Ability roll — also shows target score and success/fail badge.
     *
     * @param dice1       first die value
     * @param dice2       second die value
     * @param breakdown   full formula string built by DiceUtils
     * @param total       final total after all bonuses
     * @param targetScore the score the hero needs to reach
     */
    public static void showAbilityRoll(int dice1, int dice2,
                                       String breakdown, int total, int targetScore) {
        new Thread(() -> {
            try {
                DiceWidgets w = new DiceWidgets(true);
                Platform.runLater(() -> BoardView.showOverlay(w.overlay));

                animateDice(w, dice1, dice2);

                // Brief pause so the player can see the final dice faces
                Thread.sleep(600);

                boolean success = total >= targetScore;

                Platform.runLater(() -> {
                    w.breakdownLabel.setText(breakdown);
                    w.breakdownLabel.setVisible(true);

                    // Target line
                    w.targetLabel.setText("Target: " + targetScore);
                    w.targetLabel.setVisible(true);

                    // Success / fail badge
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

    // ── Backwards-compatible overloads (called from legacy code) ─────────────

    /** Legacy overload — no breakdown text. */
    public static void showDiceRoll(int dice1, int dice2) {
        showDiceRoll(dice1, dice2, dice1 + " + " + dice2 + "  =  " + (dice1 + dice2));
    }

    // ── Styles ────────────────────────────────────────────────────────────────

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

    // ── Widget builder ────────────────────────────────────────────────────────

    private static class DiceWidgets {
        final StackPane overlay;
        final Text      faceText1;
        final Text      faceText2;
        final Label     breakdownLabel;  // "d1 + d2  +bonus  = total"
        final Label     targetLabel;     // "Target: N"  (ability rolls only)
        final Label     resultBadge;     // "✔ SUCCESS" / "✘ FAILED"  (ability rolls only)

        DiceWidgets(boolean abilityMode) {
            // Dim backdrop
            Rectangle backdrop = new Rectangle(900, 480, Color.color(0, 0, 0, 0.75));

            // Dice faces
            faceText1 = new Text(diceFace(1));
            faceText1.setStyle("-fx-font-size: 52;");
            faceText2 = new Text(diceFace(1));
            faceText2.setStyle("-fx-font-size: 52;");

            HBox diceRow = new HBox(28, buildDieFace(faceText1), buildDieFace(faceText2));
            diceRow.setAlignment(Pos.CENTER);

            // Decorative separator under dice
            Rectangle sep = new Rectangle(220, 1);
            sep.setFill(Color.web("#8B6914", 0.6));

            // Breakdown label (always shown)
            breakdownLabel = new Label();
            breakdownLabel.setStyle(BREAKDOWN_STYLE);
            breakdownLabel.setWrapText(true);
            breakdownLabel.setMaxWidth(300);
            breakdownLabel.setAlignment(Pos.CENTER);
            breakdownLabel.setVisible(false);

            // Target + badge (ability mode only)
            targetLabel = new Label();
            targetLabel.setStyle(TARGET_STYLE);
            targetLabel.setVisible(false);

            resultBadge = new Label();
            resultBadge.setVisible(false);

            // Build panel content
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

    // ── Shared animation ──────────────────────────────────────────────────────

    /** Runs the shuffle animation then lands on the real values. */
    private static void animateDice(DiceWidgets w, int finalD1, int finalD2)
            throws InterruptedException {
        // Fast shuffle
        for (int i = 0; i < 14; i++) {
            final int t1 = (int)(Math.random() * 6) + 1;
            final int t2 = (int)(Math.random() * 6) + 1;
            Platform.runLater(() -> {
                w.faceText1.setText(diceFace(t1));
                w.faceText2.setText(diceFace(t2));
            });
            Thread.sleep(75);
        }
        // Land on real values
        Platform.runLater(() -> {
            w.faceText1.setText(diceFace(finalD1));
            w.faceText2.setText(diceFace(finalD2));
        });
        Thread.sleep(300); // brief settle pause before showing results
    }

    // ── Helpers ───────────────────────────────────────────────────────────────

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
        switch (val) {
            case 1 -> {
                return  "⚀";
            }
            case 2 -> {
                return  "⚁";
            }
            case 3 -> {
                return  "⚂";
            }
            case 4 -> {
                return  "⚃";
            }
            case 5 -> {
                return  "⚄";
            }
            case 6 -> {
                return  "⚅";
            }
            default -> {
                return  "🎲";
            }
        }
    }
}