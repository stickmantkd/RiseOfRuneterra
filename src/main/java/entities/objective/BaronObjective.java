package entities.objective;

import entities.card.ObjectiveCard;

public class BaronObjective extends ObjectiveCard {
    public BaronObjective() {
        super("Baron");
    }

    @Override
    public void checkCompletion() {
        System.out.println("Checking completion for Baron Nashor objective...");
        // Add Baron-specific logic here
    }
}
