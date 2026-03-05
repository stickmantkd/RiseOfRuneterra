package gui.board;

import NonGui.GameLogic.GameEngine;
import NonGui.BaseEntity.Player;
import NonGui.BaseEntity.Objective;
import NonGui.GameUtils.DiceUtils;
import NonGui.GameLogic.GameChoice;
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
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.paint.CycleMethod;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import static gui.GameUI.endTurn;

public class MenuArea extends VBox {

    private static Label turnLabel;

    private TilePane deckGrid;
    private Stage deckStage;
    private TilePane discardGrid;
    private Stage discardStage;
    private TilePane objectiveDeckGrid;
    private Stage objectiveDeckStage;

    // Shared dark fantasy style for popup windows
    private static final String POPUP_BG = "-fx-background-color: linear-gradient(to bottom, #1a0a00, #2a1200);";

    public MenuArea() {
        setPrefSize(178, 768);
        setMinSize(178, 768);
        setBackground(new Background(new BackgroundFill(
                Color.TRANSPARENT, CornerRadii.EMPTY, Insets.EMPTY
        )));
        setSpacing(8);
        setAlignment(Pos.TOP_CENTER);
        setPadding(new Insets(14, 10, 14, 10));

        // Dark wood panel background
        setStyle(
                "-fx-background-color: linear-gradient(to bottom, #1c0d00, #2e1800, #1c0d00);" +
                        "-fx-border-color: #8B6914; -fx-border-width: 0 2 0 0;"
        );

        // Title
        Label menuLabel = new Label("⚔  RUNETERRA  ⚔");
        menuLabel.setStyle(
                "-fx-font-family: 'Georgia';" +
                        "-fx-font-size: 11;" +
                        "-fx-font-weight: bold;" +
                        "-fx-text-fill: #FFD700;" +
                        "-fx-effect: dropshadow(gaussian, #FF8C00, 4, 0.5, 0, 0);"
        );
        menuLabel.setAlignment(Pos.CENTER);
        menuLabel.setMaxWidth(Double.MAX_VALUE);

        // Decorative separator
        Region sep1 = createSeparator();

        // Turn label
        turnLabel = new Label();
        turnLabel.setStyle(
                "-fx-font-family: 'Georgia';" +
                        "-fx-font-size: 11;" +
                        "-fx-text-fill: #F5DEB3;" +
                        "-fx-wrap-text: true;" +
                        "-fx-text-alignment: center;"
        );
        turnLabel.setMaxWidth(Double.MAX_VALUE);
        turnLabel.setAlignment(Pos.CENTER);
        turnLabel.setWrapText(true);
        updateTurnLabel();

        Region sep2 = createSeparator();

        // Action buttons
        Button drawButton    = createMenuButton("📜  Draw Card",     "draw");
        Button playButton    = createMenuButton("🃏  Play Card",     "play");
        Button abilityButton = createMenuButton("✨  Hero Ability",  "ability");
        Button objectiveBtn  = createMenuButton("🎯  Try Objective", "objective");
        Button diceButton    = createMenuButton("🎲  Roll Dice",     "dice");

        Region sep3 = createSeparator();

        // View buttons
        Button seeDeckButton      = createMenuButton("📚  View Deck",       "view");
        Button seeDiscardButton   = createMenuButton("🗑  Discard Pile",    "view");
        Button seeObjectiveDeckBtn= createMenuButton("🏆  Objective Deck",  "view");

        // Wire up actions
        drawButton.setOnAction(e -> {
            Player current = GameEngine.getCurrentPlayer();
            GameEngine.drawCard(current);
            updateTurnLabel();
            BoardView.refresh();
            endTurn(current);
        });

        playButton.setOnAction(e -> {
            Player current = GameEngine.getCurrentPlayer();
            if (!current.HandIsEmpty()) {
                int handIndex = GameChoice.selectCardsInHand(current);
                if (handIndex >= 0) GameEngine.playCard(current, handIndex);
            }
            updateTurnLabel();
            BoardView.refresh();
            endTurn(current);
        });

        abilityButton.setOnAction(e -> {
            Player current = GameEngine.getCurrentPlayer();
            int heroIndex = GameChoice.selectHeroCard(current);
            if (heroIndex >= 0) GameEngine.useHeroAbility(current, heroIndex);
            updateTurnLabel();
            BoardView.refresh();
            endTurn(current);
        });

        objectiveBtn.setOnAction(e -> {
            Player current = GameEngine.getCurrentPlayer();
            int objIndex = GameChoice.selectObjective();
            if (objIndex >= 0) GameEngine.tryObjective(current, objIndex);
            updateTurnLabel();
            BoardView.refresh();
            endTurn(current);
        });

        diceButton.setOnAction(e -> {
            int result = DiceUtils.getRoll();
            System.out.println("Dice result: " + result);
        });

        seeDeckButton.setOnAction(e -> openDeckWindow());
        seeDiscardButton.setOnAction(e -> openDiscardWindow());
        seeObjectiveDeckBtn.setOnAction(e -> openObjectiveDeckWindow());

        getChildren().addAll(
                menuLabel, sep1, turnLabel, sep2,
                drawButton, playButton, abilityButton, objectiveBtn, diceButton,
                sep3,
                seeDeckButton, seeDiscardButton, seeObjectiveDeckBtn
        );
    }

    // ── Factory helpers ──────────────────────────────────────────────────────

    private Button createMenuButton(String text, String variant) {
        Button btn = new Button(text);
        btn.setMaxWidth(Double.MAX_VALUE);
        btn.setAlignment(Pos.CENTER_LEFT);

        String base;
        if (variant.equals("view")) {
            base =
                    "-fx-background-color: linear-gradient(to bottom, #2a1a00, #1a0d00);" +
                            "-fx-text-fill: #C8A870;" +
                            "-fx-border-color: #5a3a10; -fx-border-width: 1;" +
                            "-fx-border-radius: 4; -fx-background-radius: 4;" +
                            "-fx-font-family: 'Georgia'; -fx-font-size: 11;" +
                            "-fx-padding: 5 8 5 8; -fx-cursor: hand;";
        } else {
            base =
                    "-fx-background-color: linear-gradient(to bottom, #4a2800, #2e1500);" +
                            "-fx-text-fill: #FFD700;" +
                            "-fx-border-color: #8B6914; -fx-border-width: 1;" +
                            "-fx-border-radius: 4; -fx-background-radius: 4;" +
                            "-fx-font-family: 'Georgia'; -fx-font-size: 11; -fx-font-weight: bold;" +
                            "-fx-padding: 6 8 6 8; -fx-cursor: hand;";
        }

        String hover =
                "-fx-background-color: linear-gradient(to bottom, #7a4500, #4e2800);" +
                        "-fx-text-fill: #FFFACD;" +
                        "-fx-border-color: #FFD700; -fx-border-width: 1;" +
                        "-fx-border-radius: 4; -fx-background-radius: 4;" +
                        "-fx-font-family: 'Georgia'; -fx-font-size: 11; -fx-font-weight: bold;" +
                        "-fx-padding: 6 8 6 8; -fx-cursor: hand;" +
                        "-fx-effect: dropshadow(gaussian, #FFD700, 6, 0.4, 0, 0);";

        btn.setStyle(base);
        btn.setOnMouseEntered(e -> btn.setStyle(hover));
        btn.setOnMouseExited(e -> btn.setStyle(base));
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

    // ── Turn label ───────────────────────────────────────────────────────────

    public static void updateTurnLabel() {
        if (turnLabel == null) return;
        if (!GameEngine.isGameActive()) {
            turnLabel.setText("Game Over");
            return;
        }
        Player current = GameEngine.getCurrentPlayer();
        turnLabel.setText(current.getName() + "\n⚡ AP: " + current.getActionPoint());
    }

    // ── Popup windows (deck / discard / objectives) ──────────────────────────

    private void openDeckWindow() {
        deckGrid = createPopupGrid();
        updateDeckGrid();

        ScrollPane scroll = createScrollPane(deckGrid);
        deckStage = buildPopupStage("📚  Game Deck", scroll);
        deckStage.show();

        GameEngine.deck.getGameDeck().addListener((ListChangeListener.Change<?> c) -> updateDeckGrid());
    }

    private void updateDeckGrid() {
        if (deckGrid == null) return;
        deckGrid.getChildren().clear();
        for (int i = 0; i < GameEngine.deck.getGameDeck().size(); i++) {
            deckGrid.getChildren().add(new CardView(GameEngine.deck.getGameDeck().get(i), i));
        }
    }

    private void openDiscardWindow() {
        discardGrid = createPopupGrid();
        updateDiscardGrid();

        ScrollPane scroll = createScrollPane(discardGrid);
        discardStage = buildPopupStage("🗑  Discard Pile", scroll);
        discardStage.show();

        GameEngine.deck.getDiscardPile().addListener((ListChangeListener.Change<?> c) -> updateDiscardGrid());
    }

    private void updateDiscardGrid() {
        if (discardGrid == null) return;
        discardGrid.getChildren().clear();
        for (int i = 0; i < GameEngine.deck.getDiscardPile().size(); i++) {
            discardGrid.getChildren().add(new CardView(GameEngine.deck.getDiscardPile().get(i), i));
        }
    }

    private void openObjectiveDeckWindow() {
        objectiveDeckGrid = createPopupGrid();
        updateObjectiveDeckGrid();

        ScrollPane scroll = createScrollPane(objectiveDeckGrid);
        objectiveDeckStage = buildPopupStage("🏆  Objective Deck", scroll);
        objectiveDeckStage.show();

        GameEngine.objectiveDeck.getObjectiveDeck().addListener((ListChangeListener.Change<?> c) -> updateObjectiveDeckGrid());
    }

    private void updateObjectiveDeckGrid() {
        if (objectiveDeckGrid == null) return;
        objectiveDeckGrid.getChildren().clear();
        for (int i = 0; i < GameEngine.objectiveDeck.getObjectiveDeck().size(); i++) {
            Objective obj = GameEngine.objectiveDeck.getObjectiveDeck().get(i);
            objectiveDeckGrid.getChildren().add(new ObjectiveView(obj, i));
        }
    }

    // ── Popup helpers ─────────────────────────────────────────────────────────

    /** A TilePane used as the card grid inside each popup. */
    private TilePane createPopupGrid() {
        TilePane grid = new TilePane();
        grid.setHgap(12);
        grid.setVgap(12);
        grid.setAlignment(Pos.TOP_LEFT);
        grid.setPadding(new Insets(16));
        grid.setStyle("-fx-background-color: transparent;");
        return grid;
    }

    /**
     * Wraps a TilePane in a styled ScrollPane so cards are always reachable
     * regardless of deck size.
     */
    private ScrollPane createScrollPane(TilePane grid) {
        ScrollPane scroll = new ScrollPane(grid);
        scroll.setFitToWidth(true);
        scroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scroll.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scroll.setStyle(
                "-fx-background: #1a0a00;" +
                        "-fx-background-color: #1a0a00;" +
                        // Style the scroll bar track / thumb to match the theme
                        "-fx-control-inner-background: #1a0a00;"
        );

        // Style the scroll bar itself via a lookup after the scene is shown
        scroll.skinProperty().addListener((obs, oldSkin, newSkin) -> {
            scroll.lookupAll(".scroll-bar").forEach(node -> node.setStyle(
                    "-fx-background-color: #2a1200;"
            ));
            scroll.lookupAll(".increment-button, .decrement-button").forEach(node ->
                    node.setStyle("-fx-background-color: #4a2800;")
            );
            scroll.lookupAll(".thumb").forEach(node ->
                    node.setStyle("-fx-background-color: #8B6914; -fx-background-radius: 3;")
            );
        });

        return scroll;
    }

    /**
     * Builds the popup Stage with a title bar label and the scrollable content.
     */
    private Stage buildPopupStage(String title, ScrollPane scroll) {
        // Header bar
        Label titleLabel = new Label(title);
        titleLabel.setStyle(
                "-fx-font-family: 'Georgia'; -fx-font-size: 13; -fx-font-weight: bold;" +
                        "-fx-text-fill: #FFD700;" +
                        "-fx-effect: dropshadow(gaussian, #FF8C00, 4, 0.5, 0, 0);"
        );
        titleLabel.setPadding(new Insets(10, 16, 10, 16));
        titleLabel.setMaxWidth(Double.MAX_VALUE);

        Region headerSep = new Region();
        headerSep.setPrefHeight(1);
        headerSep.setMaxWidth(Double.MAX_VALUE);
        headerSep.setStyle("-fx-background-color: linear-gradient(to right, transparent, #8B6914, transparent);");

        VBox root = new VBox(0, titleLabel, headerSep, scroll);
        VBox.setVgrow(scroll, Priority.ALWAYS);
        root.setStyle(
                "-fx-background-color: linear-gradient(to bottom, #1a0a00, #2a1200);" +
                        "-fx-border-color: #8B6914; -fx-border-width: 2;"
        );

        Scene scene = new Scene(root, 640, 480);
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