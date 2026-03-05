package gui;

import NonGui.BaseEntity.BaseCard;
import NonGui.BaseEntity.Cards.ModifierCard.ModifierCard;
import NonGui.BaseEntity.Player;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

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
        styleDialog(dialog.getDialogPane());

        String result = dialog.showAndWait().orElse(null);
        if (result == null || result.equals("Pass")) return -1;

        int choiceIndex = options.indexOf(result) - 1; // minus "Pass"
        return handIndexes.get(choiceIndex);
    }

    public static int selectModifierEffect(ModifierCard modifier) {
        List<String> options = List.of(
                "➕  +" + modifier.getPositiveModifier() + "  to a roll",
                "➖  −" + modifier.getNegativeModifier() + "  to a roll"
        );

        ChoiceDialog<String> dialog = new ChoiceDialog<>(options.get(0), options);
        dialog.setTitle("⚡ Modifier Effect");
        dialog.setHeaderText("Choose an effect for  " + modifier.getName());
        dialog.setContentText("Effect:");
        styleDialog(dialog.getDialogPane());

        String result = dialog.showAndWait().orElse(null);
        if (result == null) return -1;

        return options.indexOf(result); // 0 = positive, 1 = negative
    }

    private static void styleDialog(DialogPane dp) {
        dp.setStyle(DIALOG_STYLE);

        // Style header panel background
        javafx.scene.Node header = dp.lookup(".header-panel");
        if (header != null) {
            header.setStyle("-fx-background-color: #2e1800;");
        }

        // Style header label text
        javafx.scene.Node headerLabel = dp.lookup(".header-panel .label");
        if (headerLabel instanceof javafx.scene.control.Label) {
            Label l = (Label) headerLabel;
            l.setStyle("-fx-text-fill: #FFD700; -fx-font-family: 'Georgia'; -fx-font-size: 13;");
        }

        // Style content text (the "Select a modifier card:" label)
        javafx.scene.Node contentLabel = dp.lookup(".content .label");
        if (contentLabel instanceof javafx.scene.control.Label) {
            Label l = (Label) contentLabel;
            l.setStyle("-fx-text-fill: white; -fx-font-family: 'Georgia'; -fx-font-size: 12;");
        }

        // Style choice list items
        javafx.scene.Node listView = dp.lookup(".list-view");
        if (listView instanceof javafx.scene.control.ListView<?>) {
            ListView<?> lv = (ListView<?>) listView;
            lv.setStyle("-fx-control-inner-background: #1c0d00; -fx-text-fill: white; "
                    + "-fx-font-family: 'Georgia'; -fx-selection-bar: #FFD700; "
                    + "-fx-selection-bar-text: black;");
        }
    }
}