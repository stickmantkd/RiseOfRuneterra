package nongui.listofcards.herocard.Mage;

import nongui.baseentity.BaseCard;
import nongui.baseentity.cards.HeroCard.HeroCard;
import nongui.baseentity.cards.MagicCard.MagicCard;
import nongui.baseentity.Player;
import nongui.baseentity.properties.UnitClass;
import javafx.scene.control.ChoiceDialog;

import java.util.ArrayList;
import java.util.List;

import static nongui.gameutils.GenerationsUtils.generateRandomCard;

/**
 * Represents the "Zoe" Hero Card.
 * <p>
 * <b>Ability: Spell Thief</b><br>
 * Requirement: Roll 6+<br>
 * Effect: Draw a card. If it's a Magic card, you can choose to play it immediately for free
 * and then draw an additional card. If it's not a Magic card, simply add it to your hand.
 * <p>
 * <i>Zoe's unpredictability makes her a nightmare for opponents who rely on steady pacing.</i>
 */
public class Zoe extends HeroCard {

    /**
     * Constructs Zoe with her base stats and sparkly flavor text.
     */
    public Zoe(){
        super(
                "Zoe",
                "More sparkles! More!",
                "Spell Thief: Roll 6+. DRAW a card. If it is a Magic card, you may play it immediately and DRAW a second card.",
                UnitClass.Mage,
                6
        );
    }

    /**
     * Executes the Spell Thief ability.
     * <p>
     * Logic Flow:
     * 1. Draw a random card (simulated via GenerationsUtils).
     * 2. Type Check: If MagicCard, prompt the player via ChoiceDialog to play or keep.
     * 3. Instant Play: If chosen, trigger playCard() immediately.
     * 4. Bonus: If the first card was Magic, draw a second card regardless of whether the first was played.
     * 5. Fallback: If not Magic, add to hand and end ability.
     * * @param player The player who activated Zoe's ability.
     */
    @Override
    public void useAbility(Player player) {
        System.out.println("🌟 " + this.getName() + " uses Spell Thief!");

        // 1. Initial Draw
        BaseCard drawnCard = generateRandomCard();
        System.out.println(player.getName() + " drew: " + drawnCard.getName());

        // 2. Type Checking and Branching Logic
        if (drawnCard instanceof MagicCard) {
            MagicCard magic = (MagicCard) drawnCard;
            System.out.println("✨ It's a Magic card! Asking to play immediately...");

            // UI Selection for immediate action
            List<String> options = new ArrayList<>();
            options.add("1 : Play it immediately (Free)");
            options.add("2 : Keep it in hand");

            ChoiceDialog<String> dialog = new ChoiceDialog<>(options.get(0), options);
            dialog.setTitle("Zoe Ability: Spell Thief");
            dialog.setHeaderText("You caught a spell: " + magic.getName());
            dialog.setContentText("Do you want to use it right now?");

            String result = dialog.showAndWait().orElse("2 : Keep it in hand");

            // 3. Handling Choice
            if (result.startsWith("1")) {
                boolean played = magic.playCard(player);
                if (!played) {
                    player.addCardToHand(drawnCard);
                    System.out.println("Could not play " + magic.getName() + ". Added to hand instead.");
                } else {
                    System.out.println("💥 " + magic.getName() + " was played successfully!");
                }
            } else {
                player.addCardToHand(drawnCard);
                System.out.println("Kept " + magic.getName() + " in hand for later.");
            }

            // 4. Bonus Draw (Triggered because the first card was a Magic card)
            BaseCard secondCard = generateRandomCard();
            player.addCardToHand(secondCard);
            System.out.println("🎁 Zoe found an extra gift: " + secondCard.getName());

        } else {
            // 🛠️ Bug-fix: Ensure non-magic cards are not lost
            player.addCardToHand(drawnCard);
            System.out.println(drawnCard.getName() + " is not a Magic card. Added to hand.");
        }

        try { gui.BoardView.refresh(); } catch (Exception e) {}
    }
}