package NonGui.ListOfCards.herocard.Maskman;

import NonGui.BaseEntity.Cards.HeroCard.HeroCard;
import NonGui.BaseEntity.Player;
import NonGui.BaseEntity.Properties.UnitClass;

/**
 * Represents the "Jinx" Hero Card.
 * <p>
 * <b>Ability: Get Excited!</b><br>
 * Requirement: Roll 10+<br>
 * Effect: Draw cards until the player's hand contains exactly 7 cards.
 * <p>
 * <i>Jinx thrives on chaos; the fewer cards you have, the more powerful this ability becomes.</i>
 */
public class Jinx extends HeroCard {

    /**
     * Constructs Jinx with her base stats and chaotic flavor text.
     */
    public Jinx(){
        super(
                "Jinx",
                "Rules are made to be broken... like buildings! Or people!",
                "Get Excited!: Roll 10+. DRAW cards until you have 7 cards in your hand.",
                UnitClass.Maskman,
                10
        );
    }

    /**
     * Executes the Get Excited! ability.
     * <p>
     * Logic Flow:
     * 1. Check current hand size.
     * 2. Compare with the target threshold (7 cards).
     * 3. Calculate the deficit and execute sequential draws via player.drawRandomCard().
     * * @param player The player who activated Jinx's ability.
     */
    @Override
    public void useAbility(Player player) {
        System.out.println("🚀 " + this.getName() + " is getting excited!");

        // 1. Check current resource state
        int currentHandSize = player.getCardsInHand().size();
        int targetHandSize = 7;

        // 2. Early exit if the hand is already full or overflowing
        if (currentHandSize >= targetHandSize) {
            System.out.println("💥 " + player.getName() + " already has " + currentHandSize + " cards. No cards drawn.");
            return;
        }

        // 3. Calculation Phase
        int cardsToDraw = targetHandSize - currentHandSize;
        System.out.println("🔥 " + player.getName() + " has " + currentHandSize + " cards. Drawing " + cardsToDraw + " more...");

        // 4. Execution Phase: Refill the hand
        for (int i = 0; i < cardsToDraw; i++) {
            player.drawRandomCard();
        }

        System.out.println("✅ SUCCESS! " + player.getName() + " now has " + player.getCardsInHand().size() + " cards in hand.");

        // Refresh GUI to show the new hand
        try { gui.BoardView.refresh(); } catch (Exception e) {}
    }
}