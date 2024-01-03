package dsdb.slither;

import javafx.scene.shape.Circle;
import javafx.util.Pair;

public class SnakeBody extends Circle {
    SnakeDirection prevDirection;

    SnakeBody next;

    SnakeBody prev;

    public SnakeBody getNext() {
        return next;
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

    public SnakeBody(double x, double y) {
        super(5);
        this.setFill(javafx.scene.paint.Color.RED);
        this.setStroke(javafx.scene.paint.Color.BLACK);
        this.setCenterX(x);
        this.setCenterY(y);
    }

    public void setPrevDirection(SnakeDirection prevDirection) {
        this.prevDirection = prevDirection;
    }

    public SnakeDirection getPrevDirection() {
        return prevDirection;
    }

    public double getX() {
        return this.getCenterX();
    }
    public double getY() {
        return this.getCenterY();
    }

    public Pair<Double,Double> nextPos(SnakeDirection direction) {
        double newX = -1;
        double newY = -1;
        switch (direction) {
            case UP:
                if (this.getCenterY() == 0) {
                    newY = 990;
                } else {
                    newY =(this.getCenterY() - 10);
                }

                break;
            case DOWN:
                if (this.getCenterY() == 990) {
                    newY = 0;
                } else {
                    newY=(this.getCenterY() + 10);
                }
                break;
            case LEFT:
                if (this.getCenterX() == 0) {
                    newX = 990;
                } else {
                    newX=this.getCenterX() - 10;
                }
                break;
            case RIGHT:
                if (this.getCenterX() == 990) {
                    newX = 0;
                } else {
                    newX=this.getCenterX() + 10;
                }
                break;
        }
        return new Pair<>(newX, newY);
    }

    public void moveOne(Pair<Double,Double> nextpos) {
        if (nextpos.getKey() != -1) {
            this.setCenterX(nextpos.getKey());
        }
        if (nextpos.getValue() != -1) {
            this.setCenterY(nextpos.getValue());
        }
    }
    public void move_rec() {

        if (prev!=null) {
            setPos(prev.getX(), prev.getY());
            prev.move_rec();
        }
    }



}
