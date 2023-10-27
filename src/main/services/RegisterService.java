package services;

import dao.MemoryDAO;
import dataAccess.DataAccessException;
import models.User;
import responses.RegisterResponse;

/**
 * Service to create new users for the server
 */
public class RegisterService {
    /**
     * creates the user storing the name and password in the database
     * @param username the name passed in by the user
     * @param password the password passed in by the user
     */
    public RegisterResponse registerUser(String username, String password, String email)  {
        if (MemoryDAO.getInstance().findUser(username)) {
            return new RegisterResponse("Error: Username Already Taken");
        }
        if (password == null) {
            return new RegisterResponse("Error: Bad Request");
        }
        if (password.isEmpty()) {
            return new RegisterResponse("Error: Bad Request");
        }
        User user;
        try {
            user = MemoryDAO.getInstance().createUser(username, new User(username, password, email));
        } catch (DataAccessException e) {
            return new RegisterResponse("Error: Couldn't Access Database");
        }
        String authToken;
        try {
            authToken = MemoryDAO.getInstance().generateAuthToken(username);
        } catch (DataAccessException e) {
            return new RegisterResponse("Error: Couldn't Access Database");
        }

        return new RegisterResponse(user.getUsername(), authToken, null);
    }
}
