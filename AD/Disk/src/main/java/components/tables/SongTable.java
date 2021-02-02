package components.tables;

import database.Query;
import utils.CustomTableFormat;
import windows.MainWindow;

import javax.swing.event.ListSelectionEvent;
import java.sql.Connection;
import java.sql.SQLException;

public class SongTable extends GenericTable {
    @Override
    public void onConnect(Connection connection, GenericTable table) {
        super.onConnect(connection, this);
        try {
            CustomTableFormat tbl = Query.getSongs(connection, (String) table.getValueAt(table.getSelectedRow(), table.getColumn("title").getModelIndex()));
            for (String column : tbl.columnNames) {
                super.model.addColumn(column);
            }

            for (String[] row : tbl.rows) {
                super.model.addRow(row);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public void changeSelection(int i, int i1, boolean b, boolean b1) {
        super.changeSelection(i, i1, b, b1);
        if (getSelectedRow() == -1) return;
        MainWindow.genericLabel1.setText("Editar canción seleccionada");
        MainWindow.genericLabel2.setText("Eliminar canción seleccionada");
        MainWindow.genericLabel3.setText("");
    }
}
