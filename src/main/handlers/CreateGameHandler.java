package handlers;

import com.google.gson.Gson;
import requests.CreateGameRequest;
import services.CreateGameService;
import spark.Request;
import spark.Response;

public class CreateGameHandler {
    public Object CreateGameHandler(Request request, Response response, Class<CreateGameRequest> klass) {
        var auth = request.headers("Authorization");//request json to request object (not for clear)
        var bodyAsGson = new Gson().fromJson(request.body(),klass);//have to tell gson what to deserialize to if handler has a request
        var result = new CreateGameService().newGame(bodyAsGson.getGameName(), auth);//pass object to service
        //set status code based on response object
            //if statement looking at message
                //res.status(value)
        String message = result.getMessage();
        if (message == null) {
            response.status(200);
        }
        else if (message.toLowerCase().contains("bad")) {
            response.status(400);//code 400
        }
        else if (message.toLowerCase().contains("unauthorized")) {
            response.status(401);//code 401
        }
        else if (message.toLowerCase().contains("database")) {
            response.status(500);//code 500
        }

        return new Gson().toJson(result);//response object to response json
    }
}
