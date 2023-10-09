package chess;

public class Board implements ChessBoard {
    private ChessPiece[][] piecesOnBoard = new ChessPiece[8][8];

    @Override
    public void addPiece(ChessPosition position, ChessPiece piece) {
        this.piecesOnBoard[position.getColumnIndex()][position.getRowIndex()] = piece;
    }

    @Override
    public ChessPiece getPiece(ChessPosition position) {
        return this.piecesOnBoard[position.getColumnIndex()][position.getRowIndex()];
    }

    public ChessPiece[][] getPiecesOnBoard() {
        return piecesOnBoard;
    }

    public ChessPosition getKingPosition(ChessGame.TeamColor color) {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                ChessPosition currPosition = new Position(i,j,true);
                if (getPiece(currPosition) != null) {
                    if (getPiece(currPosition).getPieceType() == ChessPiece.PieceType.KING && getPiece(currPosition).getTeamColor() == color) {
                        return currPosition;
                    }
                }
            }
        }
        return null;
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
                this.piecesOnBoard[j][i] = null;
            }
        }
    }
    public void deletePiece(ChessPosition positionToDelete) {
        this.piecesOnBoard[positionToDelete.getColumnIndex()][positionToDelete.getRowIndex()] = null;
    }
    public void executeMove(ChessMove move) {
        ChessPosition startPosition = move.getStartPosition();
        ChessPosition endPosition = move.getEndPosition();
        if (move.getPromotionPiece() != null) {
            ChessGame.TeamColor pieceColor = this.piecesOnBoard[startPosition.getColumnIndex()][startPosition.getRowIndex()].getTeamColor();
            ChessPiece piece = null;
            switch(move.getPromotionPiece()) {
                case ROOK -> piece = new Rook(pieceColor);
                case PAWN -> piece = new Pawn(pieceColor);
                case KING -> piece = new King(pieceColor);
                case QUEEN -> piece = new Queen(pieceColor);
                case BISHOP -> piece = new Bishop(pieceColor);
                case KNIGHT -> piece = new Knight(pieceColor);
            };
            this.piecesOnBoard[endPosition.getColumnIndex()][endPosition.getRowIndex()] = piece;
        }
        else {
            this.piecesOnBoard[endPosition.getColumnIndex()][endPosition.getRowIndex()] = this.piecesOnBoard[startPosition.getColumnIndex()][startPosition.getRowIndex()];
        }
        deletePiece(startPosition);
    }
}
