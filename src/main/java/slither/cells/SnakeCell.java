package slither.cells;

import javafx.application.Platform;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import slither.SnakeDirection;

public abstract class SnakeCell extends Circle {
    private static final double ANGLE_LIMIT = 5.0;
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

    public void moveHead(Pos p){
        double deltaX = p.getX() - this.getX();
        double deltaY = p.getY() - this.getY();
    
        double targetAngle = Math.toDegrees(Math.atan2(deltaY, deltaX));
    
        // Ensure the target angle is in the range [0, 360]
        if (targetAngle < -180) {
            targetAngle += 360;
        }

        if (targetAngle > 180) {
            targetAngle -= 360;
        }
        
        // Adjust the angle smoothly within the specified limit
        double angleDiff = targetAngle - angle;
        if (angleDiff > 180) {
            angleDiff -= 360;
        } else if (angleDiff < -180) {
            angleDiff += 360;
        }
        System.out.println(angleDiff);
        
        if (angleDiff >= ANGLE_LIMIT) {
            angle += ANGLE_LIMIT;
        } else if (angleDiff <= -ANGLE_LIMIT) {
            angle -= ANGLE_LIMIT;
        } else {
            angle =targetAngle;
        }
    
        double newX = speed * Math.cos(Math.toRadians(angle)) + this.getX();
        double newY = speed * Math.sin(Math.toRadians(angle)) + this.getY();
    
        if (newX > 800) {
            newX = 0;
        } else if (newX < 0) {
            newX = 800;
        }
        if (newY > 800) {
            newY = 0;
        } else if (newY < 0) {
            newY = 800;
        }
        moveBody(newX, newY);
    }

    public void moveHeadIA(Pos p) {
        // setAngle(p);
        moveHead(p);
    }

    public void moveBody(double x, double y) {
        Platform.runLater(() -> {
            if (next != null) {
                next.moveBody(this.getX(),this.getY());
            }
            this.setX(x);
            this.setY(y);});
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
