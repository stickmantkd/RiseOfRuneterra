package entities.GameLogic;

import entities.baseObject.Objective;
import entities.baseObject.Player;
import entities.baseObject.baseCards.HeroCard.HeroCard;
import entities.baseObject.baseEntity;

import java.io.IOException;
import java.util.Objects;

public class GameUtils {
    public static Player SelectPlayer(Player[] PlayerList) throws IOException {
        System.out.println("Select Player number");
        int playerNumber = 1;
        for(Player player : PlayerList){
            System.out.println(playerNumber + " : "+ player.toString());
            playerNumber++;
        }
        int selectedPlayerNumber;
        try {
            selectedPlayerNumber = System.in.read();
        } catch (IOException e) {
            throw new IOException();
        }
        return PlayerList[selectedPlayerNumber];
    }

    public static HeroCard SelectHeroCard(Player player) throws IOException {
        HeroCard[] HeroCardList = player.getOwnedHero();
        System.out.println("Select Hero card number");
        int HeroCardNumber = 1;
        for(HeroCard hero : HeroCardList){
            if(hero == null) continue;
            System.out.println(HeroCardNumber + " : "+ hero.toString());
            HeroCardNumber++;
        }
        int selectedHeroCardNumber;
        try {
            selectedHeroCardNumber = System.in.read();
        } catch (IOException e) {
            throw new IOException();
        }
        return HeroCardList[HeroCardNumber];
    }

    public static boolean isSame(baseEntity entity1, baseEntity entity2){
        return Objects.equals(entity1.getName(), entity2.getName());
    }

    public static void Destroy(Player[] PlayerList) throws IOException {
        Player selectedPlayer = SelectPlayer(PlayerList);
        HeroCard selectedHero = SelectHeroCard(selectedPlayer);

        HeroCard[] Heroes = selectedPlayer.getOwnedHero();
        for(int i = 0; i < Heroes.length; ++i){
            if(isSame(selectedHero,Heroes[i])){
                Heroes[i] = null;
                break;
            }
        }

        selectedPlayer.setOwnedHero(Heroes);
    }
}
