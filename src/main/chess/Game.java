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
        Collection<ChessMove> moves = new HashSet<>();
        ChessBoard hyp = new Board();
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                hyp.getPiecesOnBoard()[i][j] = getBoard().getPiecesOnBoard()[i][j];
            }
        }
        for (var itr:getBoard().getPiece(startPosition).pieceMoves(hyp,startPosition)) {
            hyp = new Board();
            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 8; j++) {
                    hyp.getPiecesOnBoard()[i][j] = getBoard().getPiecesOnBoard()[i][j];
                }
            }
            hyp.executeMove(itr);
            if (!isInHypotheticalCheck(getTeamTurn(),hyp)) {
                moves.add(itr);
            }

        }
        setHypotheticalBoardAsBoard();
        return moves;

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
        setHypotheticalBoardAsBoard();
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                ChessPosition currPosition = new Position(i,j,true);
                if (getHypotheticalBoard().getPiece(currPosition) != null) {
                    if (getHypotheticalBoard().getPiece(currPosition).getTeamColor() == color) {
//                        for (var itr: validMoves(currPosition)) {
//                            getHypotheticalBoard().executeMove(itr);
//                            if (!isInCheck(getTeamTurn())) {
//                                allTeamMoves.add(itr);
//                            }
//                            setHypotheticalBoardAsBoard();
//                        }
                        this.allTeamMoves.addAll(getHypotheticalBoard().getPiece(currPosition).pieceMoves(getHypotheticalBoard(),currPosition));
                    }
                }
            }
        }
        return this.allTeamMoves;
    }
    private Collection<ChessMove> getAllTeamMoves(TeamColor color, ChessBoard hypBoard) {
        this.allTeamMoves = new HashSet<>();
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                ChessPosition currPosition = new Position(i, j, true);
                if (hypBoard.getPiece(currPosition) != null) {
                    if (hypBoard.getPiece(currPosition).getTeamColor() == color) {
//                        for (var itr: validMoves(currPosition)) {
//                            getHypotheticalBoard().executeMove(itr);
//                            if (!isInCheck(getTeamTurn())) {
//                                allTeamMoves.add(itr);
//                            }
//                            setHypotheticalBoardAsBoard();
//                        }
                        this.allTeamMoves.addAll(hypBoard.getPiece(currPosition).pieceMoves(hypBoard, currPosition));
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

    public boolean isInHypotheticalCheck(TeamColor teamColor, ChessBoard hypBoard) {
        return positionCanBeAttacked(teamColor,hypBoard.getKingPosition(teamColor), hypBoard);
    }
//    public Collection<ChessMove> avoidingCheckValidMoves() {
//
//        for (var itr: getAllTeamMoves(getTeamTurn())) {
//            getHypotheticalBoard().executeMove(itr);
//            if (!isInCheck(getTeamTurn())) {
//                validMoves.add(itr);
//            }
//            setHypotheticalBoardAsBoard();
//        }
//        return validMoves;
//    }

    public boolean positionCanBeAttacked(TeamColor teamColor, ChessPosition currPosition) {
//        allTeamMoves = new HashSet<>();
        for (var itr:getAllTeamMoves(getOtherTeam())) {
            if (itr.getEndPosition().equals(currPosition)) {
                    return true;
            }
        }
        return false;
    }
    public boolean positionCanBeAttacked(TeamColor teamColor, ChessPosition currPosition, ChessBoard hypBoard) {
//        allTeamMoves = new HashSet<>();
        for (var itr:getAllTeamMoves(getOtherTeam(),hypBoard)) {
            if (itr.getEndPosition().equals(currPosition)) {
                return true;
            }
        }
        return false;
    }


    @Override
    public boolean isInCheckmate(TeamColor teamColor) {
//        if (isInCheck(teamColor)) {
//            int sum = 0;
//            var val = validMoves(getBoard().getKingPosition(teamColor));
//            for (var itr:val) {
//                if (positionCanBeAttacked(teamColor, itr.getEndPosition())) {
//                    sum += 1;
//                }
//            }
//            return validMoves(getBoard().getKingPosition(teamColor)).size() == sum;
//        }
//        return false;
        return isInCheck(getTeamTurn()) && getAllTeamMoves(teamColor).isEmpty();
    }

    @Override
    public boolean isInStalemate(TeamColor teamColor) {
        return getAllTeamMoves(teamColor).isEmpty() && !isInCheck(teamColor);
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