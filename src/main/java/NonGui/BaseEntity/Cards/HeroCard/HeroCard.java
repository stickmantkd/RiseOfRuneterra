package NonGui.BaseEntity.Cards.HeroCard;

import NonGui.BaseEntity.Player;
import NonGui.BaseEntity.Properties.UnitClass;
import NonGui.BaseEntity.ActionCard;
import NonGui.BaseEntity.Cards.Itemcard.ItemCard;
import NonGui.BaseEntity.Properties.haveClass;
import NonGui.GameLogic.GameEngine;
import NonGui.GameUtils.ChallengeUtils;

import static NonGui.GameUtils.DiceUtils.rollForAbility;

public abstract class HeroCard extends ActionCard implements haveClass {
    private UnitClass heroClass;
    private ItemCard item;
    private boolean canUseAbility;
    private int rollTarget;

    // Track which player owns this hero
    private Player owner;

    // Default constructor
    public HeroCard() {
        super("Dummy Hero", "For Demacia!!!", "No Ability, Pure POWER", "Hero Card");
        this.item = null;
        this.canUseAbility = true;
        this.rollTarget = 0;
        this.owner = null;
    }

    // Full constructor
    public HeroCard(String name, String flavorText, String abilityDescription, UnitClass heroClass, int rollTarget) {
        super(name, flavorText, abilityDescription, "Hero Card");
        this.item = null;
        this.heroClass = heroClass;
        this.canUseAbility = true;
        this.rollTarget = rollTarget;
        this.owner = null;
    }

    // On Play
    @Override
    public boolean playCard(Player player) {
        HeroCard[] ownedHero = player.getOwnedHero();
        for (int i = 0; i < ownedHero.length; i++) {
            if (ownedHero[i] == null) {
                System.out.println("Does anyone want to challenge?");

                // Find the index of the challenged player
                int challengedIndex = -1;
                for (int j = 0; j < GameEngine.players.length; j++) {
                    if (GameEngine.players[j] == player) {
                        challengedIndex = j;
                        break;
                    }
                }

                // Run challenge phase
                boolean blocked = ChallengeUtils.resolveChallenge(challengedIndex, player, this);
                if (blocked) {
                    // Challenge succeeded → card discarded
                    return false; // play failed
                }

                // If no challenge or challenge failed → play hero
                ownedHero[i] = this;
                this.owner = player;
                this.tryUseAbility(player);
                player.setOwnedHero(ownedHero);

                System.out.println(player.getName() + " successfully played hero " + this.getName());
                return true; // play succeeded
            }
        }
        return false;
    }

    // Item handling
    public boolean equipItem(ItemCard item) {
        if (this.item != null) return false;
        this.item = item;
        item.onEquip(this);
        return true;
    }

    public boolean unEquipItem() {
        if (this.item == null) return false;
        this.item.onUnEquip(this);
        this.item = null;
        return true;
    }

    // Ability handling
    public boolean tryUseAbility(Player player) {
        if (canUseAbility && rollForAbility(this, rollTarget)) {
            useAbility(player);
            canUseAbility = false;
            return true;
        }
        return false;
    }

    public abstract void useAbility(Player player);

    // Getters and setters
    public ItemCard getItem() { return item; }
    public void setItem(ItemCard item) { this.item = item; }

    @Override
    public UnitClass getUnitClass() { return heroClass; }
    @Override
    public void setUnitClass(UnitClass unitClass) { this.heroClass = unitClass; }

    public boolean canUseAbility() { return canUseAbility; }
    public void setCanUseAbility(boolean canUseAbility) { this.canUseAbility = canUseAbility; }

    public int getRollTarget() { return rollTarget; }
    public void setRollTarget(int rollTarget) { this.rollTarget = rollTarget; }

    public Player getOwner() { return owner; }
    public void setOwner(Player owner) { this.owner = owner; }
}
