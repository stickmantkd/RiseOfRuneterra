package NonGui.GameUtils;

import NonGui.BaseEntity.Cards.HeroCard.HeroCard;
import NonGui.BaseEntity.Cards.Itemcard.ItemCard;
import NonGui.BaseEntity.Cards.MagicCard.MagicCard;
import NonGui.BaseEntity.Player;

import static NonGui.GameLogic.GameChoice.selectHeroCard;

public class GameplayUtils {
    //PlayCards
    public boolean playMagic(Player player,MagicCard magicCard) {
        return false;
    }

    public boolean playItem(ItemCard itemCard, HeroCard heroCard) {

        return false;
    }

    //Sacrifice n Destroy
    public static void SacrificeHero(Player player, int number){
        for(int i = 0; i < number; ++i){
            int selectedHero = selectHeroCard(player);
            if(player.boardIsEmpty()){
                break;
            }
            else if(!player.removeHeroCard(selectedHero)){
                System.out.println("Please selected NOT NULL hero");
                --i;
            }
        }
    }
}
