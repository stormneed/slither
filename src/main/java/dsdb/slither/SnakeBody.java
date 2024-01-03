package dsdb.slither;


import javafx.scene.paint.Color;
import javafx.util.Pair;

import java.util.ArrayList;

public class SnakeBody {
    boolean [][] board;
    SnakeCell head;
    SnakeCell tail;
    ArrayList<SnakeCell> body;

    ArrayList<Pair<Double,Double>> foods;

    public SnakeBody(Color playerColor) {
        head = new SnakeCell(500, 500,playerColor);
        SnakeCell body1 = new SnakeCell(500, 510,playerColor);
        body=new ArrayList<>();
        body.add(body1);
        SnakeCell body2 = new SnakeCell(500, 520,playerColor);
        body.add(body2);
        tail= new SnakeCell(500, 530,playerColor);
        head.setNext(body1);
        body1.setPrev(head);
        body1.setNext(body2);
        body2.setPrev(body1);
        body2.setNext(tail);
        tail.setPrev(body2);
    }

    // A tester si il peut grandir hors bordure
    public SnakeCell growSnake (Color playerColor) {
        SnakeCell newTail;
        switch (tail.prevDirection) {
            case UP :
                newTail = new SnakeCell(tail.getX(), (tail.getY()+10)%800,playerColor,tail.prevDirection);
                body.add(newTail);
                break;
            case LEFT :
                newTail = new SnakeCell((tail.getX()+10)%800, tail.getY(),playerColor,tail.prevDirection);
                body.add(newTail);
                break;
            case DOWN :
                newTail = new SnakeCell(tail.getX(), (tail.getY()-10)%800,playerColor,tail.prevDirection);
                body.add(newTail);
                break;
            case RIGHT :
                newTail = new SnakeCell((tail.getX()-10)%800, tail.getY(),playerColor,tail.prevDirection);
                body.add(newTail);
                break;
            default:
                newTail = new SnakeCell(tail.getX(), tail.getY(),playerColor,tail.prevDirection);
                body.add(newTail);
                break;

        }
        System.out.println(body.size());
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
}