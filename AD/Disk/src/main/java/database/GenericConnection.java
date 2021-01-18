package database;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class GenericConnection {
    /**
     * <p>
     *     Aquí guardo la conexión, esta es la clase de la que extiendo en las otras para los 2 tipos de conexión que se puede hacer.
     * </p>
     * <p>
     *     Reseteo las tablas e inserto los datos de nuevo por posibles cambios que haya en alguna row.
     * </p>
     */
    public Connection connection;

    // Elimino las tablas con las que trabajo si existen.
    public void deleteTables() {
        try {
            Statement stmt = connection.createStatement();

            String deleteSong = "DROP TABLE IF EXISTS `songs`;";
            stmt.executeUpdate(deleteSong);

            String deleteAuthors = "DROP TABLE IF EXISTS `authors`;";
            stmt.executeUpdate(deleteAuthors);

            String deleteAlbum = "DROP TABLE IF EXISTS `album`;";
            stmt.executeUpdate(deleteAlbum);

            stmt.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    // Creo las tablas eliminadas previamente por si hubiera habido algún cambio.
    public void createTables() {
        try {
            Statement stmt = connection.createStatement();

            String album = "CREATE TABLE IF NOT EXISTS `album` (" +
                    "`id` INT NOT NULL AUTO_INCREMENT," +
                    "`title` VARCHAR(30) NOT NULL," +
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
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    // Añado las rows correspondientes con cada tabla.
    public void addRows() {
        try {
            addAlbums();
            addSongs();
            addAuthors();
            updateSongCount();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
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

    // Inserto 2 grupos.
    private void addAlbums() throws SQLException {
        String addAlbum = "INSERT INTO `album`(title) VALUES (?);";
        PreparedStatement stmt = connection.prepareStatement(addAlbum);
        stmt.setString(1, "tallyhall");
        stmt.executeUpdate();

        stmt.setString(1, "test");
        stmt.executeUpdate();
    }

    // Inserto canciones para cada album usando PreparedStatements.
    private void addSongs() throws SQLException {
        String addSong = "INSERT INTO `songs`(title,album,duration,year) VALUES (?,?,?,?)";

        PreparedStatement stmt = connection.prepareStatement(addSong);
        stmt.setString(1, "The Binding");
        stmt.setString(2, "tallyhall");
        stmt.setDouble(3, 2.41);
        stmt.setInt(4, 2015);
        stmt.executeUpdate();

        stmt = connection.prepareStatement(addSong);
        stmt.setString(1, "Ruler of Everything");
        stmt.setString(2, "tallyhall");
        stmt.setDouble(3, 3.43);
        stmt.setInt(4, 2010);
        stmt.executeUpdate();

        stmt = connection.prepareStatement(addSong);
        stmt.setString(1, "Banana man");
        stmt.setString(2, "tallyhall");
        stmt.setDouble(3, 4.42);
        stmt.setInt(4, 2010);
        stmt.executeUpdate();

        stmt = connection.prepareStatement(addSong);
        stmt.setString(1, "test2");
        stmt.setString(2, "test");
        stmt.setDouble(3, 4.42);
        stmt.setInt(4, 2010);
        stmt.executeUpdate();
    }

    // Añado autores para la tabla de autores usando PreparedStatements.
    private void addAuthors() throws SQLException {
        String addAuthor = "INSERT INTO `authors`(name,album) VALUES (?,?)";
        PreparedStatement stmt = connection.prepareStatement(addAuthor);
        stmt.setString(1, "Rob Cantor");
        stmt.setString(2, "tallyhall");
        stmt.executeUpdate();

        stmt = connection.prepareStatement(addAuthor);
        stmt.setString(1, "Ross Federman");
        stmt.setString(2, "tallyhall");
        stmt.executeUpdate();

        stmt = connection.prepareStatement(addAuthor);
        stmt.setString(1, "Joe Hawley");
        stmt.setString(2, "tallyhall");
        stmt.executeUpdate();

        stmt = connection.prepareStatement(addAuthor);
        stmt.setString(1, "Andrew Horowitz");
        stmt.setString(2, "tallyhall");
        stmt.executeUpdate();

        stmt = connection.prepareStatement(addAuthor);
        stmt.setString(1, "Zubin Sedghi");
        stmt.setString(2, "tallyhall");
        stmt.executeUpdate();
    }

    // Saber cuantas canciones tiene cada album a modo de prueba de hacer queries.
    private void updateSongCount() throws SQLException {
        Statement stmt = connection.createStatement();
        String getCount = "SELECT COUNT(s.album) as amount,s.album as album FROM discografica.songs as s group by s.album;";
        ResultSet rs = stmt.executeQuery(getCount);
        Map<String,Integer> album = new HashMap<>();
        while (rs.next()) {
            album.put(rs.getString("album"), rs.getInt("amount"));
        }
        rs.close();

        album.forEach((title, amount) -> {
            try {
                String updateSongs = String.format("UPDATE album SET nSongs = %d WHERE title LIKE '%s';", amount, title);
                stmt.executeUpdate(updateSongs);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        });
        stmt.close();
    }
}