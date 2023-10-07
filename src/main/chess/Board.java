package chess;

public class Board implements ChessBoard {
    ChessPiece[][] piecesOnBoard = new ChessPiece[8][8];

    @Override
    public void addPiece(ChessPosition position, ChessPiece piece) {
        piecesOnBoard[position.getColumnIndex()][position.getRowIndex()] = piece;
    }

    @Override
    public ChessPiece getPiece(ChessPosition position) {
        return piecesOnBoard[position.getColumnIndex()][position.getRowIndex()];
    }

    @Override
    public void resetBoard() {
        //black team
        ChessGame.TeamColor black = ChessGame.TeamColor.BLACK;
        for (int i = 0; i < 8; i++) {
            addPiece(new Position(i,6,true), new Pawn(black));
            //piecesOnBoard[i][1] = new Pawn(black);
        }
        addPiece(new Position(0,7,true),new Rook(black));
        addPiece(new Position(1,7,true),new Knight(black));
        addPiece(new Position(2,7,true),new Bishop(black));
        addPiece(new Position(3,7,true),new Queen(black));
        addPiece(new Position(4,7,true),new King(black));
        addPiece(new Position(5,7,true),new Bishop(black));
        addPiece(new Position(6,7,true),new Knight(black));
        addPiece(new Position(7,7,true),new Rook(black));
        //white team
        ChessGame.TeamColor white = ChessGame.TeamColor.WHITE;
        for (int i = 0; i < 8; i++) {
            addPiece(new Position(i,1,true), new Pawn(white));
        }
        addPiece(new Position(0,0,true),new Rook(white));
        addPiece(new Position(1,0,true),new Knight(white));
        addPiece(new Position(2,0,true),new Bishop(white));
        addPiece(new Position(3,0,true),new Queen(white));
        addPiece(new Position(4,0,true),new King(white));
        addPiece(new Position(5,0,true),new Bishop(white));
        addPiece(new Position(6,0,true),new Knight(white));
        addPiece(new Position(7,0,true),new Rook(white));
        //null for all empty spaces
        for (int i = 2; i < 5;i++){
            for (int j = 0; j < 8; j++) {
                piecesOnBoard[j][i] = null;
            }
        }
    }
}
