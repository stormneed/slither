package slither.cells.foodcells;

import javafx.scene.paint.Color;

public class DeathFood extends FoodCellAbstract {

    public DeathFood(double x, double y) {
        super(3,x, y, 2);
       
        this.setFill(Color.BLACK);
        this.setStroke(Color.PURPLE);
        this.setStrokeWidth(2);
    }
}
