package components.tables;

import database.Query;
import utils.CustomTableFormat;

import java.sql.Connection;
import java.sql.SQLException;

public class SongTable extends GenericTable {
    @Override
    public void onConnect(Connection connection, GenericTable table) {
        super.onConnect(connection, this);
        try {
            CustomTableFormat tbl = Query.getSongs(connection, (String) table.getValueAt(table.getSelectedRow(), 1));
            for (String column : tbl.columnNames) {
                super.model.addColumn(column);
            }

            for (String[] row : tbl.rows) {
                super.model.addRow(row);
            }

            if (super.model.getRowCount() > 0) {
                super.selectFirst();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public void onClosed() {
        super.onClosed();
    }

    @Override
    public void onDisconnect() {
        super.onDisconnect();
    }
}
