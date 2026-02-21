package NonGui.BaseEntity;

import NonGui.BaseEntity.Cards.HeroCard.UnitClass;
import NonGui.BaseEntity.Cards.HeroCard.HeroCard;
import NonGui.BaseEntity.Cards.ItemCard;
import NonGui.BaseEntity.Cards.MagicCard;

import java.util.ArrayList;

import static NonGui.GameUtils.GenerationsUtils.*;

public class Player{
    //Fields
    private String name;
    private int actionPoint;
    private int maxActionPoint;
    private int ownedObjective;
    private LeaderCard ownedLeader;
    private HeroCard[] ownedHero;
    private ArrayList<baseCard> cardsInHand;

    //Constructors
    public Player(String name){
        setMaxActionPoint(3);
        setActionPoint(3);
        setOwnedObjective(0);
        initializeOwnedLeader();
        initializeOwnedHero();
        initializeCardsInHand();
    }

    //Utilities
    @Override
    public String toString() {
        return name;
    }

    public void DrawRandomCard(){
        cardsInHand.add(GenerateRandomCard());
    }

    public boolean boardIsEmpty() {
        for(HeroCard hero : ownedHero){
            if(hero != null) return false;
        }
        return true;
    }

    public void refillActionPoint() {
        setActionPoint(maxActionPoint);
    }

    //Setups
    private void initializeOwnedLeader(){
        ownedLeader = GenerateRandomLeader();
    }

    private void initializeOwnedHero(){
        ownedHero = new HeroCard[5];
    }

    private void initializeCardsInHand(){
        cardsInHand = new ArrayList<>();
        for(int i = 0; i < 5; ++i){
            DrawRandomCard();
        }
    }



    //Index Operation
    public baseCard getCardInHand(int index){
        index--; // since array index start with 0
        baseCard selectedCard = cardsInHand.get(index);
        cardsInHand.remove(selectedCard);
        return selectedCard;
    }

    public HeroCard getHeroCard(int index) {
        index--; // since array index start with 0
        return ownedHero[index];
    }

    public boolean removeHeroCard(int index) {
        if(ownedHero[index] == null) return false;
        ownedHero[index] = null;
        return true;
    }

    //CheckWinning
    public boolean isWinning(){
        return checkOwnedAllClass() || checkOwnedThreeObjective();
    }
    public boolean checkOwnedAllClass(){
        UnitClass leaderClass = getOwnedLeader().getUnitClass();
        for(HeroCard hero : ownedHero){
            if(hero == null || hero.getUnitClass() == leaderClass){
                return false;
            }
        }
        return true;
    }
    public boolean checkOwnedThreeObjective(){
        return ownedObjective >= 3;
    }


    //PlayCard
    public boolean playHero(HeroCard heroCard) {
        for (int i = 0; i < ownedHero.length; i++) {
            if (ownedHero[i] == null) {
                ownedHero[i] = heroCard;
                heroCard.useAbility();
                return true;
            }
        }
        return false;
    }

    public boolean playMagic(MagicCard magicCard) {
        return false;
    }

    public boolean playItem(ItemCard itemCard,HeroCard heroCard) {
        return false;
    }


    //getters n setters
    public LeaderCard getOwnedLeader() {
        return ownedLeader;
    }
    public void setOwnedLeader(LeaderCard leader) {
        this.ownedLeader = leader;
    }

    public String  getName() {
        return name;
    }
    public void setName(String  name) {
        this.name = name;
    }

    public int getActionPoint() {
        return actionPoint;
    }
    public void setActionPoint(int actionPoint) {
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

    public ArrayList<baseCard> getCardsInHand() {
        return cardsInHand;
    }
    public void setCardsInHand(ArrayList<baseCard> cardsInHand) {
        this.cardsInHand = cardsInHand;
    }
}
