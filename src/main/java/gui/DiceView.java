package gui;

import NonGui.BaseEntity.Cards.HeroCard.HeroCard;
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

    // ── Public entry points ──────────────────────────────────────────────────

    public static void showDiceRoll(int dice1, int dice2) {
        new Thread(() -> {
            try {
                DiceWidgets w = new DiceWidgets();

                Platform.runLater(() -> BoardView.showOverlay(w.overlay));

                // Animate
                for (int i = 0; i < 14; i++) {
                    final int t1 = (int)(Math.random() * 6) + 1;
                    final int t2 = (int)(Math.random() * 6) + 1;
                    Platform.runLater(() -> {
                        w.faceText1.setText(diceFace(t1));
                        w.faceText2.setText(diceFace(t2));
                    });
                    Thread.sleep(80);
                }

                // Final result
                Platform.runLater(() -> {
                    w.faceText1.setText(diceFace(dice1));
                    w.faceText2.setText(diceFace(dice2));
                    w.resultLabel.setText("Total: " + (dice1 + dice2));
                    w.resultLabel.setVisible(true);
                });

                Thread.sleep(1800);
                Platform.runLater(BoardView::clearOverlay);

            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }).start();
    }

    public static void showAbilityRoll(int dice1, int dice2, int total, HeroCard card) {
        new Thread(() -> {
            try {
                DiceWidgets w = new DiceWidgets();

                Platform.runLater(() -> BoardView.showOverlay(w.overlay));

                for (int i = 0; i < 14; i++) {
                    final int t1 = (int)(Math.random() * 6) + 1;
                    final int t2 = (int)(Math.random() * 6) + 1;
                    Platform.runLater(() -> {
                        w.faceText1.setText(diceFace(t1));
                        w.faceText2.setText(diceFace(t2));
                    });
                    Thread.sleep(80);
                }

                Platform.runLater(() -> {
                    w.faceText1.setText(diceFace(dice1));
                    w.faceText2.setText(diceFace(dice2));
                });

                Thread.sleep(900);

                String resultText = buildAbilityResultText(dice1, dice2, total, card);
                Platform.runLater(() -> {
                    w.resultLabel.setText(resultText);
                    w.resultLabel.setVisible(true);
                });

                Thread.sleep(1800);
                Platform.runLater(BoardView::clearOverlay);

            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }).start();
    }

    // ── Private helpers ──────────────────────────────────────────────────────

    /**
     * Build the dice overlay. Returns a holder object with references to the
     * mutable text nodes so the animation thread can update them.
     */
    private static class DiceWidgets {
        final StackPane overlay;
        final Text faceText1;
        final Text faceText2;
        final Label resultLabel;

        DiceWidgets() {
            // Dim backdrop
            Rectangle backdrop = new Rectangle(900, 480, Color.color(0, 0, 0, 0.72));

            // --- Dice 1 ---
            faceText1 = new Text("⚀");
            faceText1.setStyle("-fx-font-size: 52;");

            StackPane die1 = buildDieFace(faceText1);

            // --- Dice 2 ---
            faceText2 = new Text("⚀");
            faceText2.setStyle("-fx-font-size: 52;");

            StackPane die2 = buildDieFace(faceText2);

            // Row of dice
            HBox diceRow = new HBox(28, die1, die2);
            diceRow.setAlignment(Pos.CENTER);

            // Result line
            resultLabel = new Label();
            resultLabel.setStyle(
                    "-fx-font-family: 'Georgia';" +
                            "-fx-font-size: 20;" +
                            "-fx-font-weight: bold;" +
                            "-fx-text-fill: #FFD700;" +
                            "-fx-effect: dropshadow(gaussian, #FF8C00, 6, 0.6, 0, 0);"
            );
            resultLabel.setVisible(false);

            // Panel container
            VBox panel = new VBox(18, diceRow, resultLabel);
            panel.setAlignment(Pos.CENTER);
            panel.setPadding(new Insets(30, 40, 30, 40));
            panel.setStyle(
                    "-fx-background-color: linear-gradient(to bottom, #1c0d00, #2e1800, #1c0d00);" +
                            "-fx-border-color: #8B6914; -fx-border-width: 2;" +
                            "-fx-border-radius: 10; -fx-background-radius: 10;" +
                            "-fx-effect: dropshadow(gaussian, #000000, 20, 0.8, 0, 0);"
            );
            panel.setMaxSize(280, 200);

            overlay = new StackPane(backdrop, panel);
            overlay.setAlignment(Pos.CENTER);
        }
    }

    private static StackPane buildDieFace(Text faceText) {
        Rectangle face = new Rectangle(100, 100);
        face.setFill(Color.web("#f8f0d8"));          // parchment white
        face.setStroke(Color.web("#8B6914"));         // gold border
        face.setStrokeWidth(3);
        face.setArcWidth(14);
        face.setArcHeight(14);
        face.setStyle("-fx-effect: dropshadow(gaussian, #000, 8, 0.5, 2, 2);");

        StackPane pane = new StackPane(face, faceText);
        pane.setAlignment(Pos.CENTER);
        return pane;
    }

    /** Return a Unicode die-face character for value 1–6. */
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

    private static String buildAbilityResultText(int dice1, int dice2, int total, HeroCard card) {
        if (card.getItem() instanceof NonGui.ListOfCards.itemcard.BlueBuff) {
            return dice1 + " + " + dice2 + " + 2  =  " + total;
        } else if (card.getItem() instanceof NonGui.ListOfCards.itemcard.SnakesEmbrace) {
            return dice1 + " + " + dice2 + " − 2  =  " + total;
        } else {
            return dice1 + " + " + dice2 + "  =  " + total;
        }
    }
}