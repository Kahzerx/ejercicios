package com.fileTest;

import javax.swing.*;
import java.io.File;
import java.io.IOException;

public class FileCreationAndData {

    public static String dirName = "directorio";

    public static String fileName = String.format("%s/test.txt", dirName);

    public static void main(String[] args) throws IOException {
        boolean tryCreateDir = new File(dirName).mkdirs();
        if (tryCreateDir) System.out.printf("Dir %s created.%n", dirName);
        File file = new File(fileName);
        boolean tryCreateFile = file.createNewFile();
        if (tryCreateFile) System.out.printf("File %s created.%n", fileName);
        if (file.exists()){
            System.out.printf("Nombre: %s%n", file.getName());
            System.out.printf("Directorio padre: %s%n", file.getParent());
            System.out.printf("Ruta relativa: %s%n", file.getPath());
            System.out.printf("Ruta absoluta: %s%n", file.getAbsolutePath());
        }

        // Con FileChooser

        JFileChooser chooser = new JFileChooser();
        int returnValue = chooser.showOpenDialog(null);
        if(returnValue == JFileChooser.APPROVE_OPTION) {
            System.out.printf("%nNombre: %s%n", chooser.getSelectedFile().getName());
            System.out.printf("Directorio padre: %s%n", chooser.getCurrentDirectory().getName());
            System.out.printf("Ruta relativa: %s%n", chooser.getSelectedFile().getPath());
            System.out.printf("Ruta absoluta: %s%n", chooser.getSelectedFile().getAbsolutePath());
        }
    }
}
