package NonGui.BaseEntity;

import NonGui.BaseEntity.Cards.HeroCard.*;
import NonGui.BaseEntity.Properties.UnitClass;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import static NonGui.GameUtils.GenerationsUtils.*;

public class Player {
    // Fields
    private String name;
    private int actionPoint;
    private int maxActionPoint;
    private int ownedObjective;
    private LeaderCard ownedLeader;
    private HeroCard[] ownedHero;
    private ObservableList<ActionCard> cardsInHand; // now observable

    // Constructors
    public Player(String name) {
        setName(name);
        setMaxActionPoint(3);
        setActionPoint(3);
        setOwnedObjective(0);
        initializeOwnedLeader();
        initializeOwnedHero();
        initializeCardsInHand();
    }

    // Utilities
    @Override
    public String toString() {
        return name;
    }

    public void DrawRandomCard() {
        cardsInHand.add(GenerateRandomCard());
    }

    public void addCardToHand(ActionCard card) {
        cardsInHand.add(card);
    }

    public boolean boardIsEmpty() {
        for (HeroCard hero : ownedHero) {
            if (hero != null) return false;
        }
        return true;
    }

    public boolean HandIsEmpty() {
        return cardsInHand.isEmpty();
    }

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
        ownedLeader = GenerateRandomLeader();
    }

    private void initializeOwnedHero() {
        ownedHero = new HeroCard[5];
    }

    private void initializeCardsInHand() {
        cardsInHand = FXCollections.observableArrayList();
        for (int i = 0; i < 5; ++i) {
            DrawRandomCard();
        }
    }

    // Index Operation
    public ActionCard getCardInHand(int index) {
        ActionCard selectedCard = cardsInHand.get(index);
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

    // addHero
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
        for (HeroCard hero : ownedHero) {
            if (hero == null || hero.getUnitClass() == leaderClass) {
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

    public ObservableList<ActionCard> getCardsInHand() {
        return cardsInHand;
    }

    public void setCardsInHand(ObservableList<ActionCard> cardsInHand) {
        this.cardsInHand = cardsInHand;
    }
}
