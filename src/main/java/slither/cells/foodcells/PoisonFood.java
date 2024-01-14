package slither.cells.foodcells;

import javafx.scene.paint.Color;

public class PoisonFood extends FoodCellAbstract{

    public PoisonFood(double x, double y, int weight) {
        super(7, x, y, weight);
        this.setFill(Color.PURPLE);
    }

}
