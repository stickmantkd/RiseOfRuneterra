package NonGui.BaseEntity.Cards.ChallengeCard;

import NonGui.BaseEntity.Player;
import NonGui.BaseEntity.TriggerCard;

/**
 * Represents a Challenge Card in the game.
 * This is a specific type of TriggerCard used to interrupt or "challenge"
 * another player when they attempt to play a Hero, Item, or Magic card.
 */
public class ChallengeCard extends TriggerCard {

    // ==========================================
    // Constructor
    // ==========================================

    /**
     * Constructs a new ChallengeCard with predefined name, flavor text, and abilities.
     */
    public ChallengeCard() {
        super(
                "Challenge Card",
                "Save your words; we speak with blades.",
                "You may play this card when another player attempts to play a Hero, Item, or Magic card. CHALLENGE that card.",
                "Challenge Card"
        );
    }

    // ==========================================
    // Core Logic
    // ==========================================

    /**
     * Executes the specific trigger logic when the Challenge Card is activated.
     * * @param source The player who is playing the Challenge Card.
     * @param target The player who is being challenged.
     */
    public void trigger(Player source, Player target) {
        // Challenge-specific trigger logic
    }
}