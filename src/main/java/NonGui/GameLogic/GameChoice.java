package NonGui.GameLogic;

import NonGui.BaseEntity.Cards.HeroCard.HeroCard;
import NonGui.BaseEntity.*;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.StageStyle;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import static NonGui.GameLogic.GameEngine.*;

public class GameChoice {

    // ── Shared style constants ────────────────────────────────────────────────

    private static final String ROOT_STYLE =
            "-fx-background-color: linear-gradient(to bottom, #1c0d00, #2e1800);" +
                    "-fx-border-color: #8B6914; -fx-border-width: 2;" +
                    "-fx-border-radius: 8; -fx-background-radius: 8;";

    private static final String TITLE_STYLE =
            "-fx-font-family: 'Georgia'; -fx-font-size: 15; -fx-font-weight: bold;" +
                    "-fx-text-fill: #FFD700;" +
                    "-fx-effect: dropshadow(gaussian, #FF8C00, 6, 0.5, 0, 0);";

    private static final String SUBTITLE_STYLE =
            "-fx-font-family: 'Georgia'; -fx-font-size: 11; -fx-text-fill: #C8A870;";

    private static final String ITEM_BASE =
            "-fx-background-color: linear-gradient(to right, #2a1500, #1a0d00);" +
                    "-fx-border-color: #5a3a10; -fx-border-width: 1;" +
                    "-fx-border-radius: 4; -fx-background-radius: 4;" +
                    "-fx-font-family: 'Georgia'; -fx-font-size: 12;" +
                    "-fx-text-fill: #F5DEB3; -fx-padding: 8 14 8 14;" +
                    "-fx-cursor: hand; -fx-alignment: center-left;";

    private static final String ITEM_HOVER =
            "-fx-background-color: linear-gradient(to right, #6a3500, #4a2000);" +
                    "-fx-border-color: #FFD700; -fx-border-width: 1;" +
                    "-fx-border-radius: 4; -fx-background-radius: 4;" +
                    "-fx-font-family: 'Georgia'; -fx-font-size: 12;" +
                    "-fx-text-fill: #FFFACD; -fx-padding: 8 14 8 14;" +
                    "-fx-cursor: hand; -fx-alignment: center-left;" +
                    "-fx-effect: dropshadow(gaussian, #FFD700, 6, 0.3, 0, 0);";

    private static final String ITEM_SELECTED =
            "-fx-background-color: linear-gradient(to right, #8a4500, #5a2800);" +
                    "-fx-border-color: #FFD700; -fx-border-width: 2;" +
                    "-fx-border-radius: 4; -fx-background-radius: 4;" +
                    "-fx-font-family: 'Georgia'; -fx-font-size: 12; -fx-font-weight: bold;" +
                    "-fx-text-fill: #FFD700; -fx-padding: 8 14 8 14;" +
                    "-fx-cursor: hand; -fx-alignment: center-left;" +
                    "-fx-effect: dropshadow(gaussian, #FFD700, 8, 0.4, 0, 0);";

    private static final String CONFIRM_BTN =
            "-fx-background-color: linear-gradient(to bottom, #6a3800, #3a1e00);" +
                    "-fx-text-fill: #FFD700; -fx-font-family: 'Georgia'; -fx-font-size: 12; -fx-font-weight: bold;" +
                    "-fx-border-color: #8B6914; -fx-border-width: 1;" +
                    "-fx-border-radius: 4; -fx-background-radius: 4;" +
                    "-fx-padding: 6 20 6 20; -fx-cursor: hand;";

    private static final String CONFIRM_BTN_HOVER =
            "-fx-background-color: linear-gradient(to bottom, #9a5000, #5a2e00);" +
                    "-fx-text-fill: #FFFACD; -fx-font-family: 'Georgia'; -fx-font-size: 12; -fx-font-weight: bold;" +
                    "-fx-border-color: #FFD700; -fx-border-width: 1;" +
                    "-fx-border-radius: 4; -fx-background-radius: 4;" +
                    "-fx-padding: 6 20 6 20; -fx-cursor: hand;" +
                    "-fx-effect: dropshadow(gaussian, #FFD700, 6, 0.4, 0, 0);";

    private static final String CANCEL_BTN =
            "-fx-background-color: linear-gradient(to bottom, #2a1a0a, #1a0d00);" +
                    "-fx-text-fill: #8a7050; -fx-font-family: 'Georgia'; -fx-font-size: 12;" +
                    "-fx-border-color: #3a2510; -fx-border-width: 1;" +
                    "-fx-border-radius: 4; -fx-background-radius: 4;" +
                    "-fx-padding: 6 20 6 20; -fx-cursor: hand;";

    // ── Core dialog builder ───────────────────────────────────────────────────

    /**
     * Shows a styled selection dialog. Returns the selected index into {@code items},
     * or -1 if cancelled.
     */
    private static int showPickerDialog(String title, String subtitle, List<String> items) {
        // Result holder
        AtomicInteger result = new AtomicInteger(-1);

        // Build item buttons
        AtomicInteger selected = new AtomicInteger(0);
        List<Button> itemButtons = new ArrayList<>();

        for (int i = 0; i < items.size(); i++) {
            Button btn = new Button(items.get(i));
            btn.setMaxWidth(Double.MAX_VALUE);
            btn.setStyle(ITEM_BASE);
            itemButtons.add(btn);
        }

        // Highlight first by default
        if (!itemButtons.isEmpty()) {
            itemButtons.get(0).setStyle(ITEM_SELECTED);
        }

        // Wire selection logic
        for (int i = 0; i < itemButtons.size(); i++) {
            final int idx = i;
            Button btn = itemButtons.get(i);
            btn.setOnMouseEntered(e -> {
                if (selected.get() != idx) btn.setStyle(ITEM_HOVER);
            });
            btn.setOnMouseExited(e -> {
                btn.setStyle(selected.get() == idx ? ITEM_SELECTED : ITEM_BASE);
            });
            btn.setOnAction(e -> {
                // Deselect previous
                itemButtons.get(selected.get()).setStyle(ITEM_BASE);
                // Select new
                selected.set(idx);
                btn.setStyle(ITEM_SELECTED);
            });
        }

        // Scroll list
        VBox listBox = new VBox(5);
        listBox.getChildren().addAll(itemButtons);
        listBox.setPadding(new Insets(4));

        ScrollPane scroll = new ScrollPane(listBox);
        scroll.setFitToWidth(true);
        scroll.setMaxHeight(240);
        scroll.setPrefHeight(Math.min(items.size() * 42.0 + 8, 240));
        scroll.setStyle(
                "-fx-background: transparent; -fx-background-color: transparent;" +
                        "-fx-border-color: #3a2510; -fx-border-width: 1;" +
                        "-fx-border-radius: 4;"
        );
        scroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

        // Title
        Label titleLabel = new Label(title);
        titleLabel.setStyle(TITLE_STYLE);

        // Separator
        Region sep = new Region();
        sep.setPrefHeight(1);
        sep.setMaxWidth(Double.MAX_VALUE);
        sep.setStyle("-fx-background-color: linear-gradient(to right, transparent, #8B6914, transparent);");
        VBox.setMargin(sep, new Insets(4, 0, 4, 0));

        // Subtitle
        Label subLabel = new Label(subtitle);
        subLabel.setStyle(SUBTITLE_STYLE);
        subLabel.setWrapText(true);

        // Confirm / Cancel buttons
        Button confirmBtn = new Button("⚔  Confirm");
        confirmBtn.setStyle(CONFIRM_BTN);
        confirmBtn.setOnMouseEntered(e -> confirmBtn.setStyle(CONFIRM_BTN_HOVER));
        confirmBtn.setOnMouseExited(e  -> confirmBtn.setStyle(CONFIRM_BTN));

        Button cancelBtn = new Button("✖  Cancel");
        cancelBtn.setStyle(CANCEL_BTN);

        HBox btnRow = new HBox(10, confirmBtn, cancelBtn);
        btnRow.setAlignment(Pos.CENTER_RIGHT);

        VBox root = new VBox(10,
                titleLabel, sep, subLabel, scroll, btnRow
        );
        root.setPadding(new Insets(20, 22, 18, 22));
        root.setStyle(ROOT_STYLE);
        root.setPrefWidth(340);

        // Build dialog
        Dialog<Integer> dialog = new Dialog<>();
        dialog.setTitle(null);
        dialog.initStyle(StageStyle.TRANSPARENT);

        DialogPane dp = dialog.getDialogPane();
        dp.setContent(root);
        dp.setStyle("-fx-background-color: transparent; -fx-padding: 0;");
        dp.getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        // Hide the default OK/Cancel buttons (we use our own)
        dp.lookupButton(ButtonType.OK).setVisible(false);
        dp.lookupButton(ButtonType.OK).setManaged(false);
        dp.lookupButton(ButtonType.CANCEL).setVisible(false);
        dp.lookupButton(ButtonType.CANCEL).setManaged(false);

        // Drop shadow on the whole pane
        root.setEffect(new DropShadow(24, Color.color(0, 0, 0, 0.85)));

        // Wire our buttons to close the dialog
        confirmBtn.setOnAction(e -> {
            result.set(selected.get());
            dialog.setResult(selected.get());
            dialog.close();
        });
        cancelBtn.setOnAction(e -> {
            result.set(-1);
            dialog.setResult(-1);
            dialog.close();
        });

        dialog.showAndWait();
        return result.get();
    }

    // ── Public API ────────────────────────────────────────────────────────────

    public static int selectCardsInHand(Player player) {
        List<String> options = new ArrayList<>();
        for (int i = 0; i < player.getCardsInHand().size(); i++) {
            options.add("🃏  " + player.getCardsInHand().get(i).getName());
        }
        if (options.isEmpty()) return -1;

        return showPickerDialog(
                "Play a Card",
                player.getName() + " — choose a card from your hand",
                options
        );
    }

    public static int selectObjective() {
        List<String> options = new ArrayList<>();
        for (int i = 0; i < objectives.length; i++) {
            options.add("🏆  " + objectives[i].getName());
        }
        if (options.isEmpty()) return -1;

        return showPickerDialog(
                "Try an Objective",
                "Choose an objective to attempt",
                options
        );
    }

    public static int selectPlayer(Player[] players) {
        List<String> options = new ArrayList<>();
        for (Player p : players) {
            options.add("⚔  " + p.getName());
        }
        if (options.isEmpty()) return -1;

        return showPickerDialog(
                "Select a Player",
                "Choose a target player",
                options
        );
    }

    public static int selectHeroCard(Player player) {
        HeroCard[] heroCards = player.getOwnedHero();
        List<String> options  = new ArrayList<>();
        List<Integer> indexes = new ArrayList<>();

        for (int i = 0; i < heroCards.length; i++) {
            if (heroCards[i] != null) {
                options.add("🦸  " + heroCards[i].getName());
                indexes.add(i);
            }
        }
        if (options.isEmpty()) return -1;

        int picked = showPickerDialog(
                "Use Hero Ability",
                player.getName() + " — choose a hero",
                options
        );

        return picked < 0 ? -1 : indexes.get(picked);
    }
}