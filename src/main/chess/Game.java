package chess;

import java.util.Collection;
import java.util.HashSet;

public class Game implements ChessGame{
    private TeamColor teamTurn;
    private Collection<ChessMove> allTeamMoves;
    private ChessBoard board;
    private ChessBoard hypotheticalBoard;
    public Game (){
        this.teamTurn = TeamColor.WHITE;
        this.allTeamMoves = new HashSet<>();
        this.board = new Board();
        this.hypotheticalBoard = new Board();
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
        setHypotheticalBoardAsBoard();
        if (getHypotheticalBoard().getPiece(move.getStartPosition()).getTeamColor() != getTeamTurn()) throw new InvalidMoveException();
        if (move.getPromotionPiece() != null && getBoard().getPiece(move.getStartPosition()).getPieceType() != ChessPiece.PieceType.PAWN) { throw new InvalidMoveException(); }
        boolean validMove = false;
        for (var itr:validMoves(move.getStartPosition())) {
            if (move.getEndPosition().equals(itr.getEndPosition())) {
                validMove = true;
                break;
            }
        }
        if (!validMove) throw new InvalidMoveException();
        getHypotheticalBoard().executeMove(move);
        if (isInCheck(getTeamTurn())) {
            throw new InvalidMoveException();
        }
        getBoard().executeMove(move);
        setHypotheticalBoardAsBoard();
        setTeamTurn(getOtherTeam());
    }
    private Collection<ChessMove> getAllTeamMoves(TeamColor color) {
        this.allTeamMoves = new HashSet<>();
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                ChessPosition currPosition = new Position(i,j,true);
                if (getBoard().getPiece(currPosition) != null) {
                    if (getBoard().getPiece(currPosition).getTeamColor() == color) {
                        this.allTeamMoves.addAll(validMoves(currPosition));
                    }
                }
            }
        }
        return this.allTeamMoves;
    }

    @Override
    public boolean isInCheck(TeamColor teamColor) {
        return positionCanBeAttacked(teamColor,getHypotheticalBoard().getKingPosition(teamColor));
    }

    public boolean positionCanBeAttacked(TeamColor teamColor, ChessPosition currPosition) {
        allTeamMoves = new HashSet<>();
        for (var itr:getAllTeamMoves(getOtherTeam())) {
            if (itr.getEndPosition().equals(currPosition)) {
                    return true;
            }
        }
        return false;
    }

//    public boolean validKingMove(TeamColor teamColor, )
    @Override
    public boolean isInCheckmate(TeamColor teamColor) {
        if (isInCheck(teamColor)) {
            int sum = 0;
            var val = validMoves(getBoard().getKingPosition(teamColor));
            for (var itr:val) {
                if (positionCanBeAttacked(teamColor, itr.getEndPosition())) {
                    sum += 1;
                }
            }
            return validMoves(getBoard().getKingPosition(teamColor)).size() == sum;
        }
        return false;
    }

    @Override
    public boolean isInStalemate(TeamColor teamColor) {

        if (getAllTeamMoves(teamColor).isEmpty()) {
            return true;
        }
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
    public ChessBoard getHypotheticalBoard() { return this.hypotheticalBoard; }
    public ChessBoard setHypotheticalBoardAsBoard() {
        hypotheticalBoard = new Board();
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                getHypotheticalBoard().getPiecesOnBoard()[i][j] = getBoard().getPiecesOnBoard()[i][j];
            }
        }
        return this.hypotheticalBoard;
    }
}