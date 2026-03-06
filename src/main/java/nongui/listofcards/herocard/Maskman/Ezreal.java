package nongui.listofcards.herocard.Maskman;

import nongui.baseentity.BaseCard;
import nongui.baseentity.cards.HeroCard.HeroCard;
import nongui.baseentity.cards.Itemcard.ItemCard;
import nongui.baseentity.Player;
import nongui.baseentity.properties.UnitClass;
import javafx.scene.control.ChoiceDialog;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents the "Ezreal" Hero Card.
 * <p>
 * <b>Ability: Prodigal Explorer</b><br>
 * Requirement: Roll 8+<br>
 * Effect: DRAW 2 cards. If at least one of the drawn cards is an Item card,
 * you may choose to play one of those Item cards immediately for free.
 * <p>
 * <i>Ezreal excels at rapid equipment scaling, allowing players to gear up heroes mid-turn.</i>
 */
public class Ezreal extends HeroCard {

    /**
     * Constructs Ezreal with his base stats and explorer's spirit flavor text.
     */
    public Ezreal() {
        super(
                "Ezreal",
                "Time for a true display of skill!",
                "Prodigal Explorer: Roll 8+. DRAW 2 cards. If at least one of these cards is an Item card, you may play one of them immediately.",
                UnitClass.Maskman,
                8
        );
    }

    /**
     * Executes the Prodigal Explorer ability.
     * <p>
     * Logic Flow:
     * 1. Records initial hand size to track newly drawn cards.
     * 2. Draws 2 random cards from the deck.
     * 3. Scans newly drawn cards for ItemCard instances.
     * 4. Interaction: If an Item is found, prompts the player via ChoiceDialog to play it immediately.
     * 5. Resolution: If played, triggers the Item's playCard() logic; otherwise, keeps cards in hand.
     * * @param player The player who activated Ezreal's ability.
     */
    @Override
    public void useAbility(Player player) {
        // Refresh UI to show Ezreal's presence before action starts
        try { gui.BoardView.refresh(); } catch (Exception e) {}

        System.out.println("✨ " + this.getName() + " uses Prodigal Explorer!");

        int initialHandSize = player.getCardsInHand().size();

        // 1. Draw 2 cards phase
        player.drawRandomCard();
        player.drawRandomCard();

        int newHandSize = player.getCardsInHand().size();
        ArrayList<BaseCard> drawnCards = new ArrayList<>();

        // Capture newly drawn cards into a sub-list
        for (int i = initialHandSize; i < newHandSize; i++) {
            drawnCards.add(player.getCardsInHand().get(i));
        }

        System.out.println(player.getName() + " discovered:");
        ArrayList<ItemCard> drawnItems = new ArrayList<>();

        // 2. Identify Item cards in the discovery
        for (BaseCard card : drawnCards) {
            System.out.println("- " + card.getName());
            if (card instanceof ItemCard) {
                drawnItems.add((ItemCard) card);
            }
        }

        // 3. Instant Play Phase
        if (!drawnItems.isEmpty()) {
            List<String> options = new ArrayList<>();
            options.add("0 : Keep in hand");

            for (int i = 0; i < drawnItems.size(); i++) {
                options.add((i + 1) + " : Play " + drawnItems.get(i).getName());
            }

            ChoiceDialog<String> dialog = new ChoiceDialog<>(options.get(0), options);
            dialog.setTitle("Ezreal Ability: Prodigal Explorer");
            dialog.setHeaderText("✨ Treasure Found! You drew " + drawnItems.size() + " Item(s).");
            dialog.setContentText("Play one immediately for a display of skill?");

            String result = dialog.showAndWait().orElse("0 : Keep in hand");

            // Execute selection
            if (!result.startsWith("0")) {
                int choiceIndex = Integer.parseInt(result.split(" ")[0]) - 1;
                ItemCard itemToPlay = drawnItems.get(choiceIndex);

                System.out.println("⚡ Ezreal utilizes " + itemToPlay.getName() + "!");

                // Temporarily remove to trigger play logic
                player.getCardsInHand().remove(itemToPlay);

                // 4. Trigger item application
                boolean isSuccessfullyPlayed = itemToPlay.playCard(player);

                if (isSuccessfullyPlayed) {
                    System.out.println(">>> [SYSTEM] " + itemToPlay.getName() + " applied! <<<");
                } else {
                    // Fail-safe: Return to hand if play is aborted or failed
                    player.addCardToHand(itemToPlay);
                    System.out.println(">>> [SYSTEM] " + itemToPlay.getName() + " returned to hand. <<<");
                }
            } else {
                System.out.println("Items kept in hand for future turns.");
            }
        }

        // Final UI Sync
        try { gui.BoardView.refresh(); } catch (Exception e) {}
    }
}