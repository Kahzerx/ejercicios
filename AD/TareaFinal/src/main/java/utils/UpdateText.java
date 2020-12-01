package utils;

import application.MainWindowComponents;
import management.SaxType;

import java.util.ArrayList;

public class UpdateText {
    public static void updateMainTextArea(ArrayList<SaxType> content) {
        setText(content);
    }

    private static void setText(ArrayList<SaxType> data) {
        StringBuilder content = new StringBuilder();
        for (SaxType datum : data) {
            content.append(String.format("Fecha: %s\n", datum.content[1]));
            content.append(String.format("Arquitectura compatible: %s\n", datum.content[2]));
            content.append(String.format("Tipo: %s\n", datum.content[3]));
            content.append("Autores:\n");
            for (Object author : datum.authors) {
                content.append(String.format("-> %s\n", author));
            }
            content.append(String.format("Velocidad: %s\n", datum.content[4]));
            content.append(String.format("Categoría: %s\n", datum.content[5]));
            content.append(String.format("Disposición: %s\n", datum.content[6]));
            content.append(String.format("Bits: %s\n", datum.content[7]));
            content.append(String.format("Sistema de numeración: %s\n", datum.content[8]));
            content.append("\n===========================\n");
        }

        MainWindowComponents.mainTextArea.setText(content.toString());
    }
}
