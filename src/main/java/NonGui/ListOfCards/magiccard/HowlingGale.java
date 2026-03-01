package NonGui.ListOfCards.magiccard;

import NonGui.BaseEntity.Cards.MagicCard.MagicCard;
import NonGui.BaseEntity.Player;
import NonGui.BaseEntity.Cards.HeroCard.HeroCard;
import NonGui.BaseEntity.Cards.Itemcard.ItemCard;
import NonGui.GameLogic.GameChoice;

import java.util.ArrayList;

import static NonGui.GameLogic.GameEngine.players;

public class HowlingGale extends MagicCard {

    public HowlingGale() {
        super(
                "Howling Gale",
                "And you thought it was just a breeze!", // คำพูดของ Janna
                "Return an Item card equipped to any player's Hero card to that player's hand, then DRAW a card."
        );
    }

    @Override
    public boolean playCard(Player player) {
        System.out.println("\n🌪️ " + player.getName() + " casts " + this.getName() + "! (Return an Item to hand, then DRAW a card)");

        // ==========================================
        // 1. ค้นหาเป้าหมาย: หาผู้เล่นที่มีฮีโร่สวมใส่ไอเทมอยู่
        // ==========================================
        ArrayList<Player> playersWithItems = new ArrayList<>();

        for (Player p : players) {
            for (HeroCard hero : p.getOwnedHero()) {
                // เรียกใช้ getItem() จากคลาส HeroCard ของคุณ
                if (hero != null && hero.getItem() != null) {
                    if (!playersWithItems.contains(p)) {
                        playersWithItems.add(p);
                    }
                }
            }
        }

        // ถ้าไม่มีฮีโร่ตัวไหนบนบอร์ดใส่ไอเทมเลย สกิลจะร่ายไม่ได้
        if (playersWithItems.isEmpty()) {
            System.out.println("There are no equipped items on the board to return! The spell fizzles.");
            return false;
        }

        // ==========================================
        // 2. ขั้นตอนการ Return Item (เลือกเป้าหมายและคืนไอเทม)
        // ==========================================
        Player[] validTargetsArray = playersWithItems.toArray(new Player[0]);

        // เลือกผู้เล่นเป้าหมาย (ไม่ต้อง -1 ตามที่คุณแก้ไขระบบไว้)
        System.out.println("\nChoose a player whose hero has an item to return:");
        int targetIndex = GameChoice.selectPlayer(validTargetsArray);
        Player targetPlayer = validTargetsArray[targetIndex];

        // วนลูปให้ผู้เล่นเลือกฮีโร่จนกว่าจะเลือกตัวที่มีไอเทม
        HeroCard targetHero = null;
        while (targetHero == null || targetHero.getItem() == null) {
            System.out.println("Select a hero from " + targetPlayer.getName() + "'s board that has an item equipped:");
            int heroIndex = GameChoice.selectHeroCard(targetPlayer);
            targetHero = targetPlayer.getHeroCard(heroIndex);

            if (targetHero == null || targetHero.getItem() == null) {
                System.out.println("That hero doesn't have an item equipped! Please select again.");
            }
        }

        // ดึงการ์ดไอเทมออกมาเก็บไว้ก่อน
        ItemCard itemToReturn = targetHero.getItem();

        // ถอดไอเทมออกจากฮีโร่ (เรียกใช้ unEquipItem() ของคุณ ซึ่งจัดการ onUnEquip ให้เรียบร้อยแล้ว!)
        targetHero.unEquipItem();

        // นำการ์ดไอเทมกลับเข้ามือของเจ้าของฮีโร่
        targetPlayer.addCardToHand(itemToReturn);
        System.out.println("🌪️ SWOOSH! " + itemToReturn.getName() + " was blown off " + targetHero.getName() + " and returned to " + targetPlayer.getName() + "'s hand.");

        // ==========================================
        // 3. ขั้นตอนการ DRAW (คนร่ายจั่วการ์ด 1 ใบ)
        // ==========================================
        System.out.println(player.getName() + " draws a card from the flowing wind...");
        player.DrawRandomCard();

        System.out.println("✨ " + this.getName() + " resolved successfully!");
        return true;
    }
}
