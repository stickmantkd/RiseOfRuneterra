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
        for(int i=0;i<2;i++)GameEngine.deck.addToDeck(new Fiora());
        for(int i=0;i<2;i++)GameEngine.deck.addToDeck(new BlueBuff());
        for(int i=0;i<2;i++)GameEngine.deck.addToDeck(new ChallengeCard());
        for(int i=0;i<2;i++)GameEngine.deck.addToDeck(new FinalSpark());
        for(int i=0;i<2;i++)GameEngine.deck.addToDeck(new ElixirOfWrath());
        for(int i=0;i<2;i++)GameEngine.deck.addToDeck(new ElixirOfWrath());
        for(int i=0;i<2;i++)GameEngine.deck.addToDeck(new Charm());
        for(int i=0;i<2;i++)GameEngine.deck.addToDeck(new FinalSpark());
        for(int i=0;i<2;i++)GameEngine.deck.addToDeck(new HowlingGale());
        for(int i=0;i<2;i++)GameEngine.deck.addToDeck(new PickACard());
        for(int i=0;i<2;i++)GameEngine.deck.addToDeck(new BlueBuff());
        for(int i=0;i<2;i++)GameEngine.deck.addToDeck(new TearOfTheGoddess());
        for(int i=0;i<2;i++)GameEngine.deck.addToDeck(new CursedDoubloon());
        for(int i=0;i<2;i++)GameEngine.deck.addToDeck(new VoidBinding());
        for(int i=0;i<2;i++)GameEngine.deck.addToDeck(new Ezreal());
        for(int i=0;i<2;i++)GameEngine.deck.addToDeck(new Akali());
        for(int i=0;i<2;i++)GameEngine.deck.addToDeck(new Shaco());
        for(int i=0;i<2;i++)GameEngine.deck.addToDeck(new Olaf());
        for(int i=0;i<2;i++)GameEngine.deck.addToDeck(new Volibear());
        for(int i=0;i<2;i++)GameEngine.deck.addToDeck(new Bard());
        for(int i=0;i<2;i++)GameEngine.deck.addToDeck(new Neeko());
        for(int i=0;i<2;i++)GameEngine.deck.addToDeck(new TahmKench());
        for(int i=0;i<2;i++)GameEngine.deck.addToDeck(new Caitlyn());
        for(int i=0;i<2;i++)GameEngine.deck.addToDeck(new Jinx());
        for(int i=0;i<2;i++)GameEngine.deck.addToDeck(new Veigar());
        for(int i=0;i<2;i++)GameEngine.deck.addToDeck(new Zilean());
        for(int i=0;i<2;i++)GameEngine.deck.addToDeck(new Zoe());
        for(int i=0;i<2;i++)GameEngine.deck.addToDeck(new Braum());
        for(int i=0;i<2;i++)GameEngine.deck.addToDeck(new Nautilus());
        for(int i=0;i<2;i++)GameEngine.deck.addToDeck(new Ornn());
        for(int i=0;i<2;i++)GameEngine.deck.addToDeck(new Bard());
        for(int i=0;i<2;i++)GameEngine.deck.addToDeck(new Talon());

        // Shuffle the deck
        Collections.shuffle(GameEngine.deck.getGameDeck());

        System.out.println("Deck initialized with " + GameEngine.deck.getGameDeck().size() + " cards.");
    }
}