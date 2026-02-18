package gui.card;

import entities.card.BaseCard;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;

public class BaseCardView extends CardView {
    public BaseCardView(BaseCard card) {
        super(card, 75, 105, Color.LIGHTGRAY);

        // Build resource path based on card name
        String path = "/card/base/" + card.getType().toLowerCase() + "/" + card.getName() + ".png";

        // Defensive: check resource exists
        var resource = getClass().getResource(path);
        if (resource != null) {
            Image img = new Image(resource.toExternalForm(), 75, 105, false, true);
            ImageView imageView = new ImageView(img);
            imageView.setFitWidth(75);
            imageView.setFitHeight(105);

            // Replace default rectangle + label with image
            getChildren().clear();
            getChildren().add(imageView);
        } else {
            System.err.println("Image not found for card: " + card.getName() + " at " + path);
        }
    }
}
