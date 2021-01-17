package database;

import java.sql.DriverManager;
import java.sql.SQLException;

public class DriverManagerConnection extends GenericConnection {

    private String url;
    private String user;
    private String pass;

    public DriverManagerConnection(String url, String user, String pass) {
        this.url = url;
        this.user = user;
        this.pass = pass;
    }

    public void connect() {
        try {
            connection = DriverManager.getConnection(url, user, pass);
            System.out.printf("Conexión %s establecida.%n", this.getClass().getSimpleName());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getUrl() {
        return url;
    }

    public String getUser() {
        return user;
    }

    public String getPass() {
        return pass;
    }
}
