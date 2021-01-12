package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DBConnection {

    Connection connection;

    public DBConnection(String user, String pass) {
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/discografica?serverTimezone=UTC", user, pass);
            System.out.println("Conexión establecida.");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void deleteTables() {
        try {
            Statement stmt = connection.createStatement();

            String deleteAlbum = "DROP TABLE IF EXISTS `album`;";
            stmt.executeUpdate(deleteAlbum);

            String deleteSong = "DROP TABLE IF EXISTS `song`;";
            stmt.executeUpdate(deleteSong);

            stmt.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void createTables() {
        try {
            Statement stmt = connection.createStatement();

            String album = "CREATE TABLE IF NOT EXISTS `album` (" +
                    "`id` INT NOT NULL AUTO_INCREMENT," +
                    "`titulo` VARCHAR(30) NOT NULL," +
                    "`autor` VARCHAR(30) NOT NULL," +
                    "PRIMARY KEY (`id`))" +
                    "ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;";
            stmt.executeUpdate(album);

            String song = "CREATE TABLE IF NOT EXISTS `song` (" +
                    "`id` INT NOT NULL AUTO_INCREMENT," +
                    "`titulo` VARCHAR(30) NOT NULL," +
                    "`album` VARCHAR(30) NOT NULL," +
                    "PRIMARY KEY (`id`))" +
                    "ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;";
            stmt.executeUpdate(song);

            stmt.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void close() {
        try {
            connection.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
