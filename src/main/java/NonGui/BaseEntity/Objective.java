package NonGui.BaseEntity;

import static NonGui.GameUtils.DiceUtils.*;
import static NonGui.GameUtils.GameplayUtils.*;
import javafx.scene.control.Alert;
import javafx.application.Platform;

public abstract class Objective {
    // Fields
    private String name;
    private String flavorText;
    private String requirementDescription;
    private int minTargetRoll;
    private int maxTargetRoll;
    private String prizeDescription;
    private String punishmentDescription;

    // ==========================================
    // Constructors (เดิม)
    // ==========================================
    public Objective() {
        setName("Dummy Objective");
        setFlavorText("I am Strong!!!");
        setMinTargetRoll(6);
        setMaxTargetRoll(12);
    }

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
    @Override
    public String toString() {
        return name;
    }

    public abstract void grantPrize(Player player);
    public abstract void grantPunishment(Player player);

    public boolean canTry(Player player) {
        // To be implemented in subclasses (e.g., Check for Hero Classes)
        return true;
    }

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

    // Helper สำหรับ GUI
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
    // Getters & Setters (เดิม)
    // ==========================================
    public String getFlavorText() {
        return flavorText;
    }
    public void setFlavorText(String flavorText) {
        this.flavorText = flavorText;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getRequirementDescription() {
        return requirementDescription;
    }
    public void setRequirementDescription(String requirementDescription) {
        this.requirementDescription = requirementDescription;
    }

    public int getMinTargetRoll() {
        return minTargetRoll;
    }
    public void setMinTargetRoll(int minTargetRoll) {
        if(minTargetRoll < 2) minTargetRoll = 2;
        this.minTargetRoll = minTargetRoll;
    }

    public int getMaxTargetRoll() {
        return maxTargetRoll;
    }
    public void setMaxTargetRoll(int maxTargetRoll) {
        if(maxTargetRoll > 12) maxTargetRoll = 12;
        this.maxTargetRoll = maxTargetRoll;
    }

    public void setPrizeDescription(String description) {
        this.prizeDescription = description;
    }
    public String getPrizeDescription() {
        return prizeDescription;
    }

    public void setPunishmentDescription(String punishmentDescription) {
        this.punishmentDescription = punishmentDescription;
    }
    public String getPunishmentDescription() {
        return punishmentDescription;
    }
}