package NonGui.BaseEntity.Cards.ChallengeCard;

import NonGui.BaseEntity.Player;
import NonGui.BaseEntity.TriggerCard;

public class ChallengeCard extends TriggerCard {
    public ChallengeCard(){
        super("Challenge Card", "Save your words; we speak with blades.",
                "You may play this card when another player attempts to play a Hero, Item, or Magic card. CHALLENGE that card.",
                "Challenge Card");
    }

    public void trigger(Player source, Player target) {
        // Challenge-specific trigger logic
    }
}
