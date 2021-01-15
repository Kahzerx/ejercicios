package database;

import java.sql.DriverManager;
import java.sql.SQLException;

public class DriverManagerConnection extends GenericConnection {

    public DriverManagerConnection(String url, String user, String pass) {
        try {
            connection = DriverManager.getConnection(url, user, pass);
            System.out.printf("Conexión %s establecida.%n", this.getClass().getSimpleName());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
