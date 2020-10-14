package fileManagement;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.*;


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
            if (fileExists()) {  // Por si no has seleccionado ningún archivo y has cerrado la ventana del chooser.
                return getContent(chooser.getSelectedFile());
            }
            else {
                JOptionPane.showMessageDialog(null, "El archivo seleccionado no existe", "Error", JOptionPane.ERROR_MESSAGE);  // Input manual de un archivo que no existe.
            }
        }
        return null;
    }

    // Acción al guardar como.
    public static void createAndSaveFile(String content) {
        JFileChooser chooser = new JFileChooser();
        chooser.setDialogTitle("Guardar archivo");
        chooser.showSaveDialog(null);

        File file = chooser.getSelectedFile();
        if (file != null) {  // Por si no has seleccionado ningún archivo y has cerrado la ventana del chooser.
            String name = file.getName().split("\\.").length > 1 ? file.getName() : String.format("%s.txt", file.getName());  // Por si pones un nombre sin extensión, por defecto va un ".txt".
            File newFile = new File(file.getAbsolutePath().replace(file.getName(), name));

            if (newFile.exists() && !shouldSaveFileOverwrite(name)) {  // Prompt de si de verdad quieres sobreescribir el archivo seleccionado.
                return;
            }
            openFile = newFile;

            writeContent(openFile, content);
        }
    }

    // Acción guardar que activa el guardar como si no existe archivo.
    public static void saveFile(String content) {
        if (openFile == null || !openFile.exists()) {  // Puede que se elimine el archivo mientras está abierto?
            createAndSaveFile(content);
        }
        else {
            writeContent(openFile, content);
        }
    }

    public static void close(String content, String windowName) {
        if (isGonnaClose(content, windowName) && shouldCloseSaving()) {
            saveFile(content);
        }
        onDelete();
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
                JOptionPane.showMessageDialog(null, "No se ha encontrado el archivo", "Error", JOptionPane.ERROR_MESSAGE);
            }
            catch (IOException e) {
                JOptionPane.showMessageDialog(null, "No ha sido posible extraer el contenido del archivo", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
        return null;
    }

    // Devuelve true si cumple los requisitos para aplicar cambios sobre el textArea.
    private static boolean shouldSetText(String fileName, long fileLength) {
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
            JOptionPane.showMessageDialog(null, "No ha sido posible aplicar cambios sobre este archivo", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Devuelve true si el usuario quiere sobreescribir, si se cierra la ventana se asume un false.
    private static boolean shouldSaveFileOverwrite(String fileName) {
        return JOptionPane.YES_OPTION == JOptionPane.showConfirmDialog(null, String.format("Has seleccionado un archivo ya existente:\n - %s\nSeguro que quieres sobreescribirlo?", fileName), "Confirmación", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
    }

    // Devuelve true si el usuario decide guardar al intenta cerrar un archivo no guardado, si se cierra la ventana se asume un false.
    private static boolean shouldCloseSaving() {
        return JOptionPane.YES_OPTION == JOptionPane.showConfirmDialog(null, "Estas intentando cerrar un archivo no guardado\nQuieres guardar?", "Confirmación", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
    }

    // Devuelve true si el usuario intenta abrir un archivo sin cerrar al anterior, si se cierra la ventana se asume un false.
    private static boolean shouldOpenSaving() {
        return JOptionPane.YES_OPTION == JOptionPane.showConfirmDialog(null, "Estas intentando abrir un archivo sin guardar el anterior\nQuieres guardar?", "Confirmación", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
    }

    // Conseguir la path del archivo para el título.
    public static String getCurrentPath() {
        return openFile.getAbsolutePath();
    }

    // Comprobar si el archivo no ha sido eliminado.
    public static boolean fileExists() {
        if (fileIsNotNull()) return openFile.exists();
        return false;
    }

    // Saber si hay algún archivo asociado al documento sobre el que se está escribiendo.
    public static boolean fileIsNotNull() {
        return openFile != null;
    }

    // Eliminar el archivo asociado al documento cada vez que este se elimina.
    public static void onDelete() {
        openFile = null;
    }

    // Devuelve true si el documento que se va a cerrar tiene cambios que requieran de guardado.
    public static boolean isGonnaClose(String content, String windowName) {
        return (openFile == null && !content.equals("")) || windowName.startsWith("*");
    }

    // Añade verificación del usuario al isGonnaClose.
    public static void shouldSaveBeforeOpening(String content, String windowName) {
        if (isGonnaClose(content, windowName) && shouldOpenSaving()) {
            saveFile(content);
        }
    }
}
