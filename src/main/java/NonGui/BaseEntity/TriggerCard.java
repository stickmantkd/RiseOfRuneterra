package NonGui.BaseEntity;

public abstract class TriggerCard extends BaseCard{
    //constructor
    public TriggerCard(String name,String flavorText){
        super(name,flavorText);
    }

    //functions
    public abstract boolean onTrigger(Player player);
}
