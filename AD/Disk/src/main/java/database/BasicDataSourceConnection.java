package database;

import org.apache.commons.dbcp.BasicDataSource;
import utils.LogLevel;
import utils.TextPaneLogger;

import java.sql.SQLException;
import java.util.Arrays;

// Hago un extends ya que hay métodos que comparte ambas formas de conexión.
public class BasicDataSourceConnection extends GenericConnection {
    private final BasicDataSource dataSource;
    private String url;
    private final String name;

    // Constructor para tener un objeto para conectarme.
    public BasicDataSourceConnection(String url, String name, String user, String pass) {
        dataSource = new BasicDataSource();
        dataSource.setUrl(url);
        dataSource.setUsername(user);
        dataSource.setPassword(pass);
        this.url = url;
        this.name = name;
    }

    public boolean tryCreateDatabase() {
        return createDatabase(name);
    }

    // Establezco la conexión usando BasicDataSource.
    public boolean connect() {
        try {
            connection = dataSource.getConnection();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean tryCreateTables() {
        return createTables();
    }

    private void setUrl(String url) {
        this.url = url;
        dataSource.setUrl(url);
    }
}
