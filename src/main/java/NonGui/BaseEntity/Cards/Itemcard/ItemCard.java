package NonGui.BaseEntity.Cards.Itemcard;


import NonGui.BaseEntity.Cards.HeroCard.HeroCard;
import NonGui.BaseEntity.ActionCard;
import NonGui.BaseEntity.Player;

import static NonGui.GameLogic.GameChoice.selectHeroCard;
import static NonGui.GameLogic.GameChoice.selectPlayer;
import static NonGui.GameLogic.GameEngine.players;

public abstract class ItemCard extends ActionCard {
    public ItemCard(String name,String flavorText,String abilityDescription){
        super(name,flavorText,abilityDescription);
    }

    //On Play
    @Override
    public boolean playCard(Player player) {
        int selectedPlayerNumber = selectPlayer(players);
        Player selectedPlayer = players[selectedPlayerNumber];

        int selectedHeroNumber = selectHeroCard(selectedPlayer);
        HeroCard selectedHero = selectedPlayer.getHeroCard(selectedHeroNumber);

        return selectedHero.EquipItem(this);
    }

    //Functions
    public abstract void onEquip(HeroCard hero);

    public abstract void onUnEquip(HeroCard hero);

    //getters n setter
}
