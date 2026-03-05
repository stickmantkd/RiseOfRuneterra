package NonGui.BaseEntity.Cards.HeroCard;

import NonGui.BaseEntity.Player;
import NonGui.BaseEntity.Properties.UnitClass;
import NonGui.BaseEntity.ActionCard;
import NonGui.BaseEntity.Cards.Itemcard.ItemCard;
import NonGui.BaseEntity.Properties.HaveClass;
import NonGui.GameLogic.GameEngine;
import NonGui.GameUtils.ChallengeUtils;
import NonGui.GameUtils.DiceUtils;
import NonGui.ListOfCards.itemcard.CurseItem.AbyssalMask;

/**
 * An abstract base class representing a Hero Card in the game.
 * Hero Cards can be played onto a player's board, challenged by opponents,
 * equipped with items, and have unique abilities triggered by dice rolls.
 */
public abstract class HeroCard extends ActionCard implements HaveClass {

    // ==========================================
    // Fields
    // ==========================================
    private UnitClass heroClass;
    private ItemCard item;
    private boolean canUseAbility;
    private int rollTarget;

    // Track which player owns this hero
    private Player owner;

    // ==========================================
    // Constructors
    // ==========================================

    /**
     * Default constructor for HeroCard.
     * Initializes a dummy hero with default values.
     */
    public HeroCard() {
        super("Dummy Hero", "For Demacia!!!", "No Ability, Pure POWER", "Hero Card");
        this.item = null;
        this.canUseAbility = true;
        this.rollTarget = 0;
        this.owner = null;
    }

    /**
     * Parameterized constructor to create a specific HeroCard.
     *
     * @param name               The name of the hero.
     * @param flavorText         The background lore of the hero.
     * @param abilityDescription The description of the hero's ability.
     * @param heroClass          The class of the hero (e.g., Fighter, Mage).
     * @param rollTarget         The target number required to roll to activate the ability.
     */
    public HeroCard(String name, String flavorText, String abilityDescription, UnitClass heroClass, int rollTarget) {
        super(name, flavorText, abilityDescription, "Hero Card");
        this.item = null;
        this.heroClass = heroClass;
        this.canUseAbility = true;
        this.rollTarget = rollTarget;
        this.owner = null;
    }

    // ==========================================
    // Core Gameplay Logic
    // ==========================================

    /**
     * Attempts to play the hero card onto the player's board.
     * Includes checking for available slots and initiating the Challenge phase.
     *
     * @param player The player attempting to play the hero.
     * @return true if the hero was successfully played, false if blocked by a challenge or no slots available.
     */
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

    // ==========================================
    // Item Handling
    // ==========================================

    /**
     * Equips an ItemCard to this hero if the hero doesn't already have one.
     * @param item The ItemCard to equip.
     * @return true if successfully equipped, false if the hero already holds an item.
     */
    public boolean equipItem(ItemCard item) {
        if (this.item != null) return false;
        this.item = item;
        item.onEquip(this);
        return true;
    }

    /**
     * Unequips the currently held ItemCard from this hero.
     * @return true if successfully unequipped, false if the hero holds no item.
     */
    public boolean unEquipItem() {
        if (this.item == null) return false;
        this.item.onUnEquip(this);
        this.item = null;
        return true;
    }

    // ==========================================
    // Ability Handling
    // ==========================================

    /**
     * Attempts to use the hero's unique ability.
     * Checks for silence effects (like AbyssalMask), usage limits, and rolls dice for success.
     *
     * @param player The player controlling the hero.
     * @return true if the ability usage sequence was executed, false if blocked or already used.
     */
    public boolean tryUseAbility(Player player) {
        // 🌌 [จุดที่ต้องเพิ่ม] ถ้าใส่ Void Binding ให้คืนค่า false ทันที!
        if (this.getItem() instanceof AbyssalMask) {
            System.out.println("🌌 " + this.getName() + " is bound by the Void! Ability is sealed.");
            return false;
        }

        // โค้ดเดิมของคุณที่เช็ค canUseAbility
        if (!canUseAbility) {
            return false;
        }

        // โค้ดเดิมของคุณที่สั่งทอยเต๋า (DiceUtils.rollForAbility)
        if (DiceUtils.rollForAbility(this, this.rollTarget)) {
            this.useAbility(player);
        }

        this.canUseAbility = false;
        return true;
    }

    /**
     * Executes the specific effect of the hero's ability.
     * Subclasses must implement this to define what the ability actually does.
     * @param player The player controlling the hero.
     */
    public abstract void useAbility(Player player);

    // ==========================================
    // Getters and Setters
    // ==========================================

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