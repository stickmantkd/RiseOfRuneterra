package nongui.baseentity;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.Collections;

/**
 * Represents the main deck and discard pile in the game.
 * Handles card drawing, discarding, and deck manipulation.
 * Uses JavaFX ObservableList to allow easy data binding with the GUI.
 */
public class Deck {

    // ==========================================
    // Fields
    // ==========================================

    /** The main draw pile */
    private ObservableList<BaseCard> gameDeck;

    /** The discard pile where used or destroyed cards go */
    private ObservableList<BaseCard> discardPile;

    // ==========================================
    // Constructor
    // ==========================================

    /**
     * Initializes a new empty Deck and Discard Pile.
     */
    public Deck() {
        gameDeck = FXCollections.observableArrayList();
        discardPile = FXCollections.observableArrayList();
    }

    // ==========================================
    // Game Deck Operations
    // ==========================================

    /**
     * Adds a single card to the main deck.
     * @param card The card to add.
     */
    public void addToDeck(BaseCard card) {
        gameDeck.add(card);
    }

    /**
     * Adds multiple copies of the same card to the main deck.
     * @param card  The card to add.
     * @param count The number of copies to add.
     */
    public void addToDeck(BaseCard card, int count) {
        for (int i = 0; i < count; i++) {
            gameDeck.add(card);
        }
    }

    /**
     * Shuffles the deck and draws the top card.
     * @return The drawn BaseCard, or null if the deck is empty.
     */
    public BaseCard drawCard() {
        if (gameDeck.isEmpty()) {
            return null;
        }

        // Shuffle before each draw to ensure complete randomness
        Collections.shuffle(gameDeck);

        // Draw the first card and remove it from the deck
        BaseCard topCard = gameDeck.removeFirst();
        return topCard;
    }

    /**
     * Checks if the main draw deck is empty.
     * @return true if there are no cards left in the deck.
     */
    public boolean isDeckEmpty() {
        return gameDeck.isEmpty();
    }

    /**
     * Shuffles the main game deck randomly.
     */
    public void shuffle() {
        Collections.shuffle(gameDeck);
    }

    /**
     * Gets the entire game deck list.
     * @return The ObservableList representing the draw pile.
     */
    public ObservableList<BaseCard> getGameDeck() {
        return gameDeck;
    }

    // ==========================================
    // Discard Pile Operations
    // ==========================================

    /**
     * Adds a card to the discard pile.
     * @param card The card being discarded.
     */
    public void discardCard(BaseCard card) {
        discardPile.add(card);
    }

    /**
     * Gets the entire discard pile list.
     * @return The ObservableList representing the discard pile.
     */
    public ObservableList<BaseCard> getDiscardPile() {
        return discardPile;
    }

    /**
     * Clears all cards from the discard pile.
     */
    public void clearDiscardPile() {
        discardPile.clear();
    }
}