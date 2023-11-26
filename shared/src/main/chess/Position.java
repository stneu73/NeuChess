package chess;

import java.util.Objects;

public class Position implements ChessPosition{
    int row;
    int column;
    public Position(int column,int row) {
        this.row = row-1;
        this.column = column-1;
    }
    public Position(int column,int row,boolean indexZero) {
        if (indexZero) {
            this.row = row;
            this.column = column;
        }
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Position position = (Position) o;
        return row == position.row && column == position.column;
    }

    @Override
    public int hashCode() {
        return Objects.hash(row, column);
    }
}
