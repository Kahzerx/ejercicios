package components.tables;

import helpers.SelectionHelper;
import windows.MainWindow;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.Connection;

public class GenericTable extends JTable {

    public final DefaultTableModel model;

    public GenericTable() {
        this.setSelectionModel(new SelectionHelper());

        model = (DefaultTableModel) this.getModel();
        model.addColumn("Conexión no establecida.");
    }

    @Override
    public boolean isCellEditable(int i, int i1) {
        return false;
    }

    public void onConnect(Connection connection, GenericTable table) {
        this.setSelectionModel(new SelectionHelper());
    }

    public void onDisconnect() {
        model.addColumn("Conexión no establecida.");
        MainWindow.genericLabel1.setText("");
        MainWindow.genericLabel2.setText("");
        MainWindow.genericLabel3.setText("");
    }

    public void onClosed() {
        model.setColumnCount(0);
        model.setRowCount(0);
    }

    public void selectFirst() {
        this.setRowSelectionInterval(0, 0);
    }
}
