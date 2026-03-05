package NonGui.ListOfCards.magiccard;

import NonGui.BaseEntity.Cards.MagicCard.MagicCard;
import NonGui.BaseEntity.Player;
import NonGui.BaseEntity.BaseCard;
import NonGui.BaseEntity.Cards.HeroCard.HeroCard;
import NonGui.BaseEntity.Properties.UnitClass;
import NonGui.GameLogic.GameEngine; // เพิ่มการเรียกใช้เพื่อเช็คจำนวนผู้เล่น
import gui.board.StatusBar;
import javafx.collections.ObservableList;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.Alert;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class Charm extends MagicCard {

    public Charm() {
        super(
                "Charm",
                "Don't you trust me?",
                "DISCARD 2 cards, then STEAL a Hero card."
        );
    }

    @Override
    public boolean playCard(Player player) {
        System.out.println("\n💖 " + player.getName() + " casts " + this.getName() + "!");

        // 1. Safety Checks (เช็คทรัพยากร)
        if (player.getCardsInHand().size() < 2) {
            showSimpleAlert("Spell Failed", "You need at least 2 other cards in hand to discard!");
            return false;
        }

        // เช็คว่าเรามีที่ว่างบนบอร์ดไหม
        boolean hasSpace = false;
        for (HeroCard h : player.getOwnedHero()) {
            if (h == null) { hasSpace = true; break; }
        }
        if (!hasSpace) {
            showSimpleAlert("Spell Failed", "Your party is full! No room for a stolen hero.");
            return false;
        }

        // เช็คว่ามีเหยื่อให้ขโมยไหม
        List<Player> validTargets = new ArrayList<>();
        for (Player p : GameEngine.players) {
            if (p != player && !p.boardIsEmpty()) {
                validTargets.add(p);
            }
        }
        if (validTargets.isEmpty()) {
            showSimpleAlert("Spell Failed", "No other players have any Heroes to steal!");
            return false;
        }

        // 2. DISCARD 2 cards (ใช้ GUI ChoiceDialog)
        for (int i = 1; i <= 2; i++) {
            List<String> handOptions = player.getCardsInHand().stream()
                    .map(card -> card.getName())
                    .collect(Collectors.toList());

            ChoiceDialog<String> discardDialog = new ChoiceDialog<>(handOptions.get(0), handOptions);
            discardDialog.setTitle("Charm: Discard Phase");
            discardDialog.setHeaderText("Choose card #" + i + " to DISCARD");
            discardDialog.setContentText("Select card:");

            Optional<String> result = discardDialog.showAndWait();
            if (result.isPresent()) {
                String selectedName = result.get();
                BaseCard toDiscard = player.getCardsInHand().stream()
                        .filter(c -> c.getName().equals(selectedName))
                        .findFirst().orElse(null);

                if (toDiscard != null) {
                    player.getCardsInHand().remove(toDiscard);
                    GameEngine.deck.discardCard(toDiscard); // ส่งลงกองทิ้งด้วย
                    System.out.println("🗑️ Discarded: " + toDiscard.getName());
                }
            } else {
                // ถ้ากด Cancel กลางคัน (ในกรณีที่ยอมให้ยกเลิก) แต่ตามกฎมักจะบังคับเลือก
                // ในที่นี้ถ้าไม่เลือก จะถือว่าร่ายไม่สำเร็จและคืนการ์ด Charm (ถ้าอยากให้เป็นงั้น)
                // แต่เพื่อความง่าย เราจะบังคับวนลูปจนกว่าจะเลือกครับ
                i--;
            }
        }

        // 3. Select Target Player (เลือกเหยื่อ)
        List<String> targetNames = validTargets.stream()
                .map(Player::getName)
                .collect(Collectors.toList());

        ChoiceDialog<String> playerDialog = new ChoiceDialog<>(targetNames.get(0), targetNames);
        playerDialog.setTitle("Charm: Steal Phase");
        playerDialog.setHeaderText("Choose a player to steal from");
        Optional<String> targetResult = playerDialog.showAndWait();

        if (targetResult.isPresent()) {
            Player targetPlayer = validTargets.stream()
                    .filter(p -> p.getName().equals(targetResult.get()))
                    .findFirst().orElse(null);

            // 4. Select Hero to Steal (เลือกฮีโร่ที่จะขโมย)
            List<String> heroOptions = new ArrayList<>();
            for (int i = 0; i < targetPlayer.getOwnedHero().length; i++) {
                HeroCard h = targetPlayer.getOwnedHero()[i];
                if (h != null) {
                    heroOptions.add(i + ": " + h.getName());
                }
            }

            ChoiceDialog<String> heroDialog = new ChoiceDialog<>(heroOptions.get(0), heroOptions);
            heroDialog.setTitle("Charm: Select Hero");
            heroDialog.setHeaderText("Steal which hero from " + targetPlayer.getName() + "?");
            Optional<String> heroResult = heroDialog.showAndWait();

            if (heroResult.isPresent()) {
                int heroIdx = Integer.parseInt(heroResult.get().split(":")[0]);
                HeroCard stolenHero = targetPlayer.getHeroCard(heroIdx);

                // ย้ายของ!
                targetPlayer.removeHeroCard(heroIdx);

                // หาที่ลงในบอร์ดเรา
                HeroCard[] myHeroes = player.getOwnedHero();
                for (int j = 0; j < myHeroes.length; j++) {
                    if (myHeroes[j] == null) {
                        myHeroes[j] = stolenHero;
                        stolenHero.setOwner(player); // เปลี่ยนเจ้าของ
                        player.setOwnedHero(myHeroes);
                        break;
                    }
                }

                System.out.println("💖 SUCCESS! Stole " + stolenHero.getName());

                // สั่ง Refresh จอทันที
                try { gui.BoardView.refresh(); } catch (Exception e) {}
                if(player.getOwnedLeader().getUnitClass() == UnitClass.Mage){
                    player.DrawRandomCard(); // สั่งจั่วเพิ่ม 1 ใบ
                    StatusBar.showMessage("Mage Leader: Magic used! Drawing an extra card.");
                }
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