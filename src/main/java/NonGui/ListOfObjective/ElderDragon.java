package NonGui.ListOfObjective;

import NonGui.BaseEntity.Objective;
import NonGui.BaseEntity.Player;
import NonGui.BaseEntity.Cards.HeroCard.HeroCard;
import NonGui.GameLogic.GameEngine;
import gui.board.StatusBar;

/**
 * Represents the "Elder Dragon" Objective Card.
 * <p>
 * The Aspect of Dragon transcends all, granting absolute authority.
 * Requirement: Requires at least 4 Hero cards on your board.
 * Prize: MAX Action Points +1 (Permanent).
 * Punishment: Sacrifice (DISCARD) 1 of your Hero cards.
 */
public class ElderDragon extends Objective {

    /**
     * Constructs a new Elder Dragon objective with its requirements and penalties.
     */
    public ElderDragon() {
        super(
                "Elder Dragon",
                "The Aspect of Dragon transcends all, granting absolute authority.",
                10,
                12
        );
        setRequirementDescription("Requires at least 4 Hero cards on your board.");
        setPrizeDescription("11+ | MAX Action Points +1 (Permanent).");
        setPunishmentDescription("10- | Sacrifice (DISCARD) 1 of your Hero cards.");
    }

    @Override
    public boolean canTry(Player player) {
        int count = 0;
        for (HeroCard h : player.getOwnedHero()) {
            if (h != null) {
                count++;
            }
        }
        return count >= 4;
    }

    @Override
    public void grantPrize(Player player) {
        StatusBar.showMessage("🐲 THE DRAGON SOUL IS YOURS! Prize: " + getPrizeDescription());

        player.setMaxActionPoint(player.getMaxActionPoint() + 1);
        player.setActionPoint(player.getMaxActionPoint());
    }

    @Override
    public void grantPunishment(Player player) {
        StatusBar.showMessage("💀 ELDER DRAGON'S FURY! Punishment: " + getPunishmentDescription());

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
            dialog.setTitle("Elder Dragon's Sacrifice");
            dialog.setHeaderText("Choose a Hero to SACRIFICE");

            java.util.Optional<String> result = dialog.showAndWait();
            String selectedName = result.orElse(options.get(0));

            for (int i = 0; i < heroes.length; i++) {
                if (heroes[i] != null && heroes[i].getName().equals(selectedName)) {
                    if (heroes[i].getItem() != null) {
                        heroes[i].getItem().onUnEquip(heroes[i]);
                    }

                    GameEngine.deck.discardCard(heroes[i]);
                    StatusBar.showMessage(heroes[i].getName() + " was consumed by the Dragon...");

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