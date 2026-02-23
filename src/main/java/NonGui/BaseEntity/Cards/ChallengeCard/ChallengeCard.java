package NonGui.BaseEntity.Cards.ChallengeCard;

import NonGui.BaseEntity.Player;
import NonGui.BaseEntity.TriggerCard;

import static NonGui.GameUtils.DiceUtils.getRoll;

public class ChallengeCard extends TriggerCard {
    //Constructor;
    public ChallengeCard(){
        super("Single Combat", "Save your words; we speak with blades.");
    }

    @Override
    public boolean onTrigger(Player player) {
        int userRoll = getRoll();
        int oppRoll = getRoll();
        return userRoll > oppRoll;
    }
}
