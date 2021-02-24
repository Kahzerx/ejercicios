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
            String createTable = String.format("CREATE DATABASE IF NOT EXISTS %s;", name);
            stmt.executeUpdate(createTable);

            String use = String.format("USE %s;", name);
            stmt.executeUpdate(use);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return false;
        }
        return true;
    }

    // Creo las tablas eliminadas previamente por si hubiera habido algún cambio.
    public boolean createTables() {
        try {
            Statement stmt = connection.createStatement();

            String cat = "CREATE TABLE IF NOT EXISTS `b_category` (" +
                    "`id` INT NOT NULL AUTO_INCREMENT," +
                    "`name` VARCHAR(30) NOT NULL," +
                    "PRIMARY KEY (`id`)," +
                    "UNIQUE KEY `name`(`name`))" +
                    "ENGINE=InnoDB DEFAULT CHARSET=latin1;";
            stmt.executeUpdate(cat);

            String build = "CREATE TABLE IF NOT EXISTS `b_builds` (" +
                    "`id` INT NOT NULL AUTO_INCREMENT," +
                    "`type` VARCHAR(40) DEFAULT NULL," +
                    "`speed` INT DEFAULT NULL," +
                    "`category` VARCHAR(40) DEFAULT NULL," +
                    "`orientation` VARCHAR(40) DEFAULT NULL," +
                    "`bits` INT DEFAULT NULL," +
                    "`num_system` VARCHAR(40) DEFAULT NULL," +
                    "`date` VARCHAR(40) DEFAULT NULL," +
                    "PRIMARY KEY (`id`)," +
                    "UNIQUE KEY `id`(`id`)," +
                    "CONSTRAINT `cat_build_fk` FOREIGN KEY (`category`) REFERENCES `b_category`(`name`) ON DELETE CASCADE ON UPDATE CASCADE)" +
                    "ENGINE=InnoDB DEFAULT CHARSET=latin1;";
            stmt.executeUpdate(build);

            String authors = "CREATE TABLE IF NOT EXISTS `b_authors` (" +
                    "`id` INT NOT NULL AUTO_INCREMENT," +
                    "`name` VARCHAR(30) NOT NULL," +
                    "`buildID` INT NOT NULL," +
                    "PRIMARY KEY (`id`)," +
                    "CONSTRAINT `build_author_fk` FOREIGN KEY (`buildID`) REFERENCES `b_builds`(`id`) ON DELETE CASCADE ON UPDATE CASCADE)" +
                    "ENGINE=InnoDB DEFAULT CHARSET=latin1;";
            stmt.executeUpdate(authors);

            // Debug con InnoDB: mysql -> show engine innodb status;

            stmt.close();
            return true;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return false;
        }
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
