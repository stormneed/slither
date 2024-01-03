package dsdb.slither;

import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Game {
    AnchorPane grid;

    SnakeCell head;

    SnakeBody snakeGame;
    SnakeBody snakeIAGame;

    List<SnakeBody> players;

    ArrayList<GridCords> foods = new ArrayList<>();


    public Game() {

        grid = new AnchorPane();
        grid.setPrefSize(800, 800);
        snakeGame= new SnakeBody(Color.RED);
        snakeIAGame= new SnakeBody(Color.BLUE);
        players=new ArrayList<>();
        players.add(snakeGame);
        players.add(snakeIAGame);


        for (int i = 0; i < 20; i++) {
            foods.add(generateFood());
        }
        head = snakeGame.getHead();

        for(SnakeBody s:players) {
            SnakeCell current = s.head;
            while (current != null) {
                grid.getChildren().add(current);
                current = current.getNext();
            }
        }
    }

    public class GridCords {

        Circle foodgraph;
        double x,y;
        public GridCords(double x, double y,Circle foodgraph) {
            this.x = x;
            this.y = y;
            this.foodgraph=foodgraph;
        }

        public GridCords(double x, double y) {
            this.x = x;
            this.y = y;
            foodgraph=SnakeFactory.createFood(x,y);
        }
        public boolean isOverlap (SnakeCell body) {

                double d = Math.sqrt((body.getX() - this.x) * (body.getX() - x))
                        + (body.getY() - y) * (body.getY() - y);
                return d <= 50;
        }

        public void removeGraph() {
            grid.getChildren().remove(foodgraph);
        }
    }

    public GridCords generateFood () {
        double x = new Random().nextInt(800);
        double y = new Random().nextInt(800);
        Circle a=SnakeFactory.createFood(x,y);
        GridCords foodCords = new GridCords(x,y,a);
        grid.getChildren().add(a);
        return foodCords;
    }

    public void move(SnakeDirection direction,SnakeBody s) {
        for (GridCords foodCords : foods) {
            if (foodCords.isOverlap(head)) {
                foodCords.removeGraph();
                foods.remove(foodCords);
                grid.getChildren().add(s.growSnake(s.head.color));
                foods.add(generateFood());
                break;
            }
        }
        s.head.move(direction);
//        update();
    }


    public GridCords getClosestFood(SnakeBody s){
        SnakeCell head = s.getHead();
        GridCords closestFood = foods.get(0);
        double minDistance = Math.sqrt((head.getX() - closestFood.x) * (head.getX() - closestFood.x))
                + (head.getY() - closestFood.y) * (head.getY() - closestFood.y);
        for (GridCords food : foods) {
            double distance = Math.sqrt((head.getX() - food.x) * (head.getX() - food.x))
                    + (head.getY() - food.y) * (head.getY() - food.y);
            if (distance < minDistance) {
                minDistance = distance;
                closestFood = food;
            }
        }
        System.out.println(closestFood.x+" "+closestFood.y);
        return closestFood;
    }

    public SnakeDirection directionIA(){
        SnakeCell headIA = snakeIAGame.getHead();
        Circle food = getClosestFood(snakeIAGame).foodgraph;
        if (headIA.getX() < food.getCenterX()+10) {
            return SnakeDirection.RIGHT;
        } else if (headIA.getX() > food.getCenterX()-10) {
            return SnakeDirection.LEFT;
        } else if (headIA.getY() < food.getCenterY()+10) {
           return SnakeDirection.DOWN;
        } else if (headIA.getY() > food.getCenterY()-10) {
           return SnakeDirection.UP;
        }
        else return SnakeDirection.NONE;
    }

    public AnchorPane getGrid() {
        return grid;
    }

    public SnakeCell getHead() {
        return head;
    }



//    public void update() {
//
//        SnakeBody current = head;
//        while (current != null) {
//            SnakeBody finalCurrent = current;
//            grid.getChildren().removeIf(node -> GridPane.getColumnIndex(node) == finalCurrent.getX() && GridPane.getRowIndex(node) == finalCurrent.getY());
//            grid.add(SpaceFactory.createSnakeSpace(), current.getX(), current.getY());
//            current = current.getNext();
//        }
//        grid.add(SpaceFactory.createEmptySpace(), prevtail_x, prevtail_y);
//    }



}
