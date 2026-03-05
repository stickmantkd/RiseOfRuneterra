package NonGui.ListOfObjective;

import NonGui.BaseEntity.Objective;
import NonGui.BaseEntity.Player;
import NonGui.BaseEntity.Cards.HeroCard.HeroCard;
import NonGui.GameLogic.GameEngine;
import gui.board.StatusBar;
import javafx.application.Platform;
import javafx.scene.control.ChoiceDialog;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class ElderDragon extends Objective {

    public ElderDragon() {
        super("Elder Dragon",
                "The Aspect of Dragon transcends all, granting absolute authority.",
                11, 12);
        setRequirementDescription("10- | Requires at least 4 Hero cards on your board.");
        setPrizeDescription("11+ | MAX Action Points +1 (Permanent).");
        setPunishmentDescription("Sacrifice (DISCARD) 1 of your Hero cards.");
    }

    @Override
    public boolean canTry(Player player) {
        int count = 0;
        // ใช้ได้กับทั้ง Array และ List
        for (HeroCard h : player.getOwnedHero()) {
            if (h != null) {
                count++;
            }
        }
        return count >= 4;
    }

    @Override
    public void grantPrize(Player player) {
        StatusBar.showMessage("🐲 THE DRAGON SOUL IS YOURS! Prize: " + getPrizeDescription());

        // เพิ่ม Max Action Point ให้ผู้เล่นถาวร
        player.setMaxActionPoint(player.getMaxActionPoint() + 1);

        // แถม: เติม AP ให้เต็มทันทีในเทิร์นที่ฆ่าบอสได้ (เป็นรางวัลพิเศษ)
        player.setActionPoint(player.getMaxActionPoint());
    }

    @Override
    public void grantPunishment(Player player) {
        StatusBar.showMessage("💀 ELDER DRAGON'S FURY! Punishment: " + getPunishmentDescription());

        javafx.application.Platform.runLater(() -> {
            // 1. ดึง Array ของฮีโร่ออกมา
            HeroCard[] heroes = player.getOwnedHero();

            // 2. สร้างรายการชื่อฮีโร่ที่มีอยู่จริง (ตัวที่ไม่ใช่ null) เพื่อให้ผู้เล่นเลือก
            java.util.List<String> options = new java.util.ArrayList<>();
            for (HeroCard h : heroes) {
                if (h != null) {
                    options.add(h.getName());
                }
            }

            if (options.isEmpty()) return;

            // 3. แสดงหน้าต่างเลือก
            javafx.scene.control.ChoiceDialog<String> dialog = new javafx.scene.control.ChoiceDialog<>(options.get(0), options);
            dialog.setTitle("Elder Dragon's Sacrifice");
            dialog.setHeaderText("Choose a Hero to SACRIFICE");

            java.util.Optional<String> result = dialog.showAndWait();
            String selectedName = result.orElse(options.get(0));

            // 4. วนลูปหาช่อง (Index) ที่มีฮีโร่ชื่อตรงกับที่เลือก แล้วลบออก (set เป็น null)
            for (int i = 0; i < heroes.length; i++) {
                if (heroes[i] != null && heroes[i].getName().equals(selectedName)) {

                    // ถอดไอเทมก่อน (ถ้ามี)
                    if (heroes[i].getItem() != null) {
                        heroes[i].getItem().onUnEquip(heroes[i]);
                    }

                    // ส่งลงกองทิ้ง
                    NonGui.GameLogic.GameEngine.deck.discardCard(heroes[i]);

                    StatusBar.showMessage(heroes[i].getName() + " was consumed by the Dragon...");

                    // สั่งให้ช่องนี้ว่างเปล่า (นี่คือการลบสำหรับ Array)
                    heroes[i] = null;
                    break; // ลบเสร็จแล้วออกจากลูปทันที
                }
            }

            try { gui.BoardView.refresh(); } catch (Exception e) {}
        });
    }
}
