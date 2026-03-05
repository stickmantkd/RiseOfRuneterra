package gui.board;

/**
 * Centralized dark-fantasy theme constants used across all player areas.
 */
public final class Theme {

    private Theme() {}

    // Panel backgrounds per position
    public static final String TOP_BG    = "-fx-background-color: linear-gradient(to bottom, #0d1f2d, #162840);";
    public static final String BOTTOM_BG = "-fx-background-color: linear-gradient(to top,    #0d1f2d, #162840);";
    public static final String LEFT_BG   = "-fx-background-color: linear-gradient(to right,  #12200d, #1c3014);";
    public static final String RIGHT_BG  = "-fx-background-color: linear-gradient(to left,   #2d0d1a, #401226);";

    // Borders
    public static final String BORDER_NORMAL  = "-fx-border-color: #3a3020; -fx-border-width: 1;";
    public static final String BORDER_ACTIVE  = "-fx-border-color: #FFD700; -fx-border-width: 3;" +
                                                 "-fx-effect: dropshadow(gaussian, #FFD700, 12, 0.5, 0, 0);";

    // Name label style
    public static final String NAME_LABEL =
        "-fx-font-family: 'Georgia';" +
        "-fx-font-size: 12;" +
        "-fx-font-weight: bold;" +
        "-fx-text-fill: #FFD700;";

    // "See More" button
    public static final String SEE_MORE_BUTTON =
        "-fx-background-color: linear-gradient(to bottom, #4a2800, #2e1500);" +
        "-fx-text-fill: #FFD700;" +
        "-fx-border-color: #8B6914; -fx-border-width: 1;" +
        "-fx-border-radius: 3; -fx-background-radius: 3;" +
        "-fx-font-family: 'Georgia'; -fx-font-size: 10;" +
        "-fx-padding: 3 6 3 6; -fx-cursor: hand;";
}
