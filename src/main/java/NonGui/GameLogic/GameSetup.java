package NonGui.GameLogic;

import NonGui.BaseEntity.LeaderCard;
import NonGui.BaseEntity.Player;
import NonGui.ListOfCards.herocard.Assassin.Akali;
import NonGui.ListOfCards.herocard.Assassin.Shaco;
import NonGui.ListOfCards.itemcard.BFSword;
import NonGui.ListOfLeader.*;
import NonGui.ListOfObjective.BaronNashor;
import NonGui.ListOfObjective.BlueSentinel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GameSetup {

    // Initialize 4 players with sample leaders, heroes, and hands
    public static void initializePlayer() {
        // Step 1: create a list of all leaders
        List<LeaderCard> leaders = new ArrayList<>();
        leaders.add(new Zed());
        leaders.add(new Teemo());
        leaders.add(new Soraka());
        leaders.add(new Ryze());
        leaders.add(new Garen());
        leaders.add(new Darius());

        // Step 2: shuffle the list for randomness
        Collections.shuffle(leaders);

        // Step 3: assign each player a different leader
        for (int i = 0; i < GameEngine.players.length; i++) {
            Player p = new Player("Player " + (i + 1));

            // Give each player a random leader (unique because we shuffled)
            p.setOwnedLeader(leaders.get(i));

            // Give each player 2 heroes
            p.addHeroCard(new Shaco());
            p.addHeroCard(new Akali());

            // Give each player 2 cards in hand
            //p.addCardToHand(new Akali());
            //p.addCardToHand(new Shaco());
            //p.addCardToHand(new BFSword());

            GameEngine.players[i] = p;
        }
    }

    // Initialize 3 objectives for the center of the board
    public static void initializeObjective() {
        GameEngine.objectives[0] = new BlueSentinel();
        GameEngine.objectives[1] = new BaronNashor();
        GameEngine.objectives[2] = new BaronNashor();
    }
}
