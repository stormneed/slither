package dsdb.slither;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class SnakeCell extends Circle {
    SnakeDirection prevDirection;
     Color color;
    SnakeCell next;
    SnakeCell prev;



    public SnakeCell(double x, double y, Color c, SnakeDirection prev) {
        super(5);
        this.setFill(c);
        this.setStroke(javafx.scene.paint.Color.BLACK);
        this.setCenterX(x);
        this.setCenterY(y);
        this.prevDirection=prev;
        color=c;
    }

    public SnakeCell(double x, double y, Color c) {
        super(5);
        this.setFill(c);
        this.setStroke(javafx.scene.paint.Color.BLACK);
        this.setCenterX(x);
        this.setCenterY(y);
        this.prevDirection=SnakeDirection.UP;
        color=c;
    }

    public void setNext(SnakeCell next) {
        this.next = next;
    }

    public void setPrev(SnakeCell prev) {
        this.prev = prev;
    }
    public void setPos(double x, double y) {
        this.setCenterX(x);
        this.setCenterY(y);
    }
    public SnakeCell getNext() {
        return next;
    }
    public SnakeCell getPrev() {
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
                    setCenterY(790);
                } else {
                    setCenterY(this.getCenterY() - 10);
                }

                break;
            case DOWN:
                if (this.getCenterY() == 790) {
                    setCenterY(0);
                } else {
                    setCenterY(this.getCenterY() + 10);
                }
                break;
            case LEFT:
                if (this.getCenterX() == 0) {
                    setCenterX(790);
                } else {
                    setCenterX(this.getCenterX() - 10);
                }
                break;
            case RIGHT:
                if (this.getCenterX() == 790) {
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
