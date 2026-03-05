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

public class RedBrambleback extends Objective {
    public RedBrambleback() {
        super("Red Brambleback",
                "The Crest of Cinders empowers your strikes with burning fury.", 8, 12);
        setRequirementDescription("7- | Requires at least 1 Hero and 1 Tank.");
        setPrizeDescription("8+ | Each time you DRAW a Modifier card, you may reveal it and DRAW a second card.");
        setPunishmentDescription("DISCARD 2 cards.");
    }

    @Override
    public boolean canTry(Player player) {
        boolean hasTank = false;
        int heroCount = 0;

        for (HeroCard hero : player.getOwnedHero()) {
            if (hero != null) {
                heroCount++;
                if (hero.getUnitClass() == UnitClass.Tank) {
                    hasTank = true;
                }
            }
        }
        return heroCount >= 1 && hasTank;
    }

    @Override
    public void grantPrize(Player player) {
        StatusBar.showMessage("Slayed Red Brambleback! Prize: " + getPrizeDescription());
        player.setHasRedBuff(true);
    }

    @Override
    public void grantPunishment(Player player) {
        StatusBar.showMessage("Burned by cinders! Punishment: " + getPunishmentDescription());

        // Logic การทิ้งการ์ด 2 ใบมาตรฐาน
        for (int i = 0; i < 2; i++) {
            if (player.getCardsInHand().isEmpty()) break;

            List<String> options = player.getCardsInHand().stream()
                    .map(c -> c.getName()).collect(Collectors.toList());

            ChoiceDialog<String> dialog = new ChoiceDialog<>(options.get(0), options);
            dialog.setTitle("Red Buff's Punishment");
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