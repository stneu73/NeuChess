package chess;

import java.util.Collection;
import java.util.HashSet;

public class Pawn implements ChessPiece{
    private ChessGame.TeamColor color;
    private Collection<ChessMove> moves;
    public Pawn(ChessGame.TeamColor color) {
        this.color = color;
        moves = new HashSet<>();
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
        int x = myPosition.getColumnIndex();
        int y = myPosition.getRowIndex();
        ChessPosition currPosition = null;
        //northward (white)
        if (this.color == ChessGame.TeamColor.WHITE) {
            currPosition = new Position(x, y + 1, true);
            if (board.getPiece(currPosition) == null) {
                if (y + 1 != 7) {
                    moves.add(new Move(myPosition, currPosition));
                }
                if (y + 1 == 7) {
                    promotionMoves(myPosition,currPosition);
                }
                if (y == 1) {
                    currPosition = new Position(x, y + 2, true);
                    if (board.getPiece(currPosition) == null) {
                        moves.add(new Move(myPosition, currPosition));
                    }
                }
            }
            if (x + 1 < 8 && y + 1 < 8 && x - 1 > 0) {
                currPosition = new Position(x + 1, y + 1, true);
                if (board.getPiece(currPosition) != null) {
                    if (board.getPiece(currPosition).getTeamColor() != this.color) {
                        if (y + 1 != 7) {
                            moves.add(new Move(myPosition, currPosition));
                        }
                        if (y + 1 == 7) {
                            promotionMoves(myPosition,currPosition);
                        }
                    }
                }
                currPosition = new Position(x - 1, y + 1, true);
                if (board.getPiece(currPosition) != null) {
                    if (board.getPiece(currPosition).getTeamColor() != this.color) {
                        if (y + 1 != 7) {
                            moves.add(new Move(myPosition, currPosition));
                        }
                        if (y + 1 == 7) {
                            promotionMoves(myPosition,currPosition);
                        }
                    }
                }
            }
        }
        //southward (black)
        if (this.color == ChessGame.TeamColor.BLACK) {
            currPosition = new Position(x, y - 1, true);
            if (board.getPiece(currPosition) == null) {
                if (y - 1 !=0) {
                    moves.add(new Move(myPosition, currPosition));
                }
                if (y - 1 == 0) {
                    promotionMoves(myPosition,currPosition);
                }
                if (y == 6) {
                    currPosition = new Position(x, y - 2, true);
                    if (board.getPiece(currPosition) == null) {
                        moves.add(new Move(myPosition, currPosition));
                    }
                }
            }
            if (x + 1 < 8 && y - 1 > -1 && x - 1 > -1) {
                currPosition = new Position(x + 1, y - 1, true);
                if (board.getPiece(currPosition) != null) {
                    if (board.getPiece(currPosition).getTeamColor() != this.color) {
                        if (y - 1 != 0) {
                            moves.add(new Move(myPosition, currPosition));
                        }
                        if (y - 1 == 0) {
                            promotionMoves(myPosition,currPosition);
                        }
                    }
                }
                currPosition = new Position(x - 1, y - 1, true);
                if (board.getPiece(currPosition) != null) {
                    if (board.getPiece(currPosition).getTeamColor() != this.color) {
                        if (y - 1 != 0) {
                            moves.add(new Move(myPosition, currPosition));
                        }
                        if (y - 1 == 0) {
                            promotionMoves(myPosition,currPosition);
                        }
                    }
                }
            }
        }
        return this.moves;
    }

    private void promotionMoves(ChessPosition myPosition, ChessPosition currPosition) {
        moves.add(new Move(myPosition, currPosition, PieceType.QUEEN));
        moves.add(new Move(myPosition, currPosition, PieceType.BISHOP));
        moves.add(new Move(myPosition, currPosition, PieceType.ROOK));
        moves.add(new Move(myPosition, currPosition, PieceType.KNIGHT));
    }
}
