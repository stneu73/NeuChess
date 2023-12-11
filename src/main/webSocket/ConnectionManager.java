package webSocket;

import com.google.gson.Gson;
import org.eclipse.jetty.websocket.api.Session;
import webSocketMessages.serverMessages.ServerMessage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;

public class ConnectionManager {


    public final ConcurrentHashMap<Integer, GameSession> gameIDToGameSession = new ConcurrentHashMap<>();

    public void add(Integer gameID, String username, Session session) {
        if (!gameIDToGameSession.containsKey(gameID)) {
            gameIDToGameSession.put(gameID, new GameSession());
        }
        gameIDToGameSession.get(gameID).addConnection(new Connection(username,session));
    }

    public void removePlayer(Integer gameID, String username, Session session) {
        gameIDToGameSession.get(gameID).removeConnection(new Connection(username,session));
    }

    public void broadcastMinusOne(Integer gameID, String excludeUsername, ServerMessage serverMessage) throws IOException {
        var removeList = new ArrayList<Connection>();
        for (var c : gameIDToGameSession.get(gameID).getConnections()) {
            if (c.session.isOpen()) {
                if (!c.username.equals(excludeUsername)) {
                    c.send(new Gson().toJson(serverMessage));
                }
            } else {
                removeList.add(c);
            }
        }
        connectionCleanup(gameID, removeList);
    }

    public void broadcastAll(Integer gameID, ServerMessage serverMessage) throws Exception {
        var removeList = new ArrayList<Connection>();
        for (var c : gameIDToGameSession.get(gameID).getConnections()) {
            if (c.session.isOpen()) {
                c.send(new Gson().toJson(serverMessage));
            } else {
                removeList.add(c);
            }
        }
        connectionCleanup(gameID, removeList);
    }
    public void reply(Integer gameID,String username, ServerMessage serverMessage) throws Exception { //individual server response
        var removeList = new ArrayList<Connection>();
        for (var c : gameIDToGameSession.get(gameID).getConnections()) {
            if (c.session.isOpen()) {
                if (c.username.equals(username)) {
                    c.send(new Gson().toJson(serverMessage));
                }
            } else {
                removeList.add(c);
            }
        }
        connectionCleanup(gameID, removeList);
    }

    public void endGame(Integer gameID) {
        gameIDToGameSession.get(gameID).setGameAsOver();
//        gameIDToGameSession.get(gameID).clearConnections();
    }

    public boolean gameOverQuery(Integer gameID) {
        return gameIDToGameSession.get(gameID).getGameIsOver();
    }

    private void connectionCleanup(Integer gameID, ArrayList<Connection> removeList) {
        for (var c : removeList) {
            gameIDToGameSession.get(gameID).getConnections().remove(c);
        }
    }

    public ConcurrentHashMap<Integer, GameSession> getGameIDToGameSession() {
        return gameIDToGameSession;
    }
}
