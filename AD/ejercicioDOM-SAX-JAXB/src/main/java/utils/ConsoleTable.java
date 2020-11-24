package utils;

import de.vandermeer.asciitable.AsciiTable;
import de.vandermeer.skb.interfaces.transformers.textformat.TextAlignment;

import java.util.ArrayList;

public class ConsoleTable {
    // Por probar en consola :P.
    public static void printAsciiTable(ArrayList<String[]> info) {
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
}
