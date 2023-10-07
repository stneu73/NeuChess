package chess;

import java.util.Collection;
import java.util.HashSet;

public class King implements ChessPiece{
    private ChessGame.TeamColor color;
    private Collection<ChessMove> moves;
    public King(ChessGame.TeamColor color) {
        this.color = color;
        moves = new HashSet<>();
    }
    @Override
    public ChessGame.TeamColor getTeamColor() {
        return this.color;
    }

    @Override
    public PieceType getPieceType() {
        return PieceType.KING;
    }

    @Override
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        int x = myPosition.getColumnIndex();
        int y = myPosition.getRowIndex();
        ChessPosition currPosition = new Position(x,y+1,true);
        //North
        if (y + 1 < 8) {
            if (board.getPiece(currPosition) == null || board.getPiece(currPosition).getTeamColor() != this.color) {
                moves.add(new Move(myPosition, currPosition));
            }
        }
        //south
        if (y - 1 > -1) {
            currPosition = new Position(x, y - 1, true);
            if (board.getPiece(currPosition) == null || board.getPiece(currPosition).getTeamColor() != this.color) {
                moves.add(new Move(myPosition, currPosition));
            }
        }
        //east
        if (x + 1 < 8) {
            currPosition = new Position(x + 1, y, true);
            if (board.getPiece(currPosition) == null || board.getPiece(currPosition).getTeamColor() != this.color) {
                moves.add(new Move(myPosition, currPosition));
            }
        }
        //west
        if (x - 1 > -1) {
            currPosition = new Position(x - 1, y, true);
            if (board.getPiece(currPosition) == null || board.getPiece(currPosition).getTeamColor() != this.color) {
                moves.add(new Move(myPosition, currPosition));
            }
        }
        //north east
        if (y + 1 < 8 && x + 1 < 8) {
            currPosition = new Position(x + 1, y + 1, true);
            if (board.getPiece(currPosition) == null || board.getPiece(currPosition).getTeamColor() != this.color) {
                moves.add(new Move(myPosition, currPosition));
            }
        }
        //south west
        if (y - 1 > -1 && x - 1 > -1) {
            currPosition = new Position(x - 1, y - 1, true);
            if (board.getPiece(currPosition) == null || board.getPiece(currPosition).getTeamColor() != this.color) {
                moves.add(new Move(myPosition, currPosition));
            }
        }
        //south east
        if (y - 1 > -1 && x + 1 < 8) {
            currPosition = new Position(x + 1, y - 1, true);
            if (board.getPiece(currPosition) == null || board.getPiece(currPosition).getTeamColor() != this.color) {
                moves.add(new Move(myPosition, currPosition));
            }
        }
        //north west
        if (y + 1 < 8 && x - 1 > -1) {
            currPosition = new Position(x - 1, y + 1, true);
            if (board.getPiece(currPosition) == null || board.getPiece(currPosition).getTeamColor() != this.color) {
                moves.add(new Move(myPosition, currPosition));
            }
        }
        return moves;
    }
}
