package components.tables;

import helpers.SelectionHelper;
import windows.MainWindow;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.Connection;

/**
 * Clase de la que extienden el resto de tablas.
 */
public class GenericTable extends JTable {
    public BuildTable buildTable;
    public AuthorTable authorTable;
    public Connection connection;
    // El model de la tabla
    public final DefaultTableModel model;

    public GenericTable() {
        this.setSelectionModel(new SelectionHelper());  // Evitar que el usuario pueda no seleccionar nada o demasiado.

        model = (DefaultTableModel) this.getModel();
        model.addColumn("Conexión no establecida.");
    }

    /**
     * Que el usuario no pueda editar las celdas haciendo doble click
     */
    @Override
    public boolean isCellEditable(int i, int i1) {
        return false;
    }

    /**
     * Esto se aplica a todas las tablas.
     * @param connection conexión para consultas.
     * @param table tabla a la que hacer consultas.
     */
    public void onConnect(Connection connection, GenericTable table) {
        this.setSelectionModel(new SelectionHelper());  // Evitar que el usuario pueda no seleccionar nada o demasiado.
    }

    /**
     * Desactivar acciones de tablas cada vez que se desconecte.
     */
    public void onDisconnect() {
        model.addColumn("Conexión no establecida.");
        MainWindow.genericLabel1.setText("");
        MainWindow.genericLabel2.setText("");
        MainWindow.genericLabel3.setText("");

        MainWindow.genericButton1.setText("");
        MainWindow.genericButton1.setVisible(false);
        MainWindow.genericButton2.setText("");
        MainWindow.genericButton2.setVisible(false);
        MainWindow.genericButton3.setText("");
        MainWindow.genericButton3.setVisible(false);
    }

    /**
     * Reseteo las columnas y las filas de todas las tablas.
     */
    public void onClosed() {
        model.setColumnCount(0);
        model.setRowCount(0);
    }

    /**
     * Fuerzo que se seleccione la primera row.
     */
    public void selectFirst() {
        this.setRowSelectionInterval(0, 0);
    }
}
