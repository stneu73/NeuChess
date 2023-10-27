package handlers;

import com.google.gson.Gson;
import requests.CreateGameRequest;
import responses.CreateGameResponse;
import services.CreateGameService;
import spark.Request;
import spark.Response;

public class CreateGameHandler {
    public Object CreateGameHandler(Request request, Response response, Class<CreateGameRequest> klass) {
        String auth = request.headers("Authorization");//request json to request object (not for clear)
        CreateGameRequest bodyAsGson = new Gson().fromJson(request.body(),klass);//have to tell gson what to deserialize to if handler has a request
        CreateGameResponse result = new CreateGameService().newGame(bodyAsGson.getGameName(), auth);//pass object to service

        String message = result.getMessage();
        //set status code based on response object
        if (message == null) { //if statement looking at message
            response.status(200); //response.status(value)
        }
        else if (message.toLowerCase().contains("bad")) {
            response.status(400);
        }
        else if (message.toLowerCase().contains("unauthorized")) {
            response.status(401);
        }
        else if (message.toLowerCase().contains("database")) {
            response.status(500);
        }

        return new Gson().toJson(result);//response object to response json
    }
}
