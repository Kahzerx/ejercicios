package components.tables;

import database.Query;
import utils.CustomTableFormat;
import windows.MainWindow;

import java.sql.Connection;
import java.sql.SQLException;

public class AuthorTable extends GenericTable {
    @Override
    public void onConnect(Connection connection, GenericTable table) {
        super.onConnect(connection, table);
        try {
            CustomTableFormat tbl = Query.getAuthors(connection, (String) table.getValueAt(table.getSelectedRow(), table.getColumn("title").getModelIndex()));
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
        MainWindow.genericLabel1.setText("Editar autor seleccionado");
        MainWindow.genericLabel2.setText("Eliminar autor seleccionado");
        MainWindow.genericLabel3.setText("");

        MainWindow.genericButton1.setText("Editar");
        MainWindow.genericButton1.setVisible(true);
        MainWindow.genericButton2.setText("Eliminar");
        MainWindow.genericButton2.setVisible(true);
        MainWindow.genericButton3.setText("");
        MainWindow.genericButton3.setVisible(false);
    }
}
