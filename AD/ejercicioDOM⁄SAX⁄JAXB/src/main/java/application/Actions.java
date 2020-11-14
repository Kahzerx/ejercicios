package application;

import XMLManagement.DOM;

import javax.swing.*;

public class Actions {

    public static int m = -1;  // 0 = DOM, 1 = SAX, 2 = JAXB

    public static void open(int stat) {
        m = stat;
        switch (stat) {
            case 0:
                DOM.onOpenDOM();
        }
    }

    public static void sideActions(int val, String[] par) {
        if (m != -1) {
            switch (m) {
                case 0:  // Acciones cuando has abierto DOM.
                    if (val == 0) DOM.onAdd(par[0], par[1], par[2]);
                    else if (val == 1) DOM.writeAndClose();
                    else if (val == 2) DOM.onTitleUpdate(par[0], par[1]);
            }
        }
        else JOptionPane.showMessageDialog(null, "Debes abrir un archivo antes", "ERROR", JOptionPane.ERROR_MESSAGE);
    }
}
