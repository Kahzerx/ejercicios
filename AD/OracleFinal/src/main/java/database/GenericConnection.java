package database;

import java.sql.*;

public class GenericConnection {
    /**
     *   Aquí guardo la conexión, esta es la clase de la que extiendo en las otras para los 2 tipos de conexión que se puede hacer.
     *   Reseteo las tablas e inserto los datos de nuevo por posibles cambios que haya en alguna row.
     */
    public Connection connection;

    /**
     * Creo la base de datos y la uso.
     * @param name nombre de la base de datos que quiero que tenga.
     * @return si ha conseguido conectarse
     */
    public boolean createDatabase(String name) {
        try {
            Statement stmt = connection.createStatement();
            String createTable = String.format("ALTER SESSION SET current_schema = %s", name);
            stmt.executeUpdate(createTable);
            stmt.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return false;
        }
        return true;
    }

    // Cerrar la conexión.
    public void close() {
        try {
            connection.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
