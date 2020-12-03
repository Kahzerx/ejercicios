package application;

import utils.ResetText;
import utils.StringUtils;
import xmlManagement.JAXB;
import xmlManagement.SAX;
import xmlManagement.XPATH;

import javax.swing.*;

public class ButtonActions {
    public static void open(int stat) {
        switch (stat) {
            case 0:
                ResetText.resetAll();
                if (SAX.onOpenSax()) {
                    JAXB.openJAXB();
                }
                break;
            case 2:
                AddWindowComponents addWindowComponents = new AddWindowComponents();
                addWindowComponents.frame.setVisible(true);
                addWindowComponents.frame.setResizable(false);
                break;
        }
    }

    public static void edit(int opt, String[] info) {
        if (opt == 0) {  // Edit category
            if (StringUtils.stringCheck(info[0], info[1])) {
                JAXB.updateCat(Integer.parseInt(info[0]), info[1]);
                JAXB.getContent();
            } else
                JOptionPane.showMessageDialog(null, "No puedes editar un elemento vacio.", "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void save() {
        JAXB.saveJaxb();
    }

    public static void processQuery(int query) {
        switch (query) {
            case 0:
                XPATH.processQuery("//type[../authors/author='EEVV']");
                break;
            case 1:
                XPATH.processQuery("count(//bits[../@comp_arch='CCA'])");
                break;
            case 2:
                XPATH.processQuery("//type[../bits>7]");
                break;
        }
    }
}
