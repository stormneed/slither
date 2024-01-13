package slither;


import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import slither.cells.SnakeCell;
import slither.cells.SnakeCellBase;

import java.util.ArrayList;
import java.util.Random;

public class SnakeBody {
    private final SnakeCell head;
    private SnakeCell tail;
    private ArrayList<SnakeCell> body = new ArrayList<>();
    private SnakeDirection snakeDirection;
    public SnakeBody (Color playerColor, int tailSize, int x, int y) {
        head = new SnakeCellBase(x, y,playerColor,true);
        SnakeCell prevCell = head;
        for (int i = 0; i<tailSize; i++) {
            SnakeCell bodyCell = new SnakeCellBase(x, y+5+(i*5),playerColor);
            body.add(bodyCell);
            prevCell.setNext(bodyCell);
            bodyCell.setPrev(prevCell);
            prevCell = bodyCell;
        }
        tail = prevCell;
        snakeDirection=SnakeDirection.UP;
    }

    // A tester si il peut grandir hors bordure
    public synchronized SnakeCell growSnake (Color playerColor) {
        SnakeCell newTail;
        switch (tail.getPrevDirection()) {
            case UP :
                newTail = new SnakeCellBase(tail.getX(), (tail.getY()+5)%800,playerColor,tail);
                body.add(newTail);
                break;
            case LEFT :
                newTail = new SnakeCellBase((tail.getX()+5)%800, tail.getY(),playerColor,tail);
                body.add(newTail);
                break;
            case DOWN :
                newTail = new SnakeCellBase(tail.getX(), (tail.getY()-5)%800,playerColor,tail);
                body.add(newTail);
                break;
            case RIGHT :
                newTail = new SnakeCellBase((tail.getX()-5)%800, tail.getY(),playerColor,tail);
                body.add(newTail);
                break;
            default:
                newTail = new SnakeCellBase(tail.getX(), tail.getY(),playerColor,tail);
                body.add(newTail);
                break;

        }
        SnakeCell temp = tail;
        temp.setNext(newTail);
        tail = newTail;
        tail.setPrev(temp);
        return newTail;
    }

    public SnakeCell getHead() {
        return head;
    }

    public SnakeCell getTail() {
        return tail;
    }

    public void setTail (SnakeCell tail) {
        this.tail = tail;
    }

    public SnakeDirection getSnakeDirection() {
        return snakeDirection;
    }
    public void setSnakeDirection(SnakeDirection direction) {
        this.snakeDirection=direction;
    }

    public ArrayList<SnakeCell> getBody() {
        return body;
    }

    public void respawnSnake (int tailSize) {
        for (SnakeCell snakeCell : body) {
            if (!snakeCell.equals(head)) {
                snakeCell.destroy();
            }
        }
        body = new ArrayList<>();
        SnakeCell prevCell = head;
        int rx = new Random().nextInt(700);
        int x = rx - (rx % 10);
        int ry = new Random().nextInt(700);
        int y = ry - (ry % 10);
        snakeDirection=SnakeDirection.UP;
        head.setPrevDirection(SnakeDirection.UP);
        head.setX(x);
        head.setY(y);
        for (int i = 0; i<tailSize; i++) {
            SnakeCell bodyCell = new SnakeCellBase(x, y+5+(i*5),head.getColor());
            bodyCell.setPrevDirection(SnakeDirection.UP);
            body.add(bodyCell);
            prevCell.setNext(bodyCell);
            bodyCell.setPrev(prevCell);
            prevCell = bodyCell;
            Pane parentPane = (Pane) head.getParent();
            parentPane.getChildren().add(bodyCell);
        }
        tail = prevCell;
    }
}