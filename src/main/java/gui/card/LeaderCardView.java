package gui.card;

import entities.card.LeaderCard;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class LeaderCardView extends CardView {
    public LeaderCardView(LeaderCard card) {
        super(card, 90, 126, javafx.scene.paint.Color.DARKGRAY);

        // Load image based on leaderName
        String path = "/card/leader/" + card.getName()     + ".png";
        Image img = new Image(getClass().getResource(path).toExternalForm());
        ImageView imageView = new ImageView(img);

        imageView.setFitWidth(90);
        imageView.setFitHeight(126);

        getChildren().clear();
        getChildren().add(imageView);
    }
}
