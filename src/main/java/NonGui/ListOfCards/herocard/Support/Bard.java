package NonGui.ListOfCards.herocard.Support;

import NonGui.BaseEntity.Cards.HeroCard.HeroCard;
import NonGui.BaseEntity.Player;
import NonGui.BaseEntity.Properties.UnitClass;

/**
 * Represents the "Bard" Hero Card.
 * <p>
 * <b>Ability: Traveler's Call</b><br>
 * Requirement: Roll 7+<br>
 * Effect: The player immediately draws 2 cards from the deck.
 * <p>
 * <i>Bard provides essential utility by accelerating the player's access to their deck.</i>
 */
public class Bard extends HeroCard {

    /**
     * Constructs Bard with his base stats and mystical flavor text.
     */
    public Bard() {
        super(
                "Bard",
                "*Mystical chime noises*",
                "Traveler's Call: Roll 7+. DRAW 2 cards.",
                UnitClass.Support,
                7
        );
    }

    /**
     * Executes the Traveler's Call ability.
     * <p>
     * Logic Flow:
     * 1. Triggers 2 sequential random card draws from the deck to the player's hand.
     * * @param player The player who activated Bard's ability.
     */
    @Override
    public void useAbility(Player player) {
        System.out.println("🔔 " + this.getName() + " uses Traveler's Call!");
        System.out.println("🃏 " + player.getName() + " draws 2 cards.");

        player.drawRandomCard();
        player.drawRandomCard();

        // Sync GUI to show new cards
        try {
            gui.BoardView.refresh();
        } catch (Exception e) {
            // Silence UI refresh errors in non-GUI mode
        }
    }
}