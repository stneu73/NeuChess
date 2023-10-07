package chess;

import java.util.Collection;
import java.util.Iterator;

public class Pawn implements ChessPiece{
    private ChessGame.TeamColor color;
    private Collection<ChessMove> moves;
    private boolean moved;
    public Pawn(ChessGame.TeamColor color) {
        this.color = color;
        moved = false;
        moves = new Collection<ChessMove>() {
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
    }

    @Override
    public ChessGame.TeamColor getTeamColor() {
        return this.color;
    }

    @Override
    public PieceType getPieceType() {
        return PieceType.PAWN;
    }

    @Override
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        int x = myPosition.getColumnIndex();
        int y = myPosition.getRowIndex();
        ChessPosition currPosition = null;
        //northward (white)
        if (this.color == ChessGame.TeamColor.WHITE) {
            currPosition = new Position(x, y + 1, true);
            if (board.getPiece(currPosition) == null) {
                moves.add(new Move(myPosition, currPosition));
                if (y + 1 == 7) {
                    promotionMoves(myPosition,currPosition);
                }
                if (y == 1) {
                    currPosition = new Position(x, y + 2, true);
                    if (board.getPiece(currPosition) == null) {
                        moves.add(new Move(myPosition, currPosition));
                    }
                }
            }
            if (x + 1 < 8 && y + 1 < 8 && x - 1 > 0) {
                currPosition = new Position(x + 1, y + 1, true);
                if (board.getPiece(currPosition) != null) {
                    if (board.getPiece(currPosition).getTeamColor() != this.color) {
                        moves.add(new Move(myPosition, currPosition));
                        if (y + 1 == 7) {
                            promotionMoves(myPosition,currPosition);
                        }
                    }
                }
                currPosition = new Position(x - 1, y + 1, true);
                if (board.getPiece(currPosition) != null) {
                    if (board.getPiece(currPosition).getTeamColor() != this.color) {
                        moves.add(new Move(myPosition, currPosition));
                        if (y + 1 == 7) {
                            promotionMoves(myPosition,currPosition);
                        }
                    }
                }
            }
        }
        //southward (black)
        if (this.color == ChessGame.TeamColor.BLACK) {
            currPosition = new Position(x, y - 1, true);
            if (board.getPiece(currPosition) == null) {
                moves.add(new Move(myPosition, currPosition));
                if (y - 1 == 0) {
                    promotionMoves(myPosition,currPosition);
                }
                if (y == 6) {
                    currPosition = new Position(x, y - 2, true);
                    if (board.getPiece(currPosition) == null) {
                        moves.add(new Move(myPosition, currPosition));
                    }
                }
            }
            if (x + 1 < 8 && y - 1 > -1 && x - 1 > -1) {
                currPosition = new Position(x + 1, y - 1, true);
                if (board.getPiece(currPosition) != null) {
                    if (board.getPiece(currPosition).getTeamColor() != this.color) {
                        moves.add(new Move(myPosition, currPosition));
                        if (y - 1 == 0) {
                            promotionMoves(myPosition,currPosition);
                        }
                    }
                }
                currPosition = new Position(x - 1, y - 1, true);
                if (board.getPiece(currPosition) != null) {
                    if (board.getPiece(currPosition).getTeamColor() != this.color) {
                        moves.add(new Move(myPosition, currPosition));
                        if (y - 1 == 0) {
                            promotionMoves(myPosition,currPosition);
                        }
                    }
                }
            }
        }
        return this.moves;
    }

    private void promotionMoves(ChessPosition myPosition, ChessPosition currPosition) {
        moves.add(new Move(myPosition, currPosition, PieceType.QUEEN));
        moves.add(new Move(myPosition, currPosition, PieceType.BISHOP));
        moves.add(new Move(myPosition, currPosition, PieceType.ROOK));
        moves.add(new Move(myPosition, currPosition, PieceType.KNIGHT));
    }
}
