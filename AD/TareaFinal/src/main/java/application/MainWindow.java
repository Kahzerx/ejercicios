package application;

public class MainWindow {
    // Keep it simple \o/
    public static void main(String[] args) {
        MainWindowComponents mainWindowComponents = new MainWindowComponents();
        mainWindowComponents.frame.setVisible(true);
        mainWindowComponents.frame.setResizable(false);
    }
}
