package gui.board.field;

import NonGui.BaseEntity.Objective;
import gui.card.ObjectiveView;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * Represents the central objectives display area on the game board.
 * <p>
 * Displays the current active objectives. If an objective slot is empty,
 * a styled placeholder is shown instead.
 */
public class ObjectivesBox extends HBox {

    // --- Layout Constants ---
    private static final int SPACING = 12;
    private static final int PADDING = 6;

    // --- Placeholder Constants ---
    private static final double PH_WIDTH = 150;
    private static final double PH_HEIGHT = 210;
    private static final double PH_ARC = 4;

    // --- Style Constants ---
    private static final String PH_FILL_COLOR = "#0d1a0d";
    private static final double PH_FILL_OPACITY = 0.5;
    private static final String PH_STROKE_COLOR = "#3a4a20";
    private static final double PH_STROKE_WIDTH = 1.5;

    private static final String PH_LABEL_STYLE =
            "-fx-text-fill: " + PH_STROKE_COLOR + "; " +
                    "-fx-font-family: 'Georgia'; " +
                    "-fx-font-size: 12; " +
                    "-fx-text-alignment: center;";

    /**
     * Constructs the ObjectivesBox to display the given array of objectives.
     *
     * @param objectives The array of current game objectives.
     */
    public ObjectivesBox(Objective[] objectives) {
        super(SPACING);
        setupLayout();
        populateObjectives(objectives);
    }

    /**
     * Configures the basic layout properties of the main HBox.
     */
    private void setupLayout() {
        setAlignment(Pos.CENTER);
        setPadding(new Insets(PADDING));
    }

    /**
     * Iterates through the objectives array and populates the box with
     * either the objective card view or a placeholder if the slot is empty.
     */
    private void populateObjectives(Objective[] objectives) {
        if (objectives == null) return;

        for (int i = 0; i < objectives.length; i++) {
            if (objectives[i] != null) {
                getChildren().add(new ObjectiveView(objectives[i], i));
            } else {
                getChildren().add(buildPlaceholder());
            }
        }
    }

    /**
     * Builds an empty placeholder slot for missing objectives.
     */
    private StackPane buildPlaceholder() {
        Rectangle rect = new Rectangle(PH_WIDTH, PH_HEIGHT);
        rect.setFill(Color.web(PH_FILL_COLOR, PH_FILL_OPACITY));
        rect.setStroke(Color.web(PH_STROKE_COLOR));
        rect.setStrokeWidth(PH_STROKE_WIDTH);
        rect.setArcWidth(PH_ARC);
        rect.setArcHeight(PH_ARC);
        rect.setMouseTransparent(true);

        Label lbl = new Label("🏆\nEmpty");
        lbl.setStyle(PH_LABEL_STYLE);
        lbl.setMouseTransparent(true);

        StackPane sp = new StackPane(rect, lbl);
        sp.setMouseTransparent(true); // Ensures clicks pass through to parent if needed
        return sp;
    }
}