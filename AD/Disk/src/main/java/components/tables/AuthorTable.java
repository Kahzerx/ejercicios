package components.tables;

import database.Query;
import utils.CustomTableFormat;

import java.sql.Connection;
import java.sql.SQLException;

public class AuthorTable extends GenericTable {
    @Override
    public void onConnect(Connection connection, GenericTable table) {
        super.onConnect(connection, table);
        try {
            CustomTableFormat tbl = Query.getAuthors(connection, (String) table.getValueAt(table.getSelectedRow(), table.getColumn("title").getModelIndex()));
            for (String column : tbl.columnNames) {
                super.model.addColumn(column);
            }

            for (String[] row : tbl.rows) {
                super.model.addRow(row);
            }

            if (super.model.getRowCount() > 0) {
                super.selectFirst();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
