package database;

import java.sql.DriverManager;
import java.sql.SQLException;

// Hago un extends ya que hay métodos que comparte ambas formas de conexión.
public class DriverManagerConnection extends GenericConnection {

    private final String url;
    private final String user;
    private final String pass;

    // Constructor para tener un objeto para conectarme.
    public DriverManagerConnection(String url, String user, String pass) {
        this.url = url;
        this.user = user;
        this.pass = pass;
    }

    // Establezco la conexión usando DriverManager.
    public void connect() {
        try {
            connection = DriverManager.getConnection(url, user, pass);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
