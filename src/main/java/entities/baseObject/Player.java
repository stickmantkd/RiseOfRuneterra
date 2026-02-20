package entities.baseObject;

import entities.baseObject.baseCards.HeroCard.UnitClass;
import entities.baseObject.baseCards.HeroCard.HeroCard;
import entities.baseObject.LeaderCard.LeaderCard;

import java.util.ArrayList;

public class Player{
    //Fields
    private String name;
    private int actionPoint;
    private int maxActionPoint;
    private LeaderCard ownedLeader;
    private HeroCard[] ownedHero;
    private int ownedObjective;
    private ArrayList<baseCard> cardsInHand;

    //Constructors
    public Player(String name){
        setMaxActionPoint(3);
        setActionPoint(3);
        HeroCard[] initialOwnedHero = new HeroCard[5];
        setOwnedHero(initialOwnedHero);
        setOwnedObjective(0);
        ArrayList<baseCard> initialHand = new ArrayList<>();
        setCardsInHand(initialHand);
    }

    //Functions
    @Override
    public String toString() {
        return name;
    }

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

    public void refillActionPoint() {
        setActionPoint(maxActionPoint);
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
