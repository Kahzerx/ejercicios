package windows;

import database.Query;
import utils.DBUtils;
import utils.StringUtils;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class EditCatWindow extends JFrame {
    private final MainWindow mainWindow;
    private final int id;
    private final String name;

    private JLabel idLabel;
    private JLabel titleLabel;

    private JTextField idTextField;
    private JTextField nameTextField;

    private JButton submitButton;

    public EditCatWindow(MainWindow mainWindow, int id, String name) {
        this.mainWindow = mainWindow;
        this.id = id;
        this.name = name;

        createJLabel();

        createJTextField();

        createJButton();

        addStuff();

        createJFrame();
    }

    public void createJFrame() {
        setBounds(100, 100, 300, 200);
        setLayout(new GroupLayout(getContentPane()));
        setTitle("Editar Categoría");
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
        idLabel = (JLabel) mainWindow.createJThing(2, "ID");
        idLabel.setBounds(20, 20, 80, 30);
        titleLabel = (JLabel) mainWindow.createJThing(2, "Nombre");
        titleLabel.setBounds(20, 70, 80, 30);
    }

    private void createJTextField() {
        idTextField = (JTextField) mainWindow.createJThing(4, String.valueOf(this.id));
        idTextField.setBounds(100, 20, 170, 30);
        idTextField.setEditable(false);

        nameTextField = (JTextField) mainWindow.createJThing(4, this.name);
        nameTextField.setBounds(100, 70, 170, 30);
    }

    private void createJButton() {
        submitButton = (JButton) mainWindow.createJThing(0, "Editar");
        submitButton.setBounds(100, 120, 110, 30);
        submitButton.addActionListener(actionEvent -> {
            // handle de excepciones.
            if (StringUtils.isEmpty(nameTextField.getText())) {
                JOptionPane.showMessageDialog(null, "No puede haber campos en blanco!", "ERROR", JOptionPane.ERROR_MESSAGE);
            } else {
                String title = nameTextField.getText().trim();
                if (!Query.updateCategory(mainWindow.dataSourceConnection.connection, this.id, title)) {
                    JOptionPane.showMessageDialog(null, "Error al editar!", "ERROR", JOptionPane.ERROR_MESSAGE);
                }
                DBUtils.refresh(mainWindow.dataSourceConnection, mainWindow.logger, mainWindow);
                close();
            }
        });
    }

    private void addStuff() {
        add(idLabel);
        add(titleLabel);

        add(idTextField);
        add(nameTextField);

        add(submitButton);
    }

    private void close() {
        dispose();
        mainWindow.switchB(true);
    }
}
