package NonGui.ListOfCards.herocard.Assassin;

import NonGui.BaseEntity.Cards.HeroCard.HeroCard;
import NonGui.BaseEntity.Player;
import NonGui.BaseEntity.Properties.UnitClass;
import NonGui.GameLogic.GameChoice;

import java.util.ArrayList;

import static NonGui.GameLogic.GameEngine.players;

public class Shaco extends HeroCard {

    public Shaco(){
        super(
                "Shaco",
                "The joke's on you!",
                " ",
                UnitClass.Assassin,
                9
        );
    }

    @Override
    public void useAbility(Player player) {
        System.out.println(this.getName() + " uses Hallucinate! (STEAL a Hero)");

        // 1. เช็คก่อนว่าบอร์ดของเรามีช่องว่างพอให้ขโมยฮีโร่มาใส่หรือไม่
        boolean hasSpace = false;
        for (HeroCard h : player.getOwnedHero()) {
            if (h == null) {
                hasSpace = true;
                break;
            }
        }

        if (!hasSpace) {
            System.out.println(player.getName() + "'s party is full! You cannot steal any more heroes.");
            return;
        }

        // 2. หาผู้เล่นที่มีฮีโร่บนบอร์ดให้เราขโมย (ต้องไม่ใช่ตัวเอง และบอร์ดห้ามว่าง)
        ArrayList<Player> validTargetsList = new ArrayList<>();
        for (Player p : players) {
            if (p != player && !p.boardIsEmpty()) {
                validTargetsList.add(p);
            }
        }

        if (validTargetsList.isEmpty()) {
            System.out.println("No other players have heroes available to STEAL!");
            return;
        }

        Player[] validTargetsArray = validTargetsList.toArray(new Player[0]);

        // 3. เลือกผู้เล่นเป้าหมาย
        System.out.println(player.getName() + ", choose a player to STEAL a Hero from:");
        int targetIndex = GameChoice.selectPlayer(validTargetsArray);
        Player targetPlayer = validTargetsArray[targetIndex];

        // 4. เลือกฮีโร่บนบอร์ดเป้าหมายที่จะขโมย
        System.out.println("Select a hero from " + targetPlayer.getName() + "'s board to STEAL:");
        int heroIndex = GameChoice.selectHeroCard(targetPlayer);

        // ดึงข้อมูลการ์ดฮีโร่เป้าหมายไว้ก่อน
        HeroCard stolenHero = targetPlayer.getHeroCard(heroIndex);

        // 5. เอาฮีโร่เป้าหมายออกจากบอร์ดศัตรู
        targetPlayer.removeHeroCard(heroIndex);

        // 6. เอาฮีโร่ที่ขโมยมา ใส่ลงในช่องว่างของบอร์ดเรา
        for (int i = 0; i < player.getOwnedHero().length; i++) {
            if (player.getOwnedHero()[i] == null) {
                player.getOwnedHero()[i] = stolenHero;
                break;
            }
        }

        System.out.println("SUCCESS! " + player.getName() + " stole " + stolenHero.getName() + " from " + targetPlayer.getName() + "!");
    }
}