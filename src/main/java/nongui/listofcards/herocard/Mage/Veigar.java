package nongui.listofcards.herocard.Mage;

import nongui.baseentity.cards.HeroCard.HeroCard;
import nongui.baseentity.Player;
import nongui.baseentity.properties.UnitClass;
import nongui.gamelogic.GameChoice;
import nongui.gameutils.GameplayUtils;

import java.util.ArrayList;

import static nongui.gamelogic.GameEngine.players;

/**
 * Represents the "Veigar" Hero Card.
 * <p>
 * <b>Ability: Primordial Burst</b><br>
 * Requirement: Roll 7+<br>
 * Effect: Choose an opponent. That player must SACRIFICE one of their own Hero cards.
 * <p>
 * <i>Veigar's power forces others to make impossible choices,
 * destroying their own allies to appease his dark sorcery.</i>
 */
public class Veigar extends HeroCard {

    /**
     * Constructs Veigar with his base stats and ominous flavor text.
     */
    public Veigar(){
        super(
                "Veigar",
                "Know that if the tables were turned, I would show you no mercy!",
                "Primordial Burst: Roll 7+. Choose a player. That player must SACRIFICE a Hero card.",
                UnitClass.Mage,
                7
        );
    }

    /**
     * Executes the Primordial Burst ability.
     * <p>
     * Logic Flow:
     * 1. Target identification: Opponents with at least one hero.
     * 2. Source selection: The owner chooses which opponent to target.
     * 3. Target's Choice: The selected opponent must perform the sacrifice through GameplayUtils.
     * * @param player The player who activated Veigar's ability.
     */
    @Override
    public void useAbility(Player player) {
        // Ensure UI is updated so players see Veigar on the board before the choice pops up
        try { gui.BoardView.refresh(); } catch (Exception e) {}

        System.out.println("✨ " + this.getName() + " uses Primordial Burst!");

        // 1. Identify valid targets (Opponents with non-empty boards)
        ArrayList<Player> validTargetsList = new ArrayList<>();
        for (Player p : players) {
            if (p != player && !p.boardIsEmpty()) {
                validTargetsList.add(p);
            }
        }

        if (validTargetsList.isEmpty()) {
            System.out.println("No other players have heroes to SACRIFICE!");
            return;
        }

        Player[] validTargetsArray = validTargetsList.toArray(new Player[0]);

        // 2. Select the victim player
        System.out.println(player.getName() + ", choose a victim who must SACRIFICE a Hero:");
        int targetIndex = GameChoice.selectPlayer(validTargetsArray);

        // Handle UI cancellation by the owner
        if (targetIndex == -1) {
            System.out.println("Action canceled.");
            return;
        }

        Player targetPlayer = validTargetsArray[targetIndex];

        // 3. Force the sacrifice
        System.out.println("🔥 " + targetPlayer.getName() + " is ensnared by dark magic and must sacrifice a hero!");

        /* * Calling the robust SacrificeHero logic which handles both
         * player choice and the fail-safe "forced discard" if the dialog is closed.
         */
        GameplayUtils.SacrificeHero(targetPlayer, 1);

        try { gui.BoardView.refresh(); } catch (Exception e) {}
    }
}