package NonGui.BaseEntity.Cards.Itemcard;

import NonGui.BaseEntity.Cards.HeroCard.HeroCard;
import NonGui.BaseEntity.ActionCard;
import NonGui.BaseEntity.Player;
import NonGui.BaseEntity.Properties.UnitClass;

import static NonGui.GameLogic.GameChoice.selectHeroCard;
import static NonGui.GameLogic.GameChoice.selectPlayer;
import static NonGui.GameLogic.GameEngine.players;

public abstract class ItemCard extends ActionCard {
    public ItemCard(String name, String flavorText, String abilityDescription){
        super(name, flavorText, abilityDescription, "Item Card");
    }

    @Override
    public boolean playCard(Player player) {
        // 1. เลือกผู้เล่น
        int selectedPlayerNumber = selectPlayer(players);

        // 🛠️ ดักการกดยกเลิก (Cancel)
        if (selectedPlayerNumber == -1) {
            System.out.println(">>> Action Canceled. <<<");
            return false;
        }

        Player selectedPlayer = players[selectedPlayerNumber];

        // 🛠️ ป้องกัน Error: เช็คก่อนว่าผู้เล่นเป้าหมายมีฮีโร่ให้ใส่ไอเทมไหม
        if (selectedPlayer.boardIsEmpty()) {
            System.out.println(">>> " + selectedPlayer.getName() + " has no heroes on the board to equip this item! <<<");
            return false;
        }

        // 2. เลือกฮีโร่
        int selectedHeroNumber = selectHeroCard(selectedPlayer);

        // 🛠️ ดักการกดยกเลิก (Cancel)
        if (selectedHeroNumber == -1) {
            System.out.println(">>> Action Canceled. <<<");
            return false;
        }

        HeroCard selectedHero = null;
        try {
            // ดึงฮีโร่ (ใส่ try-catch เผื่อ GameChoice ส่ง index มาคลาดเคลื่อน)
            selectedHero = selectedPlayer.getHeroCard(selectedHeroNumber);
        } catch (IndexOutOfBoundsException e) {
            try {
                // ถ้า Error ลองลบ 1 ดูเผื่อ index เริ่มที่ 1
                selectedHero = selectedPlayer.getHeroCard(selectedHeroNumber - 1);
            } catch (Exception ex) {
                System.out.println(">>> System Error: Cannot find selected hero. <<<");
                return false;
            }
        }

        if (selectedHero == null) {
            return false;
        }

        if(player.getOwnedLeader().getUnitClass() == UnitClass.Assassin){
            player.drawRandomCard();
        }

        // 3. ใส่ไอเทมให้ฮีโร่ และคืนค่ากลับไปบอกว่าร่ายสำเร็จไหม
        return selectedHero.equipItem(this);
    }

    public abstract void onEquip(HeroCard hero);
    public abstract void onUnEquip(HeroCard hero);
}