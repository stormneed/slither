package slither;


import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import slither.cells.SnakeCell;
import slither.cells.SnakeCellBase;
import slither.cells.SnakeCellWeak;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SnakeBody {
    private final SnakeCell head;
    private SnakeCell tail;
    private ArrayList<SnakeCell> body = new ArrayList<>();
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
    }

    public SnakeBody (Color playerColor, int tailSize, int x, int y, boolean vulnerability) {
        head = new SnakeCellBase(x, y,playerColor,true);
        SnakeCell prevCell = head;
        for (int i = 0; i<tailSize; i++) {
            SnakeCell bodyCell = new SnakeCellBase(x, y+5+(i*5),playerColor);
            body.add(bodyCell);
            prevCell.setNext(bodyCell);
            bodyCell.setPrev(prevCell);
            prevCell = bodyCell;
            if (i == tailSize-1 && vulnerability) {
                for (int j = 0; j<8;j++) {
                    SnakeCell weakCell = new SnakeCellWeak(x, y+10+(i*5)+(j*5));
                    body.add(weakCell);
                    prevCell.setNext(weakCell);
                    weakCell.setPrev(prevCell);
                    prevCell = weakCell;
                }
            }
        }
        tail = prevCell;
    }

    // A tester si il peut grandir hors bordure
    public synchronized SnakeCell growSnake (Color playerColor) {
        SnakeCell newTail;
        newTail = new SnakeCellBase(tail.getX(), (tail.getY()+5)%800,playerColor);
        body.add(newTail);
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
        head.setX(x);
        head.setY(y);
        for (int i = 0; i<tailSize; i++) {
            SnakeCell bodyCell = new SnakeCellBase(x, y+5+(i*5),head.getColor());
            body.add(bodyCell);
            prevCell.setNext(bodyCell);
            bodyCell.setPrev(prevCell);
            prevCell = bodyCell;
            Pane parentPane = (Pane) head.getParent();
            parentPane.getChildren().add(bodyCell);
        }
        tail = prevCell;
    }

    public void determineTail (SnakeCell cell) {
        if (cell.getNext()!=null) {
            determineTail(cell.getNext());
        }
        else {
            this.tail = cell;
        }
    }



}