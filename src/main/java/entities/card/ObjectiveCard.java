package entities.card;

public class ObjectiveCard extends Card {
    private final String objectiveName;

    public ObjectiveCard(String objectiveName) {
        super(objectiveName, "Objective");
        this.objectiveName = objectiveName;
    }

    public String getObjectiveName() {
        return objectiveName;
    }

    // Example method
    public void checkCompletion() {
        System.out.println("Checking completion for " + objectiveName);
    }
}
