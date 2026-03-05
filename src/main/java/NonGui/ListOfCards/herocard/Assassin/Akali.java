package NonGui.ListOfCards.herocard.Assassin;

import NonGui.BaseEntity.BaseCard;
import NonGui.BaseEntity.Cards.HeroCard.HeroCard;
import NonGui.BaseEntity.Player;
import NonGui.BaseEntity.Properties.UnitClass;

import java.util.Random;

import static NonGui.GameLogic.GameEngine.players;

/**
 * Represents the "Akali" Hero Card.
 * <p>
 * <b>Ability: Perfect Execution</b><br>
 * Requirement: Roll 8+<br>
 * Effect: Checks all other players. If they have at least one Assassin-class hero on their board,
 * Akali steals one random card from their hand and adds it to the owner's hand.
 */
public class Akali extends HeroCard {

    /**
     * Constructs the Akali card with her base stats and description.
     */
    public Akali(){
        super(
                "Akali",
                "Fear the assassin with no master.",
                "Perfect Execution: Roll 8+. Pull a card from each player with an Assassin.",
                UnitClass.Assassin,
                8
        );
    }

    /**
     * Executes Akali's ability.
     * Iterates through all opponents, identifies those with Assassin heroes,
     * and performs a random hand-steal.
     * * @param player The player who activated Akali's ability.
     */
    @Override
    public void useAbility(Player player) {
        System.out.println(this.getName() + " uses their ability! (Pull cards from players with an Assassin)");

        boolean stoleAnyCard = false;
        Random rand = new Random();

        // 1. Iterate through all players in the game
        for (Player targetPlayer : players) {
            // Skip the owner
            if (targetPlayer == player) {
                continue;
            }

            // 2. Check if this target player has any Assassin hero on their board
            boolean hasAssassin = false;
            for (HeroCard hero : targetPlayer.getOwnedHero()) {
                if (hero != null && hero.getUnitClass() == UnitClass.Assassin) {
                    hasAssassin = true;
                    break;
                }
            }

            // 3. Execution: If target has Assassin and cards to steal
            if (hasAssassin) {
                if (targetPlayer.handIsEmpty()) {
                    System.out.println(targetPlayer.getName() + " has an Assassin, but no cards in hand to steal!");
                } else {
                    // Randomly select a card index safely
                    int handSize = targetPlayer.getCardsInHand().size();
                    int randomCardIndex = rand.nextInt(handSize);

                    // Transfer the card
                    BaseCard stolenCard = targetPlayer.getCardInHand(randomCardIndex);
                    targetPlayer.getCardsInHand().remove(stolenCard); // Remove from victim
                    player.addCardToHand(stolenCard);               // Add to owner

                    System.out.println("SUCCESS! " + player.getName() + " pulled " + stolenCard.getName() + " from " + targetPlayer.getName() + "'s hand!");
                    stoleAnyCard = true;
                }
            }
        }

        if (!stoleAnyCard) {
            System.out.println("No one else had an Assassin in their party, or they had no cards. Nothing happened.");
        }
    }
}