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
            return true;
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
        int x = myPosition.getColumnIndex();
        int y = myPosition.getRowIndex();
//        boolean hitAnotherPiece = false;
        //left
        if (x > 0) {
            for (int i = x; i >= 0; i--) {
                ChessPosition currPosition = new Position(i,y,true);
                if (board.getPiece(currPosition) == null) {
                    moves.add(new Move(myPosition, currPosition));
                }
                if (i == 0) break;
                currPosition = new Position(i - 1,y,true);
                if (board.getPiece(currPosition) != null) {
                    ChessPiece piece = board.getPiece(currPosition);
                    if (piece.getTeamColor() == this.color) {
                        break;
                    }
                    if (piece.getTeamColor() != this.color) {
                        moves.add(new Move(myPosition, currPosition));
                        break;
                    }
                }
            }
        }
        //right
        if (x < 7) {
            for (int i = x; i < 8; i++) {
                ChessPosition currPosition = new Position(i,y,true);
                if (board.getPiece(currPosition) == null) {
                    moves.add(new Move(myPosition, currPosition));
                }
                if (i == 7) break;
                currPosition = new Position(i+1,y,true);
                if (board.getPiece(currPosition) != null) {
                    ChessPiece piece = board.getPiece(currPosition);
                    if (piece.getTeamColor() == this.color) {
                        break;
                    }
                    if (piece.getTeamColor() != this.color) {
                        moves.add(new Move(myPosition, currPosition));
                        break;
                    }
                }
            }
        }
        //up
        if (y < 7) {
            for (int i = y; i < 8; i++) {
                ChessPosition currPosition = new Position(x,i,true);
                if (board.getPiece(currPosition) == null) {
                    moves.add(new Move(myPosition, currPosition));
                }
                if (i == 7) break;
                currPosition = new Position(x,i+1,true);
                if (board.getPiece(currPosition) != null) {
                    ChessPiece piece = board.getPiece(currPosition);
                    if (piece.getTeamColor() == this.color) {
                        break;
                    }
                    if (piece.getTeamColor() != this.color) {
                        moves.add(new Move(myPosition,currPosition));
                        break;
                    }
                }
            }
        }
        //down
        if (y > 0) {
            for (int i = y; i >= 0; i--) {
                ChessPosition currPosition = new Position(x,i,true);
                if (board.getPiece(currPosition) == null) {
                    moves.add(new Move(myPosition, currPosition));
                }
                if (i == 0) break;
                currPosition = new Position(x,i-1,true);
                if (board.getPiece(currPosition) != null) {
                    ChessPiece piece = board.getPiece(currPosition);
                    if (piece.getTeamColor() == this.color) {
                        break;
                    }
                    if (piece.getTeamColor() != this.color) {
                        moves.add(new Move(myPosition, currPosition));
                        break;
                    }
                }
            }
        }
        return this.moves;
    }
}
