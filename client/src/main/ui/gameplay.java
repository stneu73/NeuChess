package ui;

import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.Random;

import static ui.EscapeSequences.*;

public class gameplay {
    private static final int BOARD_SIZE_IN_SQUARES = 8;
    private static final int SQUARE_SIZE_IN_CHARS = 1;
    private static final int LINE_WIDTH_IN_CHARS = 0;

    public static void playChess() {
        String boardStart = "rnbqkbnrppppppppeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeePPPPPPPPRNBQKBNR";
        var out = new PrintStream(System.out, true, StandardCharsets.UTF_8);
        out.print(ERASE_SCREEN);
        String[] headers = new String[]{" a ", " b ", " c ", " d ", " e ", " f ", " g ", " h "};
        String[] rowNums = new String[]{" 1 "," 2 "," 3 "," 4 "," 5 "," 6 "," 7 "," 8 "};
        drawHeaders(out, headers);
        printChessBoard(out, boardStart,rowNums);
        drawHeaders(out, headers);

        out.println();

        String reversedBoardState = reverseBoard(boardStart);
        headers = new String[]{" h ", " g ", " f ", " e ", " d ", " c ", " b ", " a "};
        rowNums = new String[]{" 8 "," 7 "," 6 "," 5 "," 4 "," 3 "," 2 "," 1 "};
        drawHeaders(out, headers);
        printChessBoard(out,reversedBoardState,rowNums);
        drawHeaders(out, headers);
    }

    private static void drawHeaders(PrintStream out, String[] headers) {
        setForBorder(out);
        out.print("   ");
        for (int boardCol = 0; boardCol < BOARD_SIZE_IN_SQUARES; boardCol++) {
            drawHeader(out, headers[boardCol]);
        }
        out.print("   ");
        setBlack(out);
        out.println();
    }

    private static void drawHeader(PrintStream out, String headerText) {
        printHeaderText(out, headerText);
    }

    private static void printHeaderText(PrintStream out, String player) {
        out.print(player);
    }
    private static void printChessBoard(PrintStream out, String chessBoard, String[] rowNums) {
        for (int boardRow = 0; boardRow < BOARD_SIZE_IN_SQUARES; boardRow++) {
            int rowNum = boardRow+1;
            setForBorder(out);
            out.print(rowNums[boardRow]);
            drawRowOfSquares(out,rowNum,chessBoard);
            setForBorder(out);
            out.print(rowNums[boardRow]);
            setBlack(out);
            out.println();
        }
    }
    private static void drawRowOfSquares(PrintStream out, int boardRow, String chessBoard) {
        char[] nextEightPieces = chessBoard.substring((boardRow-1)*8).toCharArray();
        for (int squareRow = 0; squareRow < SQUARE_SIZE_IN_CHARS; ++squareRow) {
            for (int boardCol = 0; boardCol < BOARD_SIZE_IN_SQUARES; ++boardCol) {
//                boolean isBlack = false;
                if (boardRow % 2 == 0 && boardCol % 2 == 1) {
                    setWhite(out);
                    out.print(SET_TEXT_COLOR_BLACK);
                } else if (boardRow % 2 == 1 && boardCol % 2 == 0) {
                    setWhite(out);
                    out.print(SET_TEXT_COLOR_BLACK);
                } else {
                    setBlack(out);
                    out.print(SET_TEXT_COLOR_WHITE);
                }
                if (nextEightPieces[boardCol] == 'e') {
                    out.print("   ");
                } else {
                    out.print(" "+nextEightPieces[boardCol]+" ");
                }
            }
        }
    }

    private static String reverseBoard(String board) {
        StringBuilder boardAsSB = new StringBuilder();
        for (int i = board.length()-1; i >= 0; i--) {
            boardAsSB.append(board.charAt(i));
        }
        return boardAsSB.toString();
    }

    private static void setBlack(PrintStream out) {
        out.print(SET_BG_COLOR_BLACK);
        out.print(SET_TEXT_COLOR_BLACK);
    }

    private static void setWhite(PrintStream out) {
        out.print(SET_BG_COLOR_WHITE);
        out.print(SET_TEXT_COLOR_WHITE);
    }

    private static void setForBorder(PrintStream out) {
        out.print(SET_BG_COLOR_DARK_GREY);
        out.print(SET_TEXT_COLOR_BLUE);
    }
}
