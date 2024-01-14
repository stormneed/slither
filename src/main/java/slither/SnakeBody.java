package slither;


import javafx.application.Platform;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import slither.cells.foodcells.DeathFood;
import slither.cells.foodcells.PoisonedDeathFood;
import slither.cells.snakecells.SnakeCell;
import slither.cells.snakecells.SnakeCellBase;
import slither.cells.snakecells.SnakeCellPoison;
import slither.cells.snakecells.SnakeCellWeak;

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
    public SnakeCell growSnake (Color playerColor) {
        SnakeCell newTail;
        newTail = new SnakeCellBase(tail.getX(), (tail.getY())%800,playerColor);
        body.add(newTail);
        SnakeCell temp = tail;
        temp.setNext(newTail);
        tail = newTail;
        tail.setPrev(temp);
        return newTail;
    }

    public SnakeCell growSnakeWeak () {
        SnakeCell newTail;
        newTail = new SnakeCellWeak(tail.getX(), (tail.getY())%800);
        body.add(newTail);
        SnakeCell temp = tail;
        temp.setNext(newTail);
        tail = newTail;
        tail.setPrev(temp);
        return newTail;
    }

    public SnakeCell growSnakePoison () {
        SnakeCell newTail;
        newTail = new SnakeCellPoison(tail.getX(), (tail.getY())%800);
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

    public List<DeathFood> respawnSnake (int tailSize) {
        Pane parentPane = (Pane) head.getParent();
        ArrayList<SnakeCell> destroyed = new ArrayList<>();
        for (SnakeCell snakeCell : body) {
            if (!snakeCell.equals(head)) {
                snakeCell.destroy();
                destroyed.add(snakeCell);
            }
        }
        ArrayList<DeathFood> deathFood = new ArrayList<>();
        DeathFood deathFoodCell;
            for (int i = 0; i<destroyed.size(); i+=3) {
                                SnakeCell snakeCell = destroyed.get(i);
                if (snakeCell instanceof SnakeCellPoison) {
                    deathFoodCell = new PoisonedDeathFood(snakeCell.getX(),snakeCell.getY());
                }
                else {
                    deathFoodCell= new DeathFood(snakeCell.getX(),snakeCell.getY());
                }   
                deathFood.add(deathFoodCell);
                parentPane.getChildren().add(deathFoodCell);
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
            
            parentPane.getChildren().add(bodyCell);
        }
        tail = prevCell;
        return deathFood;
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