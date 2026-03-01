package NonGui.ListOfCards.herocard.Maskman;

import NonGui.BaseEntity.BaseCard;
import NonGui.BaseEntity.Cards.HeroCard.HeroCard;
import NonGui.BaseEntity.Cards.Itemcard.ItemCard;
import NonGui.BaseEntity.Player;
import NonGui.BaseEntity.Properties.UnitClass;

import java.util.ArrayList;
import java.util.Scanner;

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
        System.out.println(this.getName() + " uses their ability! (DRAW 2 cards & maybe play an Item)");

        int initialHandSize = player.getCardsInHand().size();

        // 1. จั่วการ์ด 2 ใบ
        player.DrawRandomCard();
        player.DrawRandomCard();

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

            // ใช้ instanceof เพื่อตรวจสอบให้ชัวร์ว่าเป็น ItemCard คลาสแท้ๆ
            if (card instanceof ItemCard) {
                drawnItems.add((ItemCard) card); // Cast ให้เป็น ItemCard แล้วเก็บเข้าลิสต์
            }
        }

        // 3. ถ้ามี Item อย่างน้อย 1 ใบ ให้ถามว่าอยากร่ายเลยไหม
        if (!drawnItems.isEmpty()) {
            System.out.println("\n✨ You drew at least one Item card! Would you like to play one immediately? (Y/N)");
            Scanner scanner = new Scanner(System.in);
            String choice = scanner.nextLine().trim().toUpperCase();

            if (choice.equals("Y")) {
                ItemCard itemToPlay = null;

                if (drawnItems.size() == 1) {
                    itemToPlay = drawnItems.get(0);
                } else {
                    // กรณีฟลุคจั่วได้ Item ทั้ง 2 ใบ ให้ผู้เล่นเลือกใบที่จะร่าย
                    System.out.println("Choose an Item card to play:");
                    for (int i = 0; i < drawnItems.size(); i++) {
                        System.out.println((i + 1) + ". " + drawnItems.get(i).getName());
                    }
                    int itemIndex = -1;
                    while (itemIndex < 1 || itemIndex > drawnItems.size()) {
                        System.out.print("Enter number (1-" + drawnItems.size() + "): ");
                        try {
                            itemIndex = Integer.parseInt(scanner.nextLine());
                        } catch (NumberFormatException e) {
                            System.out.println("Invalid input. Try again.");
                        }
                    }
                    itemToPlay = drawnItems.get(itemIndex - 1);
                }

                System.out.println("⚡ Playing " + itemToPlay.getName() + " immediately!");

                // 4. เอาการ์ดออกจากมือก่อนทำการร่าย
                player.getCardsInHand().remove(itemToPlay);

                // 5. เรียกใช้ playCard() ของไอเทมใบนั้น ซึ่งจะพาเข้าสู่ระบบเลือกฮีโร่และ Challenge ที่คุณเขียนไว้!
                boolean isSuccessfullyPlayed = itemToPlay.playCard(player);

                if (isSuccessfullyPlayed) {
                    System.out.println(">>> [SYSTEM] " + itemToPlay.getName() + " has been played! <<<");
                } else {
                    System.out.println(">>> [SYSTEM] Playing " + itemToPlay.getName() + " was canceled or failed. <<<");
                    // (Optional) ถ้าคุณอยากให้มันกลับคืนเข้ามือกรณีที่เล่นไม่สำเร็จ (เช่น ไม่มีฮีโร่ให้ใส่)
                    // คุณสามารถนำมันยัดกลับเข้ามือได้ที่นี่ ด้วย player.addCardToHand(itemToPlay);
                }

            } else {
                System.out.println("You chose to keep the Item cards in your hand.");
            }
        }
    }
}