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
        return row+1;
    }

    @Override
    public int getColumn() {
        return column+1;
    }

    public int getRowIndex() {
        return row;
    }
    public int getColumnIndex() {
        return column;
    }
}
