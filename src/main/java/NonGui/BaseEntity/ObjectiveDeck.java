package NonGui.BaseEntity;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.Collections;

/**
 * Represents the deck of objectives (bosses or tasks) in the game.
 * Manages the collection of objectives, allowing adding, drawing, and shuffling.
 * Uses JavaFX ObservableList for potential data binding with the GUI.
 */
public class ObjectiveDeck {

    // ==========================================
    // Fields
    // ==========================================

    /** The main deck (draw pile) for objectives */
    private ObservableList<Objective> objectiveDeck;

    // ==========================================
    // Constructor
    // ==========================================

    /**
     * Initializes a new empty Objective Deck.
     */
    public ObjectiveDeck() {
        objectiveDeck = FXCollections.observableArrayList();
    }

    // ==========================================
    // Objective Deck Operations
    // ==========================================

    /**
     * Adds a single objective to the deck.
     * @param objective The objective to add.
     */
    public void addToDeck(Objective objective) {
        objectiveDeck.add(objective);
    }

    /**
     * Adds multiple copies of the same objective to the deck.
     * @param objective The objective to add.
     * @param count     The number of copies to add.
     */
    public void addToDeck(Objective objective, int count) {
        for (int i = 0; i < count; i++) {
            objectiveDeck.add(objective);
        }
    }

    /**
     * Shuffles the deck and draws the top objective.
     * @return The drawn Objective, or null if the deck is empty.
     */
    public Objective drawObjective() {
        if (objectiveDeck.isEmpty()) return null;

        // Shuffle before each draw
        Collections.shuffle(objectiveDeck);

        return objectiveDeck.remove(0); // draw from top
    }

    /**
     * Checks if the objective deck is empty.
     * @return true if there are no objectives left in the deck.
     */
    public boolean isDeckEmpty() {
        return objectiveDeck.isEmpty();
    }

    /**
     * Gets the entire objective deck list.
     * @return The ObservableList representing the objective deck.
     */
    public ObservableList<Objective> getObjectiveDeck() {
        return objectiveDeck;
    }

    /**
     * Shuffles the objective deck randomly.
     */
    public void shuffle() {
        Collections.shuffle(objectiveDeck);
    }
}