package handlers;

import com.google.gson.Gson;
import requests.RegisterRequest;
import responses.RegisterResponse;
import services.RegisterService;
import spark.Request;
import spark.Response;

public class RegisterHandler {
    public Object RegisterHandler(Request request, Response response, Class<RegisterRequest> klass) {
        RegisterRequest bodyAsGson = new Gson().fromJson(request.body(), klass);
        RegisterResponse result = new RegisterService().registerUser(bodyAsGson.getUsername(), bodyAsGson.getPassword(), bodyAsGson.getEmail());

        String message = result.getMessage();
        if (message == null) {
            response.status(200);
        }
        else if (message.toLowerCase().contains("bad")) {
            response.status(400);
        }
        else if (message.toLowerCase().contains("already")) {
            response.status(403);
        }
        else if (message.toLowerCase().contains("database")) {
            response.status(500);
        }

        return new Gson().toJson(result);
    }
}
