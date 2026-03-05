package NonGui.GameLogic;

import NonGui.BaseEntity.LeaderCard;
import NonGui.BaseEntity.Player;
import NonGui.ListOfCards.herocard.Assassin.*;
import NonGui.ListOfCards.herocard.Fighter.*;
import NonGui.ListOfCards.herocard.Mage.*;
import NonGui.ListOfCards.herocard.Maskman.*;
import NonGui.ListOfCards.herocard.Support.*;
import NonGui.ListOfCards.herocard.Tank.*;
import NonGui.ListOfCards.itemcard.BuffItem.*;
import NonGui.ListOfCards.itemcard.ChangeClass.*;
import NonGui.ListOfCards.itemcard.CurseItem.*;
import NonGui.ListOfCards.magiccard.*;
import NonGui.ListOfCards.modifiercard.*;
import NonGui.ListOfLeader.*;
import NonGui.ListOfObjective.*;
import NonGui.BaseEntity.Cards.ChallengeCard.ChallengeCard;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static NonGui.GameLogic.GameEngine.*;
import static NonGui.GameUtils.GenerationsUtils.drawObjective;

/**
 * Handles the initial setup of the game state.
 * This class is responsible for creating players, assigning leaders,
 * and populating both the main deck and the objective deck.
 * * @author GeminiCollaborator
 */
public class GameSetup {

    /**
     * Initializes all players for the game session.
     * Sets up names, shuffles and assigns unique Leader cards,
     * and prepares the player objects in the global engine.
     */
    public static void initializePlayer() {
        // Step 1: Create a list of all available leaders
        List<LeaderCard> leaders = new ArrayList<>();
        leaders.add(new Zed());
        leaders.add(new Teemo());
        leaders.add(new Soraka());
        leaders.add(new Ryze());
        leaders.add(new Garen());
        leaders.add(new Darius());

        // Step 2: Shuffle for randomness
        Collections.shuffle(leaders);

        // Step 3: Initialize each player slot
        for (int i = 0; i < players.length; i++) {
            // Note: GUI Dialogs can be integrated here later.
            String playerName = "Player " + (i + 1);
            Player p = new Player(playerName);

            // Assign a unique leader from the shuffled list
            p.setOwnedLeader(leaders.get(i));

            players[i] = p;
        }
    }

    /**
     * Populates the Objective Deck with legendary monsters and bosses,
     * shuffles them, and draws the initial three objectives for the board.
     */
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

        // Draw initial 3 objectives for the center of the board
        for (int i = 0; i < 3; i++) {
            objectives[i] = drawObjective();
        }
    }

    /**
     * Fills the main Game Deck with all card types: Heroes, Items, Magic,
     * Modifiers, and Challenges. Shuffles the deck after population.
     */
    public static void initializeDeck() {
        // --- 1. HERO CARDS (2 of each) ---
        addHeroesToDeck();

        // --- 2. ITEM CARDS ---
        addItemsToDeck();

        // --- 3. MAGIC CARDS (3 of each) ---
        addMagicToDeck();

        // --- 4. MODIFIER CARDS (5 of each) ---
        addModifiersToDeck();

        // --- 5. CHALLENGE CARDS (15 cards) ---
        for (int i = 0; i < 15; i++) {
            GameEngine.deck.addToDeck(new ChallengeCard());
        }

        // Finalize deck
        Collections.shuffle(GameEngine.deck.getGameDeck());
        System.out.println("Deck initialized with " + GameEngine.deck.getGameDeck().size() + " cards.");
    }

    /**
     * Helper to add Hero cards by class.
     */
    private static void addHeroesToDeck() {
        for (int i = 0; i < 2; i++) {
            // Assassins
            GameEngine.deck.addToDeck(new Akali());
            GameEngine.deck.addToDeck(new Shaco());
            GameEngine.deck.addToDeck(new Talon());
            // Fighters
            GameEngine.deck.addToDeck(new Fiora());
            GameEngine.deck.addToDeck(new Olaf());
            GameEngine.deck.addToDeck(new Volibear());
            // Mages
            GameEngine.deck.addToDeck(new Veigar());
            GameEngine.deck.addToDeck(new Zilean());
            GameEngine.deck.addToDeck(new Zoe());
            // Marksmen
            GameEngine.deck.addToDeck(new Caitlyn());
            GameEngine.deck.addToDeck(new Ezreal());
            GameEngine.deck.addToDeck(new Jinx());
            // Support
            GameEngine.deck.addToDeck(new Bard());
            GameEngine.deck.addToDeck(new Neeko());
            GameEngine.deck.addToDeck(new TahmKench());
            // Tanks
            GameEngine.deck.addToDeck(new Braum());
            GameEngine.deck.addToDeck(new Nautilus());
            GameEngine.deck.addToDeck(new Ornn());
        }
    }

    /**
     * Helper to add Item cards (Class Change, Curse, and Buff).
     */
    private static void addItemsToDeck() {
        // Change Class (1 of each)
        GameEngine.deck.addToDeck(new BFSword());
        GameEngine.deck.addToDeck(new ForbiddenIdol());
        GameEngine.deck.addToDeck(new GiantsBelt());
        GameEngine.deck.addToDeck(new NeedlesslyLargeRod());
        GameEngine.deck.addToDeck(new RecurveBow());
        GameEngine.deck.addToDeck(new SerratedDirk());

        for (int i = 0; i < 2; i++) {
            // Curse Items
            GameEngine.deck.addToDeck(new DarkSeal());
            GameEngine.deck.addToDeck(new FrozenHeart());
            GameEngine.deck.addToDeck(new AbyssalMask());
            // Buff Items
            GameEngine.deck.addToDeck(new BlueBuff());
            GameEngine.deck.addToDeck(new TearOfTheGoddess());
        }
    }

    /**
     * Helper to add Magic cards.
     */
    private static void addMagicToDeck() {
        for (int i = 0; i < 3; i++) {
            GameEngine.deck.addToDeck(new Charm());
            GameEngine.deck.addToDeck(new BattleFury());
            GameEngine.deck.addToDeck(new FinalSpark());
            GameEngine.deck.addToDeck(new HowlingGale());
            GameEngine.deck.addToDeck(new PickACard());
        }
    }

    /**
     * Helper to add Modifier cards.
     */
    private static void addModifiersToDeck() {
        for (int i = 0; i < 5; i++) {
            GameEngine.deck.addToDeck(new Deny());
            GameEngine.deck.addToDeck(new FlashFreeze());
            GameEngine.deck.addToDeck(new ForDemacia());
            GameEngine.deck.addToDeck(new ShapedStone());
            GameEngine.deck.addToDeck(new TwinDisciplines());
        }
    }
}