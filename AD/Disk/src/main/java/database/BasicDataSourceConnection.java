package database;

import org.apache.commons.dbcp.BasicDataSource;

import java.sql.SQLException;

// Hago un extends ya que hay métodos que comparte ambas formas de conexión.
public class BasicDataSourceConnection extends GenericConnection {
    private final BasicDataSource dataSource;

    // Constructor para tener un objeto para conectarme.
    public BasicDataSourceConnection(String url, String user, String pass) {
        dataSource = new BasicDataSource();
        dataSource.setUrl(url);
        dataSource.setUsername(user);
        dataSource.setPassword(pass);
    }

    // Establezco la conexión usando BasicDataSource.
    public void connect() {
        try {
            connection = dataSource.getConnection();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
