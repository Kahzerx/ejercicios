package database;

import utils.CustomTableFormat;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Lugar donde hago todas las consultas, refactor needed ;-;.
 */
public class Query {
    /**
     * @return si la query se ha ejecutado
     */
    public static boolean insertCategory(Connection connection, String name) {
        try {
            String insertCategory = "INSERT INTO b_category(name) VALUES(?);";
            PreparedStatement stmt = connection.prepareStatement(insertCategory);
            stmt.setString(1, name);
            stmt.executeUpdate();
            stmt.close();
        } catch (SQLException throwables) {
            return false;
        }
        return true;
    }

    /**
     * @return si la query se ha ejecutado
     */
    public static boolean insertBuild(Connection connection, String type, int speed, String cat, String orientation, int bits, String numSys, String date) {
        try {
            String insertBuild = "INSERT INTO b_builds(type, speed, category, orientation, bits, num_system, date) VALUES(?,?,?,?,?,?,?);";
            PreparedStatement stmt = connection.prepareStatement(insertBuild);
            stmt.setString(1, type);
            stmt.setInt(2, speed);
            stmt.setString(3, cat);
            stmt.setString(4, orientation);
            stmt.setInt(5, bits);
            stmt.setString(6, numSys);
            stmt.setString(7, date);
            stmt.executeUpdate();
            stmt.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * @return si la query se ha ejecutado
     */
    public static boolean insertAuthor(Connection connection, String name, int buildID) {
        try {
            String insertAuthor = "INSERT INTO b_authors(name,buildID) VALUES(?,?);";
            PreparedStatement stmt = connection.prepareStatement(insertAuthor);
            stmt.setString(1, name);
            stmt.setInt(2, buildID);
            stmt.executeUpdate();
            stmt.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return false;
        }
        return true;
    }

    public static boolean deleteCategory(Connection connection, int id) {
        try {
            String deleteCategory = String.format("DELETE FROM b_category WHERE id = %d;", id);
            Statement stmt = connection.createStatement();
            stmt.executeUpdate(deleteCategory);
            stmt.close();
        } catch (SQLException throwables) {
            return false;
        }
        return true;
    }

    public static boolean deleteBuild(Connection connection, int id) {
        try {
            String deleteBuild = String.format("DELETE FROM b_builds WHERE id = %d;", id);
            Statement stmt = connection.createStatement();
            stmt.executeUpdate(deleteBuild);
            stmt.close();
        } catch (SQLException throwables) {
            return false;
        }
        return true;
    }

    public static boolean deleteAuthor(Connection connection, int id) {
        try {
            String deleteAuthor = String.format("DELETE FROM b_authors WHERE id = %d;", id);
            Statement stmt = connection.createStatement();
            stmt.executeUpdate(deleteAuthor);
            stmt.close();
        } catch (SQLException throwables) {
            return false;
        }
        return true;
    }

    public static boolean updateCategory(Connection connection, int id, String name) {
        try {
            String updateCategory = "UPDATE b_category SET name = ? WHERE id = ?";
            PreparedStatement stmt = connection.prepareStatement(updateCategory);
            stmt.setString(1, name);
            stmt.setInt(2, id);
            stmt.executeUpdate();
            stmt.close();
        } catch (SQLException throwables) {
            return false;
        }
        return true;
    }

    public static boolean updateBuild(Connection connection, int id, String type, int speed, String cat, String orientation, int bits, String numSys, String date) {
        try {
            String updateBuild = "UPDATE b_builds SET type = ?, speed = ?, category = ?, orientation = ?, bits = ?, num_system = ?, date = ? WHERE id = ?";
            PreparedStatement stmt = connection.prepareStatement(updateBuild);
            stmt.setString(1, type);
            stmt.setInt(2, speed);
            stmt.setString(3, cat);
            stmt.setString(4, orientation);
            stmt.setInt(5, bits);
            stmt.setString(6, numSys);
            stmt.setString(7, date);
            stmt.setInt(8, id);
            stmt.executeUpdate();
            stmt.close();
        } catch (SQLException throwables) {
            return false;
        }
        return true;
    }

    public static boolean updateAuthor(Connection connection, int id, String name, int buildID) {
        try {
            String updateAuthor = "UPDATE b_authors SET name = ?, buildID = ? WHERE id = ?";
            PreparedStatement stmt = connection.prepareStatement(updateAuthor);
            stmt.setString(1, name);
            stmt.setInt(2, buildID);
            stmt.setInt(3, id);
            stmt.executeUpdate();
            stmt.close();
        } catch (SQLException throwables) {
            return false;
        }
        return true;
    }

    public static CustomTableFormat getAlbums(Connection connection) throws SQLException {
        Statement stmt = connection.createStatement();
        String getCatColumns = "SELECT COLUMN_NAME FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_NAME = N'b_category';";  // Lista de nombres de columnas de la tabla category.
        ResultSet rs = stmt.executeQuery(getCatColumns);

        ArrayList<String> columns = new ArrayList<>();
        while (rs.next()) {
            columns.add(rs.getString("COLUMN_NAME"));
        }

        String getRows = "SELECT * FROM b_category;";
        rs = stmt.executeQuery(getRows);
        String[] row = new String[columns.size()];
        ArrayList<String[]> rows = new ArrayList<>();
        while (rs.next()) {
            for (int i = 0; i < columns.size(); i++) {
                String placeHolder = rs.getString(columns.get(i));
                // Si está incompleto, añade un NULL.
                row[i] = placeHolder == null ? "NULL" : placeHolder;
            }
            rows.add(row);
            row = new String[columns.size()];
        }
        rs.close();
        stmt.close();
        return new CustomTableFormat(columns, rows);
    }

    public static CustomTableFormat getBuilds(Connection connection, String id) throws SQLException {
        Statement stmt = connection.createStatement();
        String getBuildsColumns = "SELECT COLUMN_NAME FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_NAME = N'b_builds';";  // Lista de nombres de columnas de la tabla builds.
        ResultSet rs = stmt.executeQuery(getBuildsColumns);

        ArrayList<String> columns = new ArrayList<>();
        while (rs.next()) {
            columns.add(rs.getString("COLUMN_NAME"));
        }
        stmt.close();

        String getRows = "SELECT * FROM b_builds WHERE category LIKE ?;";
        PreparedStatement stmt2 = connection.prepareStatement(getRows);
        stmt2.setString(1, id);
        rs = stmt2.executeQuery();
        String[] row = new String[columns.size()];
        ArrayList<String[]> rows = new ArrayList<>();
        while (rs.next()) {
            for (int i = 0; i < columns.size(); i++) {
                String placeHolder = rs.getString(columns.get(i));
                // Si está incompleto, añade un NULL.
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
        String getAuthorsColumns = "SELECT COLUMN_NAME FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_NAME = N'b_authors';";  // Lista de nombres de columnas de la tabla authors.
        ResultSet rs = stmt.executeQuery(getAuthorsColumns);

        ArrayList<String> columns = new ArrayList<>();
        while (rs.next()) {
            columns.add(rs.getString("COLUMN_NAME"));
        }
        stmt.close();

        String getRows = "SELECT * FROM b_authors WHERE buildID LIKE ?;";
        PreparedStatement stmt2 = connection.prepareStatement(getRows);
        stmt2.setString(1, id);
        rs = stmt2.executeQuery();
        String[] row = new String[columns.size()];
        ArrayList<String[]> rows = new ArrayList<>();
        while (rs.next()) {
            for (int i = 0; i < columns.size(); i++) {
                String placeHolder = rs.getString(columns.get(i));
                // Si está incompleto, añade un NULL.
                row[i] = placeHolder == null ? "NULL" : placeHolder;
            }
            rows.add(row);
            row = new String[columns.size()];
        }
        rs.close();
        stmt2.close();
        return new CustomTableFormat(columns, rows);
    }

    public static List<String> getCategoryNames(Connection connection) {
        try {
            Statement stmt = connection.createStatement();
            String catGet = "SELECT c.name name FROM b_category c;";
            ResultSet rs = stmt.executeQuery(catGet);
            List<String> list = new ArrayList<>();
            while (rs.next()) {
                list.add(rs.getString("name"));
            }
            rs.close();
            stmt.close();

            return list;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    public static List<String> getBuildNames(Connection connection) {
        try {
            Statement stmt = connection.createStatement();
            String catGet = "SELECT b.id id FROM b_builds b;";
            ResultSet rs = stmt.executeQuery(catGet);
            List<String> list = new ArrayList<>();
            while (rs.next()) {
                list.add(rs.getString("id"));
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
