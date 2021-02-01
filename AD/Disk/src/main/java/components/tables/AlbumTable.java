package components.tables;

import database.Query;
import utils.CustomTableFormat;

import javax.swing.event.ListSelectionEvent;
import java.sql.Connection;
import java.sql.SQLException;

public class AlbumTable extends GenericTable {
    public SongTable songTable;
    private Connection connection;

    @Override
    public void onConnect(Connection connection, GenericTable table) {
        super.onConnect(connection, this);
        this.songTable = (SongTable) table;
        this.connection = connection;
        try {
            CustomTableFormat tbl = Query.getAlbums(connection);
            for (String column : tbl.columnNames) {
                super.model.addColumn(column);
            }

            for (String[] row : tbl.rows) {
                super.model.addRow(row);
            }

            if (super.model.getRowCount() > 0) {
                super.selectFirst();
                songTable.onConnect(connection, this);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public void onClosed() {
        super.onClosed();
        if (songTable != null) {
            songTable = null;
        }
    }

    @Override
    public void valueChanged(ListSelectionEvent listSelectionEvent) {
        super.valueChanged(listSelectionEvent);
        if (listSelectionEvent.getValueIsAdjusting()) {
            if (songTable != null && getSelectedRow() != -1) {
                songTable.onClosed();
                songTable.onConnect(connection, this);
            }
        }
    }
}
