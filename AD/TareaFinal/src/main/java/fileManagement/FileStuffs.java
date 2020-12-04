package fileManagement;

import javax.swing.*;
import java.io.File;

public class FileStuffs {
    public static String fileName = "logixs.xml";
    public static File updated = new File("aaa");

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
