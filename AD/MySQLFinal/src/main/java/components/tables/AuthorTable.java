package components.tables;

import database.Query;
import utils.CustomTableFormat;
import windows.MainWindow;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Tabla que muestra los autores, extiendo de una clase para tener funciones en común con las demás tablas.
 */
public class AuthorTable extends GenericTable {
    /**
     * Acciones al conectar.
     * @param connection conexión para las consultas.
     * @param table tabla para determinar que álbum ha seleccionado el usuario y listar solo lo necesario.
     */
    @Override
    public void onConnect(Connection connection, GenericTable table) {
        super.onConnect(connection, table);
        try {
            CustomTableFormat tbl = Query.getAuthors(connection, (String) table.getValueAt(table.getSelectedRow(), table.getColumn("id").getModelIndex()));
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

    /**
     * Acciones que se desbloquean al hacer click en algún autor.
     * @param i parámetros
     * @param i1 raros
     * @param b del
     * @param b1 override.
     */
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
