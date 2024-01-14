package slither.cells;

import javafx.scene.paint.Color;


public class BasicFood extends FoodCellAbstract {
    
    public BasicFood(double x, double y, int weight) {
        super(x, y, weight);
        this.setFill(Color.GREEN);
    }

}
