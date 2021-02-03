package helpers;

import javax.swing.*;

/**
 * Otra clase con un propósito bastante inútil.
 * Con esto solo puedes seleccionar una row.
 * Si haces ctrl click no te deja seleccionar más de uno ni puedes dejar de seleccionar lo ya seleccionado (evitar que hagan click en la única selección y no haya nada seleccionado en alguna tabla).
 */
public class SelectionHelper extends DefaultListSelectionModel {
    public SelectionHelper() {
        setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    }

    @Override
    public void removeSelectionInterval(int i, int i1) {
    }
}
