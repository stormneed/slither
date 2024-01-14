package slither.cells.foodcells;

import javafx.scene.paint.Color;

public class SpeedFood extends FoodCellAbstract{

    private boolean accelerate;

    public SpeedFood(double x, double y, int weight, boolean accelerate) {
        super(7, x, y, weight);
        if (accelerate) {
            this.setFill(Color.CYAN);
            this.setStroke(Color.DARKBLUE);
        }
        else {
            this.setFill(Color.ORANGE);
            this.setStroke(Color.DARKRED);
        }
        this.accelerate = accelerate;
    }

    public boolean isAccelerate() {
        return accelerate;
    }
}
