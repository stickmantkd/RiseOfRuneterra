package NonGui.ListOfCards.herocard.Maskman;

import NonGui.BaseEntity.Cards.HeroCard.HeroCard;
import NonGui.BaseEntity.Player;
import NonGui.BaseEntity.Properties.UnitClass;
import NonGui.GameLogic.GameChoice;
import NonGui.GameLogic.GameEngine;

import java.util.ArrayList;

import static NonGui.GameLogic.GameEngine.players;

/**
 * Represents the "Caitlyn" Hero Card.
 * <p>
 * <b>Ability: Ace in the Hole</b><br>
 * Requirement: Roll 9+<br>
 * Effect: Target an opponent to DESTROY one of their Hero cards, then DRAW a card.
 * <p>
 * <i>Caitlyn provides high-impact removal paired with card advantage,
 * making her a high-priority threat on the board.</i>
 */
public class Caitlyn extends HeroCard {

    /**
     * Constructs Caitlyn with her base stats and law-enforcement flavor text.
     */
    public Caitlyn(){
        super(
                "Caitlyn",
                "Meet the long gun of the law.",
                "Ace in the Hole: Roll 9+. DESTROY a Hero card and DRAW a card.",
                UnitClass.Maskman,
                9
        );
    }

    /**
     * Executes the Ace in the Hole ability.
     * <p>
     * Logic Flow:
     * 1. Target identification: Identify opponents with at least one hero on board.
     * 2. Destruction: If targets exist, prompt the player to select a victim and a specific hero to destroy.
     * 3. Resolution: Remove the hero and move it to the discard pile.
     * 4. Draw Phase: Regardless of whether a hero was destroyed, the owner draws 1 card.
     * * @param player The player who activated Caitlyn's ability.
     */
    @Override
    public void useAbility(Player player) {
        System.out.println("🎯 " + this.getName() + " is locking on! (Ace in the Hole)");

        // 1. Identify valid targets (Opponents with non-empty boards)
        ArrayList<Player> validTargetsList = new ArrayList<>();
        for (Player p : players) {
            if (p != player && !p.boardIsEmpty()) {
                validTargetsList.add(p);
            }
        }

        // 2. Destruction Phase
        if (validTargetsList.isEmpty()) {
            System.out.println("No enemy heroes available to DESTROY! Skipping destruction.");
        } else {
            Player[] validTargetsArray = validTargetsList.toArray(new Player[0]);

            // Select Target Player
            System.out.println(player.getName() + ", choose a player to DESTROY their hero:");
            int targetIndex = GameChoice.selectPlayer(validTargetsArray);

            if (targetIndex != -1) {
                Player targetPlayer = validTargetsArray[targetIndex];

                // Select Target Hero
                System.out.println("Select a hero from " + targetPlayer.getName() + "'s board to DESTROY:");
                int heroIndex = GameChoice.selectHeroCard(targetPlayer);

                if (heroIndex != -1) {
                    // Capture reference and destroy
                    HeroCard heroToDestroy = targetPlayer.getHeroCard(heroIndex);
                    targetPlayer.removeHeroCard(heroIndex);

                    // ✅ Add to discard pile
                    GameEngine.deck.discardCard(heroToDestroy);

                    System.out.println("💥 BOOM! " + (heroToDestroy != null ? heroToDestroy.getName() : "A hero") +
                            " from " + targetPlayer.getName() + "'s board was neutralized!");
                }
            }
        }

        // 3. Draw Phase (Independent of the destruction result)
        System.out.println("🃏 " + player.getName() + " draws 1 card to replenish resources.");
        player.drawRandomCard();

        // Sync GUI state
        try { gui.BoardView.refresh(); } catch (Exception e) {}
    }
}