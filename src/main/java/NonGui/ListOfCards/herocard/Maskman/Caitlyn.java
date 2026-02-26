package NonGui.ListOfCards.herocard.Maskman;

import NonGui.BaseEntity.Cards.HeroCard.HeroCard;
import NonGui.BaseEntity.Player;
import NonGui.BaseEntity.Properties.UnitClass;

import static NonGui.GameLogic.GameEngine.players;

public class Caitlyn extends HeroCard {

    public Caitlyn(){
        super(
                "Caitlyn",
                "Meet the long gun of the law.",
                "Ace in the Hole: Roll 9+. DESTROY a Hero card and DRAW a card.",
                UnitClass.Maskman
        );
    }

    @Override
    public void useAbility(Player player) {
        System.out.println(this.getName() + " uses their ability! (DESTROY an enemy hero and DRAW a card)");

        // 1. คัดกรองผู้เล่นเป้าหมาย (ต้องไม่ใช่ตัวเอง และต้องมีฮีโร่บนบอร์ดให้ทำลาย)
        java.util.ArrayList<Player> validTargetsList = new java.util.ArrayList<>();
        // อย่าลืม import static NonGui.GameLogic.GameEngine.players; ไว้ด้านบนคลาสนะครับ
        for (Player p : players) {
            if (p != player && !p.boardIsEmpty()) {
                validTargetsList.add(p);
            }
        }

        // เช็คว่ามีเป้าหมายให้ทำลายหรือไม่
        if (validTargetsList.isEmpty()) {
            System.out.println("No enemy heroes available to DESTROY! Skipping destruction.");
        } else {
            Player[] validTargetsArray = validTargetsList.toArray(new Player[0]);

            // 2. เลือกผู้เล่นเป้าหมายที่จะทำลายฮีโร่
            System.out.println(player.getName() + ", choose a player to DESTROY their hero:");
            int targetIndex = NonGui.GameLogic.GameChoice.selectPlayer(validTargetsArray);
            Player targetPlayer = validTargetsArray[targetIndex - 1];

            // 3. เลือกฮีโร่บนบอร์ดเป้าหมายและทำลายทิ้ง
            System.out.println("Select a hero from " + targetPlayer.getName() + "'s board to DESTROY:");
            int heroIndex = NonGui.GameLogic.GameChoice.selectHeroCard(targetPlayer);

            targetPlayer.removeHeroCard(heroIndex - 1); // ลบฮีโร่ออกจากบอร์ดเป้าหมาย
            System.out.println("BOOM! A hero from " + targetPlayer.getName() + "'s board has been destroyed!");
        }

        // 4. จั่วการ์ด 1 ใบ (ทำงานเสมอไม่ว่าจะได้ทำลายฮีโร่หรือไม่)
        System.out.println(player.getName() + " draws 1 card.");
        player.DrawRandomCard();
    }
}