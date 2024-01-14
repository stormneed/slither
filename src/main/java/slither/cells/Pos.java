package slither.cells;

public class Pos {
    public double x;
    public double y;

    public Pos(double x, double y) {
        this.x = x;
        this.y = y;
    }
    public void setPos(double x, double y) {
        this.x = x;
        this.y = y;
    }
    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }
}
