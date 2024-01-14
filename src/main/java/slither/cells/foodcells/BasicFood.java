package slither.cells.foodcells;

import javafx.scene.paint.Color;


public class BasicFood extends FoodCellAbstract {
    
    public BasicFood(double x, double y, int weight) {
        super(5, x, y, weight);
        this.setFill(Color.GREEN);
    }

}
