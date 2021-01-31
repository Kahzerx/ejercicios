package components.tables;

import database.Query;
import utils.CustomTableFormat;

import javax.swing.table.DefaultTableModel;
import java.sql.Connection;
import java.sql.SQLException;

public class AlbumTable extends GenericTable {

    private final DefaultTableModel model;

    public AlbumTable() {
        model = (DefaultTableModel) this.getModel();
        model.addColumn("Conexión no establecida.");
    }

    @Override
    public void onConnect(Connection connection) {
        super.onConnect(connection);
        try {
            CustomTableFormat table = Query.getAlbums(connection);
            for (String column : table.columnNames) {
                model.addColumn(column);
            }

            for (String[] row : table.rows) {
                model.addRow(row);
            }

            if (model.getRowCount() > 0) {
                super.selectFirst();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public void onClosed() {
        super.onClosed();
        model.setColumnCount(0);
        model.setRowCount(0);
    }

    @Override
    public void onDisconnect() {
        super.onDisconnect();
        model.addColumn("Conexión no establecida.");
    }
}
