package slither;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import slither.cells.SnakeCell;
import slither.cells.SnakeCellBase;

public class SnakeFactory {
    public static Pane createEmptySpace() {
        Pane pane = new Pane();
        pane.setStyle("-fx-background-color: #ffffff");
        pane.setPrefSize(10, 10);
        pane.setBorder(new javafx.scene.layout.Border(new javafx.scene.layout.BorderStroke(javafx.scene.paint.Color.BLACK, javafx.scene.layout.BorderStrokeStyle.SOLID, null, new javafx.scene.layout.BorderWidths(1))));
        return pane;
    }

    public static Circle createSnake(int x, int y) {
        Circle circle = new Circle(5);
        circle.setStyle("-fx-background-color: #ff0000");
        circle.setCenterX(x);
        circle.setCenterY(y);
        circle.setStroke(javafx.scene.paint.Color.BLACK);
        return circle;
    }

    public static Circle createFood(double x, double y) {
        Circle pane = new Circle(5);
        pane.setCenterX(x);
        pane.setCenterY(y);
        pane.setFill(new Color(0,1,0.3,1));
        return pane;
    }
}
