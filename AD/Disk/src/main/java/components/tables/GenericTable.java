package components.tables;

import javax.swing.*;
import java.sql.Connection;

public class GenericTable extends JTable {

    @Override
    public boolean isCellEditable(int i, int i1) {
        return false;
    }

    public void onConnect(Connection connection) {
        this.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    }

    public void onDisconnect() {
    }

    public void onClosed() {
    }

    public void selectFirst() {
        this.setRowSelectionInterval(0, 0);
    }
}
