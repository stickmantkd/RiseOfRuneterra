package NonGui.ListOfCards.magiccard;

import NonGui.BaseEntity.Cards.MagicCard.MagicCard;
import NonGui.BaseEntity.Player;
import NonGui.BaseEntity.BaseCard;
import NonGui.BaseEntity.Cards.HeroCard.HeroCard;
import NonGui.GameLogic.GameEngine;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.Alert;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class FinalSpark extends MagicCard {

    public FinalSpark() {
        super(
                "Final Spark",
                "DEMACIA!!!",
                "DISCARD a card, then DESTROY a Hero card."
        );
    }

    @Override
    public boolean playCard(Player player) {
        System.out.println("\n✨ " + player.getName() + " casts " + this.getName() + "!");

        // 1. Safety Check: ต้องมีการ์ดให้ทิ้ง (ไม่นับใบ Final Spark เองที่กำลังเล่นอยู่)
        if (player.getCardsInHand().isEmpty()) {
            showSimpleAlert("Spell Failed", "You have no other cards in hand to discard!");
            return false;
        }

        // 2. DISCARD Step (GUI)
        List<String> handOptions = player.getCardsInHand().stream()
                .map(BaseCard::getName)
                .collect(Collectors.toList());

        ChoiceDialog<String> discardDialog = new ChoiceDialog<>(handOptions.get(0), handOptions);
        discardDialog.setTitle("Final Spark: Discard Phase");
        discardDialog.setHeaderText("Choose a card to DISCARD to power up the laser");
        discardDialog.setContentText("Select card:");

        Optional<String> discardResult = discardDialog.showAndWait();
        if (discardResult.isPresent()) {
            String selectedName = discardResult.get();
            BaseCard toDiscard = player.getCardsInHand().stream()
                    .filter(c -> c.getName().equals(selectedName))
                    .findFirst().orElse(null);

            if (toDiscard != null) {
                player.getCardsInHand().remove(toDiscard);
                GameEngine.deck.discardCard(toDiscard); // ส่งลงกองทิ้ง
                System.out.println("🗑️ " + toDiscard.getName() + " discarded.");
            }
        } else {
            // ถ้ากดยกเลิก ไม่ให้ร่ายเวท (หรือจะบังคับให้เลือกก็ได้)
            return false;
        }

        // 3. DESTROY Step (GUI)
        // ค้นหาผู้เล่นทุกคนที่มี Hero บนบอร์ด (รวมตัวเองด้วยก็ได้ตาม Text "DESTROY a Hero")
        List<Player> validTargets = new ArrayList<>();
        for (Player p : GameEngine.players) {
            if (p != null && !p.boardIsEmpty()) {
                validTargets.add(p);
            }
        }

        if (validTargets.isEmpty()) {
            showSimpleAlert("Final Spark", "The laser fires into the sky... (No heroes to destroy)");
            return true; // ร่ายสำเร็จแต่ไม่มีเป้าหมาย
        }

        // เลือกผู้เล่นเป้าหมาย
        List<String> targetNames = validTargets.stream()
                .map(Player::getName)
                .collect(Collectors.toList());

        ChoiceDialog<String> playerDialog = new ChoiceDialog<>(targetNames.get(0), targetNames);
        playerDialog.setTitle("Final Spark: Target Selection");
        playerDialog.setHeaderText("Choose a player to blast their hero");

        Optional<String> targetPlayerName = playerDialog.showAndWait();
        if (targetPlayerName.isPresent()) {
            Player targetPlayer = validTargets.stream()
                    .filter(p -> p.getName().equals(targetPlayerName.get()))
                    .findFirst().orElse(null);

            // เลือก Hero จากบอร์ดของผู้เล่นคนนั้น
            List<String> heroOptions = new ArrayList<>();
            HeroCard[] targetHeroes = targetPlayer.getOwnedHero();
            for (int i = 0; i < targetHeroes.length; i++) {
                if (targetHeroes[i] != null) {
                    heroOptions.add(i + ": " + targetHeroes[i].getName());
                }
            }

            ChoiceDialog<String> heroDialog = new ChoiceDialog<>(heroOptions.get(0), heroOptions);
            heroDialog.setTitle("Final Spark: Vaporize!");
            heroDialog.setHeaderText("Select a hero to DESTROY from " + targetPlayer.getName() + "'s board");

            Optional<String> heroResult = heroDialog.showAndWait();
            if (heroResult.isPresent()) {
                int heroIdx = Integer.parseInt(heroResult.get().split(":")[0]);
                HeroCard destroyedHero = targetPlayer.getHeroCard(heroIdx);

                // ส่ง Hero ลงกองทิ้ง (ถ้ามีระบบทิ้ง Hero) หรือแค่ลบออก
                targetPlayer.removeHeroCard(heroIdx);
                GameEngine.deck.discardCard(destroyedHero);

                System.out.println("💥 BZZZT! " + destroyedHero.getName() + " was vaporized!");

                // Refresh หน้าจอ
                try { gui.BoardView.refresh(); } catch (Exception e) {}
                return true;
            }
        }

        return false;
    }

    private void showSimpleAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}