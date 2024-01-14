package slither.cells.foodcells;
import javafx.scene.shape.Circle;
import slither.cells.snakecells.SnakeCell;


public abstract class FoodCellAbstract extends Circle{
    public int weight;
    public static int numFood=0;
    public boolean eaten= false;

    public FoodCellAbstract(double d, double x, double y, int weight) {
        super(d);
        this.setCenterX(x);
        this.setCenterY(y);
        this.weight = weight;
        numFood++;
    }

    public boolean isOverlap (SnakeCell head) {
        if (head == null) return false;

        return Math.abs(this.getCenterX()- head.getX())<10 && Math.abs(this.getCenterY()- head.getY())<10;
    }

    public int getWeight() {
        return weight;
    }

    public boolean isEaten() {
        return eaten;
    }

    public void setEaten(boolean eaten) {
        this.eaten = eaten;
        if (eaten) {
            numFood--;
        }
    }


}
