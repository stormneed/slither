package dsdb.slither;


public class SnakeGame {
    boolean [][] board;
    SnakeBody head;
    SnakeBody tail;


    public SnakeGame() {
        board = new boolean[100][100];
        head = new SnakeBody(50, 50);
        SnakeBody body1 = new SnakeBody(50, 51);
        SnakeBody body2 = new SnakeBody(50, 52);
        tail= new SnakeBody(50, 53);
        head.setNext(body1);
        body1.setPrev(head);
        body1.setNext(body2);
        body2.setPrev(body1);
        body2.setNext(tail);
        tail.setPrev(body2);
    }

    // A tester si il peut grandir hors bordure
    public void growSnake () {
        SnakeBody newTail;
        switch (tail.prevDirection) {
            case UP :
                newTail = new SnakeBody(tail.getX(), tail.getY()+1);
                break;
            case LEFT :
                newTail = new SnakeBody(tail.getX()+1, tail.getY());
                break;
            case DOWN :
                newTail = new SnakeBody(tail.getX(), tail.getY()-1);
                break;
            case RIGHT :
                newTail = new SnakeBody(tail.getX()-1, tail.getY());
                break;
            default:
                newTail = new SnakeBody(tail.getX(), tail.getY());
                break;

        }
        SnakeBody temp = tail;
        temp.setNext(newTail);
        tail = newTail;
        tail.setPrev(temp);
    }

    public SnakeBody getHead() {
        return head;
    }

    public SnakeBody getTail() {
        return tail;
    }

    public void setTail (SnakeBody tail) {
        this.tail = tail;
    }
}