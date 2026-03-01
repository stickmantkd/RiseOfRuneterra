package gui;

import NonGui.BaseEntity.BaseCard;
import NonGui.BaseEntity.Cards.ModifierCard.ModifierCard;
import NonGui.BaseEntity.Player;
import javafx.scene.control.ChoiceDialog;

import java.util.ArrayList;
import java.util.List;

public class ModifierView {

    // Show modifier card selection dialog
    public static int selectModifierCard(Player actingPlayer, Player targetPlayer) {
        List<String> options = new ArrayList<>();
        List<Integer> handIndexes = new ArrayList<>();

        options.add("0 : Pass");

        for (int i = 0; i < actingPlayer.getCardsInHand().size(); i++) {
            BaseCard card = actingPlayer.getCardsInHand().get(i);
            if (card instanceof ModifierCard) {
                options.add((handIndexes.size() + 1) + " : " + card.getName());
                handIndexes.add(i);
            }
        }

        ChoiceDialog<String> dialog = new ChoiceDialog<>(options.get(0), options);
        dialog.setTitle("Modifier Phase");

        // Layout exactly as requested
        dialog.setHeaderText(
                actingPlayer.getName() + "\n" +
                        "Do you want to modify " + targetPlayer.getName() + "'s roll\n" +
                        "Current roll: " + targetPlayer.getCurrentRoll()
        );
        dialog.setContentText("Cards:");

        String result = dialog.showAndWait().orElse(null);
        if (result == null) return -1;

        int choice = Integer.parseInt(result.split(" ")[0]);
        if (choice == 0) return -1; // Pass

        return handIndexes.get(choice - 1);
    }

    // Show modifier effect choice (+ or -)
    public static int selectModifierEffect(ModifierCard modifier) {
        List<String> options = List.of(
                "1 : Give + " + modifier.getPositiveModifier() + " to a roll",
                "2 : Give - " + modifier.getNegativeModifier() + " to a roll"
        );

        ChoiceDialog<String> dialog = new ChoiceDialog<>(options.get(0), options);
        dialog.setTitle("Modifier Effect");
        dialog.setHeaderText("Choose an effect");
        dialog.setContentText("Options:");

        String result = dialog.showAndWait().orElse(null);
        if (result == null) return -1;

        return Integer.parseInt(result.split(" ")[0]) - 1;
    }
}
