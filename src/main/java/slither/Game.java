package slither;

import javafx.application.Platform;

import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import slither.cells.*;
import slither.cells.foodcells.BasicFood;
import slither.cells.foodcells.DeathFood;
import slither.cells.foodcells.FoodCellAbstract;
import slither.cells.foodcells.PoisonFood;
import slither.cells.foodcells.PoisonedDeathFood;
import slither.cells.foodcells.SpeedFood;
import slither.cells.foodcells.WeakFood;
import slither.cells.snakecells.SnakeCellAbstract;
import slither.cells.snakecells.SnakeCellBase;
import slither.cells.snakecells.SnakeCellWeak;


public class Game {
    private final AnchorPane grid;

    private final Pos mousePosition;
    private final List<SnakeBody> players;
    private final List<FoodCellAbstract> unSyncFoods = new ArrayList<>();
    private final List<FoodCellAbstract> foods = Collections.synchronizedList(unSyncFoods);

    public Game() {

        grid = new AnchorPane();
        grid.setPrefSize(800, 800);
        SnakeBody snakeGame= new SnakeBody(Color.RED,10,600,600,true);
        SnakeBody snakeIAGame= new SnakeBody(Color.BLUE,10,400,400,true);
        players=new ArrayList<>();
        players.add(snakeGame);
        players.add(snakeIAGame);


        for (int i = 0; i < 125; i++) {
            foods.add(generateFood());
        }

        for(SnakeBody s:players) {
            SnakeCellAbstract current = s.getHead();
            while (current != null) {
                grid.getChildren().add(current);
                current = current.getNext();
            }
        }

        mousePosition = new Pos(0, 0);
        grid.setOnMouseMoved(event -> mousePosition.setPos(event.getX(), event.getY()));
    }

    public FoodCellAbstract generateFood () {
        double x = new Random().nextInt(800);
        double y = new Random().nextInt(800);

        int r = new Random().nextInt(100);

        FoodCellAbstract foodCords;

        if (r<3) {
            foodCords = new WeakFood(x,y,0);
        }
        else if (r<6) {
            foodCords = new SpeedFood(x,y,1,true);
        }
        else if (r<9) {
            foodCords = new SpeedFood(x,y,1,false);
        }
        else if (r<12) {
            foodCords = new PoisonFood(x,y);
        }
        else {
            foodCords = new BasicFood(x,y,1);
        }


        grid.getChildren().add(foodCords);
        return foodCords;
    }



    public void move (SnakeBody s){
        Platform.runLater(()->
        {s.getHead().moveHead(mousePosition);
            handleFood(s);
            handleCollision(s);});
        }
        

    public void moveIA(Pos food, SnakeBody s) {
        Platform.runLater(()->
        {s.getHead().moveHeadIA(food);
        handleFood(s);
        handleCollision(s);}
        );

    }

    private void handleFood (SnakeBody s) {
        try {
            synchronized (foods) {
                FoodCellAbstract toRemove=null;
                for (FoodCellAbstract foodCords : foods) {
                    if (foodCords.isOverlap(s.getHead()) && !foodCords.isEaten()) {
                        foodCords.setEaten(true);
                        foods.remove(foodCords);
                        toRemove = foodCords;
                        Platform.runLater(() -> {
                            grid.getChildren().remove(foodCords);
                            if (foodCords instanceof WeakFood) {
                                for (int i = 0; i < 8; i++) {
                                    grid.getChildren().add(s.growSnakeWeak());
                                }
                            } else if (foodCords instanceof PoisonedDeathFood) {
                                List<DeathFood> destroyed = s.respawnSnake(10);
                                foods.addAll(destroyed);
                            } else if (foodCords instanceof SpeedFood) {
                                if (((SpeedFood) foodCords).isAccelerate()) {
                                    s.getHead().increaseSpeed();
                                } else {
                                    s.getHead().decreaseSpeed();
                                }
                            } else if (foodCords instanceof PoisonFood) {
                                for (int i = 0; i < 3; i++) {
                                    grid.getChildren().add(s.growSnakePoison());
                                }
                            } else {
                                grid.getChildren().add(s.growSnake(s.getHead().getColor()));
                            }

                        });
                        // iterator.remove();
                        break;
                    }
                }
                if (toRemove!=null) {
                    foods.remove(toRemove);
                }
            }

        } catch (Exception e) {
            System.out.println("aled");
        }
    }

    // private void runLaterWithDelay(Runnable runnable, Duration delay) {
    //     Timeline timeline = new Timeline(new KeyFrame(delay, event -> {
    //         Platform.runLater(runnable);
    //     }));
    //     timeline.play();
    // }

    private void handleCollision (SnakeBody s) {
        double headX = s.getHead().getX();
        double headY = s.getHead().getY();
        List<SnakeCellAbstract> destroyedCell = new ArrayList<>();
        for (SnakeBody snakeBody : players) {
            for (SnakeCellAbstract snakeCell : snakeBody.getBody()) {
                if (s.getBody().contains(snakeCell) || s.getHead().isImmune()) {continue;}
                if (Math.abs(snakeCell.getX()-headX)<10 && Math.abs(snakeCell.getY()-headY)<10) {
                    s.getHead().changeImmune();
                    Platform.runLater(() -> {
                        if (snakeCell instanceof SnakeCellWeak) {
                            snakeBody.setTail(snakeCell.getPrev());
                            ((SnakeCellWeak) snakeCell).destroyWeakPoint(destroyedCell);
                            snakeBody.getBody().removeAll(destroyedCell);
                            for (SnakeCellAbstract snakeCells : snakeBody.getBody()) {
                                if (snakeCells.getNext()!=null && destroyedCell.contains(snakeCells.getNext())) {
                                    snakeCells.setNext(null);
                                }
                            }

                            snakeBody.determineTail(snakeBody.getHead());
                        }
                        else if (snakeCell instanceof SnakeCellBase) {
                            synchronized (foods){
                                List<DeathFood> destroyed =s.respawnSnake(10);
                                foods.addAll(destroyed);
                            }

                        }
                        try {
                            Thread.sleep(50);
                            Platform.runLater(() -> s.getHead().changeImmune());
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    });
                }
            }
        }
    }






    Runnable moveRunnable = new Runnable() {
        public void run() {
            synchronized (players){

                for (SnakeBody snake : players ) {
                    if (!snake.equals(players.get(0))) { moveIA(getClosestFood(snake),snake); continue; }
                    move(snake);
                    if(FoodCellAbstract.numFood<20) {
                        Platform.runLater(() -> foods.add(generateFood()));

                    }

                }}

        }
    };

    ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();


    public synchronized Pos getClosestFood(SnakeBody s){
        synchronized(foods){
            if (foods.isEmpty()) return null;
            SnakeCellAbstract head = s.getHead();
        
            FoodCellAbstract closestFood = foods.get(0);
            double minDistance = Math.sqrt((head.getX() - closestFood.getCenterX()) * (head.getX() - closestFood.getCenterX()))
                    + (head.getY() - closestFood.getCenterY()) * (head.getY() - closestFood.getCenterY());
            for (FoodCellAbstract food : foods) {
                double distance = Math.sqrt((head.getX() - food.getCenterX()) * (head.getX() - food.getCenterX()))
                        + (head.getY() - food.getCenterY()) * (head.getY() - food.getCenterY());
                if (distance < minDistance) {
                    minDistance = distance;
                    closestFood = food;
                }
            }
            return new Pos(closestFood.getCenterX(),closestFood.getCenterY());
        }
    }

    public AnchorPane getGrid() {
        return grid;
    }



}
