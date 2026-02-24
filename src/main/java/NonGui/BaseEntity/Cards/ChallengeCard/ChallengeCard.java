package NonGui.BaseEntity.Cards.ChallengeCard;

import NonGui.BaseEntity.Player;
import NonGui.BaseEntity.TriggerCard;

public class ChallengeCard extends TriggerCard {
    public ChallengeCard(){
        super("Single Combat", "Save your words; we speak with blades.", "Fight another hero", "Challenge Card");
    }

    public void trigger(Player source, Player target) {
        // Challenge-specific trigger logic
    }
}
