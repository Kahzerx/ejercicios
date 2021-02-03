package utils;

import java.util.ArrayList;

/**
 * Formato para recibir los datos en las tablas.
 */
public class CustomTableFormat {
    public ArrayList<String> columnNames;
    public ArrayList<String[]> rows;

    public CustomTableFormat(ArrayList<String> columnNames, ArrayList<String[]> rows) {
        this.columnNames = columnNames;
        this.rows = rows;
    }
}
