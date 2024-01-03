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

public class Game {
    AnchorPane grid;

    SnakeBody head;
    SnakeGame snakeGame;



    public Game() {

        grid = new AnchorPane();
        grid.setPrefSize(1000, 1000);
        snakeGame= new SnakeGame();
        head = snakeGame.getHead();
        while (head != null) {
            grid.getChildren().add(head);
            head = head.getNext();
        }


        for (Pair<Double, Double> food : snakeGame.getFoods()) {

            grid.getChildren().add(SnakeFactory.createFood(food.getKey(), food.getValue()));
        }
    }

    public void move(SnakeDirection direction) {
        SnakeBody m=snakeGame.move(direction);
        if(m != null) {
            grid.getChildren().add(m);
        }
    }

    public AnchorPane getGrid() {
        return grid;
    }


}
