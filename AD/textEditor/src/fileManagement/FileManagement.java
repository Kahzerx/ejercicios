package fileManagement;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.*;

//TODO detectores de si es necesario guardar el archivo si se quiere abrir uno nuevo sin cerrar previamente el anterior.

public class FileManagement {
    private static File openFile;  // Archivo que se está modificando.
    // Seleccionar y abrir un archivo.
    public static String openFile() {
        JFileChooser chooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Archivos de texto", "txt", "conf", "py", "java");
        chooser.setFileFilter(filter);
        chooser.setDialogTitle("Selecciona un archivo");
        if  (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION && shouldSetText(chooser.getSelectedFile().getName(), chooser.getSelectedFile().length())) {
            openFile = chooser.getSelectedFile();
            return getContent(chooser.getSelectedFile());
        }
        return null;
    }

    // Acción al guardar como.
    public static void createFile(String content) {
        JFileChooser chooser = new JFileChooser();
        chooser.setDialogTitle("Guardar archivo");
        chooser.showSaveDialog(null);

        File file = chooser.getSelectedFile();
        String name = file.getName().split("\\.").length > 1 ? file.getName() : String.format("%s.txt", file.getName());
        File newFile = new File(file.getAbsolutePath().replace(file.getName(), name));

        if (newFile.exists() && !shouldSaveFile(name)) {  // Prompt de si de verdad quieres sobreescribir el archivo seleccionado.
            return;
        }
        openFile = newFile;

        writeContent(openFile, content);
    }

    // Acción guardar que activa el guardar como si no existe archivo.
    public static void saveFile(String content) {
        //TODO feedback de si hay que guardar o de cuando lo has hecho.
        if (openFile == null || !openFile.exists()) {  // Puede que se elimine el archivo mientras está abierto?
            createFile(content);
        }
        else {
            writeContent(openFile, content);
        }
    }

    public static void close() {
        //TODO check de si el documento está guardado.
    }

    //Devuelve el contenido de un archivo.
    private static String getContent(File file) {
        if (file.exists()) {
            try {
                BufferedReader reader = new BufferedReader(new FileReader(file));
                String line;
                StringBuilder content = new StringBuilder();
                while ((line = reader.readLine()) != null) {
                    content.append(line).append("\n");
                }
                reader.close();
                return content.toString();
            }
            catch (FileNotFoundException e) {
                System.err.println("No se ha encontrado el archivo.");
            }
            catch (IOException e) {
                System.err.println("No ha sido posible extraer el contenido del archivo.");
            }


        }
        else {
            JOptionPane.showMessageDialog(null, "El archivo seleccionado no existe", "Error", JOptionPane.ERROR_MESSAGE);
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

    // Escribir en el archivo el contenido de JTextArea.
    private static void writeContent(File file, String content) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(file));
            writer.write(content);
            writer.close();
        } catch (IOException e) {
            System.out.println("Escribir en el archivo.");
        }
    }

    // Devuelve true si cumple los requisitos para guardar el archivo.
    private static Boolean shouldSaveFile(String fileName) {
        return JOptionPane.YES_OPTION == JOptionPane.showConfirmDialog(null, String.format("Has seleccionado un archivo ya existente:\n - %s\nSeguro que quieres sobreescribirlo?", fileName), "Confirmación", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
    }

    // Conseguir la path del archivo para el título.
    public static String getCurrentPath() {
        return openFile.getAbsolutePath();
    }

    // Comprobar si el archivo no ha sido eliminado.
    public static boolean fileExists() {
        return openFile.exists();
    }

    public static Boolean fileIsNull() {
        return openFile == null;
    }

    public static void onRandomDelete() {
        openFile = null;
    }
}
