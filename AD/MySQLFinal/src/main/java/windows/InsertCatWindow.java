package windows;

import database.Query;
import utils.DBUtils;
import utils.StringUtils;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class InsertCatWindow extends JFrame {
    private final MainWindow mainWindow;

    private JLabel nameLabel;

    private JTextArea nameTextArea;

    private JButton submitButton;

    public InsertCatWindow(MainWindow mainWindow) {
        this.mainWindow = mainWindow;

        createJLabel();

        createJTextArea();

        createJButton();

        addStuff();

        createJFrame();
    }

    public void createJFrame() {
        setBounds(100, 100, 300, 150);
        setLayout(new GroupLayout(getContentPane()));
        setTitle("Insertar Categoría");
        setResizable(false);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent windowEvent) {
                super.windowClosing(windowEvent);
                close();
            }
        });
    }

    private void createJLabel() {
        nameLabel = (JLabel) mainWindow.createJThing(2, "Nombre");
        nameLabel.setBounds(20, 20, 80, 30);
    }

    private void createJTextArea() {
        nameTextArea = (JTextArea) mainWindow.createJThing(3, "");
        nameTextArea.setBounds(100, 20, 170, 30);
    }

    private void createJButton() {
        submitButton = (JButton) mainWindow.createJThing(0, "Añadir");
        submitButton.setBounds(100, 70, 110, 30);
        submitButton.addActionListener(actionEvent -> {
            if (StringUtils.isEmpty(nameTextArea.getText())) {
                JOptionPane.showMessageDialog(null, "No puede haber campos en blanco!", "ERROR", JOptionPane.ERROR_MESSAGE);
            } else {
                String name = nameTextArea.getText().trim();
                if (!Query.insertCategory(mainWindow.dataSourceConnection.connection, name)) {
                    JOptionPane.showMessageDialog(null, "Error al insertar!\nSeguro que este título no existe?", "ERROR", JOptionPane.ERROR_MESSAGE);
                }
                DBUtils.refresh(mainWindow.dataSourceConnection, mainWindow.logger, mainWindow);
                close();
            }
        });
    }

    private void addStuff() {
        add(nameLabel);

        add(nameTextArea);

        add(submitButton);
    }

    private void close() {
        dispose();
        mainWindow.switchB(true);
    }
}
