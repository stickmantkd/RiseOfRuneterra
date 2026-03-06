package nongui.baseentity;

import nongui.baseentity.cards.HeroCard.HeroCard;
import nongui.baseentity.cards.Itemcard.ItemCard;
import nongui.baseentity.cards.MagicCard.MagicCard;
import nongui.baseentity.cards.ModifierCard.ModifierCard;
import nongui.baseentity.properties.UnitClass;
import nongui.gamelogic.GameEngine;
import gui.board.StatusBar;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.util.Optional;
import java.util.ArrayList;

import static nongui.gameutils.GenerationsUtils.*;

/**
 * Represents a Player in the game.
 * Manages player's stats, cards in hand, heroes on the board, objectives, and specific boss buffs.
 */
public class Player {

    // ==========================================
    // Fields & Properties
    // ==========================================
    private String name;
    private int actionPoint;
    private int maxActionPoint;

    private LeaderCard ownedLeader;
    private HeroCard[] ownedHero;
    private ObservableList<BaseCard> cardsInHand;

    private int ownedObjectiveCount;
    private Objective[] ownedObjectives; // Store up to 3 objectives

    // --- Buffs & Statuses ---
    private boolean isUnchallengeable = false;
    private int rollBonus = 0;
    private int permanentAbilityBonus = 0; // Bonus from Blue Sentinel
    private int permanentChallengeBonus = 0; // Bonus from Infernal Drake
    private boolean canPlayItemInstantly = false; // Bonus from Yeti
    private boolean canPlayMagicInstantly = false; // Bonus from Murk Wolf
    private boolean itemUnchallengeable = false; // Bonus from Rift Herald
    private boolean hasRedBuff = false; // Bonus from Red Brambleback

    // Reactive property for current roll (GUI Integration)
    private final IntegerProperty currentRoll = new SimpleIntegerProperty(-1);

    // ==========================================
    // Constructor
    // ==========================================

    /**
     * Constructs a new Player with default setup.
     * @param name The name of the player.
     */
    public Player(String name) {
        this.name = name;
        this.maxActionPoint = 3;
        this.actionPoint = 3;
        this.ownedObjectiveCount = 0;
        this.ownedObjectives = new Objective[3];

        initializeOwnedLeader();
        initializeOwnedHero();
        initializeCardsInHand();
    }

    // ==========================================
    // Core Game Mechanics Methods
    // ==========================================

    /**
     * Draws a random card from the main deck and adds it to the player's hand.
     * Triggers boss buff effects (Yeti, Murk Wolf, Red Buff) if applicable.
     */
    public void drawRandomCard() {
        BaseCard card = GameEngine.deck.drawCard();
        if (card != null) {
            cardsInHand.add(card);
        } else {
            System.out.println("Deck is empty! No card drawn.");
            return; // Stop execution if no card is drawn
        }

        // ❄️ [YETI PRIZE LOGIC]
        if (this.canPlayItemInstantly && card instanceof ItemCard) {
            Platform.runLater(() -> {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Yeti's Blessing");
                alert.setHeaderText("You drew an Item: " + card.getName());
                alert.setContentText("Would you like to equip it immediately without spending AP?");

                Optional<ButtonType> result = alert.showAndWait();
                if (result.isPresent() && result.get() == ButtonType.OK) {
                    ((ItemCard) card).playCard(this);
                    this.cardsInHand.remove(card);
                    GameEngine.deck.discardCard(card);
                }
            });
        }

        // 🐺 [MURK WOLF PRIZE LOGIC]
        if (this.canPlayMagicInstantly && card instanceof MagicCard) {
            Platform.runLater(() -> {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Murk Wolf's Instinct");
                alert.setHeaderText("You drew a Magic Card: " + card.getName());
                alert.setContentText("Would you like to cast it immediately for 0 AP?");

                Optional<ButtonType> result = alert.showAndWait();
                if (result.isPresent() && result.get() == ButtonType.OK) {
                    ((MagicCard) card).playCard(this);
                    this.cardsInHand.remove(card);
                    GameEngine.deck.discardCard(card);
                }
            });
        }

        // 🔥 [RED BUFF LOGIC]
        if (this.hasRedBuff && card instanceof ModifierCard) {
            System.out.println("🔥 Red Buff Triggered! Revealed Modifier: " + card.getName());
            StatusBar.showMessage("Red Buff: You drew a Modifier! Drawing an extra card...");
            this.drawRandomCard(); // Draw an extra card recursively
        }
    }

    /**
     * Checks if the player has met the winning condition for the "Party Path" (All Classes).
     * @return true if the player has 5 heroes of different classes and none match the Leader's class.
     */
    public boolean isWinningByClasses() {
        UnitClass leaderClass = ownedLeader.getUnitClass();
        ArrayList<HeroCard> uniqueHeroes = new ArrayList<>();

        for (HeroCard hero : ownedHero) {
            // Return false if a slot is empty, matches leader class, or is a duplicate class
            if (hero == null || hero.getUnitClass() == leaderClass || hasDuplicateClass(hero, uniqueHeroes)) {
                return false;
            } else {
                uniqueHeroes.add(hero);
            }
        }
        return true;
    }

    /**
     * Helper method to check if a hero's class already exists in a given list.
     */
    private boolean hasDuplicateClass(HeroCard hero, ArrayList<HeroCard> existingHeroes) {
        for(HeroCard other : existingHeroes){
            if(hero.getUnitClass() == other.getUnitClass()){
                return true; // Found a duplicate
            }
        }
        return false;
    }

    /**
     * Checks if the player has won by collecting 3 Objectives.
     * @return true if player owns 3 or more objectives.
     */
    public boolean isWinningByObjectives() {
        return ownedObjectiveCount >= 3;
    }

    /**
     * Checks if the player has won the game via any condition.
     * @return true if the player wins.
     */
    public boolean isWinning() {
        return isWinningByClasses() || isWinningByObjectives();
    }

    // ==========================================
    // Action Point & Board Management
    // ==========================================

    public void increaseActionPoint(int amount) {
        this.setActionPoint(this.actionPoint + amount);
    }

    public void decreaseActionPoint(int amount) {
        this.setActionPoint(this.actionPoint - amount);
    }

    public void refillActionPoint() {
        this.setActionPoint(maxActionPoint);
    }

    public boolean boardIsEmpty() {
        for (HeroCard hero : ownedHero) {
            if (hero != null) return false;
        }
        return true;
    }

    public boolean handIsEmpty() {
        return cardsInHand.isEmpty();
    }

    // ==========================================
    // Inventory & Card Management Methods
    // ==========================================

    public void addCardToHand(BaseCard card) {
        cardsInHand.add(card);
    }

    public void removeCardFromHand(BaseCard card) {
        cardsInHand.remove(card);
    }

    public BaseCard getCardInHand(int index) {
        BaseCard selectedCard = cardsInHand.get(index);
        cardsInHand.remove(selectedCard);
        return selectedCard;
    }

    public void addHeroCard(HeroCard hero) {
        for (int i = 0; i < ownedHero.length; i++) {
            if (ownedHero[i] == null) {
                ownedHero[i] = hero;
                return;
            }
        }
    }

    public boolean removeHeroCard(int index) {
        if (ownedHero[index] == null) return false;
        ownedHero[index] = null;
        return true;
    }

    public void addOwnedObjective(Objective obj) {
        for (int i = 0; i < ownedObjectives.length; i++) {
            if (ownedObjectives[i] == null) {
                ownedObjectives[i] = obj;
                ownedObjectiveCount++;
                return;
            }
        }
    }

    // ==========================================
    // Initialization Setups
    // ==========================================

    private void initializeOwnedLeader() {
        ownedLeader = generateRandomLeader();
    }

    private void initializeOwnedHero() {
        ownedHero = new HeroCard[5];
    }

    private void initializeCardsInHand() {
        cardsInHand = FXCollections.observableArrayList();
        for (int i = 0; i < 5; ++i) {
            drawRandomCard();
        }
    }

    // ==========================================
    // Getters and Setters
    // ==========================================

    @Override
    public String toString() { return name; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public int getActionPoint() { return actionPoint; }
    public void setActionPoint(int actionPoint) {
        if (actionPoint > maxActionPoint) this.actionPoint = maxActionPoint;
        else if (actionPoint < 0) this.actionPoint = 0;
        else this.actionPoint = actionPoint;
    }

    public int getMaxActionPoint() { return maxActionPoint; }
    public void setMaxActionPoint(int maxActionPoint) { this.maxActionPoint = maxActionPoint; }

    public LeaderCard getOwnedLeader() { return ownedLeader; }
    public void setOwnedLeader(LeaderCard leader) { this.ownedLeader = leader; }

    public HeroCard[] getOwnedHero() { return ownedHero; }
    public void setOwnedHero(HeroCard[] heroList) { this.ownedHero = heroList; }
    public HeroCard getHeroCard(int index) { return ownedHero[index]; }

    public ObservableList<BaseCard> getCardsInHand() { return cardsInHand; }
    public void setCardsInHand(ObservableList<BaseCard> cardsInHand) { this.cardsInHand = cardsInHand; }

    public Objective[] getOwnedObjectives() { return ownedObjectives; }
    public int getOwnedObjectiveCount() { return ownedObjectiveCount; }

    // --- Stats & Buffs Getters/Setters ---
    public int getRollBonus() { return rollBonus; }
    public void setRollBonus(int bonus) { this.rollBonus = bonus; }

    public boolean isUnchallengeable() { return isUnchallengeable; }
    public void setUnchallengeable(boolean state) { this.isUnchallengeable = state; }

    public int getPermanentAbilityBonus() { return permanentAbilityBonus; }
    public void addPermanentAbilityBonus(int amount) { this.permanentAbilityBonus += amount; }

    public int getPermanentChallengeBonus() { return permanentChallengeBonus; }
    public void addPermanentChallengeBonus(int amount) { this.permanentChallengeBonus += amount; }

    public boolean isCanPlayItemInstantly() { return canPlayItemInstantly; }
    public void setCanPlayItemInstantly(boolean value) { this.canPlayItemInstantly = value; }

    public boolean isCanPlayMagicInstantly() { return canPlayMagicInstantly; }
    public void setCanPlayMagicInstantly(boolean value) { this.canPlayMagicInstantly = value; }

    public boolean isItemUnchallengeable() { return itemUnchallengeable; }
    public void setItemUnchallengeable(boolean value) { this.itemUnchallengeable = value; }

    public boolean isHasRedBuff() { return hasRedBuff; }
    public void setHasRedBuff(boolean value) { this.hasRedBuff = value; }

    // --- Reactive Roll Property ---
    public IntegerProperty currentRollProperty() { return currentRoll; }
    public int getCurrentRoll() { return currentRoll.get(); }
    public void setCurrentRoll(int roll) { currentRoll.set(roll); }
}