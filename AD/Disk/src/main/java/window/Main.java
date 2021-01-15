package window;

import database.BasicDataSourceConnection;
import database.DriverManagerConnection;

public class Main {

    public static String url = "jdbc:mysql://localhost:3306/discografica?serverTimezone=UTC";

    public static void main(String[] args) {
        connectDriverManager();
        connectBasicDataSource();
    }

    public static void connectDriverManager() {
        DriverManagerConnection driverManagerConnection = new DriverManagerConnection(url, "root", "root");
        driverManagerConnection.deleteTables();
        driverManagerConnection.createTables();
        driverManagerConnection.close();
    }

    public static void connectBasicDataSource() {
        BasicDataSourceConnection dataSourceConnection = new BasicDataSourceConnection(url, "root", "root");
        dataSourceConnection.deleteTables();
        dataSourceConnection.createTables();
        dataSourceConnection.close();
    }
}
