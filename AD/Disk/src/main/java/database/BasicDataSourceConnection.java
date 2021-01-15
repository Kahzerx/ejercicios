package database;

import org.apache.commons.dbcp.BasicDataSource;

import java.sql.SQLException;

public class BasicDataSourceConnection extends GenericConnection {

    public BasicDataSourceConnection(String url, String user, String pass) {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setUrl(url);
        dataSource.setUsername(user);
        dataSource.setPassword(pass);

        try {
            connection = dataSource.getConnection();
            System.out.printf("Conexión %s establecida.%n", this.getClass().getSimpleName());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
