package ui;

import java.util.Scanner;

public class postlogin {

    public static void start() {
        boolean flag = true;
        Scanner scanner = new Scanner(System.in);
        while (flag) {
            String input = scanner.nextLine();
            if (input.equalsIgnoreCase("logout")) {
                logout();
                flag = false;
            } else if (input.equalsIgnoreCase("help")) {
                help();
                System.out.println();
            } else if (input.equalsIgnoreCase("create game")) {
                createGame();
            } else if (input.equalsIgnoreCase("list games")) {
                listGames();
            } else if (input.equalsIgnoreCase("join game")) {
                joinGame();
            } else if (input.equalsIgnoreCase("join observer")) {
                joinObserver();
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

    private static void logout() {
        System.out.print("You've selected Logout");
        System.out.println();
        //TODO: make call to server to logout

        System.out.print("Thanks for playing!");
        System.out.println();
        System.out.println();
    }

    private static void createGame() {
        System.out.print("You've selected Create Game");
        System.out.println();
        //TODO: make api call to create game
    }

    private static void listGames() {
        System.out.print("You've selected List Games");
        System.out.println();
        //TODO: make call to server to list games
    }

    private static void joinGame() {
        System.out.print("You've selected Join Game");
        System.out.println();
        //TODO: make call to server to join a game
        gameplay.playChess();
    }

    private static void joinObserver() {
        System.out.print("You've selected Join Observer");
        System.out.println();
        //TODO: make call to server to watch a game
        gameplay.playChess();
    }


}
