package NonGui.ListOfCards.herocard.Tank;

import NonGui.BaseEntity.BaseCard;
import NonGui.BaseEntity.Cards.HeroCard.HeroCard;
// ⚠️ ตรวจสอบ Path ของ ModifierCard ในโปรเจกต์คุณด้วยนะครับ
import NonGui.BaseEntity.Cards.ModifierCard.ModifierCard;
import NonGui.BaseEntity.Player;
import NonGui.BaseEntity.Properties.UnitClass;
import NonGui.GameLogic.GameEngine;
import javafx.scene.control.ChoiceDialog;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class Nautilus extends HeroCard {
    public Nautilus() {
        super(
                "Nautilus",
                "Beware the depths.",
                "Dredge Line: Roll 6+. Search the discard pile for a Modifier card and add it to your hand.",
                UnitClass.Tank,
                6
        );
    }

    @Override
    public void useAbility(Player player) {
        System.out.println("⚓ Nautilus uses Dredge Line!");

        // 1. ดึงกองทิ้งจาก GameEngine (ซึ่งเป็น ObservableList)
        // แล้วกรองหาเฉพาะ Modifier
        List<BaseCard> availableModifiers = GameEngine.deck.getDiscardPile().stream()
                .filter(card -> card instanceof ModifierCard)
                .collect(Collectors.toList());

        // 2. ถ้าไม่มี Modifier ในกองทิ้งเลย
        if (availableModifiers.isEmpty()) {
            System.out.println("🌊 The depths are empty... (No Modifier cards in discard pile)");
            // แจ้งเตือนผู้เล่นสักนิดว่าไม่มีของให้เก็บ
            javafx.scene.control.Alert alert = new javafx.scene.control.Alert(
                    javafx.scene.control.Alert.AlertType.INFORMATION,
                    "No Modifier cards found in the discard pile!",
                    javafx.scene.control.ButtonType.OK
            );
            alert.showAndWait();
            return;
        }

        // 3. เตรียมชื่อการ์ดสำหรับแสดงใน ChoiceDialog
        List<String> options = availableModifiers.stream()
                .map(BaseCard::getName)
                .collect(Collectors.toList());

        // 4. แสดง ChoiceDialog
        ChoiceDialog<String> dialog = new ChoiceDialog<>(options.get(0), options);
        dialog.setTitle("Nautilus Ability: Dredge Line");
        dialog.setHeaderText("Select a Modifier card to retrieve");
        dialog.setContentText("Choose your card:");

        Optional<String> result = dialog.showAndWait();

        // 5. ดำเนินการย้ายการ์ด
        if (result.isPresent()) {
            String selectedName = result.get();

            // หาใบที่ชื่อตรงกันในลิสต์ที่เรากรองไว้
            BaseCard pickedCard = availableModifiers.stream()
                    .filter(c -> c.getName().equals(selectedName))
                    .findFirst()
                    .orElse(null);

            if (pickedCard != null) {
                // ลบออกจากกองทิ้งของ Deck
                GameEngine.deck.getDiscardPile().remove(pickedCard);
                // เพิ่มเข้ามือผู้เล่น
                player.addCardToHand(pickedCard);

                System.out.println("⚓ Nautilus dragged " + pickedCard.getName() + " back to " + player.getName() + "'s hand!");

                // Refresh GUI
                try {
                    gui.BoardView.refresh();
                } catch (Exception e) {}
            }
        }
    }
}