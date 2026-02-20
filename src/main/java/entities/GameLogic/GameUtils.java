package entities.GameLogic;

import entities.baseObject.baseCard;

import java.util.Objects;

public class GameUtils {
    public static boolean isSame(baseCard entity1, baseCard entity2){
        return Objects.equals(entity1.getName(), entity2.getName());
    }

    public static void Destroy(){

    }
}
