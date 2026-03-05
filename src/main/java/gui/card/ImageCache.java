package gui.card;

import javafx.scene.image.Image;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * Singleton image cache — every PNG is loaded from disk exactly once.
 * All subsequent requests for the same path return the cached instance,
 * preventing repeated disk I/O and the heap bloat that causes OOM crashes.
 *
 * Usage:
 *   Image img = ImageCache.get("/card/base/hero/Garen.png", 75, 105);
 *   // Returns null if the resource does not exist on the classpath.
 */
public final class ImageCache {

    // One Image object per (path + rendered size). Never grows unboundedly
    // because the number of unique card images in the game is fixed.
    private static final Map<String, Image> CACHE = new HashMap<>();

    // ── Standard dimensions ───────────────────────────────────────────────────
    public static final double THUMB_W        = 75;
    public static final double THUMB_H        = 105;
    public static final double LEADER_W       = 90;
    public static final double LEADER_H       = 126;
    public static final double FULL_W         = 200;
    public static final double FULL_H         = 280;
    public static final double FULL_LEADER_W  = 240;
    public static final double FULL_LEADER_H  = 336;
    public static final double OBJ_THUMB_W    = 150;
    public static final double OBJ_THUMB_H    = 210;
    public static final double OBJ_FULL_W     = 360;
    public static final double OBJ_FULL_H     = 504;
    public static final double ITEM_W         = 26;
    public static final double ITEM_H         = 36;

    private ImageCache() {}

    /**
     * Returns a cached {@link Image} decoded at the requested display size,
     * or {@code null} if the classpath resource does not exist.
     *
     * Background loading is intentionally OFF so the image is fully decoded
     * before the node enters the scene — this eliminates the "grey flash"
     * flicker that happens on every BoardView.refresh() call.
     *
     * @param resourcePath  absolute classpath path, e.g. "/card/base/hero card/Garen.png"
     * @param w             target width  (0 = natural)
     * @param h             target height (0 = natural)
     */
    public static Image get(String resourcePath, double w, double h) {
        String key = resourcePath + "@" + (int) w + "x" + (int) h;

        if (CACHE.containsKey(key)) {
            return CACHE.get(key); // null entries are cached too (missing files)
        }

        URL url = ImageCache.class.getResource(resourcePath);
        if (url == null) {
            CACHE.put(key, null);
            return null;
        }

        Image img = new Image(
            url.toExternalForm(),
            w, h,
            false,  // preserveRatio — caller controls exact size via ImageView
            true,   // smooth
            false   // backgroundLoading — synchronous, ready before scene render
        );

        CACHE.put(key, img);
        return img;
    }

    /** Loads at natural resolution. Prefer the sized overload where possible. */
    public static Image get(String resourcePath) {
        return get(resourcePath, 0, 0);
    }

    // ── Path builders (single source of truth for naming convention) ──────────

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

    /** Clears the cache (call on game restart to free memory if needed). */
    public static void clear() {
        CACHE.clear();
    }
}
