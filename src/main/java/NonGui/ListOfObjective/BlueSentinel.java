package NonGui.ListOfObjective;

import NonGui.BaseEntity.Objective;
import NonGui.BaseEntity.Player;
import NonGui.BaseEntity.Cards.HeroCard.HeroCard;
import NonGui.BaseEntity.Properties.UnitClass;
import NonGui.GameLogic.GameEngine;
import gui.board.StatusBar;
import javafx.scene.control.ChoiceDialog;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class BlueSentinel extends Objective {
    public BlueSentinel() {
        super("Blue Sentinel",
                "The Crest of Insight grants you ultimate clarity and magical prowess.",
                8, 12);
        setRequirementDescription("Requires at least 1 Hero and 1 Support.");
        setPrizeDescription("8+ | Each time you roll for a Hero card's effect, +1 to your roll.");
        setPunishmentDescription("7- | DISCARD 2 cards.");
    }

    @Override
    public boolean canTry(Player player) {
        // เช็คว่ามี Support อย่างน้อย 1 ตัวบนบอร์ดไหม
        boolean hasSupport = false;
        int heroCount = 0;

        for (HeroCard hero : player.getOwnedHero()) {
            if (hero != null) {
                heroCount++;
                if (hero.getUnitClass() == UnitClass.Support) {
                    hasSupport = true;
                }
            }
        }
        // ต้องมีฮีโร่อย่างน้อย 1 ตัว และหนึ่งในนั้นต้องเป็น Support
        return heroCount >= 1 && hasSupport;
    }

    @Override
    public void grantPrize(Player player) {
        StatusBar.showMessage("Slayed Blue Sentinel! " + getPrizeDescription());
        // มอบโบนัสถาวร +1 สำหรับการทอยสกิลฮีโร่
        player.addPermanentAbilityBonus(1);
    }

    @Override
    public void grantPunishment(Player player) {
        StatusBar.showMessage("Crushed by the stone golem! " + getPunishmentDescription());

        // บังคับทิ้งการ์ด 2 ใบ (ใช้ Loop เรียก ChoiceDialog)
        for (int i = 0; i < 2; i++) {
            if (player.getCardsInHand().isEmpty()) break;

            List<String> options = player.getCardsInHand().stream()
                    .map(c -> c.getName())
                    .collect(Collectors.toList());

            ChoiceDialog<String> dialog = new ChoiceDialog<>(options.get(0), options);
            dialog.setTitle("Blue Sentinel's Punishment");
            dialog.setHeaderText("Choose card to DISCARD (" + (i + 1) + "/2)");

            Optional<String> result = dialog.showAndWait();
            String selected = result.orElse(options.get(0));

            NonGui.BaseEntity.BaseCard toDiscard = player.getCardsInHand().stream()
                    .filter(c -> c.getName().equals(selected))
                    .findFirst().orElse(player.getCardsInHand().get(0));

            player.getCardsInHand().remove(toDiscard);
            GameEngine.deck.discardCard(toDiscard);
        }
    }
}