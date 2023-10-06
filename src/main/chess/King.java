package chess;

import java.util.Collection;

public class King implements ChessPiece{
    private ChessGame.TeamColor color;
    private Collection<ChessMove> moves;
    public King(ChessGame.TeamColor color) {
        this.color = color;
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
        return moves;
    }
}
