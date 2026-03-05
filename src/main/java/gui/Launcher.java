package gui;

/**
 * A separate launcher class used to start the JavaFX application.
 * <p>
 * This class does not extend {@code Application}. It acts as a wrapper
 * to bypass JavaFX module path restrictions, which is a common workaround
 * required to run the application smoothly when packaged as a fat JAR.
 */
public class Launcher {

    /**
     * The main entry point of the application.
     * It simply delegates the execution to the main {@link GameUI} class.
     *
     * @param args Command-line arguments passed to the application.
     */
    public static void main(String[] args) {
        gui.GameUI.main(args);
    }
}