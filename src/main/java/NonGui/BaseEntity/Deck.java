package NonGui.BaseEntity;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.Collections;

public class Deck {

    // The main deck (draw pile)
    private ObservableList<BaseCard> gameDeck;

    // The discard pile
    private ObservableList<BaseCard> discardPile;

    // Constructor
    public Deck() {
        gameDeck = FXCollections.observableArrayList();
        discardPile = FXCollections.observableArrayList();
    }

    // --- Game Deck Operations ---
    public void addToDeck(BaseCard card) {
        gameDeck.add(card);
    }

    // Overloaded method: add multiple copies of a card
    public void addToDeck(BaseCard card, int count) {
        for (int i = 0; i < count; i++) {
            gameDeck.add(card);
        }
    }

    public BaseCard drawCard() {
        if (gameDeck.isEmpty()) return null;

        // Shuffle before each draw
        Collections.shuffle(gameDeck);

        return gameDeck.remove(0); // draw from top
    }

    public boolean isDeckEmpty() {
        return gameDeck.isEmpty();
    }

    public ObservableList<BaseCard> getGameDeck() {
        return gameDeck;
    }

    // --- Discard Pile Operations ---
    public void discardCard(BaseCard card) {
        discardPile.add(card);
    }

    public ObservableList<BaseCard> getDiscardPile() {
        return discardPile;
    }

    public void clearDiscardPile() {
        discardPile.clear();
    }

    // Shuffle the deck
    public void shuffle() {
        Collections.shuffle(gameDeck);
    }
}
