package fileManagement;

import application.MainWindow;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;

public class FileManagement {

    // Abrir archivo.
    public static File chooseXMLFile() {
        JFileChooser chooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("XML", "XML", "xml");
        chooser.setFileFilter(filter);
        chooser.setDialogTitle("selecciona un archivo");
        if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION && chooser.getSelectedFile().exists()) {
            MainWindow.openFile = chooser.getSelectedFile();
            return chooser.getSelectedFile();
        }
        return null;
    }

    // Seleccionar archivo para guardar.
    public static File createAndSave() {
        JFileChooser chooser = new JFileChooser();
        chooser.setDialogTitle("Guardar archivo");
        chooser.showSaveDialog(null);
        File file = chooser.getSelectedFile();
        if (file != null) {  // Por si no has seleccionado ningún archivo y has cerrado la ventana del chooser.
            String name = file.getName().split("\\.").length > 1 ? file.getName() : String.format("%s.xml", file.getName());
            return new File(file.getAbsolutePath().replace(file.getName(), name));
        }
        return null;
    }
}
