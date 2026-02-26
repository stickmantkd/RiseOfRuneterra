package NonGui.ListOfCards.herocard.Mage;

import NonGui.BaseEntity.BaseCard;
import NonGui.BaseEntity.Cards.HeroCard.HeroCard;
import NonGui.BaseEntity.Cards.MagicCard.MagicCard;
import NonGui.BaseEntity.Player;
import NonGui.BaseEntity.Properties.UnitClass;

import static NonGui.GameUtils.GenerationsUtils.GenerateRandomCard;

public class Zoe extends HeroCard {

    public Zoe(){
        super(
                "Zoe",
                "More sparkles! More!",
                "Spell Thief: Roll 6+. DRAW a card. If it is a Magic card, you may play it immediately and DRAW a second card.",
                UnitClass.Mage,
                6
        );
    }

    @Override
    public void useAbility(Player player) {
        System.out.println(this.getName() + " uses Arcane Surge!");

        // 1. จั่วการ์ดทันที
        BaseCard drawnCard = GenerateRandomCard();

        System.out.println(player.getName() + " drew: " + drawnCard);

        // 2. ถ้าเป็น Magic card → เล่นทันที
        if (drawnCard instanceof MagicCard) {
            MagicCard magic = (MagicCard) drawnCard;
            System.out.println("It's a Magic card! Playing it immediately...");

            boolean played = magic.playCard(player);
            if (!played) {
                player.addCardToHand(drawnCard);
                System.out.println("Could not play the Magic card.");
            }

            // 3. จั่วการ์ดเพิ่มอีก 1 ใบ
            BaseCard secondCard = GenerateRandomCard();
            player.addCardToHand(secondCard);
            System.out.println(player.getName() + " drew an extra card: " + secondCard);
        }
    }
}
