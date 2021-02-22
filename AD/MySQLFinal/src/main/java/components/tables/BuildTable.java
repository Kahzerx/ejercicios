package components.tables;

import database.Query;
import utils.CustomTableFormat;
import windows.MainWindow;

import javax.swing.event.ListSelectionEvent;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * Tabla que muestra los builds, extiendo de una clase para tener funciones en común con las demás tablas.
 */
public class BuildTable extends GenericTable {
    /**
     * Acciones al establecer conexión con esta tabla.
     * @param connection Conexión para hacer las queries de las demás tablas.
     * @param table Aquí no hace nada pero lo necesito como parámetro en las otras tablas del extends.
     */
    @Override
    public void onConnect(Connection connection, GenericTable table) {
        super.onConnect(connection, this);
        try {
            CustomTableFormat tbl = Query.getBuilds(connection, (String) table.getValueAt(table.getSelectedRow(), table.getColumn("name").getModelIndex()));
            for (String column : tbl.columnNames) {
                super.model.addColumn(column);
            }

            for (String[] row : tbl.rows) {
                super.model.addRow(row);
            }
            // Funciones de categorías a demás de activar las otras tablas.
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    /**
     * Acciones al pulsar en algún build.
     * @param listSelectionEvent el Event Listener.
     */
    @Override
    public void valueChanged(ListSelectionEvent listSelectionEvent) {
        super.valueChanged(listSelectionEvent);
        if (listSelectionEvent.getValueIsAdjusting()) {  // Da dos pulsos, uno al presionar el click y otro al soltarlo, este es el fix.
            if (getSelectedRow() == -1) return;
            if (authorTable != null && getSelectedRow() != -1) {
                authorTable.onClosed();
                authorTable.onConnect(connection, this);
            }
        }
    }

    /**
     * Acciones que se desbloquean al hacer click en algún build.
     * @param i parámetros
     * @param i1 raros
     * @param b del
     * @param b1 override
     */
    @Override
    public void changeSelection(int i, int i1, boolean b, boolean b1) {
        super.changeSelection(i, i1, b, b1);
        if (getSelectedRow() == -1) return;
        MainWindow.genericLabel1.setText("Editar build seleccionado");
        MainWindow.genericLabel2.setText("Eliminar build seleccionado");
        MainWindow.genericLabel3.setText("Insertar autor");

        MainWindow.genericButton1.setText("Editar");
        MainWindow.genericButton1.setVisible(true);
        MainWindow.genericButton2.setText("Eliminar");
        MainWindow.genericButton2.setVisible(true);
        MainWindow.genericButton3.setText("Insertar");
        MainWindow.genericButton3.setVisible(true);
    }
}
