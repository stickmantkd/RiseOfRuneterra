package NonGui.GameUtils;

import NonGui.BaseEntity.Cards.HeroCard.HeroCard;
import NonGui.BaseEntity.Properties.UnitClass;
import NonGui.BaseEntity.Player;
import NonGui.ListOfCards.itemcard.BuffItem.BlueBuff;
import NonGui.ListOfCards.itemcard.BuffItem.TearOfTheGoddess;
import NonGui.ListOfCards.itemcard.CurseItem.DarkSeal;
import NonGui.ListOfCards.itemcard.CurseItem.FrozenHeart;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class DiceUtils {

    private static final Random rand = new Random();

    // ── Public rolls ─────────────────────────────────────────────────────────

    public static int getRoll(Player player) {
        int dice1 = rand.nextInt(6) + 1;
        int dice2 = rand.nextInt(6) + 1;
        int base  = dice1 + dice2;

        // Collect bonus terms for display
        List<String> bonusTerms = new ArrayList<>();
        int bonusTotal = 0;

        int rollBonus = player.getRollBonus();
        if (rollBonus != 0) {
            bonusTerms.add(signed(rollBonus) + " (Roll Bonus)");
            bonusTotal += rollBonus;
        }

        if (player.getOwnedLeader().getUnitClass() == UnitClass.Maskman) {
            bonusTerms.add("+1 (Maskman)");
            bonusTotal += 1;
        }

        int total = base + bonusTotal;
        player.setCurrentRoll(total);

        // Show animation with full breakdown
        String breakdown = buildBreakdown(dice1, dice2, bonusTerms, total);
        gui.DiceView.showDiceRoll(dice1, dice2, breakdown);

        // Modifier phase (may adjust total further)
        total = ModifierUtils.TriggerModifier(total, player);
        player.setCurrentRoll(total);

        return total;
    }

    public static int rollForChallenge(Player player) {
        int dice1 = 6;
        int dice2 = 6;
        int base  = dice1 + dice2;

        List<String> bonusTerms = new ArrayList<>();
        int bonusTotal = 0;

        int rollBonus = player.getRollBonus();
        if (rollBonus != 0) {
            // NOTE: getRollBonus() is applied twice in the original logic — preserved here.
            bonusTerms.add(signed(rollBonus * 2) + " (Roll Bonus ×2)");
            bonusTotal += rollBonus * 2;
        }

        int challengeBonus = player.getPermanentChallengeBonus();
        if (challengeBonus != 0) {
            bonusTerms.add(signed(challengeBonus) + " (Challenge Bonus)");
            bonusTotal += challengeBonus;
        }

        if (player.getOwnedLeader().getUnitClass() == UnitClass.Fighter) {
            bonusTerms.add("+2 (Fighter)");
            bonusTotal += 2;
        }

        int total = base + bonusTotal;
        player.setCurrentRoll(total);

        String breakdown = buildBreakdown(dice1, dice2, bonusTerms, total);
        gui.DiceView.showDiceRoll(dice1, dice2, breakdown);

        total = ModifierUtils.TriggerModifier(total, player);
        player.setCurrentRoll(total);

        return total;
    }

    public static boolean rollForAbility(HeroCard card, int targetScore) {
        int dice1 = rand.nextInt(6) + 1;
        int dice2 = rand.nextInt(6) + 1;
        int base  = dice1 + dice2;

        List<String> bonusTerms = new ArrayList<>();
        int bonusTotal = 0;

        Player owner = card.getOwner();
        if (owner != null) {
            int rollBonus = owner.getRollBonus();
            if (rollBonus != 0) {
                bonusTerms.add(signed(rollBonus) + " (Roll Bonus)");
                bonusTotal += rollBonus;
            }

            int abilityBonus = owner.getPermanentAbilityBonus();
            if (abilityBonus != 0) {
                bonusTerms.add(signed(abilityBonus) + " (Ability Bonus)");
                bonusTotal += abilityBonus;
            }

            if (owner.getOwnedLeader().getUnitClass() == UnitClass.Support) {
                bonusTerms.add("+1 (Support)");
                bonusTotal += 1;
            }
        }

        if (card.getItem() instanceof BlueBuff) {
            bonusTerms.add("+2 (Blue Buff)");
            bonusTotal += 2;
        } else if (card.getItem() instanceof FrozenHeart) {
            bonusTerms.add("-2 (Snake's Embrace)");
            bonusTotal -= 2;
        }

        int total = base + bonusTotal;

        String breakdown = buildBreakdown(dice1, dice2, bonusTerms, total);
        gui.DiceView.showAbilityRoll(dice1, dice2, breakdown, total, targetScore);

        if (owner != null) {
            owner.setCurrentRoll(total);
            total = ModifierUtils.TriggerModifier(total, owner);
            owner.setCurrentRoll(total);
        }

        boolean success = total >= targetScore;

        if (owner != null) {
            if (!success && card.getItem() instanceof TearOfTheGoddess) {
                System.out.println("💧 Tear of the Goddess: Drawing a card...");
                owner.DrawRandomCard();
            } else if (success && card.getItem() instanceof DarkSeal) {
                System.out.println("🪙 Cursed Doubloon: Success! Now pay the price...");
                handleCursedDiscard(owner);
            }
        }

        try { gui.BoardView.refresh(); } catch (Exception e) {}
        return success;
    }

    // ── Helpers ───────────────────────────────────────────────────────────────

    /**
     * Builds a human-readable breakdown string shown in the DiceView overlay.
     * Example: "4 + 3  +1 (Maskman)  = 8"
     */
    private static String buildBreakdown(int d1, int d2,
                                         List<String> bonusTerms, int total) {
        StringBuilder sb = new StringBuilder();
        sb.append(d1).append(" + ").append(d2);
        for (String term : bonusTerms) {
            sb.append("  ").append(term);
        }
        sb.append("  =  ").append(total);
        return sb.toString();
    }

    private static String signed(int value) {
        return value >= 0 ? "+" + value : String.valueOf(value);
    }

    private static void handleCursedDiscard(Player player) {
        if (player.getCardsInHand().isEmpty()) return;

        java.util.List<String> options = player.getCardsInHand().stream()
                .map(c -> c.getName())
                .collect(java.util.stream.Collectors.toList());

        javafx.application.Platform.runLater(() -> {
            javafx.scene.control.ChoiceDialog<String> dialog =
                    new javafx.scene.control.ChoiceDialog<>(options.get(0), options);
            dialog.setTitle("Cursed Doubloon");
            dialog.setHeaderText(player.getName() + ", choose a card to DISCARD (Curse Penalty)");
            dialog.setContentText("Select card:");

            java.util.Optional<String> result = dialog.showAndWait();
            String selectedName = result.orElse(options.get(0));

            NonGui.BaseEntity.BaseCard toDiscard = player.getCardsInHand().stream()
                    .filter(c -> c.getName().equals(selectedName))
                    .findFirst().orElse(player.getCardsInHand().get(0));

            player.getCardsInHand().remove(toDiscard);
            NonGui.GameLogic.GameEngine.deck.discardCard(toDiscard);
            System.out.println("🗑️ Curse claimed: " + toDiscard.getName());

            try { gui.BoardView.refresh(); } catch (Exception e) {}
        });
    }

    // Legacy fallback
    public static int getRoll() {
        return getRoll(new Player("Dummy"));
    }
}