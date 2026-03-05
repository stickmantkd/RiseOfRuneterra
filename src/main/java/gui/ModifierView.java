package gui;

import NonGui.BaseEntity.BaseCard;
import NonGui.BaseEntity.Cards.ModifierCard.ModifierCard;
import NonGui.BaseEntity.Player;
import javafx.scene.Node;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Provides the graphical interface dialogs for the Modifier Phase.
 * <p>
 * This class handles displaying options for a player to select a Modifier card from their hand
 * and choose whether to apply its positive or negative effect to a roll.
 */
public class ModifierView {

    // --- Styles ---
    private static final String DIALOG_STYLE =
            "-fx-background-color: linear-gradient(to bottom, #1c0d00, #2e1800);" +
                    "-fx-font-family: 'Georgia';";

    /**
     * Opens a dialog for the acting player to select a modifier card to play against a target's roll.
     *
     * @param actingPlayer The player who is deciding to play a modifier card.
     * @param targetPlayer The player whose roll is being modified.
     * @return The index of the selected modifier card in the acting player's hand,
     * or -1 if the player chooses to "Pass" or closes the dialog.
     */
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

        // Calculate the actual index in the player's hand (compensating for the "Pass" option)
        int choiceIndex = options.indexOf(result) - 1;
        return handIndexes.get(choiceIndex);
    }

    /**
     * Opens a dialog for the player to choose between the positive and negative effect of a modifier card.
     *
     * @param modifier The modifier card being played.
     * @return 0 for the positive effect, 1 for the negative effect, or -1 if canceled.
     */
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

    /**
     * Applies a custom dark fantasy theme to a standard JavaFX DialogPane.
     *
     * @param dp The DialogPane to be styled.
     */
    private static void styleDialog(DialogPane dp) {
        dp.setStyle(DIALOG_STYLE);

        // Style header panel background
        Node header = dp.lookup(".header-panel");
        if (header != null) {
            header.setStyle("-fx-background-color: #2e1800;");
        }

        // Style header label text
        Node headerLabel = dp.lookup(".header-panel .label");
        if (headerLabel instanceof Label l) {
            l.setStyle(
                    "-fx-text-fill: #FFD700; " +
                            "-fx-font-family: 'Georgia'; " +
                            "-fx-font-size: 13;"
            );
        }

        // Style content text (the "Select a modifier card:" label)
        Node contentLabel = dp.lookup(".content .label");
        if (contentLabel instanceof Label l) {
            l.setStyle(
                    "-fx-text-fill: white; " +
                            "-fx-font-family: 'Georgia'; " +
                            "-fx-font-size: 12;"
            );
        }

        // Style choice list items
        Node listView = dp.lookup(".list-view");
        if (listView instanceof ListView<?> lv) {
            lv.setStyle(
                    "-fx-control-inner-background: #1c0d00; " +
                            "-fx-text-fill: white; " +
                            "-fx-font-family: 'Georgia'; " +
                            "-fx-selection-bar: #FFD700; " +
                            "-fx-selection-bar-text: black;"
            );
        }
    }
}