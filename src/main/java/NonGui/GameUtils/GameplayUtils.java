package NonGui.GameUtils;

import NonGui.BaseEntity.Player;
import NonGui.BaseEntity.Cards.HeroCard.HeroCard;
import javafx.scene.control.ChoiceDialog;

import java.util.ArrayList;
import java.util.List;

import static NonGui.GameLogic.GameEngine.objectives;
import static NonGui.GameUtils.DiceUtils.getRoll;
import static NonGui.GameUtils.GenerationsUtils.generateRandomObjective;

public class GameplayUtils {
    // Sacrifice n Destroy
    public static void SacrificeHero(Player player, int number) {
        for (int i = 0; i < number; ++i) {
            if (player.boardIsEmpty()) {
                break; // stop if no heroes left
            }

            // Build list of hero options
            List<String> options = new ArrayList<>();
            for (int j = 0; j < player.getOwnedHero().length; j++) {
                HeroCard hero = player.getHeroCard(j);
                if (hero != null) {
                    options.add("Slot " + j + ": " + hero.getName());
                }
            }
            options.add("Cancel"); // allow cancel

            // Show choice dialog
            ChoiceDialog<String> dialog = new ChoiceDialog<>("Cancel", options);
            dialog.setTitle("Sacrifice Hero");
            dialog.setHeaderText(player.getName() + " - Choose a hero to sacrifice");
            dialog.setContentText("Select a hero:");

            String result = dialog.showAndWait().orElse("Cancel");

            if (result.equals("Cancel")) {
                // If cancel, retry this iteration
                --i;
                continue;
            }

            // Extract index from string
            int selectedHero = Integer.parseInt(result.split(" ")[1]);

            // Try to remove hero
            if (!player.removeHeroCard(selectedHero)) {
                System.out.println("Invalid choice: slot empty");
                --i; // retry
            }
        }
    }

    // Rotate objective
    public static void rotateObjective(int objectiveIndex) {
        objectives[objectiveIndex] = generateRandomObjective();
    }

    // Challenge (dice roll)
    public static boolean beginChallenge() {
        int userRoll = 0, oppRoll = 0;

        // Keep rolling until different results
        while (userRoll == oppRoll) {
            userRoll = getRoll();
            oppRoll = getRoll();
        }

        return userRoll > oppRoll;
    }
}
