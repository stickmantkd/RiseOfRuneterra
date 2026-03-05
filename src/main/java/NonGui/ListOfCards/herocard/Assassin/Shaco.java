package NonGui.ListOfCards.herocard.Assassin;

import NonGui.BaseEntity.Cards.HeroCard.HeroCard;
import NonGui.BaseEntity.Player;
import NonGui.BaseEntity.Properties.UnitClass;
import NonGui.GameLogic.GameChoice;

import java.util.ArrayList;

import static NonGui.GameLogic.GameEngine.players;

/**
 * Represents the "Shaco" Hero Card.
 * <p>
 * <b>Ability: Hallucinate</b><br>
 * Requirement: Roll 9+ (Note: Description says 7+, but internal logic is set to 9)<br>
 * Effect: Steals a Hero Card from another player's board and places it
 * onto the owner's board.
 * <p>
 * <i>Note: This ability requires an empty slot on the owner's board to function.</i>
 */
public class Shaco extends HeroCard {

    /**
     * Constructs Shaco with his base stats and deceptive flavor text.
     */
    public Shaco(){
        super(
                "Shaco",
                "The joke's on you!",
                // Adjusted description to match actual code logic (Stealing a Hero)
                "Hallucinate: Roll 9+. STEAL a Hero card from another player's board.",
                UnitClass.Assassin,
                9
        );
    }

    /**
     * Executes Shaco's ability to steal a hero from an opponent.
     * <p>
     * Logic Flow:
     * 1. Check for available space in owner's party.
     * 2. Identify players with non-empty boards.
     * 3. Prompt user to select a target player and then a specific hero.
     * 4. Transfer ownership of the selected hero.
     * * @param player The player who activated Shaco's ability.
     */
    @Override
    public void useAbility(Player player) {
        System.out.println(this.getName() + " uses Hallucinate! (STEAL a Hero)");

        // 1. Check if the owner's board has space for a new hero
        boolean hasSpace = false;
        for (HeroCard h : player.getOwnedHero()) {
            if (h == null) {
                hasSpace = true;
                break;
            }
        }

        if (!hasSpace) {
            System.out.println(player.getName() + "'s party is full! You cannot steal any more heroes.");
            return;
        }

        // 2. Find valid targets (Opponents with at least one hero)
        ArrayList<Player> validTargetsList = new ArrayList<>();
        for (Player p : players) {
            if (p != player && !p.boardIsEmpty()) {
                validTargetsList.add(p);
            }
        }

        if (validTargetsList.isEmpty()) {
            System.out.println("No other players have heroes available to STEAL!");
            return;
        }

        Player[] validTargetsArray = validTargetsList.toArray(new Player[0]);

        // 3. Select Target Player
        System.out.println(player.getName() + ", choose a player to STEAL a Hero from:");
        int targetIndex = GameChoice.selectPlayer(validTargetsArray);
        Player targetPlayer = validTargetsArray[targetIndex];

        // 4. Select Target Hero from the opponent's board
        System.out.println("Select a hero from " + targetPlayer.getName() + "'s board to STEAL:");
        int heroIndex = GameChoice.selectHeroCard(targetPlayer);

        // Fetch card reference before removal
        HeroCard stolenHero = targetPlayer.getHeroCard(heroIndex);

        // 5. Remove hero from opponent's board
        targetPlayer.removeHeroCard(heroIndex);

        // 6. Transfer to owner's board (fill first available null slot)
        for (int i = 0; i < player.getOwnedHero().length; i++) {
            if (player.getOwnedHero()[i] == null) {
                player.getOwnedHero()[i] = stolenHero;
                stolenHero.setOwner(player); // 🛠️ Essential: Update card's new owner reference
                break;
            }
        }

        System.out.println("SUCCESS! " + player.getName() + " stole " + stolenHero.getName() + " from " + targetPlayer.getName() + "!");

        // Refresh GUI if necessary
        try { gui.BoardView.refresh(); } catch (Exception e) {}
    }
}