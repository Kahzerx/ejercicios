package components.tables;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.Connection;

public class GenericTable extends JTable {

    public final DefaultTableModel model;

    public GenericTable() {
        this.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        model = (DefaultTableModel) this.getModel();
        model.addColumn("Conexión no establecida.");
    }

    @Override
    public boolean isCellEditable(int i, int i1) {
        return false;
    }

    public void onConnect(Connection connection, GenericTable table) {
        this.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    }

    public void onDisconnect() {
        model.addColumn("Conexión no establecida.");
    }

    public void onClosed() {
        model.setColumnCount(0);
        model.setRowCount(0);
    }

    public void selectFirst() {
        this.setRowSelectionInterval(0, 0);
    }
}
