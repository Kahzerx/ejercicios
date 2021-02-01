import windows.MainWindowComponents;

public class Main {
    /**
     * Datos de conexión para la base de datos con {@link database.BasicDataSourceConnection}.
     */
    public static String url = "jdbc:mysql://localhost:3306/?serverTimezone=UTC";
    public static String databaseName = "discografica";
    public static String user = "root";
    public static String pass = "root";

    public static void main(String[] args) {
        // Inicio los componentes.
        MainWindowComponents mainWindowComponents = new MainWindowComponents(url, databaseName, user, pass);
        mainWindowComponents.setVisible(true);
    }
}
