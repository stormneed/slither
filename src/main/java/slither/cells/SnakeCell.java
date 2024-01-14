package slither.cells;

import javafx.application.Platform;
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
    private double angle;

    public SnakeCell (int n) {
        super(n);
        speed= 2.5;
        angle=-Math.PI/2;}

    public void setNext(SnakeCell next) {
        this.next = next;
    }

    public void setAngle(Pos p) {
        double angle= Math.atan2(p.getY()-this.getY(),p.getX()-this.getX());
        double diff = angle - this.angle;
        double maxDiff = Math.toRadians(5);

        if (Math.abs(diff) < maxDiff) {
            this.angle = angle;
        }
        else{
            if (diff > maxDiff) {
                this.angle = this.angle + maxDiff;
            } else{
                this.angle = this.angle - maxDiff;
            }
        }
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

    public void moveHead(){
        double dx = speed * Math.cos(angle);
        double dy = speed * Math.sin(angle);
        double newx= this.getX()+dx;
        double newy= this.getY()+dy;
        if (newx > 800) {
            newx = 0;
        } else if (newx < 0) {
            newx = 800;
        }
        if (newy > 800) {
            newy = 0;
        } else if (newy < 0) {
            newy = 800;
        }
        moveBody(newx, newy);
    }

    public void moveHeadIA(Pos p) {
        setAngle(p);
        moveHead();
    }

    public void moveBody(double x, double y) {
        Platform.runLater(() -> {
            if (next != null) {
                next.moveBody(this.getCenterX(),this.getCenterY());
            }
            this.setCenterX(x);
            this.setCenterY(y);});
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
        Platform.runLater(() -> {
        if (getParent() != null) {
            Pane parentPane = (Pane) getParent();
            parentPane.getChildren().remove(this);
        }
    });
    }

    public Pos getPos() {
        return new Pos(this.getX(),this.getY());
    }

}
