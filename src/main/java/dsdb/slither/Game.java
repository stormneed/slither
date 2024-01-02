package dsdb.slither;

import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

import javax.swing.text.html.ImageView;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Game {
    GridPane grid;
    SnakeBody head;
    int prevtail_x;
    int prevtail_y;
    SnakeGame snakeGame;
    GridCords foodCords;


    public Game() {
        grid = new GridPane();
        for (int i = 0; i < 100; i++) {
            for (int j = 0; j < 100; j++) {
                grid.add(SpaceFactory.createEmptySpace(), i, j);
            }
        }
        snakeGame= new SnakeGame();
        generateFood();
        head = snakeGame.getHead();
        SnakeBody current = head;
        while (current != null) {
            grid.add(SpaceFactory.createSnakeSpace(), current.getX(), current.getY());
            current = current.getNext();
        }


    }

    public class GridCords {
        int x,y;
        public GridCords(int x, int y) {
            this.x = x;
            this.y = y;
        }
        public boolean isOverlap (SnakeBody body) {
            return body.getX() == x && body.getY() == y;
        }
    }

    public void generateFood () {
        int x = new Random().nextInt(100);
        int y = new Random().nextInt(100);
        foodCords = new GridCords(x,y);
        grid.add(SpaceFactory.createFoodSpace(),x,y);
    }

    public void move(SnakeDirection direction) {
        if (foodCords.isOverlap(head)) {
            snakeGame.growSnake();
            generateFood();
        }
        prevtail_x = snakeGame.getTail().getX();
        prevtail_y = snakeGame.getTail().getY();
        System.out.println(prevtail_x + " " + prevtail_y);
        head.move(direction);
        update();
    }

    public void snakeGrow () {
        SnakeBody oldTail = snakeGame.getTail();
        SnakeBody newTail = new SnakeBody(oldTail.getX(), oldTail.getY(), oldTail);
        oldTail.setNext(newTail);
        newTail.setPrev(oldTail);
        snakeGame.setTail(newTail);
    }

    public GridPane getGrid() {
        return grid;
    }

    public SnakeBody getHead() {
        return head;
    }



    public void update() {

        SnakeBody current = head;
        while (current != null) {
            SnakeBody finalCurrent = current;
            grid.getChildren().removeIf(node -> GridPane.getColumnIndex(node) == finalCurrent.getX() && GridPane.getRowIndex(node) == finalCurrent.getY());
            grid.add(SpaceFactory.createSnakeSpace(), current.getX(), current.getY());
            current = current.getNext();
        }
        grid.add(SpaceFactory.createEmptySpace(), prevtail_x, prevtail_y);
    }



}
