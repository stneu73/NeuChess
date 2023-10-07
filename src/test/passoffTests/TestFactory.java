package passoffTests;

import chess.*;

/**
 * Used for testing your code
 * Add in code using your classes for each method for each FIXME
 */
public class TestFactory {

    //Chess Functions
    //------------------------------------------------------------------------------------------------------------------
    public static ChessBoard getNewBoard(){
        return new Board();
    }

    public static ChessGame getNewGame(){
        // FIXME
		return null;
    }

    public static ChessPiece getNewPiece(ChessGame.TeamColor pieceColor, ChessPiece.PieceType type){
        ChessPiece retval = null;
        switch(type) {
            case ROOK -> retval = new Rook(pieceColor);
            case PAWN -> retval = new Pawn(pieceColor);
            case KING -> retval = new King(pieceColor);
            case QUEEN -> retval = new Queen(pieceColor);
            case BISHOP -> retval = new Bishop(pieceColor);
            case KNIGHT -> retval = new Knight(pieceColor);
        }
		return retval;
    }

    public static ChessPosition getNewPosition(Integer row, Integer col){
        return new Position(col,row);
    }

    public static ChessMove getNewMove(ChessPosition startPosition, ChessPosition endPosition, ChessPiece.PieceType promotionPiece){
        return new Move(startPosition,endPosition,promotionPiece);
    }
    //------------------------------------------------------------------------------------------------------------------


    //Server API's
    //------------------------------------------------------------------------------------------------------------------
    public static String getServerPort(){
        return "8080";
    }
    //------------------------------------------------------------------------------------------------------------------


    //Websocket Tests
    //------------------------------------------------------------------------------------------------------------------
    public static Long getMessageTime(){
        /*
        Changing this will change how long tests will wait for the server to send messages.
        3000 Milliseconds (3 seconds) will be enough for most computers. Feel free to change as you see fit,
        just know increasing it can make tests take longer to run.
        (On the flip side, if you've got a good computer feel free to decrease it)
         */
        return 3000L;
    }
    //------------------------------------------------------------------------------------------------------------------
}
