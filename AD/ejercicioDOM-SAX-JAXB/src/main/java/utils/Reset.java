package utils;

import application.WindowComponents;

public class Reset {
    public static void noFile() {
        WindowComponents.textArea.setText("");
        WindowComponents.queryResultTextArea.setText("");
        WindowComponents.queryTextArea.setText("");
        WindowComponents.updateGui(-1);
    }
}
