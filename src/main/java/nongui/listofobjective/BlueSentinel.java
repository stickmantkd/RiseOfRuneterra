package nongui.listofobjective;

import nongui.baseentity.Objective;
import nongui.baseentity.Player;
import nongui.baseentity.cards.HeroCard.HeroCard;
import nongui.baseentity.properties.UnitClass;
import nongui.gamelogic.GameEngine;
import gui.board.StatusBar;
import javafx.scene.control.ChoiceDialog;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Represents the "Blue Sentinel" Objective Card.
 * <p>
 * The Crest of Insight grants you ultimate clarity and magical prowess.
 * Requirement: Requires at least 1 Hero and 1 Support.
 * Prize: Each time you roll for a Hero card's effect, +1 to your roll.
 * Punishment: DISCARD 2 cards.
 */
public class BlueSentinel extends Objective {

    /**
     * Constructs a new Blue Sentinel objective with its requirements and penalties.
     */
    public BlueSentinel() {
        super(
                "Blue Sentinel",
                "The Crest of Insight grants you ultimate clarity and magical prowess.",
                8,
                20
        );
        setRequirementDescription("Requires at least 1 Hero and 1 Support.");
        setPrizeDescription("8+ | Each time you roll for a Hero card's effect, +1 to your roll.");
        setPunishmentDescription("7- | DISCARD 2 cards.");
    }

    @Override
    public boolean canTry(Player player) {
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
        return heroCount >= 1 && hasSupport;
    }

    @Override
    public void grantPrize(Player player) {
        StatusBar.showMessage("Slayed Blue Sentinel! " + getPrizeDescription());
        player.addPermanentAbilityBonus(1);
    }

    @Override
    public void grantPunishment(Player player) {
        StatusBar.showMessage("Crushed by the stone golem! " + getPunishmentDescription());

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

            nongui.baseentity.BaseCard toDiscard = player.getCardsInHand().stream()
                    .filter(c -> c.getName().equals(selected))
                    .findFirst().orElse(player.getCardsInHand().get(0));

            player.getCardsInHand().remove(toDiscard);
            GameEngine.deck.discardCard(toDiscard);
        }
    }
}