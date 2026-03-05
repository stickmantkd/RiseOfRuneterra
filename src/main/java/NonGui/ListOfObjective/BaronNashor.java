package NonGui.ListOfObjective;

import NonGui.BaseEntity.Cards.HeroCard.HeroCard;
import NonGui.BaseEntity.Objective;
import NonGui.BaseEntity.Player;
import gui.board.StatusBar;

import static NonGui.GameUtils.GameplayUtils.*;

public class BaronNashor extends Objective {
    //constructors
    public BaronNashor() {
        super("Baron Nashor",
                "His barony is rather small. Just large enough to accommodate only him, really.",
                10,12);
        setRequirementDescription("have 3 Heroes");
        setPrizeDescription("drawn 5 cards");
        setPunishmentDescription("Sacrifice 1 Heroes");
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
        return count >= 3;
    }

    //functions
    @Override
    public void grantPrize(Player player) {
        player.DrawRandomCard();
        player.DrawRandomCard();
        player.DrawRandomCard();
        player.DrawRandomCard();
        player.DrawRandomCard();
    }
    @Override
    public void grantPunishment(Player player) {
        StatusBar.showMessage("💀 Baron! Punishment: " + getPunishmentDescription());

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
            dialog.setTitle("Baron's Sacrifice");
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
