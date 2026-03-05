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

public class RiftHerald extends Objective {
    public RiftHerald() {
        super("Rift Herald", "Shelly is ready to charge and shatter their defenses.", 8, 12);
        setRequirementDescription("Requires at least 1 Hero and 1 Assassin.");
        setPrizeDescription("8+ |  cards you play cannot be challenged.");
        setPunishmentDescription("7- | DISCARD 2 cards.");
    }

    @Override
    public boolean canTry(Player player) {
        boolean hasAssassin = false;
        int heroCount = 0;

        for (HeroCard hero : player.getOwnedHero()) {
            if (hero != null) {
                heroCount++;
                if (hero.getUnitClass() == UnitClass.Assassin) {
                    hasAssassin = true;
                }
            }
        }
        return heroCount >= 1 && hasAssassin;
    }

    @Override
    public void grantPrize(Player player) {
        StatusBar.showMessage("Slayed Rift Herald! Shelly protects your items now.");
        player.setItemUnchallengeable(true);
    }

    @Override
    public void grantPunishment(Player player) {
        StatusBar.showMessage("Shelly headbutts you! Punishment: " + getPunishmentDescription());

        // Logic การทิ้งการ์ด 2 ใบมาตรฐาน
        for (int i = 0; i < 2; i++) {
            if (player.getCardsInHand().isEmpty()) break;

            List<String> options = player.getCardsInHand().stream()
                    .map(c -> c.getName()).collect(Collectors.toList());

            ChoiceDialog<String> dialog = new ChoiceDialog<>(options.get(0), options);
            dialog.setTitle("Rift Herald's Punishment");
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