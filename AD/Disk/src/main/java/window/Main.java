package window;

public class Main {

    public static String url = "jdbc:mysql://localhost:3306/discografica?serverTimezone=UTC";

    public static void main(String[] args) {
        WindowComponents windowComponents = new WindowComponents(url, "root", "root");
        windowComponents.setVisible(true);
    }
}
