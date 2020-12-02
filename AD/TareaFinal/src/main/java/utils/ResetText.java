package utils;

import application.MainWindowComponents;

public class ResetText {
    public static void resetAll() {
        MainWindowComponents.mainTextArea.setText("");
        MainWindowComponents.editWIdBox.removeAllItems();
        MainWindowComponents.editWCatBox.removeAllItems();
        MainWindowComponents.applyEdit1Button.setEnabled(false);
        MainWindowComponents.saveButton.setEnabled(false);
    }
}
