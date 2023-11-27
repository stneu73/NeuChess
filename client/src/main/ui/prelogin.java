package ui;

import java.util.Scanner;

public class prelogin {
    public static void main(String[] args) {
        boolean flag = true;
        System.out.print("This is Neu Chess. Type \"Help\" to get started.");
        System.out.println();
        Scanner scanner = new Scanner(System.in);
        while (flag) {
            String input = scanner.nextLine();
            if (input.equalsIgnoreCase("quit")) {
                System.out.print("Goodbye.");
                flag = false;
            } else if (input.equalsIgnoreCase("help")) {
                help();
                System.out.println();
            } else if (input.equalsIgnoreCase("login")) {
                login();
                postLogout();
            } else if (input.equalsIgnoreCase("register")) {
                register();
                postLogout();
            } else {
                System.out.print("Type \"Help\" to get started.");
                System.out.println();
            }
        }
    }

    private static void help() {
        System.out.print("Available Endpoints:");
        System.out.println();
        System.out.print("Register <USERNAME> <PASSWORD> <EMAIL> - to create an account");
        System.out.println();
        System.out.print("Login <USERNAME> <PASSWORD>");
        System.out.println();
        System.out.print("Quit - end application");
        System.out.println();
        System.out.print("Help - display possible actions");
        System.out.println();
    }

    private static void login() { //needs a username and password
        Scanner scanner = new Scanner(System.in);
        System.out.print("Please enter your Username: ");
        String username = scanner.nextLine();
        System.out.println();
        System.out.print("Please enter your Password: ");
        String password = scanner.nextLine();
        System.out.println();

        //TODO: make api call to server for login

        System.out.print("Welcome " + username +"! Type \"Help\" to see available endpoints.");
        System.out.println();
        postlogin.start();
    }

    private static void register() { //needs a username, password, and email
        Scanner scanner = new Scanner(System.in);
        System.out.print("Please create a Username: ");
        String username = scanner.nextLine();
        System.out.println();
        System.out.print("Please create a Password: ");
        String password = scanner.nextLine();
        System.out.println();
        System.out.print("Please enter your email: ");
        String email = scanner.nextLine();
        System.out.println();

        //TODO: make call to server to input into system and receive auth token

        System.out.print("Welcome " + username +"! Type \"Help\" to see available endpoints.");
        System.out.println();
        postlogin.start();
    }

    private static void postLogout() {
        System.out.print("You've successfully logged out.");
        System.out.println();
        System.out.println();
        System.out.print("Type \"Help\" for more options");
        System.out.println();
    }
}