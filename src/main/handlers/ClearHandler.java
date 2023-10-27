package handlers;

import com.google.gson.Gson;
import responses.ClearResponse;
import services.ClearService;
import spark.Request;
import spark.Response;

public class ClearHandler {
    public Object ClearHandler(Request request, Response response) {
        ClearResponse myResponse = new ClearService().clearAll();
        String message = myResponse.getMessage();
        if (message == null) {
            response.status(200);
        }
        else if (message.toLowerCase().contains("database")) {
            response.status(500);
        }
        return new Gson().toJson(myResponse);
    }
}