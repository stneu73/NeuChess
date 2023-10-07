package chess;

import java.util.Collection;
import java.util.HashSet;

public class Rook implements ChessPiece{
    private ChessGame.TeamColor color;
    private Collection<ChessMove> moves;

    public Rook(ChessGame.TeamColor color) {
        this.color = color;
        moves = new HashSet<>();
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
