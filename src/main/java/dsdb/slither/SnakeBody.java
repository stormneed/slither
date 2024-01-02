package dsdb.slither;

public class SnakeBody {
    int x;
    int y;
    SnakeBody next;
    SnakeBody prev;
    SnakeDirection prevDirection;

    public SnakeBody(int x, int y) {
        this.x = x;
        this.y = y;
        prevDirection = SnakeDirection.UP;
    }

    public SnakeBody (int x, int y, SnakeBody prev) {
        this.x = x;
        this.y = y;
        prevDirection = prev.prevDirection;
    }
    public void setNext(SnakeBody next) {
        this.next = next;
    }
    public void setPrev(SnakeBody prev) {
        this.prev = prev;
    }
    public SnakeBody getNext() {
        return next;
    }
    public SnakeBody getPrev() {
        return prev;
    }
    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }

    public void move(SnakeDirection direction) {

        SnakeDirection validDir;
        if (validMove(direction)) validDir = direction;
        else validDir = oppositeMove(direction);
        switch (validDir) {
            case UP:
                if (y == 0) {
                    y = 99;
                } else {
                    y--;
                }

                break;
            case DOWN:
                if (y == 99) {
                    y = 0;
                } else {
                    y++;
                }
                break;
            case LEFT:
                if (x == 0) {
                    x = 99;
                } else {
                    x--;
                }
                break;
            case RIGHT:
                if (x == 99) {
                    x = 0;
                } else {
                    x++;
                }
                break;
            default:
                break;
        }
        if (next != null) {
            next.move(prevDirection);
        }
        prevDirection = validDir;

    }

    private boolean validMove(SnakeDirection direction) {
        return !prevDirection.equals(oppositeMove(direction));
    }

    private SnakeDirection oppositeMove (SnakeDirection direction) {
        switch (direction) {
            case UP : return SnakeDirection.DOWN;
            case LEFT : return SnakeDirection.RIGHT;
            case DOWN : return SnakeDirection.UP;
            case RIGHT : return SnakeDirection.LEFT;
            default: return null;
        }
    }

}
