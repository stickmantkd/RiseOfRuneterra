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

public class FreljordianYeti extends Objective {
    public FreljordianYeti() {
        super("Freljordian Yeti",
                "A massive, relentless beast from the Howling Abyss.", 8, 12);
        setRequirementDescription("7- | Requires at least 1 Hero and 1 Marksman.");
        setPrizeDescription("8+ | Each time you DRAW an Item card, you may play it immediately.");
        setPunishmentDescription("DISCARD 2 cards.");
    }

    @Override
    public boolean canTry(Player player) {
        boolean hasMarksman = false;
        int heroCount = 0;

        for (HeroCard hero : player.getOwnedHero()) {
            if (hero != null) {
                heroCount++;
                if (hero.getUnitClass() == UnitClass.Maskman) {
                    hasMarksman = true;
                }
            }
        }
        return heroCount >= 1 && hasMarksman;
    }

    @Override
    public void grantPrize(Player player) {
        StatusBar.showMessage("Slayed Freljordian Yeti! Prize: " + getPrizeDescription());
        player.setCanPlayItemInstantly(true); // ปลดล็อคความสามารถพิเศษ
    }

    @Override
    public void grantPunishment(Player player) {
        StatusBar.showMessage("The Yeti stomps! Punishment: " + getPunishmentDescription());

        // ใช้ Logic การทิ้งการ์ดเหมือน Blue Sentinel (ควรแยกเป็น Utility Method ในภายหลัง)
        for (int i = 0; i < 2; i++) {
            if (player.getCardsInHand().isEmpty()) break;

            List<String> options = player.getCardsInHand().stream()
                    .map(c -> c.getName()).collect(Collectors.toList());

            ChoiceDialog<String> dialog = new ChoiceDialog<>(options.get(0), options);
            dialog.setTitle("Yeti's Punishment");
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