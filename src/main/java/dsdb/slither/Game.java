package dsdb.slither;

import javafx.application.Platform;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public class Game {
    AnchorPane grid;

    SnakeBody snakeGame;
    SnakeBody snakeIAGame;

    List<SnakeBody> players;

    ArrayList<FoodCords> foods = new ArrayList<>();


    public Game() {

        grid = new AnchorPane();
        grid.setPrefSize(800, 800);
        snakeGame= new SnakeBody(Color.RED);

        snakeIAGame= new SnakeBody(Color.BLUE);
        players=new ArrayList<>();
        players.add(snakeGame);
        players.add(snakeIAGame);
        players.add(new SnakeBody(Color.GOLD));


        for (int i = 0; i < 20; i++) {
            foods.add(generateFood());
        }

        for(SnakeBody s:players) {
            SnakeCell current = s.getHead();
            while (current != null) {
                grid.getChildren().add(current);
                current = current.getNext();
            }
        }
    }

    public class FoodCords {

        private Circle foodgraph;
        private boolean eaten;
        private double x,y;
        public static int numFood=0;
        public FoodCords(double x, double y, Circle foodgraph) {
            this.x = x;
            this.y = y;
            this.foodgraph=foodgraph;
            eaten=false;
            numFood++;
        }

        public FoodCords(double x, double y) {
            this.x = x;
            this.y = y;
            foodgraph=SnakeFactory.createFood(x,y);
        }
        public boolean isOverlap (SnakeCell head) {
            if (head == null) return false;

            double d = Math.sqrt((head.getX() - this.x) * (head.getX() - x))
                    + (head.getY() - y) * (head.getY() - y);
            return d <= 50;
        }
        public void removeGraph() {
            if (grid.getChildren().contains(foodgraph)){
            grid.getChildren().remove(foodgraph);
            numFood--;
            }
        }

        public boolean isEaten() {
            return eaten;
        }

        public void setEaten(boolean eaten) {
            this.eaten = eaten;
        }
    }

    public FoodCords generateFood () {
        double x = new Random().nextInt(800);
        double y = new Random().nextInt(800);
        Circle a=SnakeFactory.createFood(x,y);
        FoodCords foodCords = new FoodCords(x,y,a);
        grid.getChildren().add(a);
        return foodCords;
    }

    public synchronized void move(SnakeDirection direction, SnakeBody s) {
        try {
            s.getHead().move(direction);
        List<FoodCords> newFoods = new ArrayList<>();
        Iterator<FoodCords> iterator = foods.iterator();
        while (iterator.hasNext()) {
            FoodCords foodCords = iterator.next();
            if (foodCords.isOverlap(s.getHead()) && !foodCords.isEaten()) {
                foodCords.setEaten(true);
                Platform.runLater(new Runnable (){
                    @Override
                    public void run() {
                        foodCords.removeGraph();
                        grid.getChildren().add(s.growSnake(s.getHead().color));
                    }
                });
                
                iterator.remove();
                break;
            }
        }
    
        foods.addAll(newFoods);
            
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    Runnable moveRunnable = new Runnable() {
        public void run() {
            
            for (SnakeBody snake : players ) {
                if (snake.equals(players.get(1))) { move(directionIA(),players.get(1)); continue; }
                move(snake.getSnakeDirection(),snake);
                if(FoodCords.numFood<20) {
                    Platform.runLater(new Runnable (){
                        @Override
                        public void run() {
                            foods.add(generateFood());
                        }
                    });
                    
                }

            }

        }
    };

    ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();


    public synchronized FoodCords getClosestFood(SnakeBody s){
        SnakeCell head = s.getHead();
        FoodCords closestFood = foods.get(0);
        double minDistance = Math.sqrt((head.getX() - closestFood.x) * (head.getX() - closestFood.x))
                + (head.getY() - closestFood.y) * (head.getY() - closestFood.y);
        for (FoodCords food : foods) {
            double distance = Math.sqrt((head.getX() - food.x) * (head.getX() - food.x))
                    + (head.getY() - food.y) * (head.getY() - food.y);
            if (distance < minDistance) {
                minDistance = distance;
                closestFood = food;
            }
        }
        return closestFood;
    }

    public SnakeDirection directionIA(){
        SnakeCell headIA = snakeIAGame.getHead();
        FoodCords food = getClosestFood(snakeIAGame);
        if (headIA.getX() < food.x-5) {
            return SnakeDirection.RIGHT;
        } else if (headIA.getX() > food.x+5) {
            return SnakeDirection.LEFT;
        } else if (headIA.getY() < food.y-5) {
           return SnakeDirection.DOWN;
        } else if (headIA.getY() > food.y+5) {
           return SnakeDirection.UP;
        }
        else return SnakeDirection.NONE;
    }

    public AnchorPane getGrid() {
        return grid;
    }




}
