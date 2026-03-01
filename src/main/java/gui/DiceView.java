package gui;

import NonGui.BaseEntity.Cards.HeroCard.HeroCard;
import javafx.application.Platform;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

public class DiceView {

    public static void showDiceRoll(int dice1, int dice2) {
        new Thread(() -> {
            try {
                StackPane overlay = buildDiceOverlay();
                Text text1 = (Text) ((StackPane) ((HBox) overlay.getChildren().get(0)).getChildren().get(0)).getChildren().get(1);
                Text text2 = (Text) ((StackPane) ((HBox) overlay.getChildren().get(0)).getChildren().get(1)).getChildren().get(1);

                Platform.runLater(() -> BoardView.showOverlay(overlay));

                // Animate rolling numbers
                for (int i = 0; i < 10; i++) {
                    int temp1 = (int) (Math.random() * 6) + 1;
                    int temp2 = (int) (Math.random() * 6) + 1;
                    Platform.runLater(() -> {
                        text1.setText(String.valueOf(temp1));
                        text2.setText(String.valueOf(temp2));
                    });
                    Thread.sleep(100);
                }

                // Show final result
                Platform.runLater(() -> {
                    text1.setText(String.valueOf(dice1));
                    text2.setText(String.valueOf(dice2));
                });

                Thread.sleep(1500);
                Platform.runLater(BoardView::clearOverlay);

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }

    public static void showAbilityRoll(int dice1, int dice2, int total, HeroCard card) {
        new Thread(() -> {
            try {
                StackPane overlay = buildDiceOverlay();
                Text text1 = (Text) ((StackPane) ((HBox) overlay.getChildren().get(0)).getChildren().get(0)).getChildren().get(1);
                Text text2 = (Text) ((StackPane) ((HBox) overlay.getChildren().get(0)).getChildren().get(1)).getChildren().get(1);

                Platform.runLater(() -> BoardView.showOverlay(overlay));

                for (int i = 0; i < 10; i++) {
                    int temp1 = (int) (Math.random() * 6) + 1;
                    int temp2 = (int) (Math.random() * 6) + 1;
                    Platform.runLater(() -> {
                        text1.setText(String.valueOf(temp1));
                        text2.setText(String.valueOf(temp2));
                    });
                    Thread.sleep(100);
                }

                Platform.runLater(() -> {
                    text1.setText(String.valueOf(dice1));
                    text2.setText(String.valueOf(dice2));
                });

                Thread.sleep(1000);
                String resultText = buildAbilityResultText(dice1, dice2, total, card);
                Platform.runLater(() -> {
                    text1.setText(resultText);
                    text2.setText("");
                });

                Thread.sleep(1500);
                Platform.runLater(BoardView::clearOverlay);

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }

    private static StackPane buildDiceOverlay() {
        StackPane overlay = new StackPane();
        HBox diceBox = new HBox(20);

        Rectangle rect1 = new Rectangle(100, 100, Color.WHITE);
        rect1.setStroke(Color.BLACK);
        Text text1 = new Text("🎲");
        text1.setStyle("-fx-font-size: 36; -fx-font-weight: bold;");
        StackPane die1 = new StackPane(rect1, text1);

        Rectangle rect2 = new Rectangle(100, 100, Color.WHITE);
        rect2.setStroke(Color.BLACK);
        Text text2 = new Text("🎲");
        text2.setStyle("-fx-font-size: 36; -fx-font-weight: bold;");
        StackPane die2 = new StackPane(rect2, text2);

        diceBox.getChildren().addAll(die1, die2);
        overlay.getChildren().add(diceBox);

        return overlay;
    }

    private static String buildAbilityResultText(int dice1, int dice2, int total, HeroCard card) {
        if (card.getItem() instanceof NonGui.ListOfCards.itemcard.BlueBuff) {
            return "Result: " + dice1 + " + " + dice2 + " + 2 = " + total;
        } else if (card.getItem() instanceof NonGui.ListOfCards.itemcard.SnakesEmbrace) {
            return "Result: " + dice1 + " + " + dice2 + " - 2 = " + total;
        } else {
            return "Result: " + dice1 + " + " + dice2 + " = " + total;
        }
    }
}
