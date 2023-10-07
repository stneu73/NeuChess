package chess;

import java.util.Collection;
import java.util.Iterator;

public class Bishop implements ChessPiece{
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
    public Bishop(ChessGame.TeamColor color) {
        this.color = color;
    }
    @Override
    public ChessGame.TeamColor getTeamColor() {
        return this.color;
    }

    @Override
    public PieceType getPieceType() {
        return PieceType.BISHOP;
    }

    @Override
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        int x = myPosition.getColumnIndex();
        int y = myPosition.getRowIndex();

        if (y < 7) {
            //NorthWest
            if (x < 7) {
                int yT = y;
                int xT = x;
                for (int i = 0; i < 8; i++, xT--,yT++) {
                    if (xT < 0 || yT > 7) break;
                    ChessPosition currPosition = new Position(xT,yT,true);
                    if (board.getPiece(currPosition) != this) {
                        if (board.getPiece(currPosition) == null) {
                            moves.add(new Move(myPosition, currPosition));
                        } else {
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
            }
            //NorthEast
            if (x > 0) {
                int yT = y;
                int xT = x;
                for (int i = 0; i < 8; i++, xT++,yT++) {
                    if (xT > 7 || yT > 7) break;
                    ChessPosition currPosition = new Position(xT,yT,true);
                    if (board.getPiece(currPosition) != this) {
                        if (board.getPiece(currPosition) == null) {
                            moves.add(new Move(myPosition, currPosition));
                        } else {
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
            }
        }
        if (y > 0) {
            //SouthEast
            if (x < 7) {
                int yT = y;
                int xT = x;
                for (int i = 0; i < 8; i++, xT++,yT--) {
                    if (xT > 7 || yT < 0) break;
                    ChessPosition currPosition = new Position(xT,yT,true);
                    if (board.getPiece(currPosition) != this) {
                        if (board.getPiece(currPosition) == null) {
                            moves.add(new Move(myPosition, currPosition));
                        } else {
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
            }
            //SouthWest
            if (x > 0 ) {
                int yT = y;
                int xT = x;
                for (int i = 0; i < 8; i++, xT--,yT--) {
                    if (xT < 0 || yT < 0) break;
                    ChessPosition currPosition = new Position(xT,yT,true);
                    if (board.getPiece(currPosition) != this) {
                        if (board.getPiece(currPosition) == null) {
                            moves.add(new Move(myPosition, currPosition));
                        } else {
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
            }
        }
        return this.moves;
    }
}
