package slither.cells;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import slither.SnakeDirection;

public abstract class SnakeCell extends Circle {
    private double speed;
    private SnakeDirection prevDirection;
    private Color color;
    private SnakeCell next;
    private SnakeCell prev;

    public SnakeCell (int n) {
        super(n);
        speed= 5.0;
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
    public void setX(double x) {this.setCenterX(x);}
    public void setY(double y) {this.setCenterY(y);}

    public void moveHead(SnakeDirection direction) {
        SnakeDirection validDir;
        double prevx = this.getCenterX();
        double prevy = this.getCenterY();
        if (validMove(direction)) validDir = direction;
        else validDir = oppositeMove(direction);
        switch (validDir) {
            case UP:
                if (this.getCenterY() <= 0) {
                    setCenterY(790);
                } else {
                    setCenterY(this.getCenterY() - speed);
                }
                break;
            case DOWN:
                if (this.getCenterY() >= 790) {
                    setCenterY(0);
                } else {
                    setCenterY(this.getCenterY() + speed);
                }
                break;
            case LEFT:
                if (this.getCenterX() <= 0) {
                    setCenterX(790);
                } else {
                    setCenterX(this.getCenterX() - speed);
                }
                break;
            case RIGHT:
                if (this.getCenterX() >= 790) {
                    setCenterX(0);
                } else {
                    setCenterX(this.getCenterX() + speed);
                }
                break;
            default:
                break;
        }
        if (next != null) {
            next.moveBody(prevx,prevy);
        }

        prevDirection = validDir;

    }

    public void moveBody(double x, double y) {
        if (next != null) {
            next.moveBody(this.getCenterX(),this.getCenterY());
        }
        this.setCenterX(x);
        this.setCenterY(y);
    }

    public void increaseSpeed() {
        speed += 0.5;
    }

    public void decreaseSpeed() {
        speed -= 0.5;
    }

    private boolean validMove(SnakeDirection direction) {
        return !prevDirection.equals(oppositeMove(direction));
    }

    private SnakeDirection oppositeMove (SnakeDirection direction) {
        return switch (direction) {
            case UP -> SnakeDirection.DOWN;
            case LEFT -> SnakeDirection.RIGHT;
            case DOWN -> SnakeDirection.UP;
            case RIGHT -> SnakeDirection.LEFT;
            default -> SnakeDirection.UP;
        };
    }

    public SnakeDirection getPrevDirection() {
        return prevDirection;
    }

    public void setPrevDirection(SnakeDirection prevDirection) {
        this.prevDirection = prevDirection;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public void destroy() {
        if (getParent()!=null) {
            Pane parentPane = (Pane) getParent();
            parentPane.getChildren().remove(this);
        }
    }

}
