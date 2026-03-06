package nongui.listofcards.herocard.Support;

import nongui.baseentity.cards.HeroCard.HeroCard;
import nongui.baseentity.Player;
import nongui.baseentity.properties.UnitClass;
import nongui.gamelogic.GameChoice;

import java.util.ArrayList;

import static nongui.gamelogic.GameEngine.players;

/**
 * Represents the "Neeko" Hero Card.
 * <p>
 * <b>Ability: Inherent Glamour</b><br>
 * Requirement: Roll 6+<br>
 * Effect: Choose an opponent and steal one of their Hero cards.
 * In exchange, Neeko leaves your party and joins the target player's party.
 * <p>
 * <i>Neeko trick opponents by taking their best ally and leaving herself in their place.</i>
 */
public class Neeko extends HeroCard {
    public Neeko() {
        super(
                "Neeko",
                "Neeko is not a sad tomato. She is a strong tomato!",
                "Inherent Glamour: Roll 6+. Choose a player. STEAL a Hero from that player and move this card to their Party.",
                UnitClass.Support,
                6
        );
    }

    /**
     * Executes the Inherent Glamour ability.
     * <p>
     * Logic Flow:
     * 1. Target Selection: Find opponents with at least one hero.
     * 2. Identity Theft: Select a specific hero from the opponent's board.
     * 3. Swap Process:
     * - Neeko is removed from the owner's board.
     * - The stolen hero is removed from the target's board.
     * - The stolen hero is added to the owner's board.
     * - Neeko is added to the target's board.
     * * @param player The player who activated Neeko's ability.
     */
    @Override
    public void useAbility(Player player) {
        System.out.println("🦎 " + this.getName() + " uses Inherent Glamour! (SHAPESHIFT!)");

        // 1. Filter valid targets
        ArrayList<Player> validTargetsList = new ArrayList<>();
        for (Player p : players) {
            if (p != player && !p.boardIsEmpty()) {
                validTargetsList.add(p);
            }
        }

        if (validTargetsList.isEmpty()) {
            System.out.println("No other players with heroes available to STEAL from!");
            return;
        }

        Player[] validTargetsArray = validTargetsList.toArray(new Player[0]);

        // 2. Select Target Player
        System.out.println(player.getName() + ", choose a player to SWAP with:");
        int targetIndex = GameChoice.selectPlayer(validTargetsArray);

        if (targetIndex == -1) return; // Fail-safe for UI cancel

        Player targetPlayer = validTargetsArray[targetIndex];

        // 3. Select Hero to Steal
        System.out.println("Select a hero from " + targetPlayer.getName() + " to take:");
        int heroIndex = GameChoice.selectHeroCard(targetPlayer);

        if (heroIndex == -1) return;

        HeroCard stolenHero = targetPlayer.getHeroCard(heroIndex);

        // --- SWAP PROCESS ---


        // 4. Remove Neeko from current owner
        for (int i = 0; i < player.getOwnedHero().length; i++) {
            if (player.getOwnedHero()[i] == this) {
                player.removeHeroCard(i);
                break;
            }
        }

        // 5. Remove Stolen Hero from target
        targetPlayer.removeHeroCard(heroIndex);

        // 6. Add Stolen Hero to current player's party
        for (int i = 0; i < player.getOwnedHero().length; i++) {
            if (player.getOwnedHero()[i] == null) {
                player.getOwnedHero()[i] = stolenHero;
                stolenHero.setOwner(player); // 🛠️ Update ownership reference
                break;
            }
        }

        // 7. Add Neeko to target player's party
        for (int i = 0; i < targetPlayer.getOwnedHero().length; i++) {
            if (targetPlayer.getOwnedHero()[i] == null) {
                targetPlayer.getOwnedHero()[i] = this;
                this.setOwner(targetPlayer); // 🛠️ Neeko now belongs to the enemy
                break;
            }
        }

        System.out.println("✨ SUCCESS! " + player.getName() + " took " + stolenHero.getName() + "!");
        System.out.println("🦎 Neeko has infiltrated " + targetPlayer.getName() + "'s party!");

        try { gui.BoardView.refresh(); } catch (Exception e) {}
    }
}