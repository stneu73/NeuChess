package chess;

public class Position implements ChessPosition{
    int row;
    int column;
    public Position(int row, int column) {
        this.row = row-1;
        this.column = column-1;
    }
    @Override
    public int getRow() {
        return row;
    }

    @Override
    public int getColumn() {
        return column;
    }
}
