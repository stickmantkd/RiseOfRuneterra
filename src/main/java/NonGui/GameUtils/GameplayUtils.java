package NonGui.GameUtils;

import NonGui.BaseEntity.Player;

import static NonGui.GameLogic.GameChoice.selectHeroCard;


public class GameplayUtils {
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
