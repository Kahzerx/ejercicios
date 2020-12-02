package utils;

import application.MainWindowComponents;
import xmlManagement.TextAreaType;

import java.util.ArrayList;

public class UpdateText {
    public static void updateMainTextArea(ArrayList<TextAreaType> content) {
        setText(content);
        MainWindowComponents.applyEdit1Button.setEnabled(true);
        MainWindowComponents.saveButton.setEnabled(true);
    }

    private static void setText(ArrayList<TextAreaType> data) {
        StringBuilder content = new StringBuilder();
        for (TextAreaType datum : data) {
            content.append(String.format("ID: %s\n", datum.content[0]));
            content.append(String.format("Fecha: %s\n", datum.content[1]));
            content.append(String.format("Arquitectura compatible: %s\n", datum.content[2]));
            content.append(String.format("Tipo: %s\n", datum.content[3]));
            content.append("Autores:\n");
            for (Object author : datum.authors) {
                content.append(String.format("> %s\n", author));
            }
            content.append(String.format("Velocidad: %s ticks\n", datum.content[4]));
            content.append(String.format("Categoría: %s\n", datum.content[5]));
            content.append(String.format("Disposición: %s\n", datum.content[6]));
            content.append(String.format("Bits: %s\n", datum.content[7]));
            content.append(String.format("Sistema de numeración: %s\n", datum.content[8]));
            content.append("===========================\n");
        }

        MainWindowComponents.setTextArea(content.toString());
    }

    public static void updateComboBox1(ArrayList<Integer> content) {
        for (int i : content) {
            MainWindowComponents.editWIdBox.addItem(i);
        }
        for (String cat : ComponentCat.categories) {
            MainWindowComponents.editWCatBox.addItem(cat);
        }
    }
}
