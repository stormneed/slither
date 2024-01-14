package slither.cells.foodcells;

import javafx.scene.paint.Color;

public class PoisonedDeathFood extends DeathFood {

    public PoisonedDeathFood(double x, double y) {
        super(x, y);
        this.setFill(Color.PALEVIOLETRED);
        this.setStroke(Color.DARKRED);
        this.setStrokeWidth(1);
    }

}
