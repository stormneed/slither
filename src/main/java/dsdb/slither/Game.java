package dsdb.slither;

import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

import javax.swing.text.html.ImageView;

public class Game {
    GridPane grid;

    SnakeBody head;

    int prevtail_x;
    int prevtail_y;
    SnakeGame snakeGame;

    public Game() {
        grid = new GridPane();
        for (int i = 0; i < 100; i++) {
            for (int j = 0; j < 100; j++) {
                grid.add(SpaceFactory.createEmptySpace(), i, j);
            }
        }
        snakeGame= new SnakeGame();
        head = snakeGame.getHead();
        SnakeBody current = head;
        while (current != null) {
            grid.add(SpaceFactory.createSnakeSpace(), current.getX(), current.getY());
            current = current.getNext();
        }


    }

    public void move(SnakeDirection direction) {
        prevtail_x = snakeGame.getTail().getX();
        prevtail_y = snakeGame.getTail().getY();
        System.out.println(prevtail_x + " " + prevtail_y);
        head.move(direction);
        update();
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
