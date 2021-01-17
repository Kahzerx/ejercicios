package database;

import java.sql.DriverManager;
import java.sql.SQLException;

public class DriverManagerConnection extends GenericConnection {

    private final String url;
    private final String user;
    private final String pass;

    public DriverManagerConnection(String url, String user, String pass) {
        this.url = url;
        this.user = user;
        this.pass = pass;
    }

    public void connect() {
        try {
            connection = DriverManager.getConnection(url, user, pass);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
