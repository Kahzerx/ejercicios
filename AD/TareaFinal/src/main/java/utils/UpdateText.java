package utils;

import application.MainWindowComponents;
import xmlManagement.TextAreaType;

import java.util.ArrayList;
import java.util.HashMap;

public class UpdateText {
    public static void updateMainTextArea(ArrayList<TextAreaType> content) {  // Actualiza el textArea principal y activa los inputs/botones.
        setText(content);
        MainWindowComponents.applyEdit1Button.setEnabled(true);
        MainWindowComponents.saveButton.setEnabled(true);
        MainWindowComponents.CCAButton.setEnabled(true);
        MainWindowComponents.sevenButton.setEnabled(true);
        MainWindowComponents.EVButton.setEnabled(true);
        MainWindowComponents.addButton.setEnabled(true);
    }

    // Recibe la información colocada correctamente por el SAX.
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
        content.replace(content.length() - 2, content.length(), "");

        MainWindowComponents.setTextArea(content.toString());
    }

    // Actualiza el textArea de abajo para la query de count con XPath.
    public static void updateSideTextArea(int amount) {
        String content = String.format("Hay %d componentes compatibles.", amount);
        MainWindowComponents.sideTextArea.setText(opToggle(content) ? content : "");
    }

    // Esta vez con información que requiere ser parseada.
    public static void updateSideTextArea(int count, HashMap<String, Integer> stuffs) {
        StringBuilder builder = new StringBuilder(String.format("Hay %d resultados.\n\n", count));
        for (String key : stuffs.keySet()) {
            builder.append(String.format("%d -> %s\n", stuffs.get(key), key));
        }
        MainWindowComponents.sideTextArea.setText(opToggle(builder.toString()) ? builder.toString() : "");
    }

    // Los comboBox de editar categoría.
    public static void updateComboBox1(ArrayList<Integer> content) {
        for (int i : content) {
            MainWindowComponents.editWIdBox.addItem(i);
        }
        for (String cat : new String[] {"Small_gates", "Large_arithmetic", "Graphical_processing", "Memory", "Small_arithmetic", "Converters", "Control_logic", "Processing_unit", "Others"}) {
            MainWindowComponents.editWCatBox.addItem(cat);
        }
    }

    // Toggle para ocultar la información.
    private static boolean opToggle(String newContent) {
        return !newContent.equals(MainWindowComponents.sideTextArea.getText());
    }
}
