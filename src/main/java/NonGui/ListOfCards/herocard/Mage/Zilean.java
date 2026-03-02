package NonGui.ListOfCards.herocard.Mage;

import NonGui.BaseEntity.BaseCard;
import NonGui.BaseEntity.Cards.HeroCard.HeroCard;
import NonGui.BaseEntity.Cards.MagicCard.MagicCard; // ⚠️ ตรวจสอบชื่อคลาส Magic ในโปรเจกต์คุณด้วยนะครับ
import NonGui.BaseEntity.Player;
import NonGui.BaseEntity.Properties.UnitClass;
import NonGui.GameLogic.GameEngine;
import javafx.scene.control.ChoiceDialog;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class Zilean extends HeroCard {

    public Zilean() {
        super(
                "Zilean",
                "I knew you would do that.",
                "Rewind: Roll 5+. Search the discard pile for a Magic card and add it to your hand.",
                UnitClass.Mage,
                5
        );
    }

    @Override
    public void useAbility(Player player) {
        System.out.println("⏳ Zilean uses Rewind! Turning back time...");

        // 1. ดึงกองทิ้งมาหาเฉพาะ Magic Card
        List<BaseCard> magicInDiscard = GameEngine.deck.getDiscardPile().stream()
                .filter(card -> card instanceof MagicCard)
                .collect(Collectors.toList());

        // 2. ถ้าไม่มี Magic Card เลย
        if (magicInDiscard.isEmpty()) {
            System.out.println("⏳ Time is empty... No Magic cards in discard pile.");
            return;
        }

        // 3. แสดงรายชื่อให้เลือกผ่าน ChoiceDialog
        List<String> options = magicInDiscard.stream()
                .map(BaseCard::getName)
                .collect(Collectors.toList());

        ChoiceDialog<String> dialog = new ChoiceDialog<>(options.get(0), options);
        dialog.setTitle("Zilean Ability: Rewind");
        dialog.setHeaderText("Select a Magic card to bring back to the present");
        dialog.setContentText("Choose your spell:");

        Optional<String> result = dialog.showAndWait();

        // 4. ดำเนินการย้ายการ์ดกลับเข้ามือ
        result.ifPresent(selectedName -> {
            BaseCard pickedCard = magicInDiscard.stream()
                    .filter(c -> c.getName().equals(selectedName))
                    .findFirst()
                    .orElse(null);

            if (pickedCard != null) {
                // ลบจากกองทิ้ง แล้วเข้ามือกดเล่นใหม่!
                GameEngine.deck.getDiscardPile().remove(pickedCard);
                player.addCardToHand(pickedCard);

                System.out.println("⏳ " + player.getName() + " retrieved " + pickedCard.getName() + " from the past!");

                // Refresh GUI
                try {
                    gui.BoardView.refresh();
                } catch (Exception e) {}
            }
        });
    }
}