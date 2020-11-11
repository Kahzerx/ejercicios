package application;

import java.util.ArrayList;

public class MainWindow {
    private static void setText(ArrayList<String[]> info) {
        StringBuilder content = new StringBuilder();
        for (String[] row : info) {
            content.append(String.format("Publicado en: %s", row[0]));
            content.append(String.format("\nEl título es: %s", row[1]));
            content.append(String.format("\nEl autor es: %s", row[2]));
            content.append("\n===========================\n");
        }
        WindowComponents.textArea.setText(content.toString());
    }

    public static void update(ArrayList<String[]> info) {
        setText(info);
        WindowComponents.resetFields();
        WindowComponents.updateBox(info);
    }

    public static void main(String[] args) {
        WindowComponents components = new WindowComponents();
        components.frame.setVisible(true);
        components.frame.setResizable(false);
    }
}
