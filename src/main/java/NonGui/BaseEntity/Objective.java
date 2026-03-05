package NonGui.BaseEntity;

import static NonGui.GameUtils.DiceUtils.*;
import static NonGui.GameUtils.GameplayUtils.*;
import javafx.scene.control.Alert;
import javafx.application.Platform;

/**
 * An abstract base class representing an Objective (Boss Monster) in the game.
 * Players must meet requirements and roll dice to defeat them,
 * receiving either a prize upon victory or a punishment upon defeat.
 */
public abstract class Objective {

    // ==========================================
    // Fields
    // ==========================================
    private String name;
    private String flavorText;
    private String requirementDescription;
    private int minTargetRoll;
    private int maxTargetRoll;
    private String prizeDescription;
    private String punishmentDescription;

    // ==========================================
    // Constructors
    // ==========================================

    /**
     * Default constructor for Objective.
     * Initializes with dummy data and default target rolls.
     */
    public Objective() {
        setName("Dummy Objective");
        setFlavorText("I am Strong!!!");
        setMinTargetRoll(6);
        setMaxTargetRoll(12);
    }

    /**
     * Constructs a new Objective with the specified details.
     *
     * @param name          The name of the objective.
     * @param flavorText    The lore or story text.
     * @param minTargetRoll The minimum dice roll required to win.
     * @param maxTargetRoll The maximum dice roll limit.
     */
    public Objective(
            String name,
            String flavorText,
            int minTargetRoll,
            int maxTargetRoll) {
        setName(name);
        setFlavorText(flavorText);
        setMinTargetRoll(minTargetRoll);
        setMaxTargetRoll(maxTargetRoll);
    }

    // ==========================================
    // Functions & Logic
    // ==========================================

    /**
     * Returns the string representation of the objective (its name).
     * @return The name of the objective.
     */
    @Override
    public String toString() {
        return name;
    }

    /**
     * Grants a specific prize to the player upon defeating the objective.
     * @param player The player who won the challenge.
     */
    public abstract void grantPrize(Player player);

    /**
     * Applies a specific punishment to the player upon failing the challenge.
     * @param player The player who failed the challenge.
     */
    public abstract void grantPunishment(Player player);

    /**
     * Checks if the player meets the requirements to challenge this objective.
     * @param player The player attempting the challenge.
     * @return true if the player can try, false otherwise.
     */
    public boolean canTry(Player player) {
        // To be implemented in subclasses (e.g., Check for Hero Classes)
        return true;
    }

    /**
     * Executes the battle sequence between the player and the objective.
     *
     * @param index  The index of this objective on the board.
     * @param player The player attempting to complete the objective.
     * @return true if the battle occurred, false if requirements were not met.
     */
    public boolean tryToComplete(int index, Player player) {
        // 1. ตรวจสอบเงื่อนไขฮีโร่
        if (!canTry(player)) {
            showSimpleAlert("Condition Not Met", "Requirement: " + getRequirementDescription());
            return false;
        }

        // 2. ทอยเต๋า (ดึงค่า RollBonus จาก Ornn/Elixir มาคำนวณอัตโนมัติ)
        int roll = getRoll(player);

        // 3. ตรวจสอบผลแพ้-ชนะ
        if (roll >= minTargetRoll && roll <= maxTargetRoll) {
            player.addOwnedObjective(this);
            grantPrize(player);

            showSimpleAlert("Victory!", "You defeated " + name + "!\nPrize: " + prizeDescription);
            rotateObjective(index); // เลื่อน Objective ตัวใหม่ขึ้นมาแทน
        } else {
            grantPunishment(player);
            showSimpleAlert("Defeat", name + " strikes back!\nPunishment: " + punishmentDescription);
        }

        // Refresh กระดานหลังจบการต่อสู้
        try { gui.BoardView.refresh(); } catch (Exception e) {}

        return true;
    }

    /**
     * Helper method to show an alert dialog on the GUI.
     * @param title   The title of the alert.
     * @param content The text content to display.
     */
    private void showSimpleAlert(String title, String content) {
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle(title);
            alert.setHeaderText(null);
            alert.setContentText(content);
            alert.showAndWait();
        });
    }

    // ==========================================
    // Getters & Setters
    // ==========================================

    public String getFlavorText() { return flavorText; }
    public void setFlavorText(String flavorText) { this.flavorText = flavorText; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getRequirementDescription() { return requirementDescription; }
    public void setRequirementDescription(String requirementDescription) { this.requirementDescription = requirementDescription; }

    public int getMinTargetRoll() { return minTargetRoll; }
    public void setMinTargetRoll(int minTargetRoll) {
        if(minTargetRoll < 2) minTargetRoll = 2;
        this.minTargetRoll = minTargetRoll;
    }

    public int getMaxTargetRoll() { return maxTargetRoll; }
    public void setMaxTargetRoll(int maxTargetRoll) {
        if(maxTargetRoll > 12) maxTargetRoll = 12;
        this.maxTargetRoll = maxTargetRoll;
    }

    public void setPrizeDescription(String description) { this.prizeDescription = description; }
    public String getPrizeDescription() { return prizeDescription; }

    public void setPunishmentDescription(String punishmentDescription) { this.punishmentDescription = punishmentDescription; }
    public String getPunishmentDescription() { return punishmentDescription; }
}