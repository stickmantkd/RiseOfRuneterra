package NonGui.GameUtils;

import NonGui.BaseEntity.Cards.HeroCard.HeroCard;
import NonGui.BaseEntity.Properties.UnitClass;
import NonGui.ListOfCards.itemcard.BlueBuff;
import NonGui.ListOfCards.itemcard.CursedDoubloon;
import NonGui.ListOfCards.itemcard.SnakesEmbrace;
import NonGui.BaseEntity.Player;
import NonGui.ListOfCards.itemcard.TearOfTheGoddess;
import gui.board.StatusBar;

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
        int total = dice1 + dice2 + player.getRollBonus();

        if(player.getOwnedLeader().getUnitClass() == UnitClass.Maskman){
            total += 1;
        }

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


    public static int rollForChallenge(Player player) {
        int dice1 = 6;
        int dice2 = 6;
        int total = dice1 + dice2 + player.getRollBonus();

        // ✅ Set roll before modifiers so ModifierView sees correct value
        player.setCurrentRoll(total);

        // บวกโบนัสถาวรจากมังกรไฟ
        total += player.getPermanentChallengeBonus();
        if(player.getOwnedLeader().getUnitClass() == UnitClass.Fighter){
            total += 2;
        }

        // บวกโบนัสชั่วคราวจาก Ornn/Elixir
        total += player.getRollBonus();
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
    /**
     * Roll two dice for hero ability activation.
     * Applies Ornn's bonus, item effects, and modifiers.
     */
    public static boolean rollForAbility(HeroCard card, int targetScore) {
        int dice1 = rand.nextInt(6) + 1;
        int dice2 = rand.nextInt(6) + 1;
        int total = dice1 + dice2;

        // 🔨 1. เพิ่มโบนัสจาก Ornn (ถ้ามีเจ้าของ)
        Player owner = card.getOwner();
        if (owner != null) {
            total += owner.getRollBonus();
            total += owner.getPermanentAbilityBonus();// 🔵 โบนัสถาวรจาก Blue Sentinel!
            if(owner.getOwnedLeader().getUnitClass() == UnitClass.Support){
                total += 1;
            }
        }

        // 2. คำนวณผลจากไอเทมสวมใส่
        if (card.getItem() instanceof BlueBuff) {
            total += 2;
        } else if (card.getItem() instanceof SnakesEmbrace) {
            total -= 2;
        }

        // Show GUI animation
        gui.DiceView.showAbilityRoll(dice1, dice2, total, card);

        // 3. เข้าสู่ช่วงการใช้การ์ด Modifier (ถ้ามีเจ้าของ)
        if (owner != null) {
            owner.setCurrentRoll(total);
            total = ModifierUtils.TriggerModifier(total, owner);
            owner.setCurrentRoll(total);
        }

        boolean success = total >= targetScore;

        if (owner != null) {
            // 💧 [TEAR OF THE GODDESS] - ถ้าทอยไม่ผ่าน จั่ว 1
            if (!success && card.getItem() instanceof TearOfTheGoddess) {
                System.out.println("💧 Tear of the Goddess: Drawing a card...");
                owner.DrawRandomCard();
            }

            // 🪙 [CURSED DOUBLOON] - ถ้าทอยผ่าน ต้องทิ้ง 1
            else if (success && card.getItem() instanceof CursedDoubloon) {
                System.out.println("🪙 Cursed Doubloon: Success! Now pay the price...");
                handleCursedDiscard(owner);
            }
        }

        try { gui.BoardView.refresh(); } catch (Exception e) {}
        return success;
    }

    private static void handleCursedDiscard(Player player) {
        if (player.getCardsInHand().isEmpty()) return;

        // รวบรวมรายชื่อการ์ดในมือ
        java.util.List<String> options = player.getCardsInHand().stream()
                .map(c -> c.getName())
                .collect(java.util.stream.Collectors.toList());

        javafx.application.Platform.runLater(() -> {
            javafx.scene.control.ChoiceDialog<String> dialog = new javafx.scene.control.ChoiceDialog<>(options.get(0), options);
            dialog.setTitle("Cursed Doubloon");
            dialog.setHeaderText(player.getName() + ", choose a card to DISCARD (Curse Penalty)");
            dialog.setContentText("Select card:");

            java.util.Optional<String> result = dialog.showAndWait();

            // ถ้าไม่เลือก หรือกดยกเลิก บังคับทิ้งใบแรก (ตามกฎต้องทิ้ง)
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

    // Optional fallback for legacy calls
    public static int getRoll() {
        return getRoll(new Player("Dummy"));
    }
}
