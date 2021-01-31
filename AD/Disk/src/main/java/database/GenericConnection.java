package database;

import java.sql.*;

public class GenericConnection {
    /**
     *   Aquí guardo la conexión, esta es la clase de la que extiendo en las otras para los 2 tipos de conexión que se puede hacer.
     *   Reseteo las tablas e inserto los datos de nuevo por posibles cambios que haya en alguna row.
     */
    public Connection connection;

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

            String album = "CREATE TABLE IF NOT EXISTS `album` (" +
                    "`id` INT NOT NULL AUTO_INCREMENT," +
                    "`title` VARCHAR(30) NOT NULL," +
                    "`date` VARCHAR(20) DEFAULT NULL," +
                    "`nSongs` INT DEFAULT NULL," +
                    "PRIMARY KEY (`id`)," +
                    "UNIQUE KEY `title`(`title`))" +
                    "ENGINE=InnoDB DEFAULT CHARSET=latin1;";
            stmt.executeUpdate(album);

            String authors = "CREATE TABLE IF NOT EXISTS `authors` (" +
                    "`id` INT NOT NULL AUTO_INCREMENT," +
                    "`name` VARCHAR(40) DEFAULT NULL," +
                    "`album` VARCHAR(30) NOT NULL," +
                    "PRIMARY KEY (`id`)," +
                    "CONSTRAINT `album_author_fk` FOREIGN KEY (`album`) REFERENCES `album`(`title`) ON DELETE NO ACTION)" +
                    "ENGINE=InnoDB DEFAULT CHARSET=latin1;";
            stmt.executeUpdate(authors);

            String song = "CREATE TABLE IF NOT EXISTS `songs` (" +
                    "`id` INT NOT NULL AUTO_INCREMENT," +
                    "`title` VARCHAR(30) NOT NULL," +
                    "`album` VARCHAR(30) NOT NULL," +
                    "`duration` DECIMAL(6,2) DEFAULT 0," +
                    "`year` INT DEFAULT NULL," +
                    "PRIMARY KEY (`id`)," +
                    "CONSTRAINT `album_title_fk` FOREIGN KEY (`album`) REFERENCES `album`(`title`) ON DELETE NO ACTION)" +
                    "ENGINE=InnoDB DEFAULT CHARSET=latin1;";
            stmt.executeUpdate(song);

            // Debug with InnoDB: mysql -> show engine innodb status;

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
