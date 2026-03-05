package NonGui.ListOfCards.itemcard.CurseItem;

import NonGui.BaseEntity.Cards.HeroCard.HeroCard;
import NonGui.BaseEntity.Cards.Itemcard.ItemCard;

public class AbyssalMask extends ItemCard {
    public AbyssalMask() {
        super("Abyssal Mask",
                "The abyss does not protect you—it devours what remains.",
                "You cannot use this Hero card's effect.");
    }

    @Override
    public void onEquip(HeroCard hero) {
        System.out.println("🌌 Abyssal Mask has silenced " + hero.getName() + "!");
        // ปิดการใช้งานสกิลทันที
        hero.setCanUseAbility(false);
    }

    @Override
    public void onUnEquip(HeroCard hero) {
        System.out.println("✨ The Abyssal Mask releases its grip on " + hero.getName());
        // คืนค่าให้กลับมาใช้สกิลได้ (แต่ต้องไปเช็ค AP ใน GameEngine ตามปกติ)
        hero.setCanUseAbility(true);
    }
}