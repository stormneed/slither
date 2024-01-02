package dsdb.slither;


public class SnakeGame {
    boolean [][] board;
    SnakeBody head;
    SnakeBody tail;

    public SnakeGame() {
        board = new boolean[100][100];
        head = new SnakeBody(50, 50);
        SnakeBody body = new SnakeBody(50, 51);
        tail= new SnakeBody(50, 52);
        head.setNext(body);
        body.setPrev(head);
        body.setNext(tail);
        tail.setPrev(body);
    }


    public SnakeBody getHead() {
        return head;
    }

    public SnakeBody getTail() {
        return tail;
    }
}