package NonGui.GameLogic;

import NonGui.BaseEntity.Cards.ChallengeCard.ChallengeCard;
import NonGui.BaseEntity.LeaderCard;
import NonGui.BaseEntity.Player;
import NonGui.ListOfCards.herocard.Assassin.Akali;
import NonGui.ListOfCards.herocard.Assassin.Shaco;
import NonGui.ListOfCards.herocard.Fighter.Fiora;
import NonGui.ListOfCards.herocard.Support.Bard;
import NonGui.ListOfCards.herocard.Tank.Ornn;
import NonGui.ListOfCards.itemcard.*;
import NonGui.ListOfCards.magiccard.ElixirOfSorcery;
import NonGui.ListOfCards.magiccard.FinalSpark;
import NonGui.ListOfCards.modifiercard.ElixirOfWrath;
import NonGui.ListOfLeader.*;
import NonGui.ListOfObjective.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static NonGui.GameLogic.GameEngine.*;
import static NonGui.GameUtils.GenerationsUtils.drawObjective;

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

        objectiveDeck.addToDeck(new BaronNashor());
        objectiveDeck.addToDeck(new BlueSentinel());
        objectiveDeck.addToDeck(new FreljordianYeti());
        objectiveDeck.addToDeck(new GreaterMurkWolf());
        objectiveDeck.addToDeck(new InfernalDrake());
        objectiveDeck.addToDeck(new RedBrambleback());
        objectiveDeck.addToDeck(new RiftHerald());
        objectiveDeck.addToDeck(new ElderDragon());

        objectiveDeck.shuffle();

        objectives[0] = drawObjective();
        objectives[1] = drawObjective();
        objectives[2] = drawObjective();

    }

    public static void initializeDeck() {
        // Fill the deck with starting cards
        //for(int i=0;i<20;i++){GameEngine.deck.addToDeck(new Fiora());}
        for(int i=0;i<10;i++){GameEngine.deck.addToDeck(new BlueBuff());}
        for(int i=0;i<10;i++){GameEngine.deck.addToDeck(new ChallengeCard());}
        for(int i=0;i<10;i++){GameEngine.deck.addToDeck(new FinalSpark());}
        for(int i=0;i<10;i++){GameEngine.deck.addToDeck(new ElixirOfWrath());}
        //GameEngine.deck.addToDeck(new ElixirOfWrath(),20);
        //GameEngine.deck.addToDeck(new Charm(),20);
        //GameEngine.deck.addToDeck(new FinalSpark(),20);
        //GameEngine.deck.addToDeck(new HowlingGale(),20);
        //GameEngine.deck.addToDeck(new PickACard(),20);
        //GameEngine.deck.addToDeck(new BlueBuff(),20);
        //for(int i=0;i<20;i++){GameEngine.deck.addToDeck(new TearOfTheGoddess());}
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
        //for(int i=0;i<20;i++){GameEngine.deck.addToDeck(new Ornn());}
        for(int i=0;i<20;i++){GameEngine.deck.addToDeck(new Bard());}
        //GameEngine.deck.addToDeck(new Talon(),10);

        // Shuffle the deck
        Collections.shuffle(GameEngine.deck.getGameDeck());

        System.out.println("Deck initialized with " + GameEngine.deck.getGameDeck().size() + " cards.");
    }
}