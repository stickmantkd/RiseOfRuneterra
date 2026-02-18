package gui.card;

import entities.card.ObjectiveCard;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;

public class ObjectiveCardView extends CardView {
    public ObjectiveCardView(ObjectiveCard card) {
        super(card, 150, 210, Color.BEIGE);

        // Load image based on objectiveName
        String path = "/card/objective/" + card.getObjectiveName() + ".png";
        Image img = new Image(getClass().getResource(path).toExternalForm());
        ImageView imageView = new ImageView(img);

        imageView.setFitWidth(150);
        imageView.setFitHeight(210);

        getChildren().clear();
        getChildren().add(imageView);
    }
}
