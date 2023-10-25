package main;


import handlers.ClearHandler;
import spark.Request;
import spark.Response;
import spark.Spark;

public class Server {
    public static void main(String[] args) { new Server().run(); }

    private void run() {

        int port = 8080;
        Spark.port(port);
        Spark.externalStaticFileLocation("C:/Users/Spencer/Documents/Classes/2023 Fall/C S 240/NeuChess/web");

        Spark.delete("/db",this::clearDatabase);
        Spark.post("/user", this::registerUser);
        Spark.post("/session", this::);
        Spark.delete("/session", this::);
        Spark.get("/game", this::);
        Spark.post("/game", this::);
        Spark.put("/game", this::);

    }

    private Object clearDatabase(Request req, Response res) {
        res.type("application/json");
        return new ClearHandler().ClearHandler(req,res);
    }

    private Object registerUser(Request req, Response res) {

    }

    //define endpoints


    //make calls for each endpoint

}