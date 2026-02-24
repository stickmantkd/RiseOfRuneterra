package NonGui.ListOfCards.herocard.Support;

import NonGui.BaseEntity.Cards.HeroCard.HeroCard;
import NonGui.BaseEntity.Player;
import NonGui.BaseEntity.Properties.UnitClass;
import NonGui.GameLogic.GameChoice;

import java.util.ArrayList;

// นำเข้าตัวแปร players จาก GameEngine
import static NonGui.GameLogic.GameEngine.players;

public class Neeko extends HeroCard {
    public Neeko() {
        super(
                "Neeko",
                "Neeko is not a sad tomato. She is a strong tomato!",
                "Inherent Glamour: Roll 6+. Choose a player. STEAL a Hero from that player and move this card to their Party.",
                UnitClass.Support
        );
    }

    @Override
    public void useAbility(Player player) {
        System.out.println(this.getName() + " uses Inherent Glamour! (SHAPESHIFT!)");

        // 1. หาผู้เล่นที่มีฮีโร่บนบอร์ดให้เราขโมย (ต้องไม่ใช่ตัวเอง และบอร์ดห้ามว่าง)
        ArrayList<Player> validTargetsList = new ArrayList<>();
        for (Player p : players) {
            if (p != player && !p.boardIsEmpty()) {
                validTargetsList.add(p);
            }
        }

        if (validTargetsList.isEmpty()) {
            System.out.println("No other players with heroes available to STEAL from!");
            return;
        }

        Player[] validTargetsArray = validTargetsList.toArray(new Player[0]);

        // 2. เลือกผู้เล่นเป้าหมาย
        System.out.println(player.getName() + ", choose a player to STEAL a Hero from:");
        int targetIndex = GameChoice.selectPlayer(validTargetsArray);
        Player targetPlayer = validTargetsArray[targetIndex - 1];

        // 3. เลือกฮีโร่บนบอร์ดเป้าหมายที่จะขโมย
        System.out.println("Select a hero from " + targetPlayer.getName() + "'s board to STEAL:");
        int heroIndex = GameChoice.selectHeroCard(targetPlayer);

        // จำการ์ดฮีโร่เป้าหมายไว้ (getHeroCard รับค่า 1-based index ตามที่ GameChoice ส่งมา)
        HeroCard stolenHero = targetPlayer.getHeroCard(heroIndex);

        // --- เริ่มกระบวนการสลับไพ่ (Swap) ---

        // 4. เอา Neeko (this) ออกจากบอร์ดของคนร่ายสกิลก่อน
        for (int i = 0; i < player.getOwnedHero().length; i++) {
            if (player.getOwnedHero()[i] == this) {
                player.removeHeroCard(i); // เมธอดนี้รับค่า 0-based array index
                break;
            }
        }

        // 5. เอาฮีโร่เป้าหมายออกจากบอร์ดศัตรู
        targetPlayer.removeHeroCard(heroIndex - 1);

        // 6. เอาฮีโร่ที่ขโมยมา ใส่ลงในช่องว่างของบอร์ดคนร่าย
        for (int i = 0; i < player.getOwnedHero().length; i++) {
            if (player.getOwnedHero()[i] == null) {
                player.getOwnedHero()[i] = stolenHero;
                break;
            }
        }

        // 7. เอา Neeko (this) ไปใส่ในช่องว่างของบอร์ดศัตรู
        for (int i = 0; i < targetPlayer.getOwnedHero().length; i++) {
            if (targetPlayer.getOwnedHero()[i] == null) {
                targetPlayer.getOwnedHero()[i] = this;
                break;
            }
        }

        System.out.println("SUCCESS! " + player.getName() + " stole " + stolenHero.getName() + " from " + targetPlayer.getName() + "!");
        System.out.println("Neeko has moved to " + targetPlayer.getName() + "'s party.");
    }
}