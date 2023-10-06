package chess;

import java.util.Collection;

public class Pawn implements ChessPiece{
    private ChessGame.TeamColor color;
    private Collection<ChessMove> moves;
    public Pawn(ChessGame.TeamColor color) {
        this.color = color;
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
        return this.moves;
    }
}
