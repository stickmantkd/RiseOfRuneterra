package NonGui.GameLogic;

import NonGui.BaseEntity.Cards.ChallengeCard.ChallengeCard;
import NonGui.BaseEntity.LeaderCard;
import NonGui.BaseEntity.Player;
import NonGui.ListOfCards.herocard.Assassin.Akali;
import NonGui.ListOfCards.herocard.Assassin.Shaco;
import NonGui.ListOfCards.herocard.Assassin.Talon;
import NonGui.ListOfCards.herocard.Fighter.Fiora;
import NonGui.ListOfCards.herocard.Fighter.Olaf;
import NonGui.ListOfCards.herocard.Fighter.Volibear;
import NonGui.ListOfCards.herocard.Mage.Veigar;
import NonGui.ListOfCards.herocard.Mage.Zilean;
import NonGui.ListOfCards.herocard.Mage.Zoe;
import NonGui.ListOfCards.herocard.Maskman.Caitlyn;
import NonGui.ListOfCards.herocard.Maskman.Ezreal;
import NonGui.ListOfCards.herocard.Maskman.Jinx;
import NonGui.ListOfCards.herocard.Support.Bard;
import NonGui.ListOfCards.herocard.Support.Neeko;
import NonGui.ListOfCards.herocard.Support.TahmKench;
import NonGui.ListOfCards.herocard.Tank.Braum;
import NonGui.ListOfCards.herocard.Tank.Nautilus;
import NonGui.ListOfCards.herocard.Tank.Ornn;
import NonGui.ListOfCards.itemcard.*;
import NonGui.ListOfCards.magiccard.*;
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
        GameEngine.deck.addToDeck(new Fiora(),20);
        //GameEngine.deck.addToDeck(new BFSword(),20);
        //GameEngine.deck.addToDeck(new ChallengeCard(),20);
        //GameEngine.deck.addToDeck(new FinalSpark(),20);
        //GameEngine.deck.addToDeck(new ElixirOfWrath(),20);
        //GameEngine.deck.addToDeck(new Charm(),20);
        //GameEngine.deck.addToDeck(new FinalSpark(),20);
        //GameEngine.deck.addToDeck(new HowlingGale(),20);
        //GameEngine.deck.addToDeck(new PickACard(),20);
        //GameEngine.deck.addToDeck(new BlueBuff(),20);
        GameEngine.deck.addToDeck(new TearOfTheGoddess(),20);
        //GameEngine.deck.addToDeck(new CursedDoubloon(),20);
        //GameEngine.deck.addToDeck(new VoidBinding(),20);
        //GameEngine.deck.addToDeck(new Ezreal(),10);
        //GameEngine.deck.addToDeck(new Akali(),10);
        //GameEngine.deck.addToDeck(new Shaco(),15);
        //GameEngine.deck.addToDeck(new Olaf(),15);
        //GameEngine.deck.addToDeck(new Volibear(),5);
        //GameEngine.deck.addToDeck(new Bard(),15);
        //GameEngine.deck.addToDeck(new Neeko(),5);
        //GameEngine.deck.addToDeck(new TahmKench(),5);
        //GameEngine.deck.addToDeck(new Caitlyn(),5);
        //GameEngine.deck.addToDeck(new Jinx(),5);
        //GameEngine.deck.addToDeck(new Veigar(),10);
        //GameEngine.deck.addToDeck(new Zilean(),15);
        //GameEngine.deck.addToDeck(new Zoe(),10);
        //GameEngine.deck.addToDeck(new Braum(),15);
        //GameEngine.deck.addToDeck(new Nautilus(),15);
        //GameEngine.deck.addToDeck(new Ornn(),15);
        //GameEngine.deck.addToDeck(new Talon(),10);

        // Shuffle the deck
        Collections.shuffle(GameEngine.deck.getGameDeck());

        System.out.println("Deck initialized with " + GameEngine.deck.getGameDeck().size() + " cards.");
    }
}
