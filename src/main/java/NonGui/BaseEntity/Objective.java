package NonGui.BaseEntity;

import static NonGui.GameUtils.DiceUtils.getRoll;
import static NonGui.GameUtils.GameplayUtils.*;

public abstract class Objective{
    //fields
    private String name;
    private String flavorText;
    private String requirementDescription;
    private int minTargetRoll;
    private int maxTargetRoll;
    private String prizeDescription;
    private String punishmentDescription;

    //constructors
    public Objective() {
        setName("Dummy Objective");
        setFlavorText("I am Strong!!!");
        setMinTargetRoll(6);
        setMaxTargetRoll(12);
    }

    public Objective(
            String name,
            String flavorText,
            int minTargetRoll,
            int maxTargetRoll) {
        setName(name);
        setFlavorText(flavorText);
        setMinTargetRoll(minTargetRoll);
        setMaxTargetRoll(maxTargetRoll);
    }

    //


    @Override
    public String toString() {
        return name;
    }

    //functions
    public abstract void grantPrize(Player player);
    public abstract void grantPunishment(Player player);

    public boolean canTry(Player player){
        //To be implemented
        return true;
    }

    public void tryToComplete(Player player){
        if(!canTry(player)) {
            System.out.println("Your owned Heroes don't math the requirement");
        }
        int roll1 = getRoll();
        System.out.println("You rolled a " + roll1);
        int roll2 = getRoll();
        System.out.println("And you rolled a " + roll2);
        if(roll1+roll2 >= minTargetRoll && roll1+roll2 <= maxTargetRoll){
            grantPrize(player);
        }else {
            grantPunishment(player);
        }
    }

    //getters n setters

    public String getFlavorText() {
        return flavorText;
    }
    public void setFlavorText(String flavorText) {
        this.flavorText = flavorText;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getRequirementDescription() {
        return requirementDescription;
    }

    public void setRequirementDescription(String requirementDescription) {
        this.requirementDescription = requirementDescription;
    }

    public int getMinTargetRoll() {
        return minTargetRoll;
    }
    public void setMinTargetRoll(int minTargetRoll) {
        if(minTargetRoll < 2) minTargetRoll = 2;
        this.minTargetRoll = minTargetRoll;
    }

    public int getMaxTargetRoll() {
        return maxTargetRoll;
    }
    public void setMaxTargetRoll(int maxTargetRoll) {
        if(maxTargetRoll > 12) maxTargetRoll = 12;
        this.maxTargetRoll = maxTargetRoll;
    }

    public void setPrizeDescription(String description) {
        this.prizeDescription = description;
    }
    public String getPrizeDescription() {
        return prizeDescription;
    }

    public void setPunishmentDescription(String punishmentDescription) {
        this.punishmentDescription = punishmentDescription;
    }
   public String getPunishmentDescription() {
        return punishmentDescription;
    }
}
