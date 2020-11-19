package application;

import XMLManagement.DOM;
import XMLManagement.SAX;

import javax.swing.*;

public class Actions {

    public static int m = -1;  // 0 = DOM, 1 = SAX, 2 = JAXB

    public static void open(int stat) {
        m = stat;
        switch (stat) {
            case 0:
                DOM.onOpenDOM();
                break;
            case 1:
                SAX.onOpenSAX();
                break;
            case 2:
                System.out.println("WIP");
                break;
        }
        if (MainWindow.openFile != null) {
            WindowComponents.updateGui(stat);
        }
        else {
            m = -1;
            WindowComponents.updateGui(m);
        }
    }

    public static void sideActions(int val, String[] par) {
        if (m != -1) {
            switch (m) {
                case 0:  // Acciones cuando has abierto DOM.
                    switch (val) {
                        case 0:
                            DOM.onAdd(par[0], par[1], par[2]);
                            break;
                        case 1:
                            DOM.writeAndClose();
                            break;
                        case 2:
                            DOM.onTitleUpdate(par[0], par[1]);
                            break;
                    }
                    break;
                case 1:
                case 2:
                    System.out.println("WIP");
                    break;
            }
        }
        else JOptionPane.showMessageDialog(null, "Debes abrir un archivo antes", "ERROR", JOptionPane.ERROR_MESSAGE);
    }
}
