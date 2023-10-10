package chess;

import java.util.Collection;
import java.util.HashSet;

public class Queen implements ChessPiece{
    private ChessGame.TeamColor color;
    private Collection<ChessMove> moves;
    public Queen(ChessGame.TeamColor color) {
        this.color = color;
        this.moves = new HashSet<>();
    }
    @Override
    public ChessGame.TeamColor getTeamColor() {
        return this.color;
    }

    @Override
    public PieceType getPieceType() {
        return PieceType.QUEEN;
    }

    @Override
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        int x = myPosition.getColumnIndex();
        int y = myPosition.getRowIndex();
        Collection<ChessMove> moves = new HashSet<>(); //moves.clear();
        //Bishop Logic
        if (y < 7) {
            //NorthWest
            if (x > 0) {
                int yT = y;
                int xT = x;
                for (int i = 0; i < 8; i++, xT--, yT++) {
                    if (xT < 0 || yT > 7) break;
                    ChessPosition currPosition = new Position(xT,yT,true);
                    if (board.getPiece(currPosition) != this) {
                        if (board.getPiece(currPosition) == null) {
                            moves.add(new Move(myPosition, currPosition));
                        } else {
                            ChessPiece piece = board.getPiece(currPosition);
                            if (piece.getTeamColor() == this.color) {
                                break;
                            }
                            if (piece.getTeamColor() != this.color) {
                                moves.add(new Move(myPosition, currPosition));
                                break;
                            }
                        }
                    }
                }
            }
            //NorthEast
            if (x < 7) {
                int yT = y;
                int xT = x;
                for (int i = 0; i < 8; i++, xT++,yT++) {
                    if (xT > 7 || yT > 7) break;
                    ChessPosition currPosition = new Position(xT,yT,true);
                    if (board.getPiece(currPosition) != this) {
                        if (board.getPiece(currPosition) == null) {
                            moves.add(new Move(myPosition, currPosition));
                        } else {
                            ChessPiece piece = board.getPiece(currPosition);
                            if (piece.getTeamColor() == this.color) {
                                break;
                            }
                            if (piece.getTeamColor() != this.color) {
                                moves.add(new Move(myPosition, currPosition));
                                break;
                            }
                        }
                    }
                }
            }
        }
        if (y > 0) {
            //SouthEast
            if (x < 7) {
                int yT = y;
                int xT = x;
                for (int i = 0; i < 8; i++, xT++,yT--) {
                    if (xT > 7 || yT < 0) break;
                    ChessPosition currPosition = new Position(xT,yT,true);
                    if (board.getPiece(currPosition) != this) {
                        if (board.getPiece(currPosition) == null) {
                            moves.add(new Move(myPosition, currPosition));
                        } else {
                            ChessPiece piece = board.getPiece(currPosition);
                            if (piece.getTeamColor() == this.color) {
                                break;
                            }
                            if (piece.getTeamColor() != this.color) {
                                moves.add(new Move(myPosition, currPosition));
                                break;
                            }
                        }
                    }
                }
            }
            //SouthWest
            if (x > 0) {
                int yT = y;
                int xT = x;
                for (int i = 0; i < 8; i++, xT--,yT--) {
                    if (xT < 0 || yT < 0) break;
                    ChessPosition currPosition = new Position(xT,yT,true);
                    if (board.getPiece(currPosition) != this) {
                        if (board.getPiece(currPosition) == null) {
                            moves.add(new Move(myPosition, currPosition));
                        } else {
                            ChessPiece piece = board.getPiece(currPosition);
                            if (piece.getTeamColor() == this.color) {
                                break;
                            }
                            if (piece.getTeamColor() != this.color) {
                                moves.add(new Move(myPosition, currPosition));
                                break;
                            }
                        }
                    }
                }
            }
        }
        //Rook Logic
        //left
        if (x > 0) {
            for (int i = x; i >= 0; i--) {
                ChessPosition currPosition = new Position(i,y,true);
                if (board.getPiece(currPosition) == null) {
                    moves.add(new Move(myPosition, currPosition));
                }
                if (i == 0) break;
                currPosition = new Position(i - 1,y,true);
                if (board.getPiece(currPosition) != null) {
                    ChessPiece piece = board.getPiece(currPosition);
                    if (piece.getTeamColor() == this.color) {
                        break;
                    }
                    if (piece.getTeamColor() != this.color) {
                        moves.add(new Move(myPosition, currPosition));
                        break;
                    }
                }
            }
        }
        //right
        if (x < 7) {
            for (int i = x; i < 8; i++) {
                ChessPosition currPosition = new Position(i,y,true);
                if (board.getPiece(currPosition) == null) {
                    moves.add(new Move(myPosition, currPosition));
                }
                if (i == 7) break;
                currPosition = new Position(i+1,y,true);
                if (board.getPiece(currPosition) != null) {
                    ChessPiece piece = board.getPiece(currPosition);
                    if (piece.getTeamColor() == this.color) {
                        break;
                    }
                    if (piece.getTeamColor() != this.color) {
                        moves.add(new Move(myPosition, currPosition));
                        break;
                    }
                }
            }
        }
        //up
        if (y < 7) {
            for (int i = y; i < 8; i++) {
                ChessPosition currPosition = new Position(x,i,true);
                if (board.getPiece(currPosition) == null) {
                    moves.add(new Move(myPosition, currPosition));
                }
                if (i == 7) break;
                currPosition = new Position(x,i+1,true);
                if (board.getPiece(currPosition) != null) {
                    ChessPiece piece = board.getPiece(currPosition);
                    if (piece.getTeamColor() == this.color) {
                        break;
                    }
                    if (piece.getTeamColor() != this.color) {
                        moves.add(new Move(myPosition,currPosition));
                        break;
                    }
                }
            }
        }
        //down
        if (y > 0) {
            for (int i = y; i >= 0; i--) {
                ChessPosition currPosition = new Position(x,i,true);
                if (board.getPiece(currPosition) == null) {
                    moves.add(new Move(myPosition, currPosition));
                }
                if (i == 0) break;
                currPosition = new Position(x,i-1,true);
                if (board.getPiece(currPosition) != null) {
                    ChessPiece piece = board.getPiece(currPosition);
                    if (piece.getTeamColor() == this.color) {
                        break;
                    }
                    if (piece.getTeamColor() != this.color) {
                        moves.add(new Move(myPosition, currPosition));
                        break;
                    }
                }
            }
        }
        return moves;
    }
}
