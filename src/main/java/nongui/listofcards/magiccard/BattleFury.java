package nongui.listofcards.magiccard;

import nongui.baseentity.cards.MagicCard.MagicCard;
import nongui.baseentity.Player;
import nongui.baseentity.properties.UnitClass;
import gui.board.StatusBar;

/**
 * Represents the "Battle Fury" Magic Card.
 * <p>
 * I've been told I have a... temper.
 * Effect: +2 to all of your rolls until the end of your turn.
 */
public class BattleFury extends MagicCard {

    /**
     * Constructs a new Battle Fury with its identity and effect text.
     */
    public BattleFury() {
        super(
                "Battle Fury",
                "I've been told I have a... temper.",
                "+2 to all of your rolls until the end of your turn."
        );
    }

    @Override
    public boolean playCard(Player player) {
        System.out.println("🧪 " + player.getName() + " unleashes Battle Fury!");
        System.out.println("✨ All rolls get +2 bonus until the end of turn.");

        int currentBonus = player.getRollBonus();
        player.setRollBonus(currentBonus + 2);

        try {
            gui.BoardView.refresh();
        } catch (Exception e) {
            // ignore if GUI not running
        }

        if (player.getOwnedLeader().getUnitClass() == UnitClass.Mage) {
            player.drawRandomCard();
            StatusBar.showMessage("Mage Leader: Magic used! Drawing an extra card.");
        }

        return true;
    }
}