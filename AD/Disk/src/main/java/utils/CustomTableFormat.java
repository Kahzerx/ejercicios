package utils;

import java.util.ArrayList;

public class CustomTableFormat {
    public ArrayList<String> columnNames;
    public ArrayList<String[]> rows;

    public CustomTableFormat(ArrayList<String> columnNames, ArrayList<String[]> rows) {
        this.columnNames = columnNames;
        this.rows = rows;
    }
}
