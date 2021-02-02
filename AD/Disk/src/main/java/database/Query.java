package database;

import utils.CustomTableFormat;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Query {
    // Saber cuantas canciones tiene cada album a modo de prueba de hacer queries.
    public static void updateSongCount(Connection connection) throws SQLException {
        Statement stmt = connection.createStatement();
        String getCount = "SELECT (SELECT COUNT(*) FROM songs s WHERE a.title LIKE s.album) amount, a.title album FROM album a;";
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

    public static boolean insertAlbum(Connection connection, String title, String date) {
        try {
            String insertAlbum = "INSERT INTO album(title, date) VALUES(?,?);";
            PreparedStatement stmt = connection.prepareStatement(insertAlbum);
            stmt.setString(1, title);
            stmt.setString(2, date);
            stmt.executeUpdate();
            stmt.close();
            updateSongCount(connection);
        } catch (SQLException throwables) {
            return false;
        }
        return true;
    }

    public static boolean insertSong(Connection connection, String title, String album, float duration, int year) {
        try {
            String insertSong = "INSERT INTO songs(title, album, duration, year) VALUES(?,?,?,?);";
            PreparedStatement stmt = connection.prepareStatement(insertSong);
            stmt.setString(1, title);
            stmt.setString(2, album);
            stmt.setFloat(3, duration);
            stmt.setInt(4, year);
            stmt.executeUpdate();
            stmt.close();
            updateSongCount(connection);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return false;
        }
        return true;
    }

    public static boolean insertAuthor(Connection connection, String name, String album) {
        try {
            String insertAuthor = "INSERT INTO authors(name,album) VALUES(?,?);";
            PreparedStatement stmt = connection.prepareStatement(insertAuthor);
            stmt.setString(1, name);
            stmt.setString(2, album);
            stmt.executeUpdate();
            stmt.close();
            updateSongCount(connection);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return false;
        }
        return true;
    }

    public static boolean deleteAlbum(Connection connection, int id) {
        try {
            String deleteAlbum = String.format("DELETE FROM album WHERE id = %d;", id);
            Statement stmt = connection.createStatement();
            stmt.executeUpdate(deleteAlbum);
            stmt.close();
        } catch (SQLException throwables) {
            return false;
        }
        return true;
    }

    public static boolean deleteSong(Connection connection, int id) {
        try {
            String deleteSong = String.format("DELETE FROM songs WHERE id = %d;", id);
            Statement stmt = connection.createStatement();
            stmt.executeUpdate(deleteSong);
            stmt.close();
            updateSongCount(connection);
        } catch (SQLException throwables) {
            return false;
        }
        return true;
    }

    public static boolean deleteAuthor(Connection connection, int id) {
        try {
            String deleteAuthor = String.format("DELETE FROM authors WHERE id = %d;", id);
            Statement stmt = connection.createStatement();
            stmt.executeUpdate(deleteAuthor);
            stmt.close();
            updateSongCount(connection);
        } catch (SQLException throwables) {
            return false;
        }
        return true;
    }

    public static boolean updateAlbum(Connection connection, int id, String title, String date) {
        try {
            String updateAlbum = "UPDATE album SET title = ?, date = ? WHERE id = ?";
            PreparedStatement stmt = connection.prepareStatement(updateAlbum);
            stmt.setString(1, title);
            stmt.setString(2, date);
            stmt.setInt(3, id);
            stmt.executeUpdate();
            stmt.close();
            updateSongCount(connection);
        } catch (SQLException throwables) {
            return false;
        }
        return true;
    }

    public static CustomTableFormat getAlbums(Connection connection) throws SQLException {
        Statement stmt = connection.createStatement();
        String getAlbumColumns = "SELECT COLUMN_NAME FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_NAME = N'album';";
        ResultSet rs = stmt.executeQuery(getAlbumColumns);

        ArrayList<String> columns = new ArrayList<>();
        while (rs.next()) {
            columns.add(rs.getString("COLUMN_NAME"));
        }

        String getRows = "SELECT * FROM album;";
        rs = stmt.executeQuery(getRows);
        String[] row = new String[columns.size()];
        ArrayList<String[]> rows = new ArrayList<>();
        while (rs.next()) {
            for (int i = 0; i < columns.size(); i++) {
                String placeHolder = rs.getString(columns.get(i));
                row[i] = placeHolder == null ? "NULL" : placeHolder;
            }
            rows.add(row);
            row = new String[columns.size()];
        }
        rs.close();
        stmt.close();
        return new CustomTableFormat(columns, rows);
    }

    public static CustomTableFormat getSongs(Connection connection, String id) throws SQLException {
        Statement stmt = connection.createStatement();
        String getAlbumColumns = "SELECT COLUMN_NAME FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_NAME = N'songs';";
        ResultSet rs = stmt.executeQuery(getAlbumColumns);

        ArrayList<String> columns = new ArrayList<>();
        while (rs.next()) {
            columns.add(rs.getString("COLUMN_NAME"));
        }
        stmt.close();

        String getRows = "SELECT * FROM songs WHERE album LIKE ?;";
        PreparedStatement stmt2 = connection.prepareStatement(getRows);
        stmt2.setString(1, id);
        rs = stmt2.executeQuery();
        String[] row = new String[columns.size()];
        ArrayList<String[]> rows = new ArrayList<>();
        while (rs.next()) {
            for (int i = 0; i < columns.size(); i++) {
                String placeHolder = rs.getString(columns.get(i));
                row[i] = placeHolder == null ? "NULL" : placeHolder;
            }
            rows.add(row);
            row = new String[columns.size()];
        }
        rs.close();
        stmt2.close();
        return new CustomTableFormat(columns, rows);
    }

    public static CustomTableFormat getAuthors(Connection connection, String id) throws SQLException {
        Statement stmt = connection.createStatement();
        String getAlbumColumns = "SELECT COLUMN_NAME FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_NAME = N'authors';";
        ResultSet rs = stmt.executeQuery(getAlbumColumns);

        ArrayList<String> columns = new ArrayList<>();
        while (rs.next()) {
            columns.add(rs.getString("COLUMN_NAME"));
        }
        stmt.close();

        String getRows = "SELECT * FROM authors WHERE album LIKE ?;";
        PreparedStatement stmt2 = connection.prepareStatement(getRows);
        stmt2.setString(1, id);
        rs = stmt2.executeQuery();
        String[] row = new String[columns.size()];
        ArrayList<String[]> rows = new ArrayList<>();
        while (rs.next()) {
            for (int i = 0; i < columns.size(); i++) {
                String placeHolder = rs.getString(columns.get(i));
                row[i] = placeHolder == null ? "NULL" : placeHolder;
            }
            rows.add(row);
            row = new String[columns.size()];
        }
        rs.close();
        stmt2.close();
        return new CustomTableFormat(columns, rows);
    }

    public static List<String> getAlbumNames(Connection connection) {
        try {
            Statement stmt = connection.createStatement();
            String albumsGet = "SELECT a.title title FROM album a;";
            ResultSet rs = stmt.executeQuery(albumsGet);
            List<String> list = new ArrayList<>();
            while (rs.next()) {
                list.add(rs.getString("title"));
            }
            rs.close();
            stmt.close();

            return list;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }
}
