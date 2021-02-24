import windows.MainWindow;

public class Main {
    // TODO Integrity check.
    /**
     * Datos de conexión para la base de datos con {@link database.BasicDataSourceConnection}.
     */
    public static String url = "jdbc:oracle:thin:@localhost:1521/xe";
    public static String databaseName = "rBuilds";
    public static String user = "FCT";
    public static String pass = "root";

    public static void main(String[] args) {
        // Inicio los componentes.
        MainWindow mainWindow = new MainWindow(url, databaseName, user, pass);
        mainWindow.setVisible(true);
    }
}
