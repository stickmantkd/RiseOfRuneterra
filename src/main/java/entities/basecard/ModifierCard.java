package entities.basecard;

import entities.card.BaseCard;

public class ModifierCard extends BaseCard {
    private final int optionA;
    private final int optionB;

    public ModifierCard(String name, int optionA, int optionB) {
        super(name, "Modifier");
        this.optionA = optionA;
        this.optionB = optionB;
    }

    public int getOptionA() { return optionA; }
    public int getOptionB() { return optionB; }
}