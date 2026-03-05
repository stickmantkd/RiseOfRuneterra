package gui.card;

import javafx.scene.image.Image;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * A high-quality image cache for the application.
 * <p>
 * Images are loaded once at their original resolution and reused throughout the UI.
 * JavaFX GPU handles the scaling so thumbnails remain sharp without using extra memory.
 */
public final class ImageCache {

    private static final Map<String, Image> CACHE = new HashMap<>();

    // --- Standard Card Thumbnail Sizes ---
    public static final double THUMB_W = 75;
    public static final double THUMB_H = 105;

    // --- Standard Leader Sizes ---
    public static final double LEADER_W = 90;
    public static final double LEADER_H = 126;

    // --- Standard Full Card Sizes ---
    public static final double FULL_W = 200;
    public static final double FULL_H = 280;

    public static final double FULL_LEADER_W = 240;
    public static final double FULL_LEADER_H = 336;

    // --- Standard Objective Sizes ---
    public static final double OBJ_THUMB_W = 150;
    public static final double OBJ_THUMB_H = 210;

    public static final double OBJ_FULL_W = 360;
    public static final double OBJ_FULL_H = 504;

    // --- Standard Item Overlay Sizes ---
    public static final double ITEM_W = 26;
    public static final double ITEM_H = 36;

    /**
     * Private constructor to prevent instantiation of this utility class.
     */
    private ImageCache() {}

    /**
     * Retrieves an image from the cache or loads it if it doesn't exist.
     * All UI elements reuse the same Image object to optimize memory.
     *
     * @param resourcePath The relative path to the image resource.
     * @return The cached Image object, or null if the resource is not found.
     */
    public static Image get(String resourcePath) {
        if (CACHE.containsKey(resourcePath)) {
            return CACHE.get(resourcePath);
        }

        URL url = ImageCache.class.getResource(resourcePath);

        if (url == null) {
            CACHE.put(resourcePath, null);
            return null;
        }

        Image img = new Image(
                url.toExternalForm(),
                0,     // requested width (0 = original)
                0,     // requested height (0 = original)
                true,  // preserve ratio
                true,  // smooth scaling
                false  // load immediately (background loading disabled)
        );

        CACHE.put(resourcePath, img);

        return img;
    }

    // --- Path Builders ---

    public static String cardPath(String type, String name) {
        return "/card/base/" + type.toLowerCase() + "/" + name.replaceAll("\\s+", "") + ".png";
    }

    public static String leaderPath(String name) {
        return "/card/leader/" + name.replaceAll("\\s+", "") + ".png";
    }

    public static String objectivePath(String name) {
        return "/card/objective/" + name.replaceAll("\\s+", "") + ".png";
    }

    public static String itemPath(String name) {
        return "/card/base/item card/" + name.replaceAll("\\s+", "") + ".png";
    }

    /**
     * Clears all cached images to free up memory.
     */
    public static void clear() {
        CACHE.clear();
    }
}