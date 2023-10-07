package chess;

import java.util.Collection;
import java.util.HashSet;


public class Knight implements ChessPiece{
    private ChessGame.TeamColor color;
    private Collection<ChessMove> moves;
    public Knight(ChessGame.TeamColor color) {
        this.color = color;
        moves = new HashSet<>();
    }
    @Override
    public ChessGame.TeamColor getTeamColor() {
        return this.color;
    }

    @Override
    public PieceType getPieceType() {
        return PieceType.KNIGHT;
    }

    @Override
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        int x = myPosition.getColumnIndex();
        int y = myPosition.getRowIndex();
        ChessPosition currPosition;
        if (x + 2 < 8) {
            if (y + 1 < 8) {
                currPosition = new Position(x + 2, y + 1, true);
                moveCheckAndAdd(board, myPosition,currPosition);
            }
            if (y - 1 > -1) {
                currPosition = new Position(x + 2, y - 1, true);
                moveCheckAndAdd(board, myPosition,currPosition);
            }
        }
        if (x - 2 > -1) {
            if (y + 1 < 8) {
                currPosition = new Position(x - 2, y + 1, true);
                moveCheckAndAdd(board, myPosition,currPosition);
            }
            if (y - 1 > -1) {
                currPosition = new Position(x - 2, y - 1, true);
                moveCheckAndAdd(board, myPosition,currPosition);
            }
        }
        if (y + 2 < 8) {
            if (x + 1 < 8) {
                currPosition = new Position(x + 1, y + 2, true);
                moveCheckAndAdd(board, myPosition,currPosition);
            }
            if (x - 1 > -1) {
                currPosition = new Position(x - 1, y + 2, true);
                moveCheckAndAdd(board, myPosition,currPosition);
            }
        }
        if (y - 2 > -1) {
            if (x + 1 < 8) {
                currPosition = new Position(x + 1, y - 2, true);
                moveCheckAndAdd(board, myPosition,currPosition);
            }
            if (x - 1 > -1) {
                currPosition = new Position(x - 1, y - 2, true);
                moveCheckAndAdd(board, myPosition,currPosition);
            }
        }
        return this.moves;
    }

    private void moveCheckAndAdd(ChessBoard board, ChessPosition myPosition, ChessPosition currPosition) {
        if (board.getPiece(currPosition) == null || board.getPiece(currPosition).getTeamColor() != this.color) {
            moves.add(new Move(myPosition,currPosition));
        }
    }
}
