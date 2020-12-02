package application;

import utils.ResetText;
import utils.StringUtils;
import xmlManagement.JAXB;
import xmlManagement.SAX;

import javax.swing.*;

public class ButtonActions {
    public static void open(int stat) {
        if (stat == 0) {
            ResetText.resetAll();
            if (SAX.onOpenSax()) {
                JAXB.openJAXB();
            }
        }
    }

    public static void edit(int opt, String[] info) {
        switch (opt) {
            case 0:  // Edit category
                if (StringUtils.stringCheck(info[0], info[1])) {
                    JAXB.updateCat(Integer.parseInt(info[0]), info[1]);
                    JAXB.getContent();
                }
                else JOptionPane.showMessageDialog(null, "No puedes editar un elemento vacio.", "ERROR", JOptionPane.ERROR_MESSAGE);
                break;
        }
    }

    public static void save() {
    }
}
