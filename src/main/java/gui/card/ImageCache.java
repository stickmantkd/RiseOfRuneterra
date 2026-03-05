package gui.card;

import javafx.scene.image.Image;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**

 High quality image cache.

 Images are loaded once at original resolution and reused everywhere.

 JavaFX GPU handles scaling so thumbnails stay sharp.
 */
public final class ImageCache {

    private static final Map<String, Image> CACHE = new HashMap<>();

// ── Standard sizes used by UI ─────────────────────────────────

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

// ── Main loader ───────────────────────────────────────────────

    /**

     Loads an image once at full resolution.

     All UI elements reuse the same image object.
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
                0,
                0,
                true, // preserve ratio
                true, // smooth scaling
                false // load immediately
        );

        CACHE.put(resourcePath, img);

        return img;
    }

// ── Path builders ─────────────────────────────────────────────

    public static String cardPath(String type, String name) {
        return "/card/base/" + type.toLowerCase() + "/"
                + name.replaceAll("\s+", "") + ".png";
    }

    public static String leaderPath(String name) {
        return "/card/leader/" + name.replaceAll("\s+", "") + ".png";
    }

    public static String objectivePath(String name) {
        return "/card/objective/" + name.replaceAll("\s+", "") + ".png";
    }

    public static String itemPath(String name) {
        return "/card/base/item card/" + name.replaceAll("\s+", "") + ".png";
    }

    public static void clear() {
        CACHE.clear();
    }
}