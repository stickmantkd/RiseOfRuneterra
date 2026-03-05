package NonGui.GameUtils;

import NonGui.BaseEntity.Objective;
import NonGui.BaseEntity.Player;
import NonGui.BaseEntity.Cards.HeroCard.HeroCard;
import javafx.scene.control.ChoiceDialog;

import java.util.ArrayList;
import java.util.List;

import static NonGui.GameLogic.GameEngine.objectiveDeck;
import static NonGui.GameLogic.GameEngine.objectives;
import static NonGui.GameUtils.DiceUtils.getRoll;
import static NonGui.GameUtils.GenerationsUtils.drawObjective;

/**
 * Utility class providing logic for specific gameplay actions
 * such as sacrificing heroes, rotating board objectives, and basic dice challenges.
 */
public class GameplayUtils {

    // ==========================================
    // Sacrifice & Destroy Logic
    // ==========================================

    /**
     * Forces a player to choose and sacrifice (destroy) a specified number of heroes from their board.
     * Includes a fail-safe that automatically sacrifices the first hero if the dialog is closed.
     * * @param player The player who must sacrifice heroes.
     * @param number The number of heroes to be destroyed.
     */
    public static void SacrificeHero(Player player, int number) {
        for (int i = 0; i < number; ++i) {
            if (player.boardIsEmpty()) {
                break; // stop if no heroes left
            }

            // สร้าง List ของชื่อเพื่อแสดงผล และ List ของ index เพื่ออ้างอิงกลับ
            List<String> options = new ArrayList<>();
            List<Integer> validIndices = new ArrayList<>();

            for (int j = 0; j < player.getOwnedHero().length; j++) {
                HeroCard hero = player.getHeroCard(j);
                if (hero != null) {
                    options.add(hero.getName()); // ใส่แค่ชื่อฮีโร่เพียวๆ เลย
                    validIndices.add(j); // เก็บตำแหน่ง (Index) ไว้
                }
            }

            // Show choice dialog
            ChoiceDialog<String> dialog = new ChoiceDialog<>(options.get(0), options);
            dialog.setTitle("Sacrifice Hero");
            dialog.setHeaderText(player.getName() + " - You MUST sacrifice a hero!");
            dialog.setContentText("Select a hero to DESTROY:");

            // ดักรอผลลัพธ์
            String result = dialog.showAndWait().orElse(null);

            // ถ้าผู้เล่นกดปิดหน้าต่าง (พยายามหนี) บังคับลบตัวแรกสุดไปเลย เพื่อป้องกันเกมค้าง!
            int indexToRemove = validIndices.get(0);

            if (result != null) {
                // หาว่าชื่อที่เลือก ตรงกับ Index ไหนในกระดาน
                for (int j = 0; j < options.size(); j++) {
                    if (options.get(j).equals(result)) {
                        indexToRemove = validIndices.get(j);
                        break;
                    }
                }
            }

            // ทำลายฮีโร่
            player.removeHeroCard(indexToRemove);
            System.out.println(player.getName() + " sacrificed a hero.");

            // 🛠️ บังคับอัปเดตหน้าจอทันทีหลังจากลบการ์ด! (สำคัญมาก)
            try {
                gui.BoardView.refresh();
            } catch (Exception e) {
                // ignore if GUI not running
            }
        }
    }

    // ==========================================
    // Board Management Logic
    // ==========================================

    /**
     * Replaces an objective card at a specific slot on the board with a new one from the deck.
     * * @param objectiveIndex The index (0-2) of the objective to be rotated.
     */
    public static void rotateObjective(int objectiveIndex) {
        if (objectiveDeck.isDeckEmpty()) {
            System.out.println("No objectives left to rotate!");
            return;
        }

        Objective newObjective = drawObjective();
        objectives[objectiveIndex] = newObjective;

        System.out.println("Objective at slot " + objectiveIndex + " rotated to: " + newObjective.getName());
    }

    // ==========================================
    // Challenge Logic
    // ==========================================

    /**
     * Executes a simple 2d6 dice duel between two parties until a winner is determined.
     * Typically used for internal game mechanics that require a tie-breaker.
     * * @return true if the "User" (first roller) wins, false if the opponent wins.
     */
    public static boolean beginChallenge() {
        int userRoll = 0, oppRoll = 0;

        // Keep rolling until different results (Sudden Death)
        while (userRoll == oppRoll) {
            userRoll = getRoll();
            oppRoll = getRoll();
        }

        return userRoll > oppRoll;
    }
}