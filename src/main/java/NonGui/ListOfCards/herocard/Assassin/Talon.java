package NonGui.ListOfCards.herocard.Assassin;

import NonGui.BaseEntity.BaseCard;
import NonGui.BaseEntity.Cards.HeroCard.HeroCard;
import NonGui.BaseEntity.Player;
import NonGui.BaseEntity.Properties.UnitClass;
import NonGui.GameLogic.GameChoice;
import javafx.scene.control.ChoiceDialog;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static NonGui.GameLogic.GameEngine.players;

/**
 * Represents the "Talon" Hero Card.
 * <p>
 * <b>Ability: Cutthroat</b><br>
 * Requirement: Roll 6+<br>
 * Effect: Steals up to 2 random cards from a selected opponent's hand.
 * If 2 cards are stolen, the player must choose one to discard, keeping the other.
 */
public class Talon extends HeroCard {

    /**
     * Constructs Talon with his base stats and lethal description.
     */
    public Talon(){
        super(
                "Talon",
                "Live by the blade, die by the blade.",
                "Cutthroat: Roll 6+. Pull up to 2 cards and DISCARD 1",
                UnitClass.Assassin,
                6
        );
    }

    /**
     * Executes Talon's ability to raid an opponent's hand.
     * <p>
     * Logic Flow:
     * 1. Target selection (Opponent with cards).
     * 2. Randomly pull up to 2 cards.
     * 3. Interaction: If 2 cards pulled, prompt user to discard one via GUI.
     * 4. Resolution: Add remaining card(s) to owner's hand.
     * * @param player The player who activated Talon's ability.
     */
    @Override
    public void useAbility(Player player) {
        System.out.println(this.getName() + " uses their ability! (Pull up to 2 cards and DISCARD 1)");

        // 1. Identify valid targets (Opponents with at least 1 card)
        ArrayList<Player> validTargetsList = new ArrayList<>();
        for (Player p : players) {
            if (p != player && !p.handIsEmpty()) {
                validTargetsList.add(p);
            }
        }

        if (validTargetsList.isEmpty()) {
            System.out.println("No other players have cards in hand to pull!");
            return;
        }

        Player[] validTargetsArray = validTargetsList.toArray(new Player[0]);

        // 2. Select Target Player
        System.out.println(player.getName() + ", choose a player to pull cards from:");
        int targetIndex = GameChoice.selectPlayer(validTargetsArray);

        // Fail-safe for UI cancellation
        if (targetIndex == -1) {
            System.out.println("Action canceled.");
            return;
        }

        Player targetPlayer = validTargetsArray[targetIndex];

        // 3. Calculate and pull cards (Max 2)
        int pullCount = Math.min(2, targetPlayer.getCardsInHand().size());
        Random rand = new Random();
        ArrayList<BaseCard> pulledCards = new ArrayList<>();

        for (int i = 0; i < pullCount; i++) {
            int handSize = targetPlayer.getCardsInHand().size();
            int randomCardIndex = rand.nextInt(handSize);

            // Pull and remove card from victim immediately to avoid duplicate pulling
            BaseCard pulledCard = targetPlayer.getCardsInHand().remove(randomCardIndex);
            pulledCards.add(pulledCard);
        }

        System.out.println(player.getName() + " pulled " + pullCount + " cards from " + targetPlayer.getName() + ".");

        // 4. Discard Phase: Required if 2 cards were stolen
        if (pulledCards.size() > 1) {
            List<String> options = new ArrayList<>();
            for (int i = 0; i < pulledCards.size(); i++) {
                options.add((i + 1) + " : " + pulledCards.get(i).getName());
            }

            ChoiceDialog<String> dialog = new ChoiceDialog<>(options.get(0), options);
            dialog.setTitle("Talon Ability: Cutthroat");
            dialog.setHeaderText("You pulled " + pulledCards.size() + " cards. Choose one to DISCARD:");
            dialog.setContentText("Select card:");

            String result = dialog.showAndWait().orElse(null);
            int discardIndex = 0; // Default to first card if dialog is closed/canceled

            if (result != null) {
                try {
                    discardIndex = Integer.parseInt(result.split(" : ")[0]) - 1;
                } catch (Exception e) {
                    discardIndex = 0;
                }
            }

            // 5. Apply Discard
            BaseCard discardedCard = pulledCards.remove(discardIndex);
            System.out.println("🗑️ " + discardedCard.getName() + " has been DISCARDED.");
            // Optional: Move discardedCard to a global discard pile
            // GameEngine.deck.discardCard(discardedCard);
        }

        // 6. Resolution: Remaining cards enter the owner's hand
        for (BaseCard card : pulledCards) {
            player.addCardToHand(card);
            System.out.println("🃏 " + card.getName() + " added to " + player.getName() + "'s hand.");
        }
    }
}