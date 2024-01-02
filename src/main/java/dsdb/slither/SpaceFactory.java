package dsdb.slither;

import javafx.scene.layout.Pane;

public class SpaceFactory {
    public static Pane createEmptySpace() {
        Pane pane = new Pane();
        pane.setStyle("-fx-background-color: #ffffff");
        pane.setPrefSize(10, 10);
        pane.setBorder(new javafx.scene.layout.Border(new javafx.scene.layout.BorderStroke(javafx.scene.paint.Color.BLACK, javafx.scene.layout.BorderStrokeStyle.SOLID, null, new javafx.scene.layout.BorderWidths(1))));
        return pane;
    }

    public static Pane createSnakeSpace() {
        Pane pane = new Pane();
        pane.setStyle("-fx-background-color: #ff0000");
        pane.setPrefSize(10, 10);

        pane.setBorder(new javafx.scene.layout.Border(new javafx.scene.layout.BorderStroke(javafx.scene.paint.Color.BLACK, javafx.scene.layout.BorderStrokeStyle.SOLID, null, new javafx.scene.layout.BorderWidths(1))));
        return pane;
    }

    public static Pane createFoodSpace() {
        Pane pane = new Pane();
        pane.setStyle("-fx-background-color: #00ff00");
        pane.setPrefSize(10, 10);

        pane.setBorder(new javafx.scene.layout.Border(new javafx.scene.layout.BorderStroke(javafx.scene.paint.Color.BLACK, javafx.scene.layout.BorderStrokeStyle.SOLID, null, new javafx.scene.layout.BorderWidths(1))));
        return pane;
    }
}
