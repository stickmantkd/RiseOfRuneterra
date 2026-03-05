package NonGui.ListOfCards.magiccard;

import NonGui.BaseEntity.Cards.MagicCard.MagicCard;
import NonGui.BaseEntity.Player;
import NonGui.BaseEntity.BaseCard;
import NonGui.BaseEntity.Properties.UnitClass;
import NonGui.GameLogic.GameEngine;
import gui.board.StatusBar;
import javafx.scene.control.ChoiceDialog;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class PickACard extends MagicCard {

    public PickACard() {
        super(
                "Pick a Card",
                "Lady Luck is smilin'.",
                "DRAW 3 cards and DISCARD a card."
        );
    }

    @Override
    public boolean playCard(Player player) {
        System.out.println("\n🎴 " + player.getName() + " casts " + this.getName() + "!");

        // ==========================================
        // 1. DRAW step (จั่ว 3 ใบ)
        // ==========================================
        System.out.println(player.getName() + " draws 3 cards...");
        for (int i = 0; i < 3; i++) {
            player.drawRandomCard();
        }

        // Refresh จอทันทีเพื่อให้ผู้เล่นเห็นการ์ดใหม่ก่อนเลือกทิ้ง
        try { gui.BoardView.refresh(); } catch (Exception e) {}

        // ==========================================
        // 2. DISCARD step (ใช้ GUI เลือกทิ้ง 1 ใบ)
        // ==========================================
        if (player.getCardsInHand().isEmpty()) {
            System.out.println("No cards in hand to discard.");
            return true;
        }

        // เตรียมรายชื่อการ์ดในมือ (รวมใบที่เพิ่งจั่วมาด้วย)
        List<String> handOptions = player.getCardsInHand().stream()
                .map(BaseCard::getName)
                .collect(Collectors.toList());

        ChoiceDialog<String> dialog = new ChoiceDialog<>(handOptions.get(0), handOptions);
        dialog.setTitle("Pick a Card: Discard Phase");
        dialog.setHeaderText(player.getName() + ", choose 1 card to DISCARD");
        dialog.setContentText("Select card:");

        Optional<String> result = dialog.showAndWait();

        if (result.isPresent()) {
            String selectedName = result.get();
            // หาการ์ดใบแรกที่ชื่อตรงกับที่เลือก
            BaseCard toDiscard = player.getCardsInHand().stream()
                    .filter(c -> c.getName().equals(selectedName))
                    .findFirst().orElse(null);

            if (toDiscard != null) {
                player.getCardsInHand().remove(toDiscard);
                // ส่งลงกองทิ้งเพื่อให้ Nautilus/Zilean เก็บได้
                GameEngine.deck.discardCard(toDiscard);
                System.out.println("🗑️ " + toDiscard.getName() + " has been DISCARDED.");
            }
        } else {
            // ตามกฎการ์ดใบนี้ "ต้อง" ทิ้ง ถ้าผู้เล่นกด Cancel เราจะสุ่มทิ้งให้ หรือบังคับเลือกใบแรก
            BaseCard forcedDiscard = player.getCardsInHand().get(0);
            player.getCardsInHand().remove(forcedDiscard);
            GameEngine.deck.discardCard(forcedDiscard);
            System.out.println("⚠️ No card selected. Forced discard: " + forcedDiscard.getName());
        }

        // Refresh อีกรอบหลังทิ้งเสร็จ
        try { gui.BoardView.refresh(); } catch (Exception e) {}
        if(player.getOwnedLeader().getUnitClass() == UnitClass.Mage){
            player.drawRandomCard(); // สั่งจั่วเพิ่ม 1 ใบ
            StatusBar.showMessage("Mage Leader: Magic used! Drawing an extra card.");
        }
        return true;
    }
}