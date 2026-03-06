package nongui.listofcards.magiccard;

import nongui.baseentity.cards.MagicCard.MagicCard;
import nongui.baseentity.Player;
import nongui.baseentity.BaseCard;
import nongui.baseentity.cards.HeroCard.HeroCard;
import nongui.baseentity.properties.UnitClass;
import nongui.gamelogic.GameEngine;
import gui.board.StatusBar;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.Alert;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Represents the "Final Spark" Magic Card.
 * <p>
 * DEMACIA!!!
 * Effect: DISCARD a card, then DESTROY a Hero card.
 */
public class FinalSpark extends MagicCard {

    /**
     * Constructs a new Final Spark card with its identity and effect text.
     */
    public FinalSpark() {
        super(
                "Final Spark",
                "DEMACIA!!!",
                "DISCARD a card, then DESTROY a Hero card."
        );
    }

    @Override
    public boolean playCard(Player player) {
        System.out.println("\n✨ " + player.getName() + " casts " + this.getName() + "!");

        if (player.getCardsInHand().isEmpty()) {
            showSimpleAlert("Spell Failed", "You have no other cards in hand to discard!");
            return false;
        }

        List<String> handOptions = player.getCardsInHand().stream()
                .map(BaseCard::getName)
                .collect(Collectors.toList());

        ChoiceDialog<String> discardDialog = new ChoiceDialog<>(handOptions.get(0), handOptions);
        discardDialog.setTitle("Final Spark: Discard Phase");
        discardDialog.setHeaderText("Choose a card to DISCARD to power up the laser");
        discardDialog.setContentText("Select card:");

        Optional<String> discardResult = discardDialog.showAndWait();
        if (discardResult.isPresent()) {
            String selectedName = discardResult.get();
            BaseCard toDiscard = player.getCardsInHand().stream()
                    .filter(c -> c.getName().equals(selectedName))
                    .findFirst().orElse(null);

            if (toDiscard != null) {
                player.getCardsInHand().remove(toDiscard);
                GameEngine.deck.discardCard(toDiscard);
                System.out.println("🗑️ " + toDiscard.getName() + " discarded.");
            }
        } else {
            return false;
        }

        List<Player> validTargets = new ArrayList<>();
        for (Player p : GameEngine.players) {
            if (p != null && !p.boardIsEmpty()) {
                validTargets.add(p);
            }
        }

        if (validTargets.isEmpty()) {
            showSimpleAlert("Final Spark", "The laser fires into the sky... (No heroes to destroy)");
            return true;
        }

        List<String> targetNames = validTargets.stream()
                .map(Player::getName)
                .collect(Collectors.toList());

        ChoiceDialog<String> playerDialog = new ChoiceDialog<>(targetNames.get(0), targetNames);
        playerDialog.setTitle("Final Spark: Target Selection");
        playerDialog.setHeaderText("Choose a player to blast their hero");

        Optional<String> targetPlayerName = playerDialog.showAndWait();
        if (targetPlayerName.isPresent()) {
            Player targetPlayer = validTargets.stream()
                    .filter(p -> p.getName().equals(targetPlayerName.get()))
                    .findFirst().orElse(null);

            List<String> heroOptions = new ArrayList<>();
            HeroCard[] targetHeroes = targetPlayer.getOwnedHero();
            for (int i = 0; i < targetHeroes.length; i++) {
                if (targetHeroes[i] != null) {
                    heroOptions.add(i + ": " + targetHeroes[i].getName());
                }
            }

            ChoiceDialog<String> heroDialog = new ChoiceDialog<>(heroOptions.get(0), heroOptions);
            heroDialog.setTitle("Final Spark: Vaporize!");
            heroDialog.setHeaderText("Select a hero to DESTROY from " + targetPlayer.getName() + "'s board");

            Optional<String> heroResult = heroDialog.showAndWait();
            if (heroResult.isPresent()) {
                int heroIdx = Integer.parseInt(heroResult.get().split(":")[0]);
                HeroCard destroyedHero = targetPlayer.getHeroCard(heroIdx);

                targetPlayer.removeHeroCard(heroIdx);
                GameEngine.deck.discardCard(destroyedHero);

                System.out.println("💥 BZZZT! " + destroyedHero.getName() + " was vaporized!");

                try {
                    gui.BoardView.refresh();
                } catch (Exception e) {}

                if (player.getOwnedLeader().getUnitClass() == UnitClass.Mage) {
                    player.drawRandomCard();
                    StatusBar.showMessage("Mage Leader: Magic used! Drawing an extra card.");
                }
                return true;
            }
        }

        return false;
    }

    private void showSimpleAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}