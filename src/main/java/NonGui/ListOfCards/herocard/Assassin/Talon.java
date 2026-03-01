package NonGui.ListOfCards.herocard.Assassin;

import NonGui.BaseEntity.BaseCard;
import NonGui.BaseEntity.Cards.HeroCard.HeroCard;
import NonGui.BaseEntity.Player;
import NonGui.BaseEntity.Properties.UnitClass;
import NonGui.GameLogic.GameChoice;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

import static NonGui.GameLogic.GameEngine.players;

public class Talon extends HeroCard {

    public Talon(){
        super(
                "Talon",
                "Live by the blade, die by the blade.",
                "Cutthroat: Roll 6+. Pull a card from another player's hand.",
                UnitClass.Assassin,
                6
        );
    }

    @Override
    public void useAbility(Player player) {
        System.out.println(this.getName() + " uses their ability! (Pull 2 cards and DISCARD 1)");

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
        Player targetPlayer = validTargetsArray[targetIndex];

        // 3. คำนวณจำนวนการ์ดที่จะดึง (ดึง 2 ใบ แต่ถ้าเป้าหมายมีใบเดียวก็จะดึงแค่ 1 ใบ)
        int pullCount = Math.min(2, targetPlayer.getCardsInHand().size());
        Random rand = new Random();

        // สร้าง List ชั่วคราวเพื่อเก็บการ์ดที่ขโมยมาให้เราเลือก
        ArrayList<BaseCard> pulledCards = new ArrayList<>();

        for (int i = 0; i < pullCount; i++) {
            int handSize = targetPlayer.getCardsInHand().size();
            int randomCardIndex = rand.nextInt(handSize); // สุ่ม 1 ถึง handSize

            // ดึงการ์ดออกจากมือเป้าหมาย
            BaseCard pulledCard = targetPlayer.getCardInHand(randomCardIndex);
            pulledCards.add(pulledCard);
        }

        System.out.println(player.getName() + " pulled " + pullCount + " cards from " + targetPlayer.getName() + ".");

        // 4. ให้ผู้เล่นร่ายสกิลเลือกว่าจะทิ้งใบไหน (ใช้ Scanner รับค่า)
        System.out.println("Here are the cards you pulled. Select one to DISCARD:");
        for (int i = 0; i < pulledCards.size(); i++) {
            System.out.println((i + 1) + ". " + pulledCards.get(i).getName());
        }

        Scanner scanner = new Scanner(System.in);
        int discardIndex = -1;
        while (discardIndex < 1 || discardIndex > pulledCards.size()) {
            System.out.print("Enter the number of the card to discard (1 to " + pulledCards.size() + "): ");
            try {
                discardIndex = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number.");
            }
        }

        // 5. นำการ์ดที่เลือกไปทิ้ง
        BaseCard discardedCard = pulledCards.get(discardIndex - 1);
        System.out.println(discardedCard.getName() + " has been DISCARDED.");
        pulledCards.remove(discardIndex - 1);

        // 6. การ์ดที่เหลือ (ถ้ามี) จะถูกนำเข้ามือของเรา
        for (BaseCard card : pulledCards) {
            player.addCardToHand(card);
            System.out.println(card.getName() + " has been added to your hand.");
        }
    }
}
