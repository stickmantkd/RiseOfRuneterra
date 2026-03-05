package NonGui.GameLogic;

import NonGui.BaseEntity.*;
import NonGui.BaseEntity.Cards.HeroCard.HeroCard;
import NonGui.ListOfCards.itemcard.CurseItem.AbyssalMask;
import gui.BoardView;

import static NonGui.GameLogic.GameSetup.*;

/**
 * The core engine of the game.
 * Manages the global game state, including players, decks, objectives, and the turn cycle.
 * Handles the execution of player actions and synchronizes the state with the GUI.
 */
public class GameEngine {

    // ==========================================
    // Global Game State
    // ==========================================

    public static Player[] players = new Player[4];
    public static Objective[] objectives = new Objective[3]; // ✅ keep top 3 visible objectives
    public static ObjectiveDeck objectiveDeck = new ObjectiveDeck(); // ✅ full deck of objectives
    public static Deck deck = new Deck();

    private static int currentPlayerIndex = 0;
    private static boolean isGameActive = false;

    // ==========================================
    // Initialization
    // ==========================================

    /**
     * Initializes and restarts the game.
     * Sets up players, decks, objectives, and assigns the first turn.
     */
    public static void reStartGame() {
        // Create players and objectives
        initializeDeck();
        initializePlayer();
        initializeObjective(); // seeds ObjectiveDeck and fills top 3 objectives

        // Set first player and mark game active
        currentPlayerIndex = 0;
        isGameActive = true;

        // Refresh GUI board
        safeRefresh();

        System.out.println("Game started! " + getCurrentPlayer().getName() + " begins.");
    }

    // ==========================================
    // State Helpers
    // ==========================================

    /**
     * Retrieves the player whose turn it currently is.
     * @return The active Player object.
     */
    public static Player getCurrentPlayer() {
        // Return the player whose turn it currently is
        return players[currentPlayerIndex];
    }

    /**
     * Retrieves the index of the current active player.
     * @return The integer index (0-3) of the active player.
     */
    public static int getCurrentPlayerIndex() {
        // Return index of current player
        return currentPlayerIndex;
    }

    /**
     * Retrieves the currently visible objectives on the board.
     * @return An array of the top 3 Objectives.
     */
    public static Objective[] getObjectives() {
        // Return all objectives currently visible on the board
        return objectives;
    }

    /**
     * Checks if the game is currently ongoing.
     * @return true if the game is active, false if it has ended or hasn't started.
     */
    public static boolean isGameActive() {
        // Return whether game is still running
        return isGameActive;
    }

    // ==========================================
    // Turn Management
    // ==========================================

    /**
     * Advances the game to the next player's turn.
     * Checks for win conditions, refills action points, and resets temporary buffs/debuffs.
     */
    public static void nextTurn() {
        Player current = getCurrentPlayer();

        if (current.isWinning()) {
            System.out.println(current.getName() + " Wins!");
            isGameActive = false;
            return;
        }

        currentPlayerIndex = (currentPlayerIndex + 1) % players.length;

        Player next = getCurrentPlayer();
        next.refillActionPoint();

        // 🛡️ RESET บัฟ Braum เมื่อเริ่มเทิร์นใหม่
        next.setUnchallengeable(false);

        // 🔨 Reset บัฟ Ornn
        next.setRollBonus(0);

        for (HeroCard hero : next.getOwnedHero()) {
            if (hero != null) {
                // ถ้าติด Void Binding ให้เป็น false ตลอดไปจนกว่าจะถอดออก
                if (hero.getItem() instanceof AbyssalMask) {
                    hero.setCanUseAbility(false);
                } else {
                    hero.setCanUseAbility(true);
                }
            }
        }

        System.out.println("=== " + next.getName() + "'s Turn ===");
        safeRefresh();
    }

    // ==========================================
    // Player Actions
    // ==========================================

    /**
     * Allows the current player to draw a random card from the deck.
     * Costs 1 Action Point.
     * @param player The player performing the action.
     */
    public static void drawCard(Player player) {
        if (!isGameActive) return;

        if (player.getActionPoint() < 1) {
            System.out.println(player.getName() + " has no action points left!");
            return;
        }

        player.drawRandomCard();
        player.decreaseActionPoint(1);

        System.out.println(player.getName() + " drew a card.");
        safeRefresh();
    }

    /**
     * Allows the player to play an ActionCard from their hand onto the board.
     * Handles challenge interruptions and action point deduction.
     * @param player    The player playing the card.
     * @param handIndex The index of the card in the player's hand.
     */
    public static void playCard(Player player, int handIndex) {
        if (!isGameActive) return;
        if (player.handIsEmpty()) return;
        if (player.getActionPoint() < 1) {
            System.out.println(player.getName() + " has no action points left!");
            return;
        }

        BaseCard selectedCard = player.getCardInHand(handIndex);

        if (!(selectedCard instanceof ActionCard)) {
            System.out.println("Invalid action: You can't play a Trigger card.");
            player.addCardToHand(selectedCard); // put back
            return;
        }

        ActionCard actionCard = (ActionCard) selectedCard;
        boolean success = actionCard.playCard(player);

        if (!success) {
            if (selectedCard instanceof HeroCard) {
                System.out.println("Play blocked by challenge. Card discarded.");
                player.decreaseActionPoint(1);
            } else {
                player.addCardToHand(selectedCard);
                System.out.println("Play failed, card returned to hand.");
            }
        } else {
            player.decreaseActionPoint(1);
            System.out.println(player.getName() + " played " + selectedCard.getName());
        }

        safeRefresh();
    }

    /**
     * Allows the player to activate a specific hero's ability.
     * Costs 1 Action Point.
     * @param player    The player owning the hero.
     * @param heroIndex The index of the hero on the player's board.
     */
    public static void useHeroAbility(Player player, int heroIndex) {
        if (!isGameActive) return;

        if (player.getActionPoint() < 1) {
            System.out.println(player.getName() + " has no action points left!");
            return;
        }

        HeroCard hero = player.getHeroCard(heroIndex);
        if (hero == null) {
            System.out.println("Invalid action: No Hero at this slot");
            return;
        }

        if (!hero.tryUseAbility(player)) {
            System.out.println("Invalid action: Ability on cooldown or failed to cast");
            return;
        }

        player.decreaseActionPoint(1);
        System.out.println(player.getName() + " used " + hero.getName() + "'s ability!");
        safeRefresh();
    }

    /**
     * Allows the player to attempt to complete an objective on the board.
     * Costs 2 Action Points.
     * @param player         The player attempting the objective.
     * @param objectiveIndex The index of the objective on the board (0-2).
     */
    public static void tryObjective(Player player, int objectiveIndex) {
        if (!isGameActive) return;

        if (player.getActionPoint() < 2) {
            System.out.println(player.getName() + " has insufficient action points!");
            return;
        }

        // ✅ Attempt objective from the visible objectives array
        Objective objective = objectives[objectiveIndex];
        if(!objective.tryToComplete(objectiveIndex, player)){
            return;
        }

        player.decreaseActionPoint(2);

        System.out.println(player.getName() + " attempted objective: " + objective.getName());
        safeRefresh();
    }

    // ==========================================
    // View Synchronization
    // ==========================================

    /**
     * Safely triggers a refresh of the GUI BoardView.
     * Prevents the game logic from crashing if running without a GUI (headless mode).
     */
    private static void safeRefresh() {
        try {
            BoardView.refresh();
        } catch (Exception e) {
            // ignore if GUI not running
        }
    }
}