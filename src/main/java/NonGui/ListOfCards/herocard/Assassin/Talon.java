package NonGui.ListOfCards.herocard.Assassin;

import NonGui.BaseEntity.BaseCard;
import NonGui.BaseEntity.Cards.HeroCard.HeroCard;
import NonGui.BaseEntity.Player;
import NonGui.BaseEntity.Properties.UnitClass;
import NonGui.GameLogic.GameChoice;
import javafx.scene.control.ChoiceDialog;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static NonGui.GameLogic.GameEngine.players;

public class Talon extends HeroCard {

    public Talon(){
        super(
                "Talon",
                "Live by the blade, die by the blade.",
                "Cutthroat: Roll 6+. Pull up to 2 cards and DISCARD 1",
                UnitClass.Assassin,
                6
        );
    }

    @Override
    public void useAbility(Player player) {
        System.out.println(this.getName() + " uses their ability! (Pull up to 2 cards and DISCARD 1)");

        // 1. หาเป้าหมายที่สามารถดึงการ์ดได้ (ต้องไม่ใช่ตัวเอง และต้องมีการ์ดบนมืออย่างน้อย 1 ใบ)
        ArrayList<Player> validTargetsList = new ArrayList<>();
        for (Player p : players) {
            if (p != player && !p.HandIsEmpty()) {
                validTargetsList.add(p);
            }
        }

        if (validTargetsList.isEmpty()) {
            System.out.println("No other players have cards in hand to pull!");
            return;
        }

        Player[] validTargetsArray = validTargetsList.toArray(new Player[0]);

        // 2. เลือกผู้เล่นเป้าหมาย
        System.out.println(player.getName() + ", choose a player to pull cards from:");
        int targetIndex = GameChoice.selectPlayer(validTargetsArray);

        // ดักจับกรณีผู้เล่นกดยกเลิกหน้าต่าง (ป้องกัน Error)
        if (targetIndex == -1) {
            System.out.println("Action canceled.");
            return;
        }

        Player targetPlayer = validTargetsArray[targetIndex];

        // 3. คำนวณจำนวนการ์ดที่จะดึง (ดึงสูงสุด 2 ใบ)
        int pullCount = Math.min(2, targetPlayer.getCardsInHand().size());
        Random rand = new Random();

        // สร้าง List ชั่วคราวเพื่อเก็บการ์ดที่ขโมยมา
        ArrayList<BaseCard> pulledCards = new ArrayList<>();

        for (int i = 0; i < pullCount; i++) {
            int handSize = targetPlayer.getCardsInHand().size();
            int randomCardIndex = rand.nextInt(handSize); // สุ่ม 0 ถึง handSize - 1

            // ดึงการ์ดออกจากมือเป้าหมาย
            BaseCard pulledCard = targetPlayer.getCardInHand(randomCardIndex);
            pulledCards.add(pulledCard);
        }

        System.out.println(player.getName() + " pulled " + pullCount + " cards from " + targetPlayer.getName() + ".");

        // 4. ให้ผู้เล่นเลือกว่าจะทิ้งใบไหน (ใช้ GUI Dialog แทน Scanner)
        // **แก้บั๊ก**: จะให้เลือกทิ้งก็ต่อเมื่อขโมยมาได้มากกว่า 1 ใบเท่านั้น
        if (pulledCards.size() > 1) {
            List<String> options = new ArrayList<>();
            for (int i = 0; i < pulledCards.size(); i++) {
                options.add((i + 1) + " : " + pulledCards.get(i).getName());
            }

            ChoiceDialog<String> dialog = new ChoiceDialog<>(options.get(0), options);
            dialog.setTitle("Talon Ability");
            dialog.setHeaderText("You pulled " + pulledCards.size() + " cards. Choose one to DISCARD:");
            dialog.setContentText("Cards:");

            String result = dialog.showAndWait().orElse(null);
            int discardIndex = 0; // ค่าเริ่มต้นถ้าเกิดผู้เล่นกดปิดหน้าต่าง (ให้ทิ้งใบแรก)

            if (result != null) {
                discardIndex = Integer.parseInt(result.split(" ")[0]) - 1;
            }

            // 5. นำการ์ดที่เลือกไปทิ้ง
            BaseCard discardedCard = pulledCards.get(discardIndex);
            System.out.println(discardedCard.getName() + " has been DISCARDED.");
            pulledCards.remove(discardIndex);
        }

        // 6. การ์ดที่เหลือจะถูกนำเข้ามือของเรา
        for (BaseCard card : pulledCards) {
            player.addCardToHand(card);
            System.out.println(card.getName() + " has been added to your hand.");
        }
    }
}