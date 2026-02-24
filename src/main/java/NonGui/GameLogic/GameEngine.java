package NonGui.GameLogic;

import NonGui.BaseEntity.*;

import static NonGui.GameLogic.GameSetup.*;

public class GameEngine {
    public static Player[] players = new Player[4];
    public static Objective[] objectives = new Objective[3];

    private static int currentPlayerIndex = 0;
    private static boolean isGameActive = false;

    // --- Initialization ---
    public static void startGame() {
        initializePlayer();
        initializeObjective();
        currentPlayerIndex = 0;
        isGameActive = true;
    }

    // --- Helpers for GUI ---
    public static Player getCurrentPlayer() {
        return players[currentPlayerIndex];
    }

    public static Objective[] getObjectives() {
        return objectives;
    }

    public static boolean isGameActive() {
        return isGameActive;
    }

    public static void nextTurn() {
        Player current = getCurrentPlayer();
        if (current.isWinning()) {
            System.out.println(current.getName() + " Wins!");
            isGameActive = false;
            return;
        }
        current.refillActionPoint();
        currentPlayerIndex = (currentPlayerIndex + 1) % players.length;
    }

    // --- Actions ---
    public static void drawCard(Player player) {
        player.DrawRandomCard();
        player.decreaseActionPoint(1);
    }

    public static void playCard(Player player, int handIndex) {
        if (player.HandIsEmpty()) return;

        ActionCard selectedCard = player.getCardInHand(handIndex);
        if (!selectedCard.playCard(player)) {
            player.addCardToHand(selectedCard); // put back if invalid
        } else {
            player.decreaseActionPoint(1);
        }
    }

    public static void tryObjective(Player player, int objectiveIndex) {
        if (player.getActionPoint() < 2) return;
        objectives[objectiveIndex].tryToComplete(objectiveIndex, player);
        player.decreaseActionPoint(2);
    }
}
