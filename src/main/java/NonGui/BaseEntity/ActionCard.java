package NonGui.BaseEntity;

public abstract class ActionCard extends BaseCard {

    public ActionCard(String name, String flavorText, String abilityDescription, String type){
        super(name, flavorText, abilityDescription, type);
    }

    public abstract boolean playCard(Player player);
}
