package dsdb.slither;


import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;

public class SnakeGame {
    boolean [][] board;
    SnakeBody head;
    SnakeBody tail;
    ArrayList<SnakeBody> body;

    ArrayList<Pair<Double,Double>> foods;

    public SnakeGame() {
        board = new boolean[100][100];
        head = new SnakeBody(50, 50);
        SnakeBody body1 = new SnakeBody(50, 51);
        body.add(body1);
        SnakeBody body2 = new SnakeBody(50, 52);
        body.add(body2);
        tail= new SnakeBody(50, 53);
        head.setNext(body1);
        body1.setPrev(head);
        body1.setNext(body2);
        body2.setPrev(body1);
        body2.setNext(tail);
        tail.setPrev(body2);
    }

    // A tester si il peut grandir hors bordure
    public void growSnake () {
        SnakeBody newTail;
        switch (tail.prevDirection) {
            case UP :
                newTail = new SnakeBody(tail.getX(), tail.getY()+1);
                body.add(newTail);
                break;
            case LEFT :
                newTail = new SnakeBody(tail.getX()+1, tail.getY());
                body.add(newTail);
                break;
            case DOWN :
                newTail = new SnakeBody(tail.getX(), tail.getY()-1);
                body.add(newTail);
                break;
            case RIGHT :
                newTail = new SnakeBody(tail.getX()-1, tail.getY());
                body.add(newTail);
                break;
            default:
                newTail = new SnakeBody(tail.getX(), tail.getY());
                body.add(newTail);
                break;

        }
        SnakeBody temp = tail;
        temp.setNext(newTail);
        tail = newTail;
        tail.setPrev(temp);
    }

    public SnakeBody getHead() {
        return head;
    }

    public SnakeBody getTail() {
        return tail;
    }

    public void setTail (SnakeBody tail) {
        this.tail = tail;
    }
}