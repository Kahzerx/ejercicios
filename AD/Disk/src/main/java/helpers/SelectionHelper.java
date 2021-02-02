package helpers;

import javax.swing.*;

public class SelectionHelper extends DefaultListSelectionModel {
    public SelectionHelper() {
        setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    }

    @Override
    public void removeSelectionInterval(int i, int i1) {
    }
}
