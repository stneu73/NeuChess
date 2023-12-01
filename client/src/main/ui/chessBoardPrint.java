package ui;

import chess.ChessGame;
import chess.ChessMove;
import chess.Game;
import chess.Position;
import models.GameModel;

import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.LinkedHashSet;

import static ui.EscapeSequences.*;

public class chessBoardPrint {

    private static final int BOARD_SIZE_IN_SQUARES = 8;
    private static final int SQUARE_SIZE_IN_CHARS = 1;
    private static final String[] headersWhite = new String[]{" a ", " b ", " c ", " d ", " e ", " f ", " g ", " h "};
    private static final String[] rowNumsWhite = new String[]{" 1 ", " 2 ", " 3 ", " 4 ", " 5 ", " 6 ", " 7 ", " 8 "};
    private static final String[] rowNumsBlack = new String[]{" 8 ", " 7 ", " 6 ", " 5 ", " 4 ", " 3 ", " 2 ", " 1 "};
    private static final String[] headersBlack = new String[]{" h ", " g ", " f ", " e ", " d ", " c ", " b ", " a "};


    public static void playChess(String color, String chessBoard) {
        chessBoard = chessBoard.substring(0,64);
        String boardStart = "rnbqkbnrppppppppeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeePPPPPPPPRNBQKBNRwhite";
        var out = new PrintStream(System.out, true, StandardCharsets.UTF_8);
        out.print(ERASE_SCREEN);
        drawAll(color, out,chessBoard);

        out.print("\u001b[38;49;0m");
    }

    private static void drawAll(String color, PrintStream out, String chessBoard) {
        if (color.equalsIgnoreCase("black")) {
            chessBoard = reverseBoard(chessBoard);
            drawHeaders(out, headersBlack);
            drawBoard(out, chessBoard, rowNumsBlack);
            drawHeaders(out, headersBlack);

        } else {

            drawHeaders(out, headersWhite);
            drawBoard(out, chessBoard, rowNumsWhite);
            drawHeaders(out, headersWhite);
        }
        out.print("\u001b[38;49;0m");
    }

    public static void highlightBoard(String color, String position, GameModel gameModel) {
//        String color = "black";
//        GameModel gameModel = new GameModel("test");
//        String position = "b1";
        int xPos = position.charAt(0) -'a'; //index of 0
        int yPos = Integer.parseInt(position.substring(1))-1; //index of 0
        Position pos = new Position(xPos,yPos,true);
        if (!posIsNull(pos,gameModel.getGame())) {
            LinkedHashSet<Position> positionsToHighlight = getPositionsToHighlight(pos, gameModel.getGame());
            String chessBoard = gameModel.getGame().gameToString().substring(0,64);
            drawAllHighlights(color,chessBoard,positionsToHighlight,pos);

        } else {
            System.out.print("The chosen position has no piece in it.\nNothing was highlighted.\n\n");
            playChess(color,gameModel.getGame().gameToString());
        }

    }

    private static void drawAllHighlights(String color, String chessBoard, LinkedHashSet<Position> positionsToHighlight, Position pos) {
        var out = new PrintStream(System.out, true, StandardCharsets.UTF_8);
        out.print(ERASE_SCREEN);
        if (color.equalsIgnoreCase("black")) {
            chessBoard = reverseBoard(chessBoard);
            drawHeaders(out, headersBlack);
            drawBoard(out, chessBoard, rowNumsBlack,positionsToHighlight,pos);
            drawHeaders(out, headersBlack);

        } else {
            drawHeaders(out, headersWhite);
            drawBoard(out, chessBoard, rowNumsWhite,positionsToHighlight,pos);
            drawHeaders(out, headersWhite);
        }
    }

    private static void drawBoard(PrintStream out, String chessBoard, String[] rowNums, LinkedHashSet<Position> positionsToHighlight,Position pos) {
        for (int boardRow = 0; boardRow < BOARD_SIZE_IN_SQUARES; boardRow++) {
            LinkedHashSet<Position> posToPass = new LinkedHashSet<>();
            for (var itr : positionsToHighlight) {
                if (itr.getRowIndex() == boardRow) {
                    posToPass.add(itr);
                }
            }
            int rowNum = boardRow+1;
            setForBorder(out);
            out.print(rowNums[boardRow]);
            drawRowOfSquares(out,rowNum,chessBoard,positionsToHighlight,pos);
            setForBorder(out);
            out.print(rowNums[boardRow]);
            setBlack(out);
            out.println();
        }
    }

    private static void drawRowOfSquares(PrintStream out, int boardRow, String chessBoard, LinkedHashSet<Position> positionsToHighlight,Position pos) {
        char[] nextEightPieces = chessBoard.substring((boardRow-1)*8).toCharArray();
//        for (int squareRow = 0; squareRow < SQUARE_SIZE_IN_CHARS; ++squareRow) {
            for (int boardCol = 0; boardCol < BOARD_SIZE_IN_SQUARES; ++boardCol) {
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
                for (var itr:positionsToHighlight) {
                    if (8-itr.getRowIndex() == boardRow && itr.getColumnIndex() == boardCol) {
                        if (boardRow % 2 == 0 && boardCol % 2 == 1) {
                            setWhite(out);
                            out.print("\u001b[44;1m");
                        } else if (boardRow % 2 == 1 && boardCol % 2 == 0) {
                            setWhite(out);
                            out.print("\u001b[44;1m");
                        } else {
                            setBlack(out);
                            out.print("\u001b[104;1m");
                        }
                    }
                }
                if (8-pos.getRowIndex() == boardRow && pos.getColumnIndex() == boardCol) {
                    out.print("\u001b[47;1m");
                }


                if (nextEightPieces[boardCol] == 'e') {
                    out.print("   ");
                } else {
                    out.print(" "+nextEightPieces[boardCol]+" ");
                }
//            }
        }
    }

    private static LinkedHashSet<Position> getPositionsToHighlight(Position originPos,ChessGame game) {
//            if (!Objects.equals(game.getGame().getBoard().getPiece(pos).getTeamColor().toString(), color)) {} //use if team color is important
            Collection<ChessMove> validMoves = game.validMoves(originPos);
            LinkedHashSet<Position> retVal = new LinkedHashSet<>();
            for (var itr : validMoves) {
                retVal.add((Position) itr.getEndPosition());
            }
        return retVal;
    }

    private static boolean posIsNull(Position pos, ChessGame game) {
        return game.getBoard().getPiece(pos) == null;
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
    private static void drawBoard(PrintStream out, String chessBoard, String[] rowNums) {
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
//        for (int squareRow = 0; squareRow < SQUARE_SIZE_IN_CHARS; ++squareRow) {
            for (int boardCol = 0; boardCol < BOARD_SIZE_IN_SQUARES; ++boardCol) {
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
//        }
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
