package database;

import org.apache.commons.dbcp.BasicDataSource;

import java.sql.SQLException;

// Hago un extends ya que hay métodos que comparte ambas formas de conexión.
public class BasicDataSourceConnection extends GenericConnection {
    private final BasicDataSource dataSource;
    private final String name;

    /**
     * Objeto de la conexión con {@link BasicDataSource}.
     * @param url url que quiero usar, en este caso vinculada a mysql.
     * @param name nombre de la base de datos.
     * @param user nombre de usuario de la base de datos.
     * @param pass contraseña de la base de datos.
     */
    public BasicDataSourceConnection(String url, String name, String user, String pass) {
        dataSource = new BasicDataSource();
        dataSource.setUrl(url);
        dataSource.setUsername(user);
        dataSource.setPassword(pass);
        this.name = name;
    }

    public boolean tryUseDatabase() {
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
}
