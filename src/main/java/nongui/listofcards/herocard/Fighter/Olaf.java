package nongui.listofcards.herocard.Fighter;

import nongui.baseentity.BaseCard;
import nongui.baseentity.cards.HeroCard.HeroCard;
import nongui.baseentity.Player;
import nongui.baseentity.properties.UnitClass;
import nongui.gamelogic.GameChoice;
import nongui.gamelogic.GameEngine;
import javafx.scene.control.ChoiceDialog;

import java.util.ArrayList;
import java.util.List;

import static nongui.gamelogic.GameEngine.players;

/**
 * Represents the "Olaf" Hero Card.
 * <p>
 * <b>Ability: Ragnarok</b><br>
 * Requirement: Roll 10+<br>
 * Effect: Discard up to 3 cards from your hand. For each card discarded,
 * you may DESTROY one Hero card from any opponent's board.
 * <p>
 * <i>Olaf turns his own resources into pure destructive power.</i>
 */
public class Olaf extends HeroCard {

    /**
     * Constructs Olaf with his base stats and berserker flavor text.
     */
    public Olaf(){
        super(
                "Olaf",
                "Leave nothing behind!",
                "Ragnarok: Roll 10+. DISCARD up to 3 cards. For each card discarded, DESTROY a Hero card.",
                UnitClass.Fighter,
                10
        );
    }

    /**
     * Executes the Ragnarok ability.
     * <p>
     * Logic Flow:
     * 1. Determine maximum possible discards (capped at 3).
     * 2. Prompt user for the number of cards to sacrifice via ChoiceDialog.
     * 3. Perform discards from owner's hand.
     * 4. For each successful discard, target and destroy an opponent's hero.
     * * @param player The player who activated Olaf's ability.
     */
    @Override
    public void useAbility(Player player) {
        try { gui.BoardView.refresh(); } catch (Exception e) {}

        System.out.println(this.getName() + " uses Ragnarok!");

        // Check potential discard amount
        int maxDiscard = Math.min(3, player.getCardsInHand().size());
        if (maxDiscard == 0) {
            System.out.println(player.getName() + " has no cards to discard. Ragnarok fails.");
            return;
        }

        // 1. Ask for amount to discard
        List<String> amountOptions = new ArrayList<>();
        for (int i = 0; i <= maxDiscard; i++) {
            amountOptions.add(String.valueOf(i));
        }

        ChoiceDialog<String> amountDialog = new ChoiceDialog<>(amountOptions.get(0), amountOptions);
        amountDialog.setTitle("Olaf Ability: Ragnarok");
        amountDialog.setHeaderText(player.getName() + ", prepare for sacrifice!");
        amountDialog.setContentText("How many cards to DISCARD? (0 to " + maxDiscard + "):");

        String result = amountDialog.showAndWait().orElse("0");
        int numToDiscard = Integer.parseInt(result);

        if (numToDiscard == 0) {
            System.out.println(player.getName() + " chose not to discard. Ragnarok ends.");
            return;
        }

        // 2. Perform Discards
        for (int i = 0; i < numToDiscard; i++) {
            System.out.println("Select card to DISCARD (" + (i + 1) + "/" + numToDiscard + "):");
            int cardIndex = GameChoice.selectCardsInHand(player);

            if (cardIndex != -1) {
                // Ensure card is moved to global discard pile
                BaseCard discarded = player.getCardsInHand().remove(cardIndex);
                GameEngine.deck.discardCard(discarded);
                System.out.println("Discarded: " + discarded.getName());
            } else {
                // If user cancels during sub-selection, we should probably reduce the kill count
                numToDiscard = i;
                break;
            }
        }

        // 3. Destroy Heroes based on count

        for (int i = 0; i < numToDiscard; i++) {
            ArrayList<Player> validTargetsList = new ArrayList<>();
            for (Player p : players) {
                if (p != player && !p.boardIsEmpty()) {
                    validTargetsList.add(p);
                }
            }

            if (validTargetsList.isEmpty()) {
                System.out.println("No enemy heroes left to destroy!");
                break;
            }

            Player[] validTargetsArray = validTargetsList.toArray(new Player[0]);
            System.out.println("Choose target player to DESTROY hero (" + (i + 1) + "/" + numToDiscard + "):");
            int targetIndex = GameChoice.selectPlayer(validTargetsArray);

            if (targetIndex == -1) continue;

            Player targetPlayer = validTargetsArray[targetIndex];
            int heroIndex = GameChoice.selectHeroCard(targetPlayer);

            if (heroIndex != -1) {
                // Fetch reference for the discard pile
                HeroCard heroToDestroy = targetPlayer.getHeroCard(heroIndex);

                // Logic based on your comment about Index 1 vs Index 0
                boolean removed;
                try {
                    removed = targetPlayer.removeHeroCard(heroIndex - 1);
                } catch (Exception e) {
                    removed = targetPlayer.removeHeroCard(heroIndex);
                }

                if (removed && heroToDestroy != null) {
                    GameEngine.deck.discardCard(heroToDestroy);
                    System.out.println("🪓 Ragnarok claimed " + heroToDestroy.getName() + "!");
                }

                try { gui.BoardView.refresh(); } catch (Exception e) {}
            }
        }
    }
}