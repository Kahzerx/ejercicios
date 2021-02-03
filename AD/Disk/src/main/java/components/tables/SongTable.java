package components.tables;

import database.Query;
import utils.CustomTableFormat;
import windows.MainWindow;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Tabla que muestra las canciones, extiendo de una clase para tener funciones en común con las demás tablas.
 */
public class SongTable extends GenericTable {
    /**
     * Acciones al establecer conexión con esta tabla.
     * @param connection Conexión para hacer las queries de las demás tablas.
     * @param table Aquí no hace nada pero lo necesito como parámetro en las otras tablas del extends.
     */
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

    /**
     * Acciones que se desbloquean al hacer click en alguna canción.
     * @param i parámetros
     * @param i1 raros
     * @param b del
     * @param b1 override
     */
    @Override
    public void changeSelection(int i, int i1, boolean b, boolean b1) {
        super.changeSelection(i, i1, b, b1);
        if (getSelectedRow() == -1) return;
        MainWindow.genericLabel1.setText("Editar canción seleccionada");
        MainWindow.genericLabel2.setText("Eliminar canción seleccionada");
        MainWindow.genericLabel3.setText("");

        MainWindow.genericButton1.setText("Editar");
        MainWindow.genericButton1.setVisible(true);
        MainWindow.genericButton2.setText("Eliminar");
        MainWindow.genericButton2.setVisible(true);
        MainWindow.genericButton3.setText("");
        MainWindow.genericButton3.setVisible(false);
    }
}
