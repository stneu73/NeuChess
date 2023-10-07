package chess;

import javax.swing.*;
import java.util.Collection;
import java.util.HashSet;

public class Game implements ChessGame{
    TeamColor teamTurn;
    Collection<ChessMove> allEnemyMoves;
    ChessBoard board;
    public Game (){
        this.teamTurn = TeamColor.WHITE;
        this.allEnemyMoves = new HashSet<>();
        this.board = new Board();
    }
    @Override
    public TeamColor getTeamTurn() {
        return this.teamTurn;
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
    private Collection<ChessMove> getAllEnemyMoves() {

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                ChessPosition currPosition = new Position(i,j);
                if (getBoard().getPiece(currPosition).getTeamColor() != getTeamTurn()) {
                    allEnemyMoves.addAll(validMoves(currPosition));
                }
            }
        }
        return this.allEnemyMoves;
    }

    @Override
    public boolean isInCheck(TeamColor teamColor) {
        return positionCanBeAttacked(teamColor,getBoard().getKingPosition(teamColor));
    }

    public boolean positionCanBeAttacked(TeamColor teamColor, ChessPosition currPosition) {
        for (var itr:getAllEnemyMoves()) {
            if (itr.getEndPosition() == currPosition) {
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
        board.resetBoard();
    }

    @Override
    public ChessBoard getBoard() {
        return this.board;
    }
}
