package slither.cells.snakecells;

import javafx.scene.paint.Color;

import java.util.List;

public class SnakeCellWeak extends SnakeCell {

    private boolean marked;
    public SnakeCellWeak(double x, double y) {
        super(5);
        this.setFill(Color.GREEN);
        this.setStroke(Color.PURPLE);
        this.setCenterX(x);
        this.setCenterY(y);
        this.setColor(Color.GREEN);
        this.marked = false;
    }

    public void destroyWeakPoint (List<SnakeCell> listCell) {
        if (getPrev()!=null && getPrev() instanceof SnakeCellWeak && !((SnakeCellWeak) getPrev()).marked) {
            this.marked = true;
            listCell.add(this);
            ((SnakeCellWeak) getPrev()).destroyWeakPoint(listCell);
        }
        if (getNext()!=null && getNext() instanceof SnakeCellWeak && !((SnakeCellWeak) getNext()).marked) {
            this.marked = true;
            listCell.add(this);
            ((SnakeCellWeak) getNext()).destroyWeakPoint(listCell) ;
        }
        if (getNext()!=null && !(getNext() instanceof SnakeCellWeak)) {
            getNext().destroyFrom(listCell);
        }
        destroy();
    }

}
