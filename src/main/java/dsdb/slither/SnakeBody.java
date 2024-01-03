package dsdb.slither;

import javafx.scene.shape.Circle;
import javafx.util.Pair;

public class SnakeBody extends Circle {
    SnakeDirection prevDirection;

    SnakeBody next;
    SnakeBody prev;
    SnakeDirection prevDirection;



    public SnakeBody (double x, double y, SnakeDirection prev) {
        super(5);
        this.setFill(javafx.scene.paint.Color.RED);
        this.setStroke(javafx.scene.paint.Color.BLACK);
        this.setCenterX(x);
        this.setCenterY(y);
        this.prevDirection=prev;
    }

    public SnakeBody(double x, double y) {
        new SnakeBody(x,y,SnakeDirection.UP);
    }

    public void setNext(SnakeBody next) {
        this.next = next;
    }

    public void setPrev(SnakeBody prev) {
        this.prev = prev;
    }
    public void setPos(double x, double y) {
        this.setCenterX(x);
        this.setCenterY(y);
    }
    public SnakeBody getNext() {
        return next;
    }
    public SnakeBody getPrev() {
        return prev;
    }
    public double getX() {
        return this.getCenterX();
    }
    public double getY() {
        return this.getCenterY();
    }

    public void move(SnakeDirection direction) {

        SnakeDirection validDir;
        if (validMove(direction)) validDir = direction;
        else validDir = oppositeMove(direction);
        switch (validDir) {
            case UP:
                if (this.getCenterY() == 0) {
                    setCenterY(990);
                } else {
                    setCenterY(this.getCenterY() - 10);
                }

                break;
            case DOWN:
                if (this.getCenterY() == 990) {
                    setCenterY(0);
                } else {
                    setCenterY(this.getCenterY() + 10);
                }
                break;
            case LEFT:
                if (this.getCenterX() == 0) {
                    setCenterX(990);
                } else {
                    setCenterX(this.getCenterX() - 10);
                }
                break;
            case RIGHT:
                if (this.getCenterX() == 990) {
                    setCenterX(0);
                } else {
                    setCenterX(this.getCenterX() + 10);
                }
                break;
            default:
                break;
        }
        if (next != null) {
            next.move(prevDirection);
        }
        prevDirection = validDir;

    }

    private boolean validMove(SnakeDirection direction) {
        return !prevDirection.equals(oppositeMove(direction));
    }

    private SnakeDirection oppositeMove (SnakeDirection direction) {
        switch (direction) {
            case UP : return SnakeDirection.DOWN;
            case LEFT : return SnakeDirection.RIGHT;
            case DOWN : return SnakeDirection.UP;
            case RIGHT : return SnakeDirection.LEFT;
            default: return null;
        }
    }

}
