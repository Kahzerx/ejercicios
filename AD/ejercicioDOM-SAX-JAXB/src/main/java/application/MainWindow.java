package application;

import de.vandermeer.asciitable.AsciiTable;
import de.vandermeer.skb.interfaces.transformers.textformat.TextAlignment;

import java.io.File;
import java.util.ArrayList;

public class MainWindow {
    public static File openFile;

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

    public static void update(ArrayList<String[]> info, String mode) {
        setText(info, mode);
        WindowComponents.resetFields();
        WindowComponents.updateBox(info);
    }

    // Por probar en consola :P.
    private static void printAsciiTable(ArrayList<String[]> info) {
        AsciiTable table = new AsciiTable();
        table.addRule();
        table.addRow(null, null, null, String.format("%d Libros", info.size())).setTextAlignment(TextAlignment.CENTER);
        table.addRule();
        table.addRow("Título", "Autor", "Editorial", "Año").setTextAlignment(TextAlignment.CENTER);
        for (String[] row : info) {
            table.addRule();
            table.addRow(row[1], row[2], row[3], row[0]);
        }
        table.addRule();

        String render = table.render();
        System.out.println(render);
    }

    public static void noFile() {
        WindowComponents.textArea.setText("");
        WindowComponents.updateGui(-1);
    }

    public static void main(String[] args) {
        WindowComponents components = new WindowComponents();
        components.frame.setVisible(true);
        components.frame.setResizable(false);
    }
}