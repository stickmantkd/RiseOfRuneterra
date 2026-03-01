package NonGui.GameUtils;

import NonGui.BaseEntity.Cards.HeroCard.HeroCard;
import NonGui.ListOfCards.itemcard.BlueBuff;
import NonGui.ListOfCards.itemcard.SnakesEmbrace;
import NonGui.BaseEntity.Player;

import java.util.Random;

public class DiceUtils {

    private static final Random rand = new Random();

    /**
     * Roll two dice (1–6 each).
     * Returns the sum of both dice, after modifier phase.
     */
    public static int getRoll(Player player) {
        int dice1 = rand.nextInt(6) + 1;
        int dice2 = rand.nextInt(6) + 1;
        int total = dice1 + dice2;

        // ✅ Set roll before modifiers so ModifierView sees correct value
        player.setCurrentRoll(total);

        // Show GUI animation separately
        gui.DiceView.showDiceRoll(dice1, dice2);

        // Trigger modifier phase
        total = ModifierUtils.TriggerModifier(total, player);

        // Update after modifiers
        player.setCurrentRoll(total);

        return total;
    }

    /**
     * Roll two dice for hero ability activation.
     * Applies item effects (Blue Buff +2, Snake’s Embrace -2).
     * Returns true if total >= targetScore, after modifier phase.
     */
    public static boolean rollForAbility(HeroCard card, int targetScore) {
        int dice1 = rand.nextInt(6) + 1;
        int dice2 = rand.nextInt(6) + 1;
        int total = dice1 + dice2;

        if (card.getItem() instanceof BlueBuff) {
            total += 2;
        } else if (card.getItem() instanceof SnakesEmbrace) {
            total -= 2;
        }

        // Show GUI animation separately
        gui.DiceView.showAbilityRoll(dice1, dice2, total, card);

        // Apply modifiers if owner exists
        Player owner = card.getOwner();
        if (owner != null) {
            owner.setCurrentRoll(total);
            total = ModifierUtils.TriggerModifier(total, owner);
            owner.setCurrentRoll(total);
        }

        return total >= targetScore;
    }

    // Optional fallback for legacy calls
    public static int getRoll() {
        return getRoll(new Player("Dummy"));
    }
}
