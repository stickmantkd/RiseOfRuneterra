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

/**
 * Handles the visual interface for the Modifier Phase.
 * Provides styled dialogs for players to interrupt or enhance rolls using Modifier cards.
 * <p>
 * This class uses JavaFX ChoiceDialogs with custom CSS lookups to maintain
 * the game's dark fantasy aesthetic.
 * * @author GeminiCollaborator
 */
public class ModifierView {

    // --- Style Constants ---
    private static final String DIALOG_STYLE =
            "-fx-background-color: linear-gradient(to bottom, #1c0d00, #2e1800);" +
                    "-fx-font-family: 'Georgia';";

    private static final String HEADER_BG = "-fx-background-color: #2e1800;";
    private static final String TEXT_GOLD = "-fx-text-fill: #FFD700; -fx-font-family: 'Georgia'; -fx-font-size: 13px;";
    private static final String TEXT_WHITE = "-fx-text-fill: white; -fx-font-family: 'Georgia'; -fx-font-size: 12px;";

    private static final String LIST_VIEW_STYLE =
            "-fx-control-inner-background: #1c0d00;" +
                    "-fx-text-fill: white;" +
                    "-fx-font-family: 'Georgia';" +
                    "-fx-selection-bar: #FFD700;" +
                    "-fx-selection-bar-text: black;";

    /**
     * Prompts the acting player to select a Modifier card from their hand to apply
     * to a target's roll.
     *
     * @param actingPlayer The player who may play a modifier.
     * @param targetPlayer The player whose roll is being modified.
     * @return The index of the selected card in the acting player's hand, or -1 if they "Pass".
     */
    public static int selectModifierCard(Player actingPlayer, Player targetPlayer) {
        List<String> options = new ArrayList<>();
        List<Integer> handIndexes = new ArrayList<>();

        options.add("Pass");

        // Filter hand for Modifier cards only
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
                String.format("%s — Modify %s's roll?\nCurrent roll: %d",
                        actingPlayer.getName(), targetPlayer.getName(), targetPlayer.getCurrentRoll())
        );
        dialog.setContentText("Select a modifier card:");

        DialogPane dp = dialog.getDialogPane();
        styleDialog(dp);
        adjustDialogPosition(dialog, dp);

        String result = dialog.showAndWait().orElse(null);

        if (result == null || result.equals("Pass")) {
            return -1;
        }

        // Calculate correct index (subtracting 1 because of the "Pass" option at index 0)
        int choiceIndex = options.indexOf(result) - 1;
        return handIndexes.get(choiceIndex);
    }

    /**
     * Prompts the player to choose whether to use the positive or negative effect
     * of a specific Modifier card.
     *
     * @param modifier The modifier card being played.
     * @return 0 for Positive effect, 1 for Negative effect, or -1 if cancelled.
     */
    public static int selectModifierEffect(ModifierCard modifier) {
        List<String> options = List.of(
                "➕  +" + modifier.getPositiveModifier() + "  to a roll",
                "➖  −" + modifier.getNegativeModifier() + "  to a roll"
        );

        ChoiceDialog<String> dialog = new ChoiceDialog<>(options.get(0), options);
        dialog.setTitle("⚡ Modifier Effect");
        dialog.setHeaderText("Choose an effect for " + modifier.getName());
        dialog.setContentText("Select desired outcome:");

        DialogPane dp = dialog.getDialogPane();
        styleDialog(dp);
        adjustDialogPosition(dialog, dp);

        String result = dialog.showAndWait().orElse(null);
        if (result == null) return -1;

        return options.indexOf(result);
    }

    /**
     * Applies custom CSS styles to the DialogPane to match the game theme.
     * Utilizes .lookup() to find internal JavaFX nodes.
     */
    private static void styleDialog(DialogPane dp) {
        dp.setStyle(DIALOG_STYLE);
        dp.applyCss();
        dp.layout();

        // 1. Header Styling
        Node header = dp.lookup(".header-panel");
        if (header != null) {
            header.setStyle(HEADER_BG);
        }

        Node headerLabel = dp.lookup(".header-panel .label");
        if (headerLabel instanceof Label) {
            headerLabel.setStyle(TEXT_GOLD);
        }

        // 2. Content Styling
        Node contentLabel = dp.lookup(".content .label");
        if (contentLabel instanceof Label) {
            contentLabel.setStyle(TEXT_WHITE);
        }

        // 3. ListView Styling (The dropdown list)
        Node listView = dp.lookup(".list-view");
        if (listView instanceof ListView<?>) {
            listView.setStyle(LIST_VIEW_STYLE);
        }
    }

    /**
     * Shifts the dialog to the right side of the screen to avoid obscuring
     * the center of the game board.
     */
    private static void adjustDialogPosition(ChoiceDialog<String> dialog, DialogPane dp) {
        dialog.setOnShown(e -> {
            Stage stage = (Stage) dp.getScene().getWindow();
            // Offset to the right to keep board visibility
            stage.setX(stage.getX() + stage.getWidth() * 1.5);
        });
    }
}