package fileManagement;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;

public class FileManagement {
    public static File chooseXMLFile() {
        JFileChooser chooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("XML", "XML", "xml");
        chooser.setFileFilter(filter);
        chooser.setDialogTitle("selectiona un archivo");
        if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION && chooser.getSelectedFile().exists()) {
            return chooser.getSelectedFile();
        }
        return null;
    }
}
