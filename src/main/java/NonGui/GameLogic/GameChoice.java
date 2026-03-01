package NonGui.GameLogic;

import NonGui.BaseEntity.Cards.HeroCard.HeroCard;
import NonGui.BaseEntity.*;
import javafx.scene.control.ChoiceDialog;

import java.util.ArrayList;
import java.util.List;

import static NonGui.GameLogic.GameEngine.*;

public class GameChoice {

    // --- Select a card from hand ---
    public static int selectCardsInHand(Player player) {
        List<String> options = new ArrayList<>();
        for (int i = 0; i < player.getCardsInHand().size(); i++) {
            options.add((i+1) + " : " + player.getCardsInHand().get(i).getName());
        }

        ChoiceDialog<String> dialog = new ChoiceDialog<>(options.get(0), options);
        dialog.setTitle("Select Card");
        dialog.setHeaderText(player.getName() + " - Choose a card");
        dialog.setContentText("Cards:");

        String result = dialog.showAndWait().orElse(null);
        if (result == null) return -1;

        return Integer.parseInt(result.split(" ")[0]) - 1;
    }

    // --- Select objective ---
    public static int selectObjective() {
        List<String> options = new ArrayList<>();
        for (int i = 0; i < objectives.length; i++) {
            options.add((i+1) + " : " + objectives[i].getName());
        }

        ChoiceDialog<String> dialog = new ChoiceDialog<>(options.get(0), options);
        dialog.setTitle("Select Objective");
        dialog.setHeaderText("Choose an objective");
        dialog.setContentText("Objectives:");

        String result = dialog.showAndWait().orElse(null);
        if (result == null) return -1;

        return Integer.parseInt(result.split(" ")[0]) - 1;
    }

    // --- Select player ---
    public static int selectPlayer(Player[] players) {
        List<String> options = new ArrayList<>();
        for (int i = 0; i < players.length; i++) {
            options.add((i+1) + " : " + players[i].getName());
        }

        ChoiceDialog<String> dialog = new ChoiceDialog<>(options.get(0), options);
        dialog.setTitle("Select Player");
        dialog.setHeaderText("Choose a target player");
        dialog.setContentText("Players:");

        String result = dialog.showAndWait().orElse(null);
        if (result == null) return -1;

        return Integer.parseInt(result.split(" ")[0]) - 1;
    }

    // --- Select hero card ---
    public static int selectHeroCard(Player player) {
        HeroCard[] heroCards = player.getOwnedHero();
        List<String> options = new ArrayList<>();
        List<Integer> indexes = new ArrayList<>();

        for (int i = 0; i < heroCards.length; i++) {
            if (heroCards[i] != null) {
                options.add((indexes.size()+1) + " : " + heroCards[i].getName());
                indexes.add(i);
            }
        }

        if (options.isEmpty()) return -1;

        ChoiceDialog<String> dialog = new ChoiceDialog<>(options.get(0), options);
        dialog.setTitle("Select Hero");
        dialog.setHeaderText(player.getName() + " - Choose a hero");
        dialog.setContentText("Heroes:");

        String result = dialog.showAndWait().orElse(null);
        if (result == null) return -1;

        int choice = Integer.parseInt(result.split(" ")[0]) - 1;
        return indexes.get(choice);
    }
}
