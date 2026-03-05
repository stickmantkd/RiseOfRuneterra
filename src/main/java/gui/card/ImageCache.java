package gui.card;

import javafx.scene.image.Image;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * Singleton image cache — every PNG is loaded only once per size.
 * Prevents repeated disk I/O and keeps JavaFX memory usage stable.
 */
public final class ImageCache {

    private static final Map<String, Image> CACHE = new HashMap<>();

    // ── Standard dimensions ─────────────────────────────────────────────

    public static final double THUMB_W = 75;
    public static final double THUMB_H = 105;

    public static final double LEADER_W = 90;
    public static final double LEADER_H = 126;

    public static final double FULL_W = 200;
    public static final double FULL_H = 280;

    public static final double FULL_LEADER_W = 240;
    public static final double FULL_LEADER_H = 336;

    public static final double OBJ_THUMB_W = 150;
    public static final double OBJ_THUMB_H = 210;

    public static final double OBJ_FULL_W = 360;
    public static final double OBJ_FULL_H = 504;

    public static final double ITEM_W = 26;
    public static final double ITEM_H = 36;

    private ImageCache() {}

    // ── Main cache loader ───────────────────────────────────────────────

    /**
     * Load an image at a specific render size.
     * The same (path + size) image is only loaded once.
     */
    public static Image get(String resourcePath, double w, double h) {

        String key = resourcePath + "@" + (int) w + "x" + (int) h;

        if (CACHE.containsKey(key)) {
            return CACHE.get(key);
        }

        URL url = ImageCache.class.getResource(resourcePath);

        if (url == null) {
            CACHE.put(key, null);
            return null;
        }

        Image img = new Image(
                url.toExternalForm(),
                w,
                h,
                false,  // preserveRatio
                true,   // smooth scaling
                false   // backgroundLoading (load immediately)
        );

        CACHE.put(key, img);

        return img;
    }

    /**
     * Load image at original resolution.
     */
    public static Image get(String resourcePath) {
        return get(resourcePath, 0, 0);
    }

    // ── Path helpers ────────────────────────────────────────────────────

    public static String cardPath(String type, String name) {
        return "/card/base/" + type.toLowerCase() + "/"
                + name.replaceAll("\\s+", "") + ".png";
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

    // ── Cache control ───────────────────────────────────────────────────

    /**
     * Clears the entire cache.
     * Useful when restarting the game to free memory.
     */
    public static void clear() {
        CACHE.clear();
    }
}