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
                UnitClass.Mage,
                7
        );
    }

    @Override
    public void useAbility(Player player) {
        // 🛠️ บังคับอัปเดตหน้าจอให้ Veigar โผล่มาบนบอร์ดก่อนเด้งป๊อปอัป
        try {
            gui.BoardView.refresh();
        } catch (Exception e) {}

        System.out.println(this.getName() + " uses their ability! (Force a player to SACRIFICE a Hero)");

        // 1. คัดกรองผู้เล่นเป้าหมาย (ต้องไม่ใช่ตัวเอง และต้องมีฮีโร่บนบอร์ดให้สังเวย)
        java.util.ArrayList<Player> validTargetsList = new java.util.ArrayList<>();
        for (Player p : players) {
            if (p != player && !p.boardIsEmpty()) {
                validTargetsList.add(p);
            }
        }

        if (validTargetsList.isEmpty()) {
            System.out.println("No other players have heroes to SACRIFICE!");
            return;
        }

        Player[] validTargetsArray = validTargetsList.toArray(new Player[0]);

        // 2. คนร่ายสกิลเลือกผู้เล่นเป้าหมาย
        System.out.println(player.getName() + ", choose a player who must SACRIFICE a Hero:");
        int targetIndex = NonGui.GameLogic.GameChoice.selectPlayer(validTargetsArray);

        // ดักกรณีคนร่ายเปลี่ยนใจกดยกเลิก
        if (targetIndex == -1) {
            System.out.println("Action canceled.");
            return;
        }

        Player targetPlayer = validTargetsArray[targetIndex];

        // 3. บังคับให้เป้าหมายสังเวยฮีโร่ 1 ตัว
        System.out.println(targetPlayer.getName() + " is targeted and must SACRIFICE a hero!");
        NonGui.GameUtils.GameplayUtils.SacrificeHero(targetPlayer, 1);
    }
}