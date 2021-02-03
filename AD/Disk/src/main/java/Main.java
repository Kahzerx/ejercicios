import windows.MainWindow;

public class Main {
    // TODO Integrity check.
    /**
     * Datos de conexión para la base de datos con {@link database.BasicDataSourceConnection}.
     */
    public static String url = "jdbc:mysql://localhost:3306/?serverTimezone=UTC";
    public static String databaseName = "discos";
    public static String user = "root";
    public static String pass = "root";

    public static void main(String[] args) {
        // Inicio los componentes.
        MainWindow mainWindow = new MainWindow(url, databaseName, user, pass);
        mainWindow.setVisible(true);
    }
}
