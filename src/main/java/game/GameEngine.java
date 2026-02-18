package game;

import gui.GameUI;
import javafx.application.Application;

public class GameEngine {

    // Entry point for Gradle run
    public static void main(String[] args) {
        System.out.println("Launching Rise of Runeterra...");

        // Directly launch JavaFX GUI
        Application.launch(GameUI.class, args);
    }
}
