package slither.cells.foodcells;

import javafx.scene.paint.Color;

public class PoisonFood extends FoodCellAbstract{

    public PoisonFood(double x, double y) {
        super(7, x, y, 0);
        this.setFill(Color.PURPLE);
    }

}
