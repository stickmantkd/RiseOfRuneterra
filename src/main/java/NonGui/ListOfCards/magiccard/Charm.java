package NonGui.ListOfCards.magiccard;

import NonGui.BaseEntity.Cards.MagicCard.MagicCard;
import NonGui.BaseEntity.Player;
import NonGui.BaseEntity.BaseCard;
import NonGui.BaseEntity.Cards.HeroCard.HeroCard;
import NonGui.BaseEntity.Properties.UnitClass;
import NonGui.GameLogic.GameEngine;
import gui.board.StatusBar;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.Alert;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Represents the "Charm" Magic Card.
 * <p>
 * Don't you trust me?
 * Effect: DISCARD 2 cards, then STEAL a Hero card.
 */
public class Charm extends MagicCard {

    /**
     * Constructs a new Charm card with its identity and effect text.
     */
    public Charm() {
        super(
                "Charm",
                "Don't you trust me?",
                "DISCARD 2 cards, then STEAL a Hero card."
        );
    }

    @Override
    public boolean playCard(Player player) {
        System.out.println("\n💖 " + player.getName() + " casts " + this.getName() + "!");

        if (player.getCardsInHand().size() < 2) {
            showSimpleAlert("Spell Failed", "You need at least 2 other cards in hand to discard!");
            return false;
        }

        boolean hasSpace = false;
        for (HeroCard h : player.getOwnedHero()) {
            if (h == null) {
                hasSpace = true;
                break;
            }
        }

        if (!hasSpace) {
            showSimpleAlert("Spell Failed", "Your party is full! No room for a stolen hero.");
            return false;
        }

        List<Player> validTargets = new ArrayList<>();
        for (Player p : GameEngine.players) {
            if (p != player && !p.boardIsEmpty()) {
                validTargets.add(p);
            }
        }

        if (validTargets.isEmpty()) {
            showSimpleAlert("Spell Failed", "No other players have any Heroes to steal!");
            return false;
        }

        for (int i = 1; i <= 2; i++) {
            List<String> handOptions = player.getCardsInHand().stream()
                    .map(card -> card.getName())
                    .collect(Collectors.toList());

            ChoiceDialog<String> discardDialog = new ChoiceDialog<>(handOptions.get(0), handOptions);
            discardDialog.setTitle("Charm: Discard Phase");
            discardDialog.setHeaderText("Choose card #" + i + " to DISCARD");
            discardDialog.setContentText("Select card:");

            Optional<String> result = discardDialog.showAndWait();
            if (result.isPresent()) {
                String selectedName = result.get();
                BaseCard toDiscard = player.getCardsInHand().stream()
                        .filter(c -> c.getName().equals(selectedName))
                        .findFirst().orElse(null);

                if (toDiscard != null) {
                    player.getCardsInHand().remove(toDiscard);
                    GameEngine.deck.discardCard(toDiscard);
                    System.out.println("🗑️ Discarded: " + toDiscard.getName());
                }
            } else {
                i--;
            }
        }

        List<String> targetNames = validTargets.stream()
                .map(Player::getName)
                .collect(Collectors.toList());

        ChoiceDialog<String> playerDialog = new ChoiceDialog<>(targetNames.get(0), targetNames);
        playerDialog.setTitle("Charm: Steal Phase");
        playerDialog.setHeaderText("Choose a player to steal from");
        Optional<String> targetResult = playerDialog.showAndWait();

        if (targetResult.isPresent()) {
            Player targetPlayer = validTargets.stream()
                    .filter(p -> p.getName().equals(targetResult.get()))
                    .findFirst().orElse(null);

            List<String> heroOptions = new ArrayList<>();
            for (int i = 0; i < targetPlayer.getOwnedHero().length; i++) {
                HeroCard h = targetPlayer.getOwnedHero()[i];
                if (h != null) {
                    heroOptions.add(i + ": " + h.getName());
                }
            }

            ChoiceDialog<String> heroDialog = new ChoiceDialog<>(heroOptions.get(0), heroOptions);
            heroDialog.setTitle("Charm: Select Hero");
            heroDialog.setHeaderText("Steal which hero from " + targetPlayer.getName() + "?");
            Optional<String> heroResult = heroDialog.showAndWait();

            if (heroResult.isPresent()) {
                int heroIdx = Integer.parseInt(heroResult.get().split(":")[0]);
                HeroCard stolenHero = targetPlayer.getHeroCard(heroIdx);

                targetPlayer.removeHeroCard(heroIdx);

                HeroCard[] myHeroes = player.getOwnedHero();
                for (int j = 0; j < myHeroes.length; j++) {
                    if (myHeroes[j] == null) {
                        myHeroes[j] = stolenHero;
                        stolenHero.setOwner(player);
                        player.setOwnedHero(myHeroes);
                        break;
                    }
                }

                System.out.println("💖 SUCCESS! Stole " + stolenHero.getName());

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
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}