package window;

public class Main {
    /**
     * Datos de conexión para la base de datos con {@link database.BasicDataSourceConnection}.
     */
    public static String url = "jdbc:mysql://localhost:3306/discografica?serverTimezone=UTC";
    public static String user = "root";
    public static String pass = "root";

    public static void main(String[] args) {
        // Inicio los componentes.
        WindowComponents windowComponents = new WindowComponents(url, user, pass);
        windowComponents.setVisible(true);
    }

}
