package main;


import spark.Spark;

public class Server {
    public static void main(String[] args) {

        int port = 8080;
        Spark.port(port);
        Spark.externalStaticFileLocation("C:\\Users\\Spencer\\Documents\\Classes\\2023 Fall\\C S 240\\NeuChess");

    }
    //define endpoints


    //make calls for each endpoint

}