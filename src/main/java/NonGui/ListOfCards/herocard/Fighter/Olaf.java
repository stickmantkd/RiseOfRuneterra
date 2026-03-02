package NonGui.ListOfCards.herocard.Fighter;

import NonGui.BaseEntity.Cards.HeroCard.HeroCard;
import NonGui.BaseEntity.Player;
import NonGui.BaseEntity.Properties.UnitClass;
import NonGui.GameLogic.GameChoice;
import javafx.scene.control.ChoiceDialog;

import java.util.ArrayList;
import java.util.List;

import static NonGui.GameLogic.GameEngine.players;

public class Olaf extends HeroCard {

    public Olaf(){
        super(
                "Olaf",
                "Leave nothing behind!",
                "Ragnarok: Roll 10+. DISCARD up to 3 cards. For each card discarded, DESTROY a Hero card.",
                UnitClass.Fighter,
                10
        );
    }

    @Override
    public void useAbility(Player player) {
        // 🛠️ อัปเดตหน้าจอก่อนเริ่มสกิล
        try {
            gui.BoardView.refresh();
        } catch (Exception e) {}

        System.out.println(this.getName() + " uses Ragnarok!");

        // เช็คจำนวนการ์ดบนมือก่อน ว่ามีให้ทิ้งสูงสุดกี่ใบ
        int maxDiscard = Math.min(3, player.getCardsInHand().size());
        if (maxDiscard == 0) {
            System.out.println(player.getName() + " has no cards to discard. Ragnarok ends.");
            return;
        }

        // 1. ถามผู้เล่นว่าจะทิ้งการ์ดกี่ใบ (เปลี่ยนมาใช้ ChoiceDialog แทน Scanner แก้เกมค้าง!)
        List<String> amountOptions = new ArrayList<>();
        for (int i = 0; i <= maxDiscard; i++) {
            amountOptions.add(String.valueOf(i));
        }

        ChoiceDialog<String> amountDialog = new ChoiceDialog<>(amountOptions.get(0), amountOptions);
        amountDialog.setTitle("Olaf Ability: Ragnarok");
        amountDialog.setHeaderText(player.getName() + ", choose amount to DISCARD");
        amountDialog.setContentText("How many cards do you want to DISCARD? (0 to " + maxDiscard + "):");

        // ดักผลลัพธ์ ถ้ากดปิดหน้าต่าง (Cancel) ให้ถือว่าเลือก 0
        String result = amountDialog.showAndWait().orElse("0");
        int numToDiscard = Integer.parseInt(result);

        if (numToDiscard == 0) {
            System.out.println(player.getName() + " chose to discard 0 cards. Ragnarok ends.");
            return;
        }

        // 2. ทิ้งการ์ดตามจำนวนที่ระบุ
        for (int i = 0; i < numToDiscard; i++) {
            System.out.println(player.getName() + ", select a card to DISCARD (" + (i + 1) + "/" + numToDiscard + "):");
            int cardIndex = GameChoice.selectCardsInHand(player);

            // ดักกดยกเลิก
            if (cardIndex != -1) {
                player.getCardInHand(cardIndex); // โค้ดคุณบอกว่าเมธอดนี้ remove ให้อยู่แล้ว
                System.out.println("Card discarded.");
            }
        }

        // 3. ทำลายฮีโร่ตามจำนวนการ์ดที่ทิ้งไป
        for (int i = 0; i < numToDiscard; i++) {
            ArrayList<Player> validTargetsList = new ArrayList<>();
            for (Player p : players) {
                if (p != player && !p.boardIsEmpty()) {
                    validTargetsList.add(p);
                }
            }

            if (validTargetsList.isEmpty()) {
                System.out.println("No enemy heroes left to destroy!");
                break;
            }

            Player[] validTargetsArray = validTargetsList.toArray(new Player[0]);

            System.out.println(player.getName() + ", choose a player to DESTROY their hero (" + (i + 1) + "/" + numToDiscard + "):");
            int targetIndex = GameChoice.selectPlayer(validTargetsArray);

            if (targetIndex == -1) {
                System.out.println("Canceled targeting.");
                continue;
            }

            Player targetPlayer = validTargetsArray[targetIndex];

            System.out.println("Select a hero from " + targetPlayer.getName() + "'s board to DESTROY:");
            int heroIndex = GameChoice.selectHeroCard(targetPlayer);

            if (heroIndex != -1) {
                try {
                    // 🛠️ แก้ไขตามคอมเมนต์คุณ: ถ้า selectHeroCard ส่งค่าเริ่มที่ 1 มา ต้องลบ 1
                    // ผมใส่ try-catch กันเหนียวไว้ให้ ถ้า error มันจะลองไม่ลบ 1 แทน (เผื่อโค้ด GameChoice ปรับแก้ไปแล้ว)
                    targetPlayer.removeHeroCard(heroIndex - 1);
                } catch (IndexOutOfBoundsException e) {
                    targetPlayer.removeHeroCard(heroIndex);
                }

                System.out.println("A hero from " + targetPlayer.getName() + " has been destroyed!");

                // 🛠️ อัปเดตหน้าจอทันทีเมื่อการ์ดหายไป
                try {
                    gui.BoardView.refresh();
                } catch (Exception e) {}
            }
        }
    }
}