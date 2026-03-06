package gui.board;

import nongui.gamelogic.GameEngine;
import nongui.baseentity.Player;
import nongui.baseentity.Objective;
import nongui.gameutils.DiceUtils;
import nongui.gamelogic.GameChoice;
import gui.BoardView;
import gui.card.CardView;
import gui.card.ObjectiveView;
import javafx.collections.ListChangeListener;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import static gui.GameUI.endTurn;

/**
 * Represents the right-side menu area of the game board.
 * <p>
 * Handles main player actions (Draw, Play, Use Ability, Roll Dice) and provides
 * access to view game decks (Main Deck, Discard Pile, Objectives).
 */
public class MenuArea extends VBox {

    // --- Layout Constants ---
    private static final double MENU_WIDTH = 178;
    private static final double MENU_HEIGHT = 768;
    private static final double POPUP_WIDTH = 640;
    private static final double POPUP_HEIGHT = 480;

    // --- Style Constants ---
    private static final String MENU_BG_STYLE =
            "-fx-background-color: linear-gradient(to bottom, #1c0d00, #2e1800, #1c0d00);" +
                    "-fx-border-color: #8B6914; -fx-border-width: 0 2 0 0;";

    private static final String TITLE_STYLE =
            "-fx-font-family: 'Georgia'; -fx-font-size: 11; -fx-font-weight: bold; " +
                    "-fx-text-fill: #FFD700; -fx-effect: dropshadow(gaussian, #FF8C00, 4, 0.5, 0, 0);";

    private static final String TURN_LABEL_STYLE =
            "-fx-font-family: 'Georgia'; -fx-font-size: 11; -fx-text-fill: #F5DEB3; " +
                    "-fx-wrap-text: true; -fx-text-alignment: center;";

    private static final String POPUP_BG =
            "-fx-background-color: linear-gradient(to bottom, #1a0a00, #2a1200);";

    // --- Instance Variables ---
    private static Label turnLabel;

    private TilePane deckGrid;
    private Stage deckStage;

    private TilePane discardGrid;
    private Stage discardStage;

    private TilePane objectiveDeckGrid;
    private Stage objectiveDeckStage;

    /**
     * Constructs the main action menu for the game.
     */
    public MenuArea() {
        setupLayout();
        buildUI();
        setupGlobalDeckListeners();
    }

    private void setupLayout() {
        setPrefSize(MENU_WIDTH, MENU_HEIGHT);
        setMinSize(MENU_WIDTH, MENU_HEIGHT);
        setBackground(new Background(new BackgroundFill(Color.TRANSPARENT, CornerRadii.EMPTY, Insets.EMPTY)));
        setSpacing(8);
        setAlignment(Pos.TOP_CENTER);
        setPadding(new Insets(14, 10, 14, 10));
        setStyle(MENU_BG_STYLE);
    }

    private void buildUI() {
        // 1. Header Area
        Label menuLabel = new Label("⚔  RUNETERRA  ⚔");
        menuLabel.setStyle(TITLE_STYLE);
        menuLabel.setAlignment(Pos.CENTER);
        menuLabel.setMaxWidth(Double.MAX_VALUE);

        turnLabel = new Label();
        turnLabel.setStyle(TURN_LABEL_STYLE);
        turnLabel.setMaxWidth(Double.MAX_VALUE);
        turnLabel.setAlignment(Pos.CENTER);
        updateTurnLabel();

        // 2. Action Buttons
        Button drawButton    = createMenuButton("📜  Draw Card", false);
        Button playButton    = createMenuButton("🃏  Play Card", false);
        Button abilityButton = createMenuButton("✨  Hero Ability", false);
        Button objectiveBtn  = createMenuButton("🎯  Try Objective", false);
        Button diceButton    = createMenuButton("🎲  Roll Dice", false);

        wireActionButtons(drawButton, playButton, abilityButton, objectiveBtn, diceButton);

        // 3. View Buttons
        Button seeDeckButton      = createMenuButton("📚  View Deck", true);
        Button seeDiscardButton   = createMenuButton("🗑  Discard Pile", true);
        Button seeObjectiveDeckBtn= createMenuButton("🏆  Objective Deck", true);

        seeDeckButton.setOnAction(e -> openDeckWindow());
        seeDiscardButton.setOnAction(e -> openDiscardWindow());
        seeObjectiveDeckBtn.setOnAction(e -> openObjectiveDeckWindow());

        // 4. Assemble View
        getChildren().addAll(
                menuLabel, createSeparator(),
                turnLabel, createSeparator(),
                drawButton, playButton, abilityButton, objectiveBtn, diceButton,
                createSeparator(),
                seeDeckButton, seeDiscardButton, seeObjectiveDeckBtn
        );
    }

    // ── Button Logic & Listeners ─────────────────────────────────────────────

    private void wireActionButtons(Button draw, Button play, Button ability, Button obj, Button dice) {
        draw.setOnAction(e -> {
            Player current = GameEngine.getCurrentPlayer();
            GameEngine.drawCard(current);
            finishAction(current);
        });

        play.setOnAction(e -> {
            Player current = GameEngine.getCurrentPlayer();
            if (!current.handIsEmpty()) {
                int handIndex = GameChoice.selectCardsInHand(current);
                if (handIndex >= 0) GameEngine.playCard(current, handIndex);
            }
            finishAction(current);
        });

        ability.setOnAction(e -> {
            Player current = GameEngine.getCurrentPlayer();
            int heroIndex = GameChoice.selectHeroCard(current);
            if (heroIndex >= 0) GameEngine.useHeroAbility(current, heroIndex);
            finishAction(current);
        });

        obj.setOnAction(e -> {
            Player current = GameEngine.getCurrentPlayer();
            int objIndex = GameChoice.selectObjective();
            if (objIndex >= 0) GameEngine.tryObjective(current, objIndex);
            finishAction(current);
        });

        dice.setOnAction(e -> {
            int result = DiceUtils.getRoll();
            System.out.println("Dice result: " + result);
        });
    }

    private void finishAction(Player current) {
        updateTurnLabel();
        BoardView.refresh();
        endTurn(current);
    }

    // ── Global Deck Listeners (Prevents Memory Leak) ──────────────────────────

    /**
     * Attaches listeners ONCE during menu creation to safely update popup windows
     * if they happen to be open when a deck changes.
     */
    private void setupGlobalDeckListeners() {
        GameEngine.deck.getGameDeck().addListener((ListChangeListener.Change<?> c) -> {
            if (deckStage != null && deckStage.isShowing()) updateDeckGrid();
        });

        GameEngine.deck.getDiscardPile().addListener((ListChangeListener.Change<?> c) -> {
            if (discardStage != null && discardStage.isShowing()) updateDiscardGrid();
        });

        GameEngine.objectiveDeck.getObjectiveDeck().addListener((ListChangeListener.Change<?> c) -> {
            if (objectiveDeckStage != null && objectiveDeckStage.isShowing()) updateObjectiveDeckGrid();
        });
    }

    // ── Factory Helpers ──────────────────────────────────────────────────────

    private Button createMenuButton(String text, boolean isViewVariant) {
        Button btn = new Button(text);
        btn.setMaxWidth(Double.MAX_VALUE);
        btn.setAlignment(Pos.CENTER_LEFT);

        String baseStyle = isViewVariant ?
                "-fx-background-color: linear-gradient(to bottom, #2a1a00, #1a0d00); -fx-text-fill: #C8A870; -fx-border-color: #5a3a10; -fx-border-width: 1; -fx-border-radius: 4; -fx-background-radius: 4; -fx-font-family: 'Georgia'; -fx-font-size: 11; -fx-padding: 5 8 5 8; -fx-cursor: hand;" :
                "-fx-background-color: linear-gradient(to bottom, #4a2800, #2e1500); -fx-text-fill: #FFD700; -fx-border-color: #8B6914; -fx-border-width: 1; -fx-border-radius: 4; -fx-background-radius: 4; -fx-font-family: 'Georgia'; -fx-font-size: 11; -fx-font-weight: bold; -fx-padding: 6 8 6 8; -fx-cursor: hand;";

        String hoverStyle =
                "-fx-background-color: linear-gradient(to bottom, #7a4500, #4e2800); -fx-text-fill: #FFFACD; -fx-border-color: #FFD700; -fx-border-width: 1; -fx-border-radius: 4; -fx-background-radius: 4; -fx-font-family: 'Georgia'; -fx-font-size: 11; -fx-font-weight: bold; -fx-padding: 6 8 6 8; -fx-cursor: hand; -fx-effect: dropshadow(gaussian, #FFD700, 6, 0.4, 0, 0);";

        btn.setStyle(baseStyle);
        btn.setOnMouseEntered(e -> btn.setStyle(hoverStyle));
        btn.setOnMouseExited(e -> btn.setStyle(baseStyle));
        return btn;
    }

    private Region createSeparator() {
        Region sep = new Region();
        sep.setPrefHeight(1);
        sep.setMaxWidth(Double.MAX_VALUE);
        sep.setStyle("-fx-background-color: linear-gradient(to right, transparent, #8B6914, transparent);");
        VBox.setMargin(sep, new Insets(3, 0, 3, 0));
        return sep;
    }

    // ── Turn Label ───────────────────────────────────────────────────────────

    public static void updateTurnLabel() {
        if (turnLabel == null) return;
        if (!GameEngine.isGameActive()) {
            turnLabel.setText("Game Over");
            return;
        }
        Player current = GameEngine.getCurrentPlayer();
        turnLabel.setText(current.getName() + "\n⚡ AP: " + current.getActionPoint());
    }

    // ── Popup Windows (Deck / Discard / Objectives) ──────────────────────────

    private void openDeckWindow() {
        if (deckStage != null && deckStage.isShowing()) {
            deckStage.toFront();
            return;
        }
        deckGrid = createPopupGrid();
        updateDeckGrid();
        deckStage = buildPopupStage("📚  Game Deck", createScrollPane(deckGrid));
        deckStage.show();
    }

    private void updateDeckGrid() {
        deckGrid.getChildren().clear();
        for (int i = 0; i < GameEngine.deck.getGameDeck().size(); i++) {
            deckGrid.getChildren().add(new CardView(GameEngine.deck.getGameDeck().get(i), i));
        }
    }

    private void openDiscardWindow() {
        if (discardStage != null && discardStage.isShowing()) {
            discardStage.toFront();
            return;
        }
        discardGrid = createPopupGrid();
        updateDiscardGrid();
        discardStage = buildPopupStage("🗑  Discard Pile", createScrollPane(discardGrid));
        discardStage.show();
    }

    private void updateDiscardGrid() {
        discardGrid.getChildren().clear();
        for (int i = 0; i < GameEngine.deck.getDiscardPile().size(); i++) {
            discardGrid.getChildren().add(new CardView(GameEngine.deck.getDiscardPile().get(i), i));
        }
    }

    private void openObjectiveDeckWindow() {
        if (objectiveDeckStage != null && objectiveDeckStage.isShowing()) {
            objectiveDeckStage.toFront();
            return;
        }
        objectiveDeckGrid = createPopupGrid();
        updateObjectiveDeckGrid();
        objectiveDeckStage = buildPopupStage("🏆  Objective Deck", createScrollPane(objectiveDeckGrid));
        objectiveDeckStage.show();
    }

    private void updateObjectiveDeckGrid() {
        objectiveDeckGrid.getChildren().clear();
        for (int i = 0; i < GameEngine.objectiveDeck.getObjectiveDeck().size(); i++) {
            Objective obj = GameEngine.objectiveDeck.getObjectiveDeck().get(i);
            objectiveDeckGrid.getChildren().add(new ObjectiveView(obj, i));
        }
    }

    // ── Popup Helpers ─────────────────────────────────────────────────────────

    private TilePane createPopupGrid() {
        TilePane grid = new TilePane();
        grid.setHgap(12);
        grid.setVgap(12);
        grid.setAlignment(Pos.TOP_LEFT);
        grid.setPadding(new Insets(16));
        grid.setStyle("-fx-background-color: transparent;");
        return grid;
    }

    private ScrollPane createScrollPane(TilePane grid) {
        ScrollPane scroll = new ScrollPane(grid);
        scroll.setFitToWidth(true);
        scroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scroll.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scroll.setStyle("-fx-background: #1a0a00; -fx-background-color: #1a0a00; -fx-control-inner-background: #1a0a00;");

        scroll.skinProperty().addListener((obs, oldSkin, newSkin) -> {
            scroll.lookupAll(".scroll-bar").forEach(node -> node.setStyle("-fx-background-color: #2a1200;"));
            scroll.lookupAll(".increment-button, .decrement-button").forEach(node -> node.setStyle("-fx-background-color: #4a2800;"));
            scroll.lookupAll(".thumb").forEach(node -> node.setStyle("-fx-background-color: #8B6914; -fx-background-radius: 3;"));
        });

        return scroll;
    }

    private Stage buildPopupStage(String title, ScrollPane scroll) {
        Label titleLabel = new Label(title);
        titleLabel.setStyle(TITLE_STYLE);
        titleLabel.setPadding(new Insets(10, 16, 10, 16));
        titleLabel.setMaxWidth(Double.MAX_VALUE);

        Region headerSep = createSeparator();

        VBox root = new VBox(0, titleLabel, headerSep, scroll);
        VBox.setVgrow(scroll, Priority.ALWAYS);
        root.setStyle(POPUP_BG + "-fx-border-color: #8B6914; -fx-border-width: 2;");

        Scene scene = new Scene(root, POPUP_WIDTH, POPUP_HEIGHT);
        scene.setFill(Color.web("#1a0a00"));

        Stage stage = new Stage();
        stage.setAlwaysOnTop(true);
        stage.setTitle(title);
        stage.setScene(scene);
        stage.setMinWidth(400);
        stage.setMinHeight(300);
        return stage;
    }
}