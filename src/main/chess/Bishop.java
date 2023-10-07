package chess;

import java.util.Collection;
import java.util.HashSet;

public class Bishop implements ChessPiece{
    private ChessGame.TeamColor color;
    private Collection<ChessMove> moves = new HashSet<>();
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
            if (x > 0) {
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
            if (x < 7) {
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
