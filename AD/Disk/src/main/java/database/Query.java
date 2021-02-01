package database;

import utils.CustomTableFormat;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Query {
    // Saber cuantas canciones tiene cada album a modo de prueba de hacer queries.
    public static void updateSongCount(Connection connection) throws SQLException {
        Statement stmt = connection.createStatement();
        String getCount = "SELECT (SELECT COUNT(*) FROM discografica.songs s WHERE a.title LIKE s.album) amount, a.title album FROM discografica.album a;";
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
            String insertAlbum = "INSERT INTO discografica.album(title, date) VALUES(?,?);";
            PreparedStatement stmt = connection.prepareStatement(insertAlbum);
            stmt.setString(1, title);
            stmt.setString(2, date);
            stmt.executeUpdate();
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

        String getRows = "SELECT * FROM discografica.album;";
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

        String getRows = String.format("SELECT * FROM discografica.songs WHERE album LIKE '%s';", id);
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

    public static CustomTableFormat getAuthors(Connection connection, String id) throws SQLException {
        Statement stmt = connection.createStatement();
        String getAlbumColumns = "SELECT COLUMN_NAME FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_NAME = N'authors';";
        ResultSet rs = stmt.executeQuery(getAlbumColumns);

        ArrayList<String> columns = new ArrayList<>();
        while (rs.next()) {
            columns.add(rs.getString("COLUMN_NAME"));
        }

        String getRows = String.format("SELECT * FROM discografica.authors WHERE album LIKE '%s';", id);
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
}
