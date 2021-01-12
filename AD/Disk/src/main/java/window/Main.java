package window;

import database.DBConnection;

public class Main {
    public static void main(String[] args) {
        DBConnection dbConnection = new DBConnection("root", "root");
        dbConnection.deleteTables();
        dbConnection.createTables();
        dbConnection.close();
    }
}
