package fileManagement;

import application.MainWindow;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.*;

//TODO detectores de si es necesario guardar el archivo si se quiere abrir uno nuevo sin cerrar previamente el anterior.

public class FileManagement {
    // Seleccionar y abrir un archivo.
    public static void openFile() throws IOException {
        JFileChooser chooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Archivos de texto", "txt", "conf", "py", "java");
        chooser.setFileFilter(filter);
        chooser.setDialogTitle("Selecciona un archivo");
        if  (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION && shouldSetText(chooser.getSelectedFile().getName(), chooser.getSelectedFile().length())) {
            MainWindow.textArea.setText(getContent(chooser.getSelectedFile()));
            MainWindow.textArea.setCaretPosition(0);  // Pongo el cursor en la primera linea.
        }
    }

    // Acción al guardar como.
    public static void createFile() throws IOException {
        JFileChooser chooser = new JFileChooser();
        chooser.setDialogTitle("Guardar archivo");
        chooser.showSaveDialog(null);

        File file = chooser.getSelectedFile();
        String name = file.getName().split("\\.").length > 1 ? file.getName() : String.format("%s.txt", file.getName());

        BufferedWriter writer = new BufferedWriter(new FileWriter(file.getAbsolutePath().replace(file.getName(), name)));
        String content = MainWindow.textArea.getText();

        writer.write(content);
        writer.close();

        //TODO checker de si debería sobreescribir archivo ya existente.
    }

    public static void saveFile() {
        //TODO guardar archivo si existe.
    }

    //Devuelve el contenido de un archivo.
    private static String getContent(File file) throws IOException {
        if (file.exists()) {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line;
            StringBuilder content = new StringBuilder();

            while ((line = reader.readLine()) != null) {
                content.append(line).append("\n");
            }
            reader.close();

            return content.toString();
        }
        else {
            System.err.println("El archivo no existe :(");
        }
        return null;
    }

    // Devuelve true si cumple los requisitos para aplicar cambios sobre el textArea.
    private static Boolean shouldSetText(String fileName, long fileLength) {
        int warningSize = 60;  // Archivos demasiado grandes pueden dar problemas e incluso crashear el programa.
        if (fileLength > warningSize * Math.pow(2, 20)) {
            return JOptionPane.YES_OPTION == JOptionPane.showConfirmDialog(null, String.format("Este archivo pesa más de %dMB:\n - %s (%.2fMB)\nEstas seguro de que quieres continuar?\nEl programa puede dejar de responder", warningSize, fileName, fileLength * Math.pow(2, -20)), "Confirmación", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        }
        return true;
    }
}
