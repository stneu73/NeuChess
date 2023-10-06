package chess;

import java.util.Collection;

public class Rook implements ChessPiece{
    private ChessGame.TeamColor color;
    private Collection<ChessMove> moves;
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
        for (int i = x; i < 8; i++) {
            moves.add(new Move(myPosition,new Position(y,i)));
        }
        //up
        for (int i = y; i > 0; i--) {
            moves.add(new Move(myPosition,new Position(i,x)));
        }
        //down
        for (int i = y; i < 0; i++) {
            moves.add(new Move(myPosition,new Position(i,x)));
        }
        return this.moves;
    }
}
