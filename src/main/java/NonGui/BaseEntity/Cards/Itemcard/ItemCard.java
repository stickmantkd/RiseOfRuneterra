package NonGui.BaseEntity.Cards.Itemcard;


import NonGui.BaseEntity.Cards.HeroCard.HeroCard;
import NonGui.BaseEntity.baseCard;

public abstract class ItemCard extends baseCard {
    private String abilityDescription; //Dice,Skill,Mark

    public ItemCard(String name,String flavorText,String AbilityDescription){
        super(name,flavorText);
        setAbilityDescription(abilityDescription);
    }

    //Functions
    public abstract void enableAbility(HeroCard hero);

    public abstract void disableAbility(HeroCard hero);

    //getters n setters
    public String getAbilityDescription() {
        return abilityDescription;
    }
    public void setAbilityDescription(String abilityDescription) {
        this.abilityDescription = abilityDescription;
    }
}
