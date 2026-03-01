package NonGui.ListOfCards.herocard.Fighter;

import NonGui.BaseEntity.Cards.HeroCard.HeroCard;
import NonGui.BaseEntity.Player;
import NonGui.BaseEntity.Properties.UnitClass;
import NonGui.GameLogic.GameChoice;
import NonGui.GameLogic.GameEngine;

public class Fiora extends HeroCard {

    public Fiora() {
        super(
                "Fiora",
                "I demand satisfaction.",
                "Riposte: Roll 8+. DESTROY a Hero card.",
                UnitClass.Fighter,
                8
        );
    }


    @Override
    public void useAbility(Player player) {
        //Roll 8+. DESTROY a Hero card.
        // According to the requirement: Roll 8+. DESTROY a Hero card.
        System.out.println(getName() + " uses Riposte!");

        // 1. Select a target player (Enemies or yourself, but usually an enemy)
        int selectedPlayerIndex = GameChoice.selectPlayer(GameEngine.players);
        Player targetPlayer = GameEngine.players[selectedPlayerIndex]; // Adjustment if index starts at 1

        // 2. Check if the target player even has any heroes to destroy
        if (targetPlayer.boardIsEmpty()) {
            System.out.println(targetPlayer.getName() + " has no Hero cards to destroy.");
            return;
        }

        // 3. Select which Hero card to destroy from that player's board
        int selectedHeroIndex = GameChoice.selectHeroCard(targetPlayer);

        // 4. DESTROY! (Removing from their ownedHero array)
        boolean success = targetPlayer.removeHeroCard(selectedHeroIndex);

        if (success) {
            System.out.println("En Garde! A Hero card from " + targetPlayer.getName() + " was destroyed.");
        } else {
            System.out.println("Failed to destroy the Hero card.");
        }
    }
}