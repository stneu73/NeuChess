package ui;

import java.util.Locale;
import java.util.Scanner;
import java.util.regex.MatchResult;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class PostLogin {

    public static void start(String authToken) {
        boolean flag = true;
        Scanner scanner = new Scanner(System.in);
        while (flag) {
            String input = scanner.nextLine();
            if (input.equalsIgnoreCase("logout")) {
                logout(authToken);
                flag = false;
            } else if (input.equalsIgnoreCase("help")) {
                help();
                System.out.println();
            } else if (input.equalsIgnoreCase("create game")) {
                createGame(authToken);
            } else if (input.equalsIgnoreCase("list games")) {
                listGames(authToken);
            } else if (input.equalsIgnoreCase("join game")) {
                joinGame(authToken);
            } else if (input.equalsIgnoreCase("join observer")) {
                joinObserver(authToken);
            } else {
                System.out.print("Type \"Help\" to get started.");
                System.out.println();
            }
        }
    }

    private static void help() {
        System.out.println();
        System.out.print("Available Endpoints:");
        System.out.println();
        System.out.print("Create Game - make a new chess game to play");
        System.out.println();
        System.out.print("List Games - list all current games");
        System.out.println();
        System.out.print("Join Game - join a current game as a player");
        System.out.println();
        System.out.print("Join Observer - watch a current game");
        System.out.println();
        System.out.print("Logout - end current session");
        System.out.println();
        System.out.print("Help - display possible actions");
        System.out.println();

    }

    private static void logout(String authToken) {
        System.out.print("You've selected Logout");
        System.out.println();
        try {
            ServerFacade.logout(authToken);
            System.out.print("Thanks for playing!");
            System.out.println();
            System.out.println();
        } catch (Exception e) {
            System.out.print("Logout Failure. " + e.getMessage());
            System.out.println();
            System.out.print("Type \"Help\" for options");
        }


    }

    private static void createGame(String authToken) {
        System.out.print("You've selected Create Game");
        System.out.println();
        System.out.print("Please enter a name you'd like to give the game");
        System.out.println();
        String gameName = new Scanner(System.in).nextLine();

        try {
            ServerFacade.createGame(gameName,authToken);
        } catch (Exception e) {
            System.out.print("Create Game Failure. " + e.getMessage());
            System.out.println();
            System.out.print("Type \"Help\" for options");
        }

        System.out.print("Game Created: " + gameName);
        System.out.println();
        System.out.print("Access Game List for GameID");
        System.out.println();
        System.out.println();
    }

    private static void listGames(String authToken) {
        System.out.print("You've selected List Games");
        System.out.println();

        try {
            ServerFacade.listGames(authToken);
        } catch (Exception e) {
            System.out.print("List Games Error: " + e.getMessage());
        }
    }

    private static void joinGame(String authToken) {
        Scanner s = new Scanner(System.in);
        System.out.print("You've selected Join Game");
        System.out.println();
        System.out.print("Please enter the GameID of the game you would like to join");
        System.out.println();
        String gameID = s.nextLine();
        System.out.print("Please enter the color you would like to play as (white or black).");
        System.out.println();
//        System.out.print("Leave empty and hit enter to just observe");
//        System.out.println();
        String color = s.nextLine();
        while (!color.equalsIgnoreCase("white") && !color.equalsIgnoreCase("black")) {
            System.out.print("Please enter the color you would like to play as (white or black).");
            System.out.println();
            color = s.nextLine();
        }

        try {
            ServerFacade.joinGame(Integer.parseInt(gameID),authToken,color);
            gameplay.start();
        } catch (Exception e) {
            System.out.print("Join Game Failure. " + e.getMessage());
            System.out.println();
        }

    }

    private static void joinObserver(String authToken) {
        System.out.print("You've selected Join Observer");
        System.out.println();
        System.out.print("Please enter the GameID of the game you would like to join");
        System.out.println();
        String gameID = new Scanner(System.in).nextLine();
        try {
            ServerFacade.joinGame(Integer.parseInt(gameID),authToken,null);
            gameplay.start();
        } catch (Exception e) {
            System.out.print("Join Game Failure. " + e.getMessage());
            System.out.println();
        }

    }

}
