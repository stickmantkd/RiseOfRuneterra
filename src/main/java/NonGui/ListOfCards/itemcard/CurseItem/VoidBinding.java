package NonGui.ListOfCards.itemcard.CurseItem;

import NonGui.BaseEntity.Cards.HeroCard.HeroCard;
import NonGui.BaseEntity.Cards.Itemcard.ItemCard;

public class VoidBinding extends ItemCard {
    public VoidBinding() {
        super("Void Binding",
                "Dark shackles that silence the strongest souls.",
                "You cannot use this Hero card's effect.");
    }

    @Override
    public void onEquip(HeroCard hero) {
        System.out.println("🌌 Void Binding has silenced " + hero.getName() + "!");
        // ปิดการใช้งานสกิลทันที
        hero.setCanUseAbility(false);
    }

    @Override
    public void onUnEquip(HeroCard hero) {
        System.out.println("✨ The shackles of the Void are broken for " + hero.getName());
        // คืนค่าให้กลับมาใช้สกิลได้ (แต่ต้องไปเช็ค AP ใน GameEngine ตามปกติ)
        hero.setCanUseAbility(true);
    }
}