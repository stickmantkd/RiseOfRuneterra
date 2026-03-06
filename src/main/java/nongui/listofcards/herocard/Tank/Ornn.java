package nongui.listofcards.herocard.Tank;

import nongui.baseentity.cards.HeroCard.HeroCard;
import nongui.baseentity.Player;
import nongui.baseentity.properties.UnitClass;

/**
 * Represents the "Ornn" Hero Card.
 * <p>
 * <b>Ability: Master Craftsman</b><br>
 * Requirement: Roll 9+<br>
 * Effect: Grant the player a flat +5 bonus to all dice rolls
 * for the remainder of their current turn.
 * <p>
 * <i>Ornn’s masterpieces turn even the unluckiest rolls into legendary successes.</i>
 */
public class Ornn extends HeroCard {

    /**
     * Constructs Ornn with his base stats and humble craftsman flavor text.
     */
    public Ornn() {
        super(
                "Ornn",
                "I have made this. It is good.",
                "Master Craftsman: Roll 9+. +5 to all of your rolls until the end of your turn.",
                UnitClass.Tank,
                9
        );
    }

    /**
     * Executes the Master Craftsman ability.
     * <p>
     * Logic Flow:
     * 1. Accesses the player's current roll bonus attribute.
     * 2. Adds a substantial +5 modifier to that attribute.
     * 3. This bonus should be factored into all subsequent rolls within the same turn cycle.
     * * @param player The player whose equipment is being upgraded by Ornn.
     */
    @Override
    public void useAbility(Player player) {
        System.out.println("🔨 " + this.getName() + " forges a masterpiece! (Master Craftsman)");
        System.out.println("🔥 All rolls for " + player.getName() + " get +5 until the end of this turn.");

        // Apply the significant forge bonus
        player.setRollBonus(player.getRollBonus() + 5);

        // Sync UI to potentially display the bonus value near the player's profile
        try {
            gui.BoardView.refresh();
        } catch (Exception e) {}
    }
}