package entities.objective;

import entities.card.ObjectiveCard;

public class ElderDragonObjective extends ObjectiveCard {
    public ElderDragonObjective() {
        super("ElderDragon");
    }

    @Override
    public void checkCompletion() {
        System.out.println("Checking completion for Elder Dragon objective...");
        // Add Elder Dragon-specific logic here
    }
}
