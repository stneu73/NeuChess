package ui;

import webSocketCommands.ResignCommand;
import webSocketMessages.userCommands.UserGameCommand;

import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.Random;
import java.util.Scanner;

import static ui.EscapeSequences.*;

public class gameplay {
    private static String authToken = null;
    private static Integer gameID;
    private static WSClient socket;
    public gameplay(Integer gameID, String authToken) {
        gameplay.gameID = gameID;
        gameplay.authToken = authToken;
    }

    public static void start() {
        Scanner s = new Scanner(System.in);
        System.out.print("You've joined a game.\nType \"Help\" to get started.");
        while (true) {
            String input = s.nextLine();
            if (input.equalsIgnoreCase("help")) {
                help();
            } else if (input.equalsIgnoreCase("redraw")) {
                redrawChessBoard();
            } else if (input.equalsIgnoreCase("leave")) {
                leaveGame();
                break;
            } else if (input.equalsIgnoreCase("make move")) {
                makeMove();
            } else if (input.equalsIgnoreCase("resign")) {
                resign();
                break;
            } else if (input.equalsIgnoreCase("legal moves")) {
                highlightMoves();
            } else {
                System.out.print("Type \"Help\" to get started.\n");
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

    private static void redrawChessBoard() {

    }

    private static void leaveGame() {

    }

    private static void makeMove() {
        System.out.print("Enter the start and end coordinates of the piece you would like to move.\n");
        Scanner input = new Scanner(System.in);


    }

    private static void resign() {
        System.out.print("Are you sure you would like to resign? If so, type \"YES\"\n");
        Scanner input = new Scanner(System.in);
        if (input.nextLine().equals("YES")) {
            try {
                socket.send(new ResignCommand(authToken, gameID));
            } catch (Exception ignored) {
            }
        } else {
            System.out.print("You have not resigned. Type \"Help\" for more options\n");
        }

    }

    private static void highlightMoves() {
        System.out.print("Enter the coordinate of the piece you would like to see the moves for.\n");
        Scanner s = new Scanner(System.in);
        StringBuilder sb = new StringBuilder();
        System.out.print("Enter Letter: ");

        sb.append(s.nextLine());
        System.out.print("Enter Number: ");
        //TODO: add logic to check correct input
        sb.append(s.nextLine());
//        chessBoardPrint.highlightBoard(String color,String sb.toString(),gameModel);

    }
}
