package services;

import dao.MemoryDAO;
import dataAccess.DataAccessException;
import responses.LoginResponse;
import responses.LogoutResponse;

/**
 * Logs a previously existing user into the server
 */
public class LoginService {
    /**
     * Logs a previously existing user into the server
     * @param username the given username from the user attempting to log in
     * @param password the given password from the user attempting to log in
     */
    public LoginResponse login(String username, String password) {
        if (!MemoryDAO.getInstance().findUser(username)) {
            return new LoginResponse("Error: Unauthorized: Username Does Not Exist");
        }
        models.User user;
        try {
            user = MemoryDAO.getInstance().getUser(username);
        } catch (DataAccessException e) {
            return new LoginResponse("Error: Couldn't Access Database");
        }

        if (!user.getPassword().equals(password)) {
            return new LoginResponse("Error: Unauthorized: Incorrect Password");
        }

        String authToken;
        try {
            authToken = MemoryDAO.getInstance().insertAuthToken(username);
        } catch (DataAccessException e) {
            return new LoginResponse("Error: Couldn't Access Database");
        }


        return new LoginResponse(username,authToken,null);
    }

}