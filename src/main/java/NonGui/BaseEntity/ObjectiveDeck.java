package NonGui.BaseEntity;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.Collections;

public class ObjectiveDeck {

    // The main deck (draw pile)
    private ObservableList<Objective> objectiveDeck;

    // Constructor
    public ObjectiveDeck() {
        objectiveDeck = FXCollections.observableArrayList();
    }

    // --- Objective Deck Operations ---
    public void addToDeck(Objective objective) {
        objectiveDeck.add(objective);
    }

    // Overloaded method: add multiple copies of an objective
    public void addToDeck(Objective objective, int count) {
        for (int i = 0; i < count; i++) {
            objectiveDeck.add(objective);
        }
    }

    public Objective drawObjective() {
        if (objectiveDeck.isEmpty()) return null;

        // Shuffle before each draw
        Collections.shuffle(objectiveDeck);

        return objectiveDeck.remove(0); // draw from top
    }

    public boolean isDeckEmpty() {
        return objectiveDeck.isEmpty();
    }

    public ObservableList<Objective> getObjectiveDeck() {
        return objectiveDeck;
    }

    // Shuffle the deck
    public void shuffle() {
        Collections.shuffle(objectiveDeck);
    }
}
