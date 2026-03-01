package NonGui.GameLogic;

import NonGui.BaseEntity.Cards.ChallengeCard.ChallengeCard;
import NonGui.BaseEntity.LeaderCard;
import NonGui.BaseEntity.Player;
import NonGui.ListOfCards.herocard.Assassin.Akali;
import NonGui.ListOfCards.herocard.Assassin.Shaco;
import NonGui.ListOfCards.herocard.Fighter.Fiora;
import NonGui.ListOfCards.herocard.Maskman.Ezreal;
import NonGui.ListOfCards.itemcard.BFSword;
import NonGui.ListOfCards.modifiercard.ElixirOfWrath;
import NonGui.ListOfLeader.*;
import NonGui.ListOfObjective.BaronNashor;
import NonGui.ListOfObjective.BlueSentinel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static NonGui.GameLogic.GameEngine.players;
import static NonGui.GameLogic.GameEngine.objectives;

import NonGui.ListOfObjective.TestObjective;
import javafx.scene.control.TextInputDialog;
import java.util.Optional;

public class GameSetup {

    // Initialize 4 players with names, leaders, heroes, and hands
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

        // Step 3: assign each player
        for (int i = 0; i < players.length; i++) {
            // --- GUI popup for player name ---
            /*
            TextInputDialog dialog = new TextInputDialog("Player " + (i + 1));
            dialog.setTitle("Enter Player Name");
            dialog.setHeaderText("Setup Player " + (i + 1));
            dialog.setContentText("Please enter the player's name:");

            Optional<String> result = dialog.showAndWait();
            String playerName = result.orElse("Player " + (i + 1));
            */

            // --- Uncomment this block for preset names (testing) ---
            String playerName = "TestPlayer" + (i + 1);

            Player p = new Player(playerName);

            // Assign a random leader (unique because we shuffled)
            p.setOwnedLeader(leaders.get(i));

            // Give each player 2 heroes
            p.addHeroCard(new Shaco());
            p.addHeroCard(new Akali());

            // Optionally give cards in hand
            /*
            p.addCardToHand(new Fiora());
            p.addCardToHand(new BFSword());
            p.addCardToHand(new ChallengeCard());
            p.addCardToHand(new ElixirOfWrath());
             */

            players[i] = p;
        }
    }

    // Initialize objectives for the center of the board
    public static void initializeObjective() {
        objectives[0] = new TestObjective();
        objectives[1] = new BlueSentinel();
        objectives[2] = new BaronNashor();
    }

    public static void initializeDeck() {
        // Fill the deck with starting cards
        GameEngine.deck.addToDeck(new Fiora(),5);
        GameEngine.deck.addToDeck(new BFSword(),5);
        GameEngine.deck.addToDeck(new ChallengeCard(),5);
        GameEngine.deck.addToDeck(new ElixirOfWrath(),5);
        GameEngine.deck.addToDeck(new Ezreal(),5);
        GameEngine.deck.addToDeck(new Akali(),5);
        GameEngine.deck.addToDeck(new Shaco(),5);

        // Shuffle the deck
        Collections.shuffle(GameEngine.deck.getGameDeck());

        System.out.println("Deck initialized with " + GameEngine.deck.getGameDeck().size() + " cards.");
    }
}
