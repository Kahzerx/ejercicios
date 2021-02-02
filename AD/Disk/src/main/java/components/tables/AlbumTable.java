package components.tables;

import database.Query;
import utils.CustomTableFormat;
import windows.MainWindow;

import javax.swing.event.ListSelectionEvent;
import java.sql.Connection;
import java.sql.SQLException;

public class AlbumTable extends GenericTable {
    public SongTable songTable;
    public AuthorTable authorTable;
    private Connection connection;

    @Override
    public void onConnect(Connection connection, GenericTable table) {
        super.onConnect(connection, this);
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
                authorTable.onConnect(connection, this);
                songTable.onConnect(connection, this);
                MainWindow.genericLabel1.setText("Editar album seleccionado");
                MainWindow.genericLabel3.setText("Insertar cancion");
                MainWindow.genericLabel2.setText("Insertar autor");
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

        if (authorTable != null) {
            authorTable = null;
        }
    }

    @Override
    public void valueChanged(ListSelectionEvent listSelectionEvent) {
        super.valueChanged(listSelectionEvent);
        if (listSelectionEvent.getValueIsAdjusting()) {
            if (getSelectedRow() == -1) return;
            if (songTable != null && getSelectedRow() != -1) {
                songTable.onClosed();
                songTable.onConnect(connection, this);
            }

            if (authorTable != null && getSelectedRow() != -1) {
                authorTable.onClosed();
                authorTable.onConnect(connection, this);
            }
        }
    }

    @Override
    public void changeSelection(int i, int i1, boolean b, boolean b1) {
        super.changeSelection(i, i1, b, b1);
        if (getSelectedRow() == -1) return;

        MainWindow.genericLabel1.setText("Editar album seleccionado");
        MainWindow.genericLabel3.setText("Insertar cancion");
        MainWindow.genericLabel2.setText("Insertar autor");
    }
}
