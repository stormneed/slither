package slither.cells;

import javafx.scene.paint.Color;
import slither.SnakeDirection;

public class SnakeCellBase extends SnakeCell {

    public SnakeCellBase(double x, double y, Color c, SnakeCell prev) {
        super(5);
        this.setFill(c);
        this.setCenterX(x);
        this.setCenterY(y);
        this.setPrevDirection(prev.getPrevDirection());
        this.setColor(c);
    }
    public SnakeCellBase(double x, double y, Color c, SnakeCell prev, boolean outline) {
        super(5);
        this.setFill(c);
        if (outline) {
            this.setStroke(javafx.scene.paint.Color.BLACK);
        }
        this.setCenterX(x);
        this.setCenterY(y);
        this.setPrevDirection(prev.getPrevDirection());
        this.setColor(c);
    }

    public SnakeCellBase(double x, double y, Color c) {
        super(5);
        this.setFill(c);
        this.setCenterX(x);
        this.setCenterY(y);
        this.setPrevDirection(SnakeDirection.UP);
        this.setColor(c);
    }
    public SnakeCellBase(double x, double y, Color c, boolean outline) {
        super(5);
        this.setFill(c);
        if (outline) {
            this.setStroke(javafx.scene.paint.Color.BLACK);
        }
        this.setCenterX(x);
        this.setCenterY(y);
        this.setPrevDirection(SnakeDirection.UP);
        this.setColor(c);
    }

}
