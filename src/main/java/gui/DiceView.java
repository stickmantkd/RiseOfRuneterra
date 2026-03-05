package gui;

import NonGui.BaseEntity.Cards.HeroCard.HeroCard;
import NonGui.ListOfCards.itemcard.BuffItem.BlueBuff;
import NonGui.ListOfCards.itemcard.CurseItem.SnakesEmbrace;
import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.paint.CycleMethod;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

public class DiceView {

    // ─── Public API ───────────────────────────────────────────────────────────

    public static void showDiceRoll(int dice1, int dice2) {
        animateDiceOverlay(dice1, dice2, null, -1, -1, -1);
    }

    public static void showAbilityRoll(int dice1, int dice2, int total, HeroCard card) {
        animateDiceOverlay(dice1, dice2, card, dice1, dice2, total);
    }

    // ─── Core Animation ───────────────────────────────────────────────────────

    private static void animateDiceOverlay(
            int finalDice1, int finalDice2,
            HeroCard card, int d1, int d2, int total) {

        new Thread(() -> {
            try {
                Platform.runLater(() -> {
                    Stage stage = new Stage();
                    stage.setTitle("Dice Roll");

                    DiceOverlay overlay = new DiceOverlay(stage);

                    Scene scene = new Scene(overlay.root);
                    stage.setScene(scene);
                    stage.show();

                    // Roll animation in background thread
                    new Thread(() -> {
                        try {
                            for (int i = 0; i < 12; i++) {
                                int r1 = (int) (Math.random() * 6) + 1;
                                int r2 = (int) (Math.random() * 6) + 1;
                                long delay = 60 + i * 12L;
                                Platform.runLater(() -> overlay.update(r1, r2));
                                Thread.sleep(delay);
                            }

                            Platform.runLater(() -> overlay.update(finalDice1, finalDice2));
                            Thread.sleep(900);

                            if (card != null) {
                                String result = buildAbilityResultText(d1, d2, total, card);
                                Platform.runLater(() -> overlay.showResult(result));
                            }
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                        }
                    }).start();
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }

    // ─── Overlay Builder ──────────────────────────────────────────────────────

    private static class DiceOverlay {

        final VBox root;
        private final Text label1;
        private final Text label2;
        private final Text resultText;
        private final VBox container;

        DiceOverlay(Stage stage) {
            // Backdrop
            Rectangle backdrop = new Rectangle(320, 240);
            backdrop.setArcWidth(20);
            backdrop.setArcHeight(20);
            backdrop.setFill(Color.color(0.07, 0.07, 0.10, 0.93));
            DropShadow shadow = new DropShadow(30, Color.color(0, 0, 0, 0.6));
            backdrop.setEffect(shadow);

            // Die faces
            label1 = makeDieLabel();
            label2 = makeDieLabel();

            StackPane die1 = makeDie(label1);
            StackPane die2 = makeDie(label2);

            HBox diceRow = new HBox(24, die1, die2);
            diceRow.setAlignment(Pos.CENTER);

            // Result line
            resultText = new Text();
            resultText.setFont(Font.font("Monospaced", FontWeight.NORMAL, 14));
            resultText.setFill(Color.color(0.75, 0.75, 0.85));
            resultText.setOpacity(0);

            // Close button
            Button closeBtn = new Button("Close");
            closeBtn.setOnAction(e -> stage.close());

            container = new VBox(18, diceRow, resultText, closeBtn);
            container.setAlignment(Pos.CENTER);
            container.setPadding(new Insets(28, 36, 28, 36));

            StackPane stack = new StackPane(backdrop, container);
            stack.setAlignment(Pos.CENTER);

            root = new VBox(stack);
            root.setAlignment(Pos.CENTER);
        }

        void update(int d1, int d2) {
            label1.setText(pip(d1));
            label2.setText(pip(d2));
        }

        void showResult(String text) {
            resultText.setText(text);
            FadeTransition ft = new FadeTransition(Duration.millis(400), resultText);
            ft.setFromValue(0);
            ft.setToValue(1);
            ft.play();
        }
    }

    // ─── Component Helpers ────────────────────────────────────────────────────

    private static StackPane makeDie(Text label) {
        Rectangle face = new Rectangle(88, 88);
        face.setArcWidth(14);
        face.setArcHeight(14);
        face.setFill(new LinearGradient(
                0, 0, 1, 1, true, CycleMethod.NO_CYCLE,
                new Stop(0, Color.color(0.97, 0.97, 1.0)),
                new Stop(1, Color.color(0.85, 0.86, 0.92))
        ));

        DropShadow glow = new DropShadow(10, Color.color(0.5, 0.5, 1.0, 0.35));
        face.setEffect(glow);

        StackPane die = new StackPane(face, label);
        die.setAlignment(Pos.CENTER);
        return die;
    }

    private static Text makeDieLabel() {
        Text t = new Text("⚄");
        t.setFont(Font.font("Serif", FontWeight.BOLD, 40));
        t.setFill(Color.color(0.12, 0.12, 0.18));
        return t;
    }

    private static String pip(int value) {
        return switch (value) {
            case 1 -> "⚀";
            case 2 -> "⚁";
            case 3 -> "⚂";
            case 4 -> "⚃";
            case 5 -> "⚄";
            case 6 -> "⚅";
            default -> "?";
        };
    }

    private static String buildAbilityResultText(int dice1, int dice2, int total, HeroCard card) {
        if (card.getItem() instanceof BlueBuff) {
            return dice1 + " + " + dice2 + " + 2  =  " + total;
        } else if (card.getItem() instanceof SnakesEmbrace) {
            return dice1 + " + " + dice2 + " − 2  =  " + total;
        } else {
            return dice1 + " + " + dice2 + "  =  " + total;
        }
    }
}
