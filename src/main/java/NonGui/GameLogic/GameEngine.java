package NonGui.GameLogic;

import NonGui.BaseEntity.*;
import NonGui.BaseEntity.Cards.HeroCard.HeroCard;
import NonGui.ListOfCards.itemcard.CurseItem.AbyssalMask;
import gui.BoardView;

import static NonGui.GameLogic.GameSetup.*;

public class GameEngine {
    public static Player[] players = new Player[4];
    public static Objective[] objectives = new Objective[3]; // ✅ keep top 3 visible objectives
    public static ObjectiveDeck objectiveDeck = new ObjectiveDeck(); // ✅ full deck of objectives
    public static Deck deck = new Deck();

    private static int currentPlayerIndex = 0;
    private static boolean isGameActive = false;

    // --- Initialization ---
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

    // --- Helpers ---
    public static Player getCurrentPlayer() {
        // Return the player whose turn it currently is
        return players[currentPlayerIndex];
    }

    public static int getCurrentPlayerIndex() {
        // Return index of current player
        return currentPlayerIndex;
    }

    public static Objective[] getObjectives() {
        // Return all objectives currently visible on the board
        return objectives;
    }

    public static boolean isGameActive() {
        // Return whether game is still running
        return isGameActive;
    }

    // --- Turn cycle ---
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

    // --- Actions ---
    public static void drawCard(Player player) {
        if (!isGameActive) return;

        if (player.getActionPoint() < 1) {
            System.out.println(player.getName() + " has no action points left!");
            return;
        }

        player.DrawRandomCard();
        player.decreaseActionPoint(1);

        System.out.println(player.getName() + " drew a card.");
        safeRefresh();
    }

    public static void playCard(Player player, int handIndex) {
        if (!isGameActive) return;
        if (player.HandIsEmpty()) return;
        if (player.getActionPoint() < 1) {
            System.out.println(player.getName() + " has no action points left!");
            return;
        }

        BaseCard selectedCard = player.getCardInHand(handIndex);

        if (!(selectedCard instanceof ActionCard action)) {
            System.out.println("Invalid action: You can't play a Trigger card.");
            player.addCardToHand(selectedCard); // put back
            return;
        }

        boolean success = action.playCard(player);

        if (!success) {
            if (selectedCard instanceof NonGui.BaseEntity.Cards.HeroCard.HeroCard) {
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

    private static void safeRefresh() {
        try {
            BoardView.refresh();
        } catch (Exception e) {
            // ignore if GUI not running
        }
    }
}
