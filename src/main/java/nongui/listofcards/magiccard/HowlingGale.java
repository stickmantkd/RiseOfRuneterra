package nongui.listofcards.magiccard;

import nongui.baseentity.cards.MagicCard.MagicCard;
import nongui.baseentity.Player;
import nongui.baseentity.cards.HeroCard.HeroCard;
import nongui.baseentity.cards.Itemcard.ItemCard;
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
 * Represents the "Howling Gale" Magic Card.
 * <p>
 * And you thought it was just a breeze!
 * Effect: Return an Item card equipped to any player's Hero card to that player's hand, then DRAW a card.
 */
public class HowlingGale extends MagicCard {

    /**
     * Constructs a new Howling Gale card with its identity and effect text.
     */
    public HowlingGale() {
        super(
                "Howling Gale",
                "And you thought it was just a breeze!",
                "Return an Item card equipped to any player's Hero card to that player's hand, then DRAW a card."
        );
    }

    @Override
    public boolean playCard(Player player) {
        System.out.println("\n🌪️ " + player.getName() + " casts " + this.getName() + "!");

        List<Player> playersWithItems = new ArrayList<>();
        for (Player p : GameEngine.players) {
            if (p == null) continue;
            for (HeroCard hero : p.getOwnedHero()) {
                if (hero != null && hero.getItem() != null) {
                    if (!playersWithItems.contains(p)) {
                        playersWithItems.add(p);
                    }
                }
            }
        }

        if (playersWithItems.isEmpty()) {
            showSimpleAlert("Spell Failed", "There are no equipped items on the board to return!");
            return false;
        }

        List<String> targetNames = playersWithItems.stream()
                .map(Player::getName)
                .collect(Collectors.toList());

        ChoiceDialog<String> playerDialog = new ChoiceDialog<>(targetNames.get(0), targetNames);
        playerDialog.setTitle("Howling Gale: Select Target");
        playerDialog.setHeaderText("Choose a player to return their item");

        Optional<String> playerResult = playerDialog.showAndWait();
        if (playerResult.isPresent()) {
            Player targetPlayer = playersWithItems.stream()
                    .filter(p -> p.getName().equals(playerResult.get()))
                    .findFirst().orElse(null);

            List<String> heroOptions = new ArrayList<>();
            HeroCard[] targetHeroes = targetPlayer.getOwnedHero();
            for (int i = 0; i < targetHeroes.length; i++) {
                if (targetHeroes[i] != null && targetHeroes[i].getItem() != null) {
                    heroOptions.add(i + ": " + targetHeroes[i].getName() + " (" + targetHeroes[i].getItem().getName() + ")");
                }
            }

            ChoiceDialog<String> heroDialog = new ChoiceDialog<>(heroOptions.get(0), heroOptions);
            heroDialog.setTitle("Howling Gale: Select Hero");
            heroDialog.setHeaderText("Which hero's item should be blown away?");

            Optional<String> heroResult = heroDialog.showAndWait();
            if (heroResult.isPresent()) {
                int heroIdx = Integer.parseInt(heroResult.get().split(":")[0]);
                HeroCard targetHero = targetPlayer.getHeroCard(heroIdx);

                ItemCard itemToReturn = targetHero.getItem();

                targetHero.unEquipItem();

                targetPlayer.addCardToHand(itemToReturn);
                System.out.println("🌪️ SWOOSH! " + itemToReturn.getName() + " returned to " + targetPlayer.getName() + "'s hand.");

                player.drawRandomCard();
                System.out.println("✨ " + player.getName() + " drew a card from the wind.");

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