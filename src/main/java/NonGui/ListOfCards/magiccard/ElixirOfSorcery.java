package NonGui.ListOfCards.magiccard;

import NonGui.BaseEntity.Cards.MagicCard.MagicCard;
import NonGui.BaseEntity.Player;
import NonGui.BaseEntity.Properties.UnitClass;
import gui.board.StatusBar;

public class ElixirOfSorcery extends MagicCard {
    public ElixirOfSorcery() {
        super(
                "Elixir of Sorcery",
                "True power comes to those who drink deep.",
                "+2 to all of your rolls until the end of your turn."
        );
    }

    @Override
    public boolean playCard(Player player) {
        System.out.println("🧪 " + player.getName() + " drinks Elixir of Sorcery!");
        System.out.println("✨ All rolls get +2 bonus until the end of turn.");

        // 1. เพิ่มโบนัสการทอยให้กับตัวแปร rollBonus ในคลาส Player
        // เราใช้ += เพื่อให้มันทับซ้อนกับบัฟอื่นได้ (เช่น ถ้า Ornn บัฟอยู่แล้ว +5 แล้วกินยานี้อีก +2 ก็จะเป็น +7)
        int currentBonus = player.getRollBonus();
        player.setRollBonus(currentBonus + 2);

        // 2. Refresh GUI เพื่อให้หน้าจอแสดงสถานะใหม่ (ถ้ามี Label บอกโบนัส)
        try {
            gui.BoardView.refresh();
        } catch (Exception e) {
            // ignore if GUI not running
        }
        if(player.getOwnedLeader().getUnitClass() == UnitClass.Mage){
            player.DrawRandomCard(); // สั่งจั่วเพิ่ม 1 ใบ
            StatusBar.showMessage("Mage Leader: Magic used! Drawing an extra card.");
        }
        return true;
    }
}