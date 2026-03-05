package NonGui.ListOfCards.herocard.Tank;

import NonGui.BaseEntity.Cards.HeroCard.HeroCard;
import NonGui.BaseEntity.Player;
import NonGui.BaseEntity.Properties.UnitClass;

/**
 * Represents the "Braum" Hero Card.
 * <p>
 * <b>Ability: Unbreakable</b><br>
 * Requirement: Roll 8+<br>
 * Effect: For the remainder of the current turn, any cards the player plays
 * cannot be Challenged by opponents.
 * <p>
 * <i>Braum provides a window of absolute security, ensuring your key plays succeed
 * without interference.</i>
 */
public class Braum extends HeroCard {

    /**
     * Constructs Braum with his base stats and heart-warming flavor text.
     */
    public Braum() {
        super(
                "Braum",
                "Stand behind Braum!",
                "Unbreakable: Roll 8+. Cards you play cannot be challenged for the rest of your turn.",
                UnitClass.Tank,
                8
        );
    }

    /**
     * Executes the Unbreakable ability.
     * <p>
     * Logic Flow:
     * 1. Activates a protection flag on the player.
     * 2. This flag should be checked by the GameEngine whenever an opponent
     * tries to use a "Challenge" card against this player.
     * * @param player The player who is now protected by Braum's shield.
     */
    @Override
    public void useAbility(Player player) {
        System.out.println("🛡️ " + this.getName() + " uses Unbreakable! Stand behind him!");
        System.out.println("✨ " + player.getName() + "'s cards cannot be challenged for the rest of this turn.");

        // Activates the protective state
        player.setUnchallengeable(true);

        // Refresh GUI to potentially show a shield icon or effect
        try {
            gui.BoardView.refresh();
        } catch (Exception e) {}
    }
}