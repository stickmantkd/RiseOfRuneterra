package NonGui.GameUtils;

import NonGui.ListOfCards.herocard.Fighter.Fiora;
import NonGui.BaseEntity.Properties.UnitClass;
import NonGui.BaseEntity.LeaderCard;
import NonGui.BaseEntity.BaseCard;
import NonGui.ListOfCards.herocard.Fighter.Olaf;
import NonGui.ListOfCards.herocard.Fighter.Volibear;
import NonGui.ListOfCards.herocard.Mage.Veigar;
import NonGui.ListOfCards.herocard.Maskman.Caitlyn;
import NonGui.ListOfCards.herocard.Maskman.Jinx;
import NonGui.ListOfCards.herocard.Support.Bard;
import NonGui.ListOfCards.herocard.Support.Neeko;
import NonGui.ListOfCards.herocard.Support.TahmKench;

import java.util.Random;


public class GenerationsUtils {


    public static BaseCard GenerateRandomCard(){
        Random rand = new Random();

        // กำหนดจำนวนฮีโร่ทั้งหมดที่มีในระบบตอนนี้ (5 ตัว)
        // nextInt(5) จะสุ่มตัวเลขตั้งแต่ 0 ถึง 4
        int randomHeroIndex = rand.nextInt(5);

        switch (randomHeroIndex) {
            case 0:
                return new Fiora();
            case 1:
                return new Bard();
            case 2:
                return new Volibear();
            case 3:
                return new Olaf();
            case 4:
                return new Veigar();
            default:
                // กันเหนียว (Fallback) กรณีเกิดข้อผิดพลาด
                return new Fiora();
        }
    }

    public static LeaderCard GenerateRandomLeader(){
        //currently just generate a dumbo
        return new LeaderCard("dumbo","Dumbass", UnitClass.Fighter);
    }
}
