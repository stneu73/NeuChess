package webSocket;

import org.eclipse.jetty.websocket.api.Session;

import java.io.IOException;
import java.util.Objects;

public class Connection {


    public String username;
    public Session session;

    public Connection(String visitorName, Session session) {
        this.username = visitorName;
        this.session = session;
    }

    public void send(String msg) throws IOException {
        session.getRemote().sendString(msg);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Connection that = (Connection) o;
        return Objects.equals(username, that.username) && Objects.equals(session, that.session);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, session);
    }

}