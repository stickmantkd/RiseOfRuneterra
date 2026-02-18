package entities.basecard;

import entities.card.BaseCard;

public class HeroCard extends BaseCard {
    private final String heroClass;   // Fighter Mage Tank Marksman Assassin Specialist
    private final int rollRequirement;

    public HeroCard(String name, String heroClass, int rollRequirement) {
        super(name, "Hero");
        this.heroClass = heroClass;
        this.rollRequirement = rollRequirement;
    }

    public String getHeroClass() { return heroClass; }
    public int getRollRequirement() { return rollRequirement; }


    public boolean attemptEffect(int roll) {
        return roll >= rollRequirement;
    }
}