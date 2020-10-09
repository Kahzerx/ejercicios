package com.fileTest;

import java.io.*;

public class BufferedWriterReader {
    public static void main(String[] args) throws IOException {
        String dirName = "BufferedWriterReader";
        String fileName = "BufferedWriterReader.txt";

        Generic.tryCreateDir(dirName);

        bufferedWriter(dirName, fileName);
        bufferedReader(dirName, fileName);
    }

    private static void bufferedWriter(String dir, String file) throws IOException {
        String msg = "Hola, que tal?\nHooooola";

        BufferedWriter writer = new BufferedWriter(new FileWriter(String.format("%s/%s", dir, file)));
        writer.write(msg);
        writer.close();
    }

    private static void bufferedReader(String dir, String file) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(String.format("%s/%s", dir, file)));
        String line;

        while ((line = reader.readLine()) != null) {
            line = line.replaceAll("[\\r\\n]", "");
            System.out.println(line);
        }
        reader.close();
    }
}
