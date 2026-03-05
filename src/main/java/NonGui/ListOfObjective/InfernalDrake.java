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

/**
 * Represents the "Infernal Drake" Objective Card.
 * <p>
 * The flames of war fuel your power and destruction.
 * Requirement: Requires at least 1 Hero and 1 Fighter.
 * Prize: Each time you roll for a Challenge card, +1 to your roll.
 * Punishment: DISCARD 2 cards.
 */
public class InfernalDrake extends Objective {

    /**
     * Constructs a new Infernal Drake objective with its requirements and penalties.
     */
    public InfernalDrake() {
        super(
                "Infernal Drake",
                "The flames of war fuel your power and destruction.",
                8,
                12
        );
        setRequirementDescription("Requires at least 1 Hero and 1 Fighter.");
        setPrizeDescription("8+ | Each time you roll for a Challenge card, +1 to your roll.");
        setPunishmentDescription("7- | DISCARD 2 cards.");
    }

    @Override
    public boolean canTry(Player player) {
        boolean hasFighter = false;
        int heroCount = 0;

        for (HeroCard hero : player.getOwnedHero()) {
            if (hero != null) {
                heroCount++;
                if (hero.getUnitClass() == UnitClass.Fighter) {
                    hasFighter = true;
                }
            }
        }
        return heroCount >= 1 && hasFighter;
    }

    @Override
    public void grantPrize(Player player) {
        StatusBar.showMessage("Slayed Infernal Drake! Prize: " + getPrizeDescription());
        player.addPermanentChallengeBonus(1);
    }

    @Override
    public void grantPunishment(Player player) {
        StatusBar.showMessage("Scorched by the Drake! Punishment: " + getPunishmentDescription());

        for (int i = 0; i < 2; i++) {
            if (player.getCardsInHand().isEmpty()) break;

            List<String> options = player.getCardsInHand().stream()
                    .map(c -> c.getName())
                    .collect(Collectors.toList());

            ChoiceDialog<String> dialog = new ChoiceDialog<>(options.get(0), options);
            dialog.setTitle("Infernal Drake's Punishment");
            dialog.setHeaderText("Choose card to DISCARD (" + (i + 1) + "/2)");

            Optional<String> result = dialog.showAndWait();
            String selected = result.orElse(options.get(0));

            NonGui.BaseEntity.BaseCard toDiscard = player.getCardsInHand().stream()
                    .filter(c -> c.getName().equals(selected))
                    .findFirst()
                    .orElse(player.getCardsInHand().get(0));

            player.getCardsInHand().remove(toDiscard);
            GameEngine.deck.discardCard(toDiscard);
        }
    }
}