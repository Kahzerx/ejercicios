package com.fileTest;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class FileWriterReader {
    public static void main(String[] args) throws IOException {
        String dirName = "FileWriterReader";
        String fileName = "FileWriterReader.txt";

        Generic.tryCreateDir(dirName);

        fileWriter(dirName, fileName);
        fileReader(dirName, fileName);
    }

    private static void fileWriter(String dir, String file) throws IOException {
        String msg = "Hola, que tal?\nHooooola";

        FileWriter writer = new FileWriter(String.format("%s/%s", dir, file));
        writer.write(msg);
        writer.close();
    }

    private static void fileReader(String dir, String file) throws IOException {
        FileReader reader = new FileReader(String.format("%s/%s", dir, file));

        int value = reader.read();

        while (value != -1) {
            System.out.println((char) value);
            value = reader.read();
        }
        reader.close();
    }
}
