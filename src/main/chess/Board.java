package chess;

public class Board implements ChessBoard {
    ChessPiece[][] piecesOnBoard;
    @Override
    public void addPiece(ChessPosition position, ChessPiece piece) {
        piecesOnBoard[position.getRow()][position.getColumn()] = piece;
    }

    @Override
    public ChessPiece getPiece(ChessPosition position) {
        return piecesOnBoard[position.getRow()][position.getColumn()];
    }

    @Override
    public void resetBoard() {
        //black team
        for (int i = 0; i < 8; i++) {
            piecesOnBoard[i][1] = new Pawn();
        }
        piecesOnBoard[0][0] = new Rook();
        piecesOnBoard[1][0] = new Knight();
        piecesOnBoard[2][0] = new Bishop();
        piecesOnBoard[3][0] = new Queen();
        piecesOnBoard[4][0] = new King();
        piecesOnBoard[5][0] = new Bishop();
        piecesOnBoard[6][0] = new Knight();
        piecesOnBoard[7][0] = new Rook();
        //white team
        for (int i = 0; i < 8; i++) {
            piecesOnBoard[i][6] = new Pawn();
        }
        piecesOnBoard[0][7] = new Rook();
        piecesOnBoard[1][7] = new Knight();
        piecesOnBoard[2][7] = new Bishop();
        piecesOnBoard[3][7] = new Queen();
        piecesOnBoard[4][7] = new King();
        piecesOnBoard[5][7] = new Bishop();
        piecesOnBoard[6][7] = new Knight();
        piecesOnBoard[7][7] = new Rook();
        //null for all empty spaces
        for (int i = 2; i < 5;i++){
            for (int j = 0; j < 8; j++) {
                piecesOnBoard[j][i] = null;
            }
        }
    }
}
