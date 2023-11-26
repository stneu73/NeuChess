package services;

import dao.SQLDAO;
import dataAccess.DataAccessException;
import responses.LoginResponse;

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
        SQLDAO dao = new SQLDAO();
        try {
            if (!dao.findUser(username)) {//!MemoryDAO.getInstance().findUser(username)) {
                return new LoginResponse("Error: Unauthorized: Username Does Not Exist");
            }
        } catch (DataAccessException e) {
            return new LoginResponse("Error: Couldn't Access Database");
        }
        models.User user;
        try {
            user = dao.getUser(username);//MemoryDAO.getInstance().getUser(username);
        } catch (DataAccessException e) {
            return new LoginResponse("Error: Couldn't Access Database");
        }

        if (!user.getPassword().equals(password)) {
            return new LoginResponse("Error: Unauthorized: Incorrect Password");
        }

        String authToken;
        try {
            authToken = dao.generateAuthToken(username);//MemoryDAO.getInstance().generateAuthToken(username);
        } catch (DataAccessException e) {
            return new LoginResponse("Error: Couldn't Access Database");
        }


        return new LoginResponse(username,authToken,null);
    }
}