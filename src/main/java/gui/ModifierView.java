package gui;

import NonGui.BaseEntity.BaseCard;
import NonGui.BaseEntity.Cards.ModifierCard.ModifierCard;
import NonGui.BaseEntity.Player;
import javafx.scene.Node;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class ModifierView {

    private static final String DIALOG_STYLE =
            "-fx-background-color: linear-gradient(to bottom, #1c0d00, #2e1800);" +
                    "-fx-font-family: 'Georgia';";

    public static int selectModifierCard(Player actingPlayer, Player targetPlayer) {

        List<String> options = new ArrayList<>();
        List<Integer> handIndexes = new ArrayList<>();

        options.add("Pass");

        for (int i = 0; i < actingPlayer.getCardsInHand().size(); i++) {
            BaseCard card = actingPlayer.getCardsInHand().get(i);
            if (card instanceof ModifierCard) {
                options.add(card.getName());
                handIndexes.add(i);
            }
        }

        ChoiceDialog<String> dialog = new ChoiceDialog<>(options.get(0), options);
        dialog.setTitle("⚡ Modifier Phase");
        dialog.setHeaderText(
                actingPlayer.getName() + " — Modify " + targetPlayer.getName() + "'s roll?\n" +
                        "Current roll: " + targetPlayer.getCurrentRoll()
        );
        dialog.setContentText("Select a modifier card:");

        DialogPane dp = dialog.getDialogPane();
        styleDialog(dp);

        // Shift dialog to the right when shown
        dialog.setOnShown(e -> {
            Stage stage = (Stage) dp.getScene().getWindow();
            stage.setX(stage.getX() + stage.getWidth() * 1.5);
        });

        String result = dialog.showAndWait().orElse(null);
        if (result == null || result.equals("Pass")) return -1;

        int choiceIndex = options.indexOf(result) - 1;
        return handIndexes.get(choiceIndex);
    }

    public static int selectModifierEffect(ModifierCard modifier) {

        List<String> options = List.of(
                "➕  +" + modifier.getPositiveModifier() + "  to a roll",
                "➖  −" + modifier.getNegativeModifier() + "  to a roll"
        );

        ChoiceDialog<String> dialog = new ChoiceDialog<>(options.get(0), options);
        dialog.setTitle("⚡ Modifier Effect");
        dialog.setHeaderText("Choose an effect for " + modifier.getName());
        dialog.setContentText("Effect:");

        DialogPane dp = dialog.getDialogPane();
        styleDialog(dp);

        // Shift dialog to the right when shown
        dialog.setOnShown(e -> {
            Stage stage = (Stage) dp.getScene().getWindow();
            stage.setX(stage.getX() + stage.getWidth() * 1.5);
        });

        String result = dialog.showAndWait().orElse(null);
        if (result == null) return -1;

        return options.indexOf(result);
    }

    private static void styleDialog(DialogPane dp) {

        dp.setStyle(DIALOG_STYLE);

        // Force CSS so lookup works
        dp.applyCss();
        dp.layout();

        // Header panel background
        Node header = dp.lookup(".header-panel");
        if (header != null) {
            header.setStyle("-fx-background-color: #2e1800;");
        }

        // Header text
        Node headerLabel = dp.lookup(".header-panel .label");
        if (headerLabel instanceof Label) {
            Label l = (Label) headerLabel;
            l.setStyle(
                    "-fx-text-fill: #FFD700;" +
                            "-fx-font-family: 'Georgia';" +
                            "-fx-font-size: 13px;"
            );
        }

        // Content text
        Node contentLabel = dp.lookup(".content .label");
        if (contentLabel instanceof Label) {
            Label l = (Label) contentLabel;
            l.setStyle(
                    "-fx-text-fill: white;" +
                            "-fx-font-family: 'Georgia';" +
                            "-fx-font-size: 12px;"
            );
        }

        // List view styling
        Node listView = dp.lookup(".list-view");
        if (listView instanceof ListView<?>) {
            ListView<?> lv = (ListView<?>) listView;
            lv.setStyle(
                    "-fx-control-inner-background: #1c0d00;" +
                            "-fx-text-fill: white;" +
                            "-fx-font-family: 'Georgia';" +
                            "-fx-selection-bar: #FFD700;" +
                            "-fx-selection-bar-text: black;"
            );
        }
    }
}