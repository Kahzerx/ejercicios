package utils;

import application.WindowComponents;

import java.util.ArrayList;

import static utils.ConsoleTable.printAsciiTable;

public class Update {
    public static void updateMainTextArea(ArrayList<String[]> info, String mode) {
        WindowComponents.resetFields();
        WindowComponents.resetTextArea();
        WindowComponents.updateBox(info);
        setText(info, mode);
    }

    private static void setText(ArrayList<String[]> info, String mode) {
        StringBuilder content = new StringBuilder();
        if (!info.isEmpty()) {
            content.append(String.format("Se van a mostrar los libros de este documento [%s]\n\n", mode));
        }
        for (String[] row : info) {
            content.append(String.format("Publicado en: %s", row[0]));
            content.append(String.format("\nEl título es: %s", row[1]));
            content.append(String.format("\nEl autor es: %s", row[2]));
            content.append(String.format("\nLa editorial es: %s", row[3]));
            content.append("\n===========================\n");
        }
        WindowComponents.textArea.setText(content.toString());
        printAsciiTable(info);
    }

    public static void updateQueryTextArea(ArrayList<String[]> info) {
        StringBuilder content = new StringBuilder();
        for (String[] row : info) {
            content.append(String.join(" - ", row));
            content.append("\n===========================\n");
        }
        WindowComponents.queryResultTextArea.setText(content.toString());
    }
}
