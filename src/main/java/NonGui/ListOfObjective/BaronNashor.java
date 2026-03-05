package NonGui.ListOfObjective;

import NonGui.BaseEntity.Cards.HeroCard.HeroCard;
import NonGui.BaseEntity.Objective;
import NonGui.BaseEntity.Player;
import gui.board.StatusBar;

/**
 * Represents the "Baron Nashor" Objective Card.
 * <p>
 * His barony is rather small. Just large enough to accommodate only him, really.
 * Requirement: Have 3 Heroes.
 * Prize: Draw 5 cards.
 * Punishment: Sacrifice 1 Hero.
 */
public class BaronNashor extends Objective {

    /**
     * Constructs a new Baron Nashor objective with its requirements and penalties.
     */
    public BaronNashor() {
        super(
                "Baron Nashor",
                "His barony is rather small. Just large enough to accommodate only him, really.",
                10,
                12
        );
        setRequirementDescription("have 3 Heroes");
        setPrizeDescription("10+ | drawn 5 cards");
        setPunishmentDescription("9- | Sacrifice 1 Heroes");
    }

    @Override
    public boolean canTry(Player player) {
        int count = 0;
        for (HeroCard h : player.getOwnedHero()) {
            if (h != null) {
                count++;
            }
        }
        return count >= 3;
    }

    @Override
    public void grantPrize(Player player) {
        for (int i = 0; i < 5; i++) {
            player.drawRandomCard();
        }
    }

    @Override
    public void grantPunishment(Player player) {
        StatusBar.showMessage("💀 Baron! Punishment: " + getPunishmentDescription());

        javafx.application.Platform.runLater(() -> {
            HeroCard[] heroes = player.getOwnedHero();
            java.util.List<String> options = new java.util.ArrayList<>();

            for (HeroCard h : heroes) {
                if (h != null) {
                    options.add(h.getName());
                }
            }

            if (options.isEmpty()) return;

            javafx.scene.control.ChoiceDialog<String> dialog = new javafx.scene.control.ChoiceDialog<>(options.get(0), options);
            dialog.setTitle("Baron's Sacrifice");
            dialog.setHeaderText("Choose a Hero to SACRIFICE");

            java.util.Optional<String> result = dialog.showAndWait();
            String selectedName = result.orElse(options.get(0));

            for (int i = 0; i < heroes.length; i++) {
                if (heroes[i] != null && heroes[i].getName().equals(selectedName)) {
                    if (heroes[i].getItem() != null) {
                        heroes[i].getItem().onUnEquip(heroes[i]);
                    }

                    NonGui.GameLogic.GameEngine.deck.discardCard(heroes[i]);
                    StatusBar.showMessage(heroes[i].getName() + " was consumed by the Baron...");

                    heroes[i] = null;
                    break;
                }
            }
            try {
                gui.BoardView.refresh();
            } catch (Exception e) {}
        });
    }
}