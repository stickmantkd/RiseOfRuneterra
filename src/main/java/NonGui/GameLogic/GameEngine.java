package NonGui.GameLogic;

import NonGui.BaseEntity.*;
import NonGui.BaseEntity.Cards.HeroCard.HeroCard;
import NonGui.ListOfCards.itemcard.CurseItem.VoidBinding;
import gui.BoardView;

import static NonGui.GameLogic.GameSetup.*;

public class GameEngine {
    public static Player[] players = new Player[4];
    public static Objective[] objectives = new Objective[3];
    public static Deck deck = new Deck();

    private static int currentPlayerIndex = 0;
    private static boolean isGameActive = false;

    // --- Initialization ---
    public static void reStartGame() {
        // Create players and objectives
        initializeDeck();
        initializePlayer();
        initializeObjective();


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
        // Return all objectives on the board
        return objectives;
    }

    public static boolean isGameActive() {
        // Return whether game is still running
        return isGameActive;
    }

    // --- Turn cycle ---
    // ในไฟล์ GameEngine.java ตรงเมธอด nextTurn
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

        // ใน GameEngine.nextTurn()
        for (HeroCard hero : next.getOwnedHero()) {
            if (hero != null) {
                // ถ้าติด Void Binding ให้เป็น false ตลอดไปจนกว่าจะถอดออก
                if (hero.getItem() instanceof VoidBinding) {
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
        // Ensure game is active
        if (!isGameActive) return;

        // Check AP
        if (player.getActionPoint() < 1) {
            System.out.println(player.getName() + " has no action points left!");
            return;
        }

        // Draw card and reduce AP
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

        // Try to play card
        boolean success = action.playCard(player);

        if (!success) {
            // If it’s a HeroCard, ChallengeUtils already handled discard
            if (selectedCard instanceof NonGui.BaseEntity.Cards.HeroCard.HeroCard) {
                System.out.println("Play blocked by challenge. Card discarded.");
                player.decreaseActionPoint(1);
                // Do NOT return to hand
            } else {
                // Other failures → return to hand
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
        // Ensure game is active
        if (!isGameActive) return;

        // Check AP
        if (player.getActionPoint() < 1) {
            System.out.println(player.getName() + " has no action points left!");
            return;
        }

        // Get hero at index
        HeroCard hero = player.getHeroCard(heroIndex);
        if (hero == null) {
            System.out.println("Invalid action: No Hero at this slot");
            return;
        }

        // ⛓️ [จับตาย VOID BINDING ตรงนี้!]
        // เช็คว่าไอเทมที่ใส่อยู่ ชื่อคลาสตรงกับ VoidBinding หรือไม่
        if (hero.getItem() != null && hero.getItem().getClass().getSimpleName().equals("VoidBinding")) {
            System.out.println("❌ SILENCED! " + hero.getName() + " cannot use abilities while wearing Void Binding!");

            // เด้ง Alert แจ้งเตือนคนเล่น
            javafx.application.Platform.runLater(() -> {
                javafx.scene.control.Alert alert = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.WARNING);
                alert.setTitle("Void Binding Active");
                alert.setHeaderText("Hero Silenced");
                alert.setContentText(hero.getName() + " cannot use abilities!");
                alert.showAndWait();
            });

            return; // 🛑 หยุดการทำงานทันที ไม่คืน AP ไม่ทอยเต๋า
        }

        // Try ability
        if (!hero.tryUseAbility(player)) {
            System.out.println("Invalid action: Ability on cooldown or failed to cast");
            return;
        }

        // Deduct AP and confirm
        player.decreaseActionPoint(1);
        System.out.println(player.getName() + " used " + hero.getName() + "'s ability!");
        safeRefresh();
    }

    public static void tryObjective(Player player, int objectiveIndex) {
        // Ensure game is active
        if (!isGameActive) return;

        // Check AP
        if (player.getActionPoint() < 2) {
            System.out.println(player.getName() + " has insufficient action points!");
            return;
        }

        // Attempt objective
        objectives[objectiveIndex].tryToComplete(objectiveIndex, player);

        // Deduct AP
        player.decreaseActionPoint(2);

        System.out.println(player.getName() + " attempted objective: " + objectives[objectiveIndex].getName());
        safeRefresh();
    }

    private static void safeRefresh() {
        // Refresh GUI board, ignore errors if GUI not running
        try {
            BoardView.refresh();
        } catch (Exception e) {
            // ignore if GUI not running
        }
    }
}
