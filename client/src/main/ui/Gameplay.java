package ui;

import chess.ChessGame;
import chess.Game;
import chess.Move;
import chess.Position;
import webSocketCommands.*;

import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class Gameplay {
    private static String authToken = null;
    private static Integer gameID;
    private static WSClient socket;
    private static String gameBoard;

    private static String color;
    public Gameplay(Integer gameID, String authToken, String color) {
        try {
            socket = new WSClient();
        } catch (Exception e) {
            System.out.print(e.getMessage());

        }
        Gameplay.gameID = gameID;
        Gameplay.authToken = authToken;
        Gameplay.color = color;
        start();
    }

    public static void start() {
        boolean gameOver = false;
        if (color != null) {
            try {
                if (color.equalsIgnoreCase("black")) {
                    socket.send(new JoinGameCommand(authToken, gameID, ChessGame.TeamColor.BLACK));
                } else {
                    socket.send(new JoinGameCommand(authToken, gameID, ChessGame.TeamColor.WHITE));
                }
            } catch (Exception e) {
                System.out.print(e.getMessage());
            }
        } else {
            try {
                socket.send(new JoinObserverCommand(authToken, gameID));
            } catch (Exception e) {
                System.out.print(e.getMessage());
                gameOver = true;
            }
        }

        if (!gameOver) {
            Scanner s = new Scanner(System.in);
            System.out.print("You've joined a game.\nType \"Help\" to get started.\n");
            boolean flag = true;
            while (flag) {
                String input = s.nextLine();
                if (input.equalsIgnoreCase("help")) {
                    help();
                } else if (input.equalsIgnoreCase("redraw")) {
                    redrawChessBoard(gameBoard);
                } else if (input.equalsIgnoreCase("leave")) {
                    leaveGame();
                    flag = false;
                } else if (input.equalsIgnoreCase("make move")) {
                    makeMove();
                } else if (input.equalsIgnoreCase("resign")) {
                    flag = resign(flag);

                } else if (input.equalsIgnoreCase("legal moves")) {
                    highlightMoves();
                } else {
                    System.out.print("Type \"Help\" to get started.\n");
                }
            }
        }
    }

    private static void help() {
        System.out.print("Available actions:\n");
        System.out.print("Redraw - Redraws the chess board\n");
        System.out.print("Leave - Leaves the game returning to pregame screen\n");
        System.out.print("Make Move - <START COORDINATE> <END COORDINATE> - Enacts a move on the board\n");
        System.out.print("Resign - Forfeit the game\n");
        System.out.print("Legal Moves - <PIECE COORDINATE> - Highlights a piece's legal moves on the board\n\n");
    }

    public static void redrawChessBoard(String game) {
        Gameplay.gameBoard = game;
        ChessBoardPrint.playChess(color,game);
    }

    private static void leaveGame() {
        try {
            socket.send(new LeaveCommand(authToken,gameID));
        } catch (Exception ignored) {}
    }

    private static void makeMove() {
        System.out.print("Enter the start and end coordinates of the piece you would like to move.\n");
        System.out.print("Enter start position: ");
        Position start = createPosition();
        System.out.print("\nEnter end position: ");
        Position end = createPosition();
        try {
            socket.send(new MakeMoveCommand(authToken,gameID,new Move(start, end)));
        } catch (Exception ignore) {}
    }



    private static boolean resign(boolean flag) {
        System.out.print("Are you sure you would like to resign? If so, type \"YES\"\n");
        Scanner input = new Scanner(System.in);
        if (input.nextLine().equals("YES")) {
            try {
                socket.send(new ResignCommand(authToken, gameID));
                flag = false;
            } catch (Exception e) {
                System.out.print(e.getMessage());
            }
        } else {
            System.out.print("You have not resigned. Type \"Help\" for more options\n");
        }
        return flag;
    }

    private static void highlightMoves() {
        System.out.print("Enter the coordinate of the piece you would like to see the moves for.\n");
        ChessBoardPrint.highlightBoard(color,createPosition(),gameBoard);
    }

    private static Position createPosition() {
        Scanner input = new Scanner(System.in);
        System.out.print("\n Please enter a letter a-h: ");
        String x = input.nextLine();
        while (!List.of("a","b","c","d","e","f","g","h").contains(x)) {
            System.out.print("Please enter a letter a-h.\n");
            x = input.nextLine();
        }
        System.out.print("\nPlease enter a number 1-8.\n ");
        int y = input.nextInt() - 1;
        if (!List.of(0,1,2,3,4,5,6,7).contains(y)) {
            while (!List.of(0, 1, 2, 3, 4, 5, 6, 7).contains(y)) {
                System.out.print("Please enter a number 1-8.\n ");
                y = input.nextInt() - 1;
            }
        }
        int xAsInt = x.charAt(0) - 'a';
        return new Position(xAsInt,y,true);
    }

}
