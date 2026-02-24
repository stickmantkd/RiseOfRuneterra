package NonGui.ListOfCards.herocard.Mage;

import NonGui.BaseEntity.Cards.HeroCard.HeroCard;
import NonGui.BaseEntity.Player;
import NonGui.BaseEntity.Properties.UnitClass;

import static NonGui.GameLogic.GameEngine.players;

public class Veigar extends HeroCard {

    public Veigar(){
        super(
                "Veigar",
                "Know that if the tables were turned, I would show you no mercy!",
                "Primordial Burst: Roll 7+. Choose a player. That player must SACRIFICE a Hero card.",
                UnitClass.Mage
        );
    }

    @Override
    public void useAbility(Player player) {
        System.out.println(this.getName() + " uses their ability! (Force a player to SACRIFICE a Hero)");

        // 1. คัดกรองผู้เล่นเป้าหมาย (ต้องไม่ใช่ตัวเอง และต้องมีฮีโร่บนบอร์ดให้สังเวย)
        java.util.ArrayList<NonGui.BaseEntity.Player> validTargetsList = new java.util.ArrayList<>();
        // อย่าลืม import static NonGui.GameLogic.GameEngine.players; ไว้ด้านบนคลาสนะครับ
        for (NonGui.BaseEntity.Player p : players) {
            if (p != player && !p.boardIsEmpty()) {
                validTargetsList.add(p);
            }
        }

        if (validTargetsList.isEmpty()) {
            System.out.println("No other players have heroes to SACRIFICE!");
            return;
        }

        NonGui.BaseEntity.Player[] validTargetsArray = validTargetsList.toArray(new NonGui.BaseEntity.Player[0]);

        // 2. คนร่ายสกิลเลือกผู้เล่นเป้าหมาย
        System.out.println(player.getName() + ", choose a player who must SACRIFICE a Hero:");
        int targetIndex = NonGui.GameLogic.GameChoice.selectPlayer(validTargetsArray);
        NonGui.BaseEntity.Player targetPlayer = validTargetsArray[targetIndex - 1];

        // 3. บังคับให้เป้าหมายสังเวยฮีโร่ 1 ตัว โดยเรียกใช้จาก GameplayUtils ที่คุณเขียนเตรียมไว้
        System.out.println(targetPlayer.getName() + " is targeted and must SACRIFICE a hero!");
        NonGui.GameUtils.GameplayUtils.SacrificeHero(targetPlayer, 1);

        System.out.println("A hero from " + targetPlayer.getName() + "'s party has been sacrificed!");
    }
}
