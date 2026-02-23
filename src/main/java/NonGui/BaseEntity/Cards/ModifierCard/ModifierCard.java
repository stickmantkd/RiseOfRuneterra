package NonGui.BaseEntity.Cards.ModifierCard;

import NonGui.BaseEntity.Player;
import NonGui.BaseEntity.TriggerCard;

import static NonGui.GameLogic.GameChoice.selectModifierEffect;

public class ModifierCard extends TriggerCard {
    //Fields
    private int positiveModifier;
    private int negativeModifier;

    //Constructors
    public ModifierCard(){
        super("Modifier Card", "Can grant or take power!");
        setPositiveModifier(2);
        setNegativeModifier(2);
    }

    public ModifierCard(String name, String flavorText, int positiveModifier, int negativeModifier){
        super(name, flavorText);
        setPositiveModifier(positiveModifier);
        setNegativeModifier(negativeModifier);
    }

    //function
    public int useModifier() {
        int selectedEffect = selectModifierEffect(this);
        return switch (selectedEffect) {
            case 0 -> positiveModifier;
            case 1 -> negativeModifier;
            default -> 0;
        };
    }

    //getters n setters
    public int getPositiveModifier() {
        return positiveModifier;
    }
    public void setPositiveModifier(int positiveModifier) {
        this.positiveModifier = positiveModifier;
    }

    public int getNegativeModifier() {
        return negativeModifier;
    }
    public void setNegativeModifier(int negativeModifier) {
        this.negativeModifier = negativeModifier;
    }
}
