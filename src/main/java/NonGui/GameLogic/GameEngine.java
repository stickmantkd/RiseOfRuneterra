package NonGui.GameLogic;

import NonGui.BaseEntity.*;
import gui.BoardView;

import java.util.Scanner;

import static NonGui.GameLogic.GameSetup.*;
import static NonGui.GameLogic.GameChoice.*;

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

        // GUI refresh if available
        try {
            BoardView.refresh();
        } catch (Exception e) {
            // ignore if GUI not running
        }

        System.out.println("Game started! " + getCurrentPlayer().getName() + " begins.");
    }

    // --- Helpers ---
    public static Player getCurrentPlayer() {
        return players[currentPlayerIndex];
    }

    public static int getCurrentPlayerIndex() {
        return currentPlayerIndex;
    }

    public static Objective[] getObjectives() {
        return objectives;
    }

    public static boolean isGameActive() {
        return isGameActive;
    }

    // --- Turn cycle ---
    public static void nextTurn() {
        Player current = getCurrentPlayer();

        // Check win condition before ending turn
        if (current.isWinning()) {
            System.out.println(current.getName() + " Wins!");
            isGameActive = false;
            return;
        }

        // End current turn, move to next player
        currentPlayerIndex = (currentPlayerIndex + 1) % players.length;

        // Start of next turn
        Player next = getCurrentPlayer();
        next.refillActionPoint();

        System.out.println("=== " + next.getName() + "'s Turn ===");

        // GUI refresh if available
        try {
            BoardView.refresh();
        } catch (Exception e) {
            // ignore if GUI not running
        }
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
            System.out.println("Invalid action: You can't play this card.");
            player.addCardToHand(selectedCard);
            return;
        }

        if (!action.playCard(player)) {
            player.addCardToHand(selectedCard); // put back if invalid
            System.out.println("Play failed, card returned to hand.");
        } else {
            player.decreaseActionPoint(1);
            System.out.println(player.getName() + " played " + selectedCard.getName());
        }

        safeRefresh();
    }

    public static void tryObjective(Player player, int objectiveIndex) {
        if (!isGameActive) return;
        if (player.getActionPoint() < 2) {
            System.out.println(player.getName() + " has insufficient action points!");
            return;
        }

        objectives[objectiveIndex].tryToComplete(objectiveIndex, player);
        player.decreaseActionPoint(2);

        System.out.println(player.getName() + " attempted objective: " + objectives[objectiveIndex].getName());
        safeRefresh();
    }

    private static void safeRefresh() {
        try {
            BoardView.refresh();
        } catch (Exception e) {
            // ignore if GUI not running
        }
    }

    // --- Console front end ---
    public static void main(String[] args) {
        System.out.println("Launching Rise of Runeterra...");
        Scanner keyBoard = new Scanner(System.in);

        while (true) {
            System.out.println("=============================");
            System.out.println("Welcome to Rise of Runeterra");
            System.out.println("=============================");
            System.out.println("What are you doing?");
            System.out.println("1) Start Game");
            System.out.println("2) Quit");
            System.out.println("=============================");
            int results = getChoice();

            if (results == 1) {
                startGame();
                runConsoleLoop();
            } else if (results == 2) {
                break;
            } else {
                System.out.println("Invalid Input, Terminate the game.");
                break;
            }
        }
    }

    private static void runConsoleLoop() {
        boolean active = true;
        while (active) {
            Player currentPlayer = getCurrentPlayer();
            while (currentPlayer.getActionPoint() > 0) {
                System.out.println("It's " + currentPlayer + "'s turn");
                System.out.println("=============================");
                System.out.println("Action Point left: " + currentPlayer.getActionPoint());
                System.out.println("Choose your action");
                System.out.println("1 : Draw a card");
                System.out.println("2 : Play a card");
                System.out.println("3 : Try to complete the objective");

                switch (getChoice()) {
                    case 1 -> drawCard(currentPlayer);
                    case 2 -> {
                        if (currentPlayer.HandIsEmpty()) {
                            System.out.println("Invalid action: Hand Is Empty!");
                            continue;
                        }
                        int cardIndex = selectCardsInHand(currentPlayer);
                        playCard(currentPlayer, cardIndex);
                    }
                    case 3 -> {
                        if (currentPlayer.getActionPoint() < 2) {
                            System.out.println("Invalid: You need 2 AP to attempt an Objective");
                            continue;
                        }
                        int objectiveIndex = selectObjective();
                        tryObjective(currentPlayer, objectiveIndex);
                    }
                }
                System.out.println("=============================");
            }
            if (currentPlayer.isWinning()) {
                System.out.println(currentPlayer + " Wins");
                System.out.println("Good Game Go Next");
                System.out.println("=============================");
                active = false;
            }
            nextTurn();
        }
    }
}
