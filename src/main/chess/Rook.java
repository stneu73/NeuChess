package chess;

import java.util.Collection;
import java.util.Iterator;

public class Rook implements ChessPiece{
    private ChessGame.TeamColor color;
    private Collection<ChessMove> moves = new Collection<ChessMove>() {
        @Override
        public int size() {
            return 0;
        }

        @Override
        public boolean isEmpty() {
            return false;
        }

        @Override
        public boolean contains(Object o) {
            return false;
        }

        @Override
        public Iterator<ChessMove> iterator() {
            return null;
        }

        @Override
        public Object[] toArray() {
            return new Object[0];
        }

        @Override
        public <T> T[] toArray(T[] a) {
            return null;
        }

        @Override
        public boolean add(ChessMove chessMove) {
            return false;
        }

        @Override
        public boolean remove(Object o) {
            return false;
        }

        @Override
        public boolean containsAll(Collection<?> c) {
            return false;
        }

        @Override
        public boolean addAll(Collection<? extends ChessMove> c) {
            return false;
        }

        @Override
        public boolean removeAll(Collection<?> c) {
            return false;
        }

        @Override
        public boolean retainAll(Collection<?> c) {
            return false;
        }

        @Override
        public void clear() {

        }
    };

    public Rook(ChessGame.TeamColor color) {
        this.color = color;
    }
    @Override
    public ChessGame.TeamColor getTeamColor() {
        return this.color;
    }

    @Override
    public PieceType getPieceType() {
        return PieceType.ROOK;
    }

    @Override
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        int x = myPosition.getColumn();
        int y = myPosition.getRow();
        boolean hitAnotherPiece = false;
        //left
        for (int i = x; i > 0; i--) {
            if (hitAnotherPiece) break;
            ChessPosition pos = new Position(y,i);
            if (board.getPiece(pos) == null){
                moves.add(new Move(myPosition,new Position(y,i)));
            }
            else if (board.getPiece(pos) != null) {
                if (board.getPiece(pos).getTeamColor() == this.color) {
                    hitAnotherPiece = true;
                    break;
                }
                if (board.getPiece(pos).getTeamColor() != this.color) {
                    moves.add(new Move(myPosition,new Position(y,i)));
                    hitAnotherPiece = true;
                    break;
                }
            }
        }
        //right
        for (int i = x; i < 7; i++) {
            moves.add(new Move(myPosition,new Position(y,i)));
        }
        //up
        for (int i = y; i > 0; i--) {
            moves.add(new Move(myPosition,new Position(i,x)));
        }
        //down
        for (int i = y; i < 7; i++) {
            moves.add(new Move(myPosition,new Position(i,x)));
        }
        return this.moves;
    }
}
