package utils;

import components.TextPaneLogger;
import database.BasicDataSourceConnection;
import windows.MainWindow;

import javax.swing.*;
import java.sql.SQLException;

public class DBUtils {
    /**
     * Conectarse dependiendo del método que quieras usar.
     */
    public static void connect(BasicDataSourceConnection dataSourceConnection, TextPaneLogger logger, MainWindow windowComponents) {
        try {
            // Check de si debería cerrar la conexión porque ya está abierta con otro método.
            shouldClose(dataSourceConnection, logger);
            windowComponents.categoryTable.onClosed();
            windowComponents.buildTable.onClosed();
            windowComponents.authorTable.onClosed();

            if (dataSourceConnection.connection == null || dataSourceConnection.connection.isClosed()) {
                connectBasicDataSource(dataSourceConnection, logger);
                windowComponents.categoryTable.buildTable = windowComponents.buildTable;
                windowComponents.buildTable.authorTable = windowComponents.authorTable;
                windowComponents.categoryTable.onConnect(dataSourceConnection.connection, null);
                windowComponents.insertCatButton.setEnabled(true);
                windowComponents.deleteCatButton.setEnabled(true);
            } else {
                windowComponents.categoryTable.onDisconnect();
                windowComponents.buildTable.onDisconnect();
                windowComponents.authorTable.onDisconnect();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    /**
     * Cierro la conexión con el close() de {@link database.GenericConnection}.
     */
    public static void disconnect(BasicDataSourceConnection dataSourceConnection, TextPaneLogger logger, MainWindow windowComponents) {
        try {
            shouldClose(dataSourceConnection, logger);
            windowComponents.insertCatButton.setEnabled(false);
            windowComponents.deleteCatButton.setEnabled(false);
            windowComponents.categoryTable.onClosed();
            windowComponents.buildTable.onClosed();
            windowComponents.authorTable.onClosed();

            windowComponents.categoryTable.onDisconnect();
            windowComponents.buildTable.onDisconnect();
            windowComponents.authorTable.onDisconnect();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    /**
     * Refrescar la conexión, AKA apagar y encender con extra steps, pero es más fácil de leer y entender.
     * @param dataSourceConnection conexión.
     * @param logger no sé por qué, si ya paso el windowComponents.
     * @param windowComponents el objeto de la ventana principal.
     */
    public static void refresh(BasicDataSourceConnection dataSourceConnection, TextPaneLogger logger, MainWindow windowComponents) {
        connect(dataSourceConnection, logger, windowComponents);
    }

    /**
     * Confirmación de usuario.
     * @param msg mensaje que sale en el pop up.
     * @return true si ha presionado que Sí.
     */
    public static boolean confirmation(String msg) {
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

        if (!dataSourceConnection.connect()) {
            logger.log(LogLevel.ERROR, "Error al establecer conexión.");
            return;
        }

        if (dataSourceConnection.connection != null) {
            if (dataSourceConnection.connection.isClosed()) {
                logger.log(LogLevel.ERROR, "Error al establecer conexión.");
                return;
            }
            logger.log(LogLevel.SUCCESS, "Conexión establecida.");
        } else {
            logger.log(LogLevel.ERROR, "Error al establecer conexión.");
        }

        if (!dataSourceConnection.tryCreateDatabase()) {
            logger.log(LogLevel.ERROR, "Error al crear la base de datos!");
            return;
        }

        if (!dataSourceConnection.tryCreateTables()) {
            logger.log(LogLevel.ERROR, "Error al crear tablas.");
            dataSourceConnection.close();
        }
    }
}
