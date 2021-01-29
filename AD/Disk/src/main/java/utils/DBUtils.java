package utils;

import database.BasicDataSourceConnection;

import javax.swing.*;
import java.sql.SQLException;

public class DBUtils {
    /**
     * Conectarse dependiendo del método que quieras usar.
     */
    public static void connect(BasicDataSourceConnection dataSourceConnection, TextPaneLogger logger) {
        try {
            // Check de si debería cerrar la conexión porque ya está abierta con otro método.
            shouldClose(dataSourceConnection, logger);
            if (dataSourceConnection.connection == null || dataSourceConnection.connection.isClosed()) {
                connectBasicDataSource(dataSourceConnection, logger);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    /**
     * Cierro la conexión con el close() de {@link database.GenericConnection}.
     */
    public static void disconnect(BasicDataSourceConnection dataSourceConnection, TextPaneLogger logger) {
        try {
            shouldClose(dataSourceConnection, logger);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    /**
     * Confirmación de usuario.
     * @param msg mensaje que sale en el pop up.
     * @return true si ha presionado que Sí.
     */
    private static boolean confirmation(String msg) {
        return JOptionPane.showConfirmDialog(null, msg) == JOptionPane.YES_OPTION;
    }


    /**
     * Cerrar la conexión.
     * @param dataSourceConnection objeto de la conexión.
     * @param logger el logger de actividad.
     * @throws SQLException posible excepción de SQL.
     */
    private static void shouldClose(BasicDataSourceConnection dataSourceConnection, TextPaneLogger logger) throws SQLException {
        if (dataSourceConnection.connection != null && !dataSourceConnection.connection.isClosed()) {
            logger.log(LogLevel.INFORMATION, "Cerrando conexión...");
            dataSourceConnection.close();
            logger.log(LogLevel.SUCCESS, "Conexión cerrada.");
        }
    }

    /**
     * Conexión con la base de datos y queries genéricas usando {@link org.apache.commons.dbcp.BasicDataSource}.
     */
    private static void connectBasicDataSource(BasicDataSourceConnection dataSourceConnection, TextPaneLogger logger) throws SQLException {
        logger.log(LogLevel.INFORMATION, "Estableciendo conexión...");
        dataSourceConnection.connect();
        if (dataSourceConnection.connection != null) {
            if (dataSourceConnection.connection.isClosed()) {
                logger.log(LogLevel.ERROR, "Error al establecer conexión.");
                return;
            }
            logger.log(LogLevel.SUCCESS, "Conexión establecida.");
        } else {
            logger.log(LogLevel.ERROR, "Error al establecer conexión.");
        }
    }
}