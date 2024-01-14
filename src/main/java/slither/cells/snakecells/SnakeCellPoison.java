package slither.cells.snakecells;

import javafx.scene.paint.Color;

public class SnakeCellPoison extends SnakeCell{

    public SnakeCellPoison(double x, double y) {
        super(5);
        this.setFill(Color.PURPLE);
        this.setStroke(Color.DARKRED);
        this.setCenterX(x);
        this.setCenterY(y);
    }

}
