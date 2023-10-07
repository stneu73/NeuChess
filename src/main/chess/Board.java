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
            addPiece(new Position(i,1), new Pawn(black));
            //piecesOnBoard[i][1] = new Pawn(black);
        }
        piecesOnBoard[0][0] = new Rook(black);
        piecesOnBoard[1][0] = new Knight(black);
        piecesOnBoard[2][0] = new Bishop(black);
        piecesOnBoard[3][0] = new Queen(black);
        piecesOnBoard[4][0] = new King(black);
        piecesOnBoard[5][0] = new Bishop(black);
        piecesOnBoard[6][0] = new Knight(black);
        piecesOnBoard[7][0] = new Rook(black);
        //white team
        ChessGame.TeamColor white = ChessGame.TeamColor.WHITE;
        for (int i = 0; i < 8; i++) {
            piecesOnBoard[i][6] = new Pawn(white);
        }
        piecesOnBoard[0][7] = new Rook(white);
        piecesOnBoard[1][7] = new Knight(white);
        piecesOnBoard[2][7] = new Bishop(white);
        piecesOnBoard[3][7] = new Queen(white);
        piecesOnBoard[4][7] = new King(white);
        piecesOnBoard[5][7] = new Bishop(white);
        piecesOnBoard[6][7] = new Knight(white);
        piecesOnBoard[7][7] = new Rook(white);
        //null for all empty spaces
        for (int i = 2; i < 5;i++){
            for (int j = 0; j < 8; j++) {
                piecesOnBoard[j][i] = null;
            }
        }
    }
}
