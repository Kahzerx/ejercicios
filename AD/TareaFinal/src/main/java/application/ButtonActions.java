package application;

import utils.ResetText;
import utils.StringUtils;
import xmlManagement.DOM;
import xmlManagement.JAXB;
import xmlManagement.SAX;
import xmlManagement.XPATH;

import javax.swing.*;

public class ButtonActions {
    private static AddWindowComponents addWindowComponents;
    public static void open(int stat) {
        switch (stat) {
            case 0:
                ResetText.resetAll();
                if (SAX.onOpenSax()) {  // Abrir archivo usando SAX y creando objeto JAXB.
                    JAXB.openJAXB();
                }
                break;
            case 2:
                // Inicializar la ventana de añadir componente.
                addWindowComponents = new AddWindowComponents();
                addWindowComponents.frame.setVisible(true);
                addWindowComponents.frame.setResizable(false);
                MainWindowComponents.addButton.setEnabled(false);
                break;
        }
    }

    public static void edit(int opt, String[] info) {  // Se ejecuta al presionar el botón de editar categoría.
        if (opt == 0) {  // Edit category
            if (StringUtils.stringCheck(info[0], info[1])) {
                JAXB.updateCat(Integer.parseInt(info[0]), info[1]);
                JAXB.getContent();
            } else
                JOptionPane.showMessageDialog(null, "No puedes editar un elemento vacio.", "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void save() {
        JAXB.saveJaxb();  // Guardar desde el objecto JAXB.
    }

    public static void processQuery(int query) {  // Cada una de las queries para XPath.
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

    public static void addAuthor(String actualContent, String newAuthor) {  // Añadir autores para nuevo componente.
        if (!StringUtils.stringCheck(newAuthor)) {
            JOptionPane.showMessageDialog(null, "Introduce algún autor", "ERROR", JOptionPane.ERROR_MESSAGE);
            return;
        }
        AddWindowComponents.authorTextArea.setText(actualContent + newAuthor + "\n");
        AddWindowComponents.authorField.setText("");
    }

    // Check de si las variables necesarias para añadir un nuevo componente son correctas.
    public static void add(String[] date, String arch, String type, String speed, String category, String orientation, String bits, String numSys, String author) {
        if (!StringUtils.stringCheck(type, author, speed, bits)) {  // Si has dejado algún campo sin completar.
            JOptionPane.showMessageDialog(null, "Asegúrate de completar todos los campos", "ERROR", JOptionPane.ERROR_MESSAGE);
        }
        else if (!StringUtils.isInt(speed, bits)) {  // Si los campos que deberían tener números no los tienen.
            JOptionPane.showMessageDialog(null, "Asegúrate de que la velocidad y bits son integers", "ERROR", JOptionPane.ERROR_MESSAGE);
        }
        else {
            int[] d = new int[] {Integer.parseInt(date[0]), Integer.parseInt(date[1]), Integer.parseInt(date[2])};
            addWindowComponents.frame.dispose();  // Finalizar la ventana de añadir componentes.
            DOM.addComponent(JAXB.getId(), d, arch, type, speed, category, orientation, bits, numSys, author);
        }
    }
}
