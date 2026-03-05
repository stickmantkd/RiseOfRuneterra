package NonGui.ListOfCards.herocard.Maskman;

import NonGui.BaseEntity.BaseCard;
import NonGui.BaseEntity.Cards.HeroCard.HeroCard;
import NonGui.BaseEntity.Cards.Itemcard.ItemCard;
import NonGui.BaseEntity.Player;
import NonGui.BaseEntity.Properties.UnitClass;
import javafx.scene.control.ChoiceDialog;

import java.util.ArrayList;
import java.util.List;

public class Ezreal extends HeroCard {

    public Ezreal() {
        super(
                "Ezreal",
                "Time for a true display of skill!",
                "Prodigal Explorer: Roll 8+. DRAW 2 cards. If at least one of these cards is an Item card, you may play one of them immediately.",
                UnitClass.Maskman,
                8
        );
    }

    @Override
    public void useAbility(Player player) {
        // 🛠️ อัปเดตหน้าจอก่อนเริ่มสกิล
        try {
            gui.BoardView.refresh();
        } catch (Exception e) {}

        System.out.println(this.getName() + " uses their ability! (DRAW 2 cards & maybe play an Item)");

        int initialHandSize = player.getCardsInHand().size();

        // 1. จั่วการ์ด 2 ใบ
        player.drawRandomCard();
        player.drawRandomCard();

        int newHandSize = player.getCardsInHand().size();
        ArrayList<BaseCard> drawnCards = new ArrayList<>();

        // เก็บการ์ดที่เพิ่งจั่วเข้ามาไว้ใน List
        for (int i = initialHandSize; i < newHandSize; i++) {
            drawnCards.add(player.getCardsInHand().get(i));
        }

        System.out.println(player.getName() + " drew the following cards:");
        ArrayList<ItemCard> drawnItems = new ArrayList<>();

        // 2. แสดงการ์ดที่จั่วได้ และเช็คว่าเป็น ItemCard หรือไม่
        for (BaseCard card : drawnCards) {
            System.out.println("- " + card.getName());

            if (card instanceof ItemCard) {
                drawnItems.add((ItemCard) card);
            }
        }

        // 3. ถ้ามี Item อย่างน้อย 1 ใบ ให้ถามว่าอยากร่ายเลยไหม (เปลี่ยนจาก Scanner เป็น GUI)
        if (!drawnItems.isEmpty()) {
            List<String> options = new ArrayList<>();
            options.add("0 : Keep in hand"); // ตัวเลือกแรกคือเก็บเข้ามือ

            for (int i = 0; i < drawnItems.size(); i++) {
                options.add((i + 1) + " : Play " + drawnItems.get(i).getName());
            }

            ChoiceDialog<String> dialog = new ChoiceDialog<>(options.get(0), options);
            dialog.setTitle("Ezreal Ability: Prodigal Explorer");
            dialog.setHeaderText("✨ You drew at least one Item card!");
            dialog.setContentText("Would you like to play one immediately?");

            String result = dialog.showAndWait().orElse("0 : Keep in hand");

            // ถ้าไม่ได้เลือก "0 : Keep in hand" แสดงว่าจะร่าย
            if (!result.startsWith("0")) {
                int choiceIndex = Integer.parseInt(result.split(" ")[0]) - 1;
                ItemCard itemToPlay = drawnItems.get(choiceIndex);

                System.out.println("⚡ Playing " + itemToPlay.getName() + " immediately!");

                // 4. เอาการ์ดออกจากมือก่อนทำการร่าย
                player.getCardsInHand().remove(itemToPlay);

                // 5. เรียกใช้ playCard() ของไอเทมใบนั้น
                boolean isSuccessfullyPlayed = itemToPlay.playCard(player);

                if (isSuccessfullyPlayed) {
                    System.out.println(">>> [SYSTEM] " + itemToPlay.getName() + " has been played! <<<");
                } else {
                    System.out.println(">>> [SYSTEM] Playing " + itemToPlay.getName() + " was canceled or failed. Returned to hand. <<<");
                    // 🛠️ คืนการ์ดเข้ามือถ้าเล่นไม่สำเร็จ หรือกดยกเลิกระหว่างเล่น
                    player.addCardToHand(itemToPlay);
                }
            } else {
                System.out.println("You chose to keep the Item cards in your hand.");
            }
        }

        // 🛠️ อัปเดตหน้าจอหลังจั่วและจัดเตรียมของเสร็จ
        try {
            gui.BoardView.refresh();
        } catch (Exception e) {}
    }
}