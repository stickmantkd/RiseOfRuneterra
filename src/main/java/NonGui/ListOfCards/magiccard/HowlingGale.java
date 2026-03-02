package NonGui.ListOfCards.magiccard;

import NonGui.BaseEntity.Cards.MagicCard.MagicCard;
import NonGui.BaseEntity.Player;
import NonGui.BaseEntity.Cards.HeroCard.HeroCard;
import NonGui.BaseEntity.Cards.Itemcard.ItemCard;
import NonGui.GameLogic.GameEngine;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.Alert;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class HowlingGale extends MagicCard {

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

        // 1. ค้นหาผู้เล่นที่มี Hero สวม Item อยู่
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

        // ถ้าไม่มีใครใส่ไอเทมเลย ร่ายไม่ได้ (คืนการ์ด)
        if (playersWithItems.isEmpty()) {
            showSimpleAlert("Spell Failed", "There are no equipped items on the board to return!");
            return false;
        }

        // 2. เลือกผู้เล่นเป้าหมาย (GUI)
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

            // 3. เลือก Hero ที่มีไอเทม (กรองเฉพาะตัวที่มีไอเทมเท่านั้นมาให้เลือก)
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

                // 4. ดำเนินการคืนไอเทม
                ItemCard itemToReturn = targetHero.getItem();

                // ถอดไอเทม (onUnEquip จะทำงานอัตโนมัติ)
                targetHero.unEquipItem();

                // คืนเข้ามือเจ้าของ
                targetPlayer.addCardToHand(itemToReturn);
                System.out.println("🌪️ SWOOSH! " + itemToReturn.getName() + " returned to " + targetPlayer.getName() + "'s hand.");

                // 5. DRAW (ผู้ร่ายจั่วการ์ด 1 ใบ)
                player.DrawRandomCard();
                System.out.println("✨ " + player.getName() + " drew a card from the wind.");

                // Refresh GUI
                try { gui.BoardView.refresh(); } catch (Exception e) {}
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