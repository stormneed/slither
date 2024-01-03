package dsdb.slither;


import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;

public class SnakeGame {
    boolean [][] board;
    SnakeBody head;

    ArrayList<SnakeBody> body;

    ArrayList<Pair<Double,Double>> foods;

    public SnakeGame() {
        board = new boolean[100][100];
        head = new SnakeBody(500, 500);
        body= new ArrayList<>();
        body.add(new SnakeBody(500, 505));
        head.setNext(body.get(0));
        SnakeBody tail= new SnakeBody(500, 510);
        body.add(tail);
        body.get(0).setPrev(head);
        body.get(0).setNext(tail);
        tail.setPrev(body.get(0));
        foods= new ArrayList<>();
        for(int i=0;i<10;i++){
            foods.add(new Pair<>((Math.random() * 1000), (Math.random() * 1000)));
        }
    }


    public SnakeBody getHead() {
        return head;
    }


    public boolean touchFood(Pair<Double,Double> npos, Pair<Double, Double> food,double snakesize, int foodsize) {
        double d = Math.sqrt((npos.getKey() - food.getKey()) * (npos.getKey() - food.getKey())
                + (npos.getValue() - food.getValue()) * (npos.getValue() - food.getValue()));
        return d <= 20;
    }

    public SnakeBody move(SnakeDirection direction) {
        double oldX = head.getX();
        double oldY = head.getY();
        Pair<Double,Double> nextpos= head.nextPos(direction);
        for (int i = 0; i < foods.size(); i++) {
            if (touchFood(nextpos, foods.get(i),head.getRadius(),5)){
                System.out.println("touch food");
                head.moveOne(nextpos);
                body.get(0).setPrevDirection(direction);
                foods.remove(0);
                SnakeBody newBody = new SnakeBody(oldX, oldY);
                newBody.setPrevDirection(direction);
                head.setNext(newBody);
                newBody.setNext(body.get(0));
                newBody.setPrev(head);
                body.get(0).setPrev(newBody);
                body.add(0, newBody);
                return newBody;
            }
        }
        moveBody(head.getPrevDirection(), body.get(body.size() - 1));
        head.moveOne(nextpos);
        head.setPrevDirection(direction);
        return null;


    }

    private void moveBody(SnakeDirection direction,SnakeBody current) {
        current.move_rec();
        current.setPrevDirection(direction);

    }

    public SnakeBody getTail() {
        return body.get(body.size()-1);
    }

    public ArrayList<Pair<Double, Double>> getFoods() {
        return foods;
    }

    public List<SnakeBody> getBody() {
        return body;
    }
}