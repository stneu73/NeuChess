package ui;

import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.Random;
import java.util.Scanner;

import static ui.EscapeSequences.*;

public class gameplay {

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
            } else if (input.equalsIgnoreCase("make move")) {
                makeMove();
            } else if (input.equalsIgnoreCase("resign")) {
                resign();
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

        } else {
            System.out.print("You have not resigned. Type \"Help\" for more options\n");
        }

    }

    private static void highlightMoves() {
        System.out.print("Enter the coordinate of the piece you would like to see the moves for.\n");

    }
}
