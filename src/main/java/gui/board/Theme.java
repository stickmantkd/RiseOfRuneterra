package gui.board;

/**
 * Centralized dark-fantasy theme constants used across all player areas.
 * <p>
 * Consolidates styling to ensure a consistent look and feel across the game board UI.
 */
public final class Theme {

    // Prevent instantiation of utility class
    private Theme() {}

    // --- Core Palette & Typography ---
    // แยกสีและฟอนต์หลักออกมา เพื่อให้แก้แค่จุดเดียวแล้วเปลี่ยนได้ทั้งเกม
    private static final String COLOR_GOLD = "#FFD700";
    private static final String COLOR_GOLD_DARK = "#8B6914";
    private static final String FONT_FAMILY = "'Georgia'";

    // --- Panel Backgrounds (Per Position) ---
    public static final String TOP_BG    = "-fx-background-color: linear-gradient(to bottom, #0d1f2d, #162840);";
    public static final String BOTTOM_BG = "-fx-background-color: linear-gradient(to top,    #0d1f2d, #162840);";
    public static final String LEFT_BG   = "-fx-background-color: linear-gradient(to right,  #12200d, #1c3014);";
    public static final String RIGHT_BG  = "-fx-background-color: linear-gradient(to left,   #2d0d1a, #401226);";

    // --- Borders ---
    public static final String BORDER_NORMAL  = "-fx-border-color: #3a3020; -fx-border-width: 1;";

    public static final String BORDER_ACTIVE  =
            "-fx-border-color: " + COLOR_GOLD + "; -fx-border-width: 3;" +
                    "-fx-effect: dropshadow(gaussian, " + COLOR_GOLD + ", 12, 0.5, 0, 0);";

    // --- Labels ---
    public static final String NAME_LABEL =
            "-fx-font-family: " + FONT_FAMILY + ";" +
                    "-fx-font-size: 12;" +
                    "-fx-font-weight: bold;" +
                    "-fx-text-fill: " + COLOR_GOLD + ";";

    // --- Buttons ---
    public static final String SEE_MORE_BUTTON =
            "-fx-background-color: linear-gradient(to bottom, #4a2800, #2e1500);" +
                    "-fx-text-fill: " + COLOR_GOLD + ";" +
                    "-fx-border-color: " + COLOR_GOLD_DARK + "; -fx-border-width: 1;" +
                    "-fx-border-radius: 3; -fx-background-radius: 3;" +
                    "-fx-font-family: " + FONT_FAMILY + "; -fx-font-size: 10;" +
                    "-fx-padding: 3 6 3 6; -fx-cursor: hand;";

    // ✨ เพิ่ม Hover State สำหรับปุ่ม See More (ให้เรืองแสงเวลาเอาเมาส์ไปชี้)
    public static final String SEE_MORE_BUTTON_HOVER =
            "-fx-background-color: linear-gradient(to bottom, #7a4500, #4e2800);" +
                    "-fx-text-fill: #FFFACD;" +
                    "-fx-border-color: " + COLOR_GOLD + "; -fx-border-width: 1;" +
                    "-fx-border-radius: 3; -fx-background-radius: 3;" +
                    "-fx-font-family: " + FONT_FAMILY + "; -fx-font-size: 10;" +
                    "-fx-padding: 3 6 3 6; -fx-cursor: hand;" +
                    "-fx-effect: dropshadow(gaussian, " + COLOR_GOLD + ", 4, 0.4, 0, 0);";
}