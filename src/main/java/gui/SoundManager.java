package gui;

import javafx.scene.media.AudioClip;

public class SoundManager {

    private static final AudioClip CLICK =
            new AudioClip(SoundManager.class.getResource("/sfx/buttonclick.wav").toExternalForm());

    private static final AudioClip HOVER =
            new AudioClip(SoundManager.class.getResource("/sfx/cardhover.wav").toExternalForm());

    private static final AudioClip DICE =
            new AudioClip(SoundManager.class.getResource("/sfx/diceroll.wav").toExternalForm());

    public static void click() {
        CLICK.play();
    }

    public static void hover() {
        HOVER.play();
    }

    public static void dice() {
        DICE.play();
    }
}