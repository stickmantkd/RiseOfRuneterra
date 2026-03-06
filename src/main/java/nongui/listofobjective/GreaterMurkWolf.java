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
 * Represents the "Greater Murk Wolf" Objective Card.
 * <p>
 * A vicious, two-headed apex predator stalking the jungle.
 * Requirement: Requires at least 1 Hero and 1 Mage.
 * Prize: Each time you DRAW a Magic card, you may play it immediately.
 * Punishment: DISCARD 2 cards.
 */
public class GreaterMurkWolf extends Objective {

    /**
     * Constructs a new Greater Murk Wolf objective with its requirements and penalties.
     */
    public GreaterMurkWolf() {
        super(
                "Greater Murk Wolf",
                "A vicious, two-headed apex predator stalking the jungle.",
                8,
                12
        );
        setRequirementDescription("Requires at least 1 Hero and 1 Mage.");
        setPrizeDescription("8+ | Each time you DRAW a Magic card, you may play it immediately.");
        setPunishmentDescription("7- | DISCARD 2 cards.");
    }

    @Override
    public boolean canTry(Player player) {
        boolean hasMage = false;
        int heroCount = 0;

        for (HeroCard hero : player.getOwnedHero()) {
            if (hero != null) {
                heroCount++;
                if (hero.getUnitClass() == UnitClass.Mage) {
                    hasMage = true;
                }
            }
        }
        return heroCount >= 1 && hasMage;
    }

    @Override
    public void grantPrize(Player player) {
        StatusBar.showMessage("Slayed Greater Murk Wolf! Prize: " + getPrizeDescription());
        player.setCanPlayMagicInstantly(true);
    }

    @Override
    public void grantPunishment(Player player) {
        StatusBar.showMessage("The Wolf bites hard! Punishment: " + getPunishmentDescription());

        for (int i = 0; i < 2; i++) {
            if (player.getCardsInHand().isEmpty()) break;

            List<String> options = player.getCardsInHand().stream()
                    .map(c -> c.getName())
                    .collect(Collectors.toList());

            ChoiceDialog<String> dialog = new ChoiceDialog<>(options.get(0), options);
            dialog.setTitle("Murk Wolf's Punishment");
            dialog.setHeaderText("Choose card to DISCARD (" + (i + 1) + "/2)");

            Optional<String> result = dialog.showAndWait();
            String selected = result.orElse(options.get(0));

            nongui.baseentity.BaseCard toDiscard = player.getCardsInHand().stream()
                    .filter(c -> c.getName().equals(selected))
                    .findFirst()
                    .orElse(player.getCardsInHand().get(0));

            player.getCardsInHand().remove(toDiscard);
            GameEngine.deck.discardCard(toDiscard);
        }
    }
}