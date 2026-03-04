package NonGui.BaseEntity;

import NonGui.BaseEntity.Cards.HeroCard.HeroCard;
import NonGui.BaseEntity.Cards.Itemcard.ItemCard;
import NonGui.BaseEntity.Cards.MagicCard.MagicCard;
import NonGui.BaseEntity.Properties.UnitClass;
import NonGui.GameLogic.GameEngine;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.util.Optional;

import java.util.ArrayList;

import static NonGui.GameUtils.GenerationsUtils.*;

public class Player {
    // Fields
    private String name;
    private int actionPoint;
    private int maxActionPoint;
    private int ownedObjective;
    private Objective[] ownedObjectives; // store up to 3 objectives
    private LeaderCard ownedLeader;
    private HeroCard[] ownedHero;
    private ObservableList<BaseCard> cardsInHand;
    private boolean isUnchallengeable = false;
    private int rollBonus = 0; // ปกติเป็น 0
    private int permanentAbilityBonus = 0; // โบนัสถาวรจากการปราบ Blue Sentinel
    private boolean canPlayItemInstantly = false;
    private boolean canPlayMagicInstantly = false;

    // NEW: reactive property for current roll
    private final IntegerProperty currentRoll = new SimpleIntegerProperty(-1);

    // Constructors
    public Player(String name) {
        setName(name);
        setMaxActionPoint(3);
        setActionPoint(3);
        setOwnedObjective(0);
        initializeOwnedLeader();
        initializeOwnedHero();
        initializeCardsInHand();
        ownedObjectives = new Objective[3];
    }

    // Utilities
    @Override
    public String toString() {
        return name;
    }

    // Draw a card from the main deck into hand
    public void DrawRandomCard() {
        BaseCard card = GameEngine.deck.drawCard(); // pull from deck
        if (card != null) {
            cardsInHand.add(card);
        } else {
            System.out.println("Deck is empty! No card drawn.");
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
                    // เรียก Method สำหรับเลือก Hero เพื่อสวมใส่ไอเทม (คล้ายกับการเล่นไอเทมปกติ)
                    ((ItemCard) card).playCard(this);
                    this.getCardsInHand().remove(card);
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
                    // ร่ายเวทมนตร์ทันที
                    ((MagicCard) card).playCard(this);
                    // นำออกจากมือเพราะร่ายไปแล้ว
                    this.getCardsInHand().remove(card);
                    GameEngine.deck.discardCard(card);
                }
            });
        }
    }

    // Add card to hand
    public void addCardToHand(BaseCard card) {
        cardsInHand.add(card);
    }

    // Check if board has no heroes
    public boolean boardIsEmpty() {
        for (HeroCard hero : ownedHero) {
            if (hero != null) return false;
        }
        return true;
    }

    // Check if hand is empty
    public boolean HandIsEmpty() {
        return cardsInHand.isEmpty();
    }

    // Action point management
    public void increaseActionPoint(int incAp) {
        this.setActionPoint(actionPoint + incAp);
    }

    public void decreaseActionPoint(int decAp) {
        this.setActionPoint(actionPoint - decAp);
    }

    public void refillActionPoint() {
        setActionPoint(maxActionPoint);
    }

    // Setups
    private void initializeOwnedLeader() {
        ownedLeader = generateRandomLeader();
    }

    private void initializeOwnedHero() {
        ownedHero = new HeroCard[5];
    }

    public int getRollBonus() { return rollBonus; }
    public void setRollBonus(int bonus) { this.rollBonus = bonus; }

    private void initializeCardsInHand() {
        cardsInHand = FXCollections.observableArrayList();
        for (int i = 0; i < 5; ++i) {
            DrawRandomCard();
        }
    }

    // Index Operation
    public BaseCard getCardInHand(int index) {
        BaseCard selectedCard = cardsInHand.get(index);
        cardsInHand.remove(selectedCard);
        return selectedCard;
    }

    public HeroCard getHeroCard(int index) {
        return ownedHero[index];
    }

    public boolean removeHeroCard(int index) {
        if (ownedHero[index] == null) return false;
        ownedHero[index] = null;
        return true;
    }

    // Add hero
    public void addHeroCard(HeroCard hero) {
        for (int i = 0; i < ownedHero.length; i++) {
            if (ownedHero[i] == null) {
                ownedHero[i] = hero;
                return;
            }
        }
    }

    // CheckWinning
    public boolean isWinning() {
        return checkOwnedAllClass() || checkOwnedThreeObjective();
    }

    public boolean checkOwnedAllClass() {
        UnitClass leaderClass = getOwnedLeader().getUnitClass();
        ArrayList<HeroCard> otherHeroes = new ArrayList<>();
        for (HeroCard hero : ownedHero) {
            if (hero == null ||
                hero.getUnitClass() == leaderClass ||
                repeatClass(hero, otherHeroes)){
                    return false;
            } else {
                otherHeroes.add(hero);
            }
        }
        return true;
    }

    private boolean repeatClass(HeroCard hero, ArrayList<HeroCard> otherHeroes) {
        for(HeroCard other : otherHeroes){
            if( hero.getUnitClass() == other.getUnitClass() ){
                return false;
            }
        }
        return true;
    }

    public boolean checkOwnedThreeObjective() {
        return ownedObjective >= 3;
    }

    // Getters and setters
    public LeaderCard getOwnedLeader() {
        return ownedLeader;
    }

    public void setOwnedLeader(LeaderCard leader) {
        this.ownedLeader = leader;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getActionPoint() {
        return actionPoint;
    }

    public void setActionPoint(int actionPoint) {
        if (actionPoint > maxActionPoint) actionPoint = maxActionPoint;
        if (actionPoint < 0) actionPoint = 0;
        this.actionPoint = actionPoint;
    }

    public int getMaxActionPoint() {
        return maxActionPoint;
    }

    public void setMaxActionPoint(int maxActionPoint) {
        this.maxActionPoint = maxActionPoint;
    }

    public HeroCard[] getOwnedHero() {
        return ownedHero;
    }

    public void setOwnedHero(HeroCard[] heroList) {
        this.ownedHero = heroList;
    }

    public int getOwnedObjective() {
        return ownedObjective;
    }

    public void setOwnedObjective(int ownedObjective) {
        this.ownedObjective = ownedObjective;
    }

    public ObservableList<BaseCard> getCardsInHand() {
        return cardsInHand;
    }

    public void setCardsInHand(ObservableList<BaseCard> cardsInHand) {
        this.cardsInHand = cardsInHand;
    }

    public boolean isUnchallengeable() { return isUnchallengeable; }
    public void setUnchallengeable(boolean state) { this.isUnchallengeable = state; }// unified: observable + BaseCard

    public int getPermanentAbilityBonus() { return permanentAbilityBonus; }
    public void addPermanentAbilityBonus(int amount) { this.permanentAbilityBonus += amount; }

    // Remove a specific card from hand
    public void removeCardFromHand(BaseCard card) {
        cardsInHand.remove(card);
    }

    // --- NEW: Roll property accessors ---
    public IntegerProperty currentRollProperty() {
        return currentRoll;
    }

    public int getCurrentRoll() {
        return currentRoll.get();
    }

    public void setCurrentRoll(int roll) {
        currentRoll.set(roll);
    }

    public void addOwnedObjective(Objective obj) {
        for (int i = 0; i < ownedObjectives.length; i++) {
            if (ownedObjectives[i] == null) {
                ownedObjectives[i] = obj;
                ownedObjective++; // keep count in sync
                return;
            }
        }
    }

    public Objective[] getOwnedObjectives() {
        return ownedObjectives;
    }

    public boolean isCanPlayItemInstantly() { return canPlayItemInstantly; }
    public void setCanPlayItemInstantly(boolean value) { this.canPlayItemInstantly = value; }

    public boolean isCanPlayMagicInstantly() { return canPlayMagicInstantly; }
    public void setCanPlayMagicInstantly(boolean value) { this.canPlayMagicInstantly = value; }

}
