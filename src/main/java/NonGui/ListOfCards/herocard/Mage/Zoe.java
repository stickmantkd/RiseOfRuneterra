package NonGui.ListOfCards.herocard.Mage;

import NonGui.BaseEntity.BaseCard;
import NonGui.BaseEntity.Cards.HeroCard.HeroCard;
import NonGui.BaseEntity.Cards.MagicCard.MagicCard;
import NonGui.BaseEntity.Player;
import NonGui.BaseEntity.Properties.UnitClass;
import javafx.scene.control.ChoiceDialog;

import java.util.ArrayList;
import java.util.List;

import static NonGui.GameUtils.GenerationsUtils.generateRandomCard;

public class Zoe extends HeroCard {

    public Zoe(){
        super(
                "Zoe",
                "More sparkles! More!",
                "Spell Thief: Roll 6+. DRAW a card. If it is a Magic card, you may play it immediately and DRAW a second card.",
                UnitClass.Mage,
                6
        );
    }

    @Override
    public void useAbility(Player player) {
        System.out.println(this.getName() + " uses Spell Thief!");

        // 1. จั่วการ์ดใบแรก
        BaseCard drawnCard = generateRandomCard();
        System.out.println(player.getName() + " drew: " + drawnCard.getName());

        // 2. เช็คว่าเป็น Magic card หรือไม่
        if (drawnCard instanceof MagicCard) {
            MagicCard magic = (MagicCard) drawnCard;
            System.out.println("It's a Magic card! Asking to play immediately...");

            // สร้างหน้าต่างให้ผู้เล่นเลือกว่าจะใช้เลย หรือเก็บเข้ามือ
            List<String> options = new ArrayList<>();
            options.add("1 : Play it immediately");
            options.add("2 : Keep it in hand");

            ChoiceDialog<String> dialog = new ChoiceDialog<>(options.get(0), options);
            dialog.setTitle("Zoe Ability");
            dialog.setHeaderText("You drew a Magic Card: " + magic.getName());
            dialog.setContentText("Do you want to play it immediately for free?");

            String result = dialog.showAndWait().orElse("2 : Keep it in hand");

            // ถ้าเลือกเล่นทันที
            if (result.startsWith("1")) {
                boolean played = magic.playCard(player);
                if (!played) {
                    player.addCardToHand(drawnCard);
                    System.out.println("Could not play the Magic card. It was added to your hand.");
                } else {
                    System.out.println(magic.getName() + " was played successfully!");
                }
            } else {
                // ถ้าเลือกเก็บเข้ามือ
                player.addCardToHand(drawnCard);
                System.out.println("Kept " + magic.getName() + " in hand.");
            }

            // 3. จั่วการ์ดใบที่สองเพิ่ม (ตามสกิลที่ระบุว่าถ้าได้ Magic จะได้จั่วอีกใบ)
            BaseCard secondCard = generateRandomCard();
            player.addCardToHand(secondCard);
            System.out.println(player.getName() + " drew an extra card: " + secondCard.getName());

        } else {
            // 🛠️ แก้ไขบั๊กการ์ดหาย: ถ้าไม่ใช่ Magic card ให้เก็บเข้ามือเสมอ
            player.addCardToHand(drawnCard);
            System.out.println(drawnCard.getName() + " is not a Magic card. Added to hand.");
        }
    }
}