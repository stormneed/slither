package dsdb.slither;

import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.util.Pair;

import javax.swing.text.html.ImageView;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.ArrayList;
import java.util.List;

public class Game {
    AnchorPane grid;

    SnakeBody head;
    int prevtail_x;
    int prevtail_y;
    SnakeGame snakeGame;
    GridCords foodCords;




    public Game() {

        grid = new AnchorPane();
        grid.setPrefSize(1000, 1000);
        snakeGame= new SnakeGame();
        generateFood();
        head = snakeGame.getHead();
        while (head != null) {
            grid.getChildren().add(head);
            head = head.getNext();
        }


        for (Pair<Double, Double> food : snakeGame.getFoods()) {

            grid.getChildren().add(SnakeFactory.createFood(food.getKey(), food.getValue()));
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


        SnakeBody m=snakeGame.move(direction);
        if(m != null) {
            grid.getChildren().add(m);
        }
    }

    public void snakeGrow () {
        SnakeBody oldTail = snakeGame.getTail();
        SnakeBody newTail = new SnakeBody(oldTail.getX(), oldTail.getY(), oldTail);
        oldTail.setNext(newTail);
        newTail.setPrev(oldTail);
        snakeGame.setTail(newTail);
    }


    public AnchorPane getGrid() {
        return grid;
    }

    public SnakeBody getHead() {
        return head;
    }


}
