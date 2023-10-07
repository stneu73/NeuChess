package chess;

import java.util.Collection;
import java.util.HashSet;

public class Game implements ChessGame{
    TeamColor teamTurn;
    Collection<ChessMove> allTeamMoves;
    ChessBoard board;
    public Game (){
        this.teamTurn = TeamColor.WHITE;
        this.allTeamMoves = new HashSet<>();
        this.board = new Board();
    }
    @Override
    public TeamColor getTeamTurn() {
        return this.teamTurn;
    }

    public TeamColor getOtherTeam() {
        if (getTeamTurn() == TeamColor.WHITE) {
            return TeamColor.BLACK;
        }
        return TeamColor.WHITE;
    }

    @Override
    public void setTeamTurn(TeamColor team) {
        this.teamTurn = team;
    }

    @Override
    public Collection<ChessMove> validMoves(ChessPosition startPosition) {
        return getBoard().getPiece(startPosition).pieceMoves(getBoard(),startPosition);
    }

    @Override
    public void makeMove(ChessMove move) throws InvalidMoveException {

    }
    private Collection<ChessMove> getAllTeamMoves(TeamColor color) {

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                ChessPosition currPosition = new Position(i,j,true);
                if (getBoard().getPiece(currPosition) != null) {
                    if (getBoard().getPiece(currPosition).getTeamColor() == color) {
                        allTeamMoves.addAll(validMoves(currPosition));
                    }
                }
            }
        }
        return this.allTeamMoves;
    }

    @Override
    public boolean isInCheck(TeamColor teamColor) {
        return positionCanBeAttacked(teamColor,getBoard().getKingPosition(teamColor));
    }

    public boolean positionCanBeAttacked(TeamColor teamColor, ChessPosition currPosition) {
        for (var itr: getAllTeamMoves(getOtherTeam())) {
            if (itr.getEndPosition().getColumnIndex() == currPosition.getColumnIndex() && itr.getEndPosition().getRowIndex() == currPosition.getRowIndex()) {
                    return true;
            }
        }
        return false;
    }
    @Override
    public boolean isInCheckmate(TeamColor teamColor) {
        if (isInCheck(teamColor)) {
            for (var itr:validMoves(getBoard().getKingPosition(teamColor))) {
                if (!positionCanBeAttacked(teamColor, itr.getEndPosition())) break;
            }
        }
        return false;
    }

    @Override
    public boolean isInStalemate(TeamColor teamColor) {

        return false;
    }

    @Override
    public void setBoard(ChessBoard board) {
        this.board = board;
    }

    @Override
    public ChessBoard getBoard() {
        return this.board;
    }
}
