package windows;

import database.Query;
import utils.DBUtils;
import utils.StringUtils;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;
import java.util.Objects;

@SuppressWarnings("unchecked")
public class InsertAuthorWindow extends JFrame {
    private final MainWindow mainWindow;

    private JLabel nameLabel;
    private JLabel buildLabel;

    private JTextField nameTextField;

    private JButton submitButton;

    private JComboBox<String> buildList;

    public InsertAuthorWindow(MainWindow mainWindow) {
        this.mainWindow = mainWindow;

        createJLabel();

        createJTextField();

        createJButton();

        createJComboBox();

        addStuff();

        createJFrame();
    }

    public void createJFrame() {
        setBounds(100, 100, 300, 200);
        setLayout(new GroupLayout(getContentPane()));
        setTitle("Insertar Autor");
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
        buildLabel = (JLabel) mainWindow.createJThing(2, "build_id");
        buildLabel.setBounds(20, 70, 80, 30);
    }

    private void createJTextField() {
        nameTextField = (JTextField) mainWindow.createJThing(4, "");
        nameTextField.setBounds(100, 20, 170, 30);
    }

    private void createJComboBox() {
        buildList = (JComboBox<String>) mainWindow.createJThing(1, "");
        buildList.setBounds(100, 70, 170, 30);
        List<String> albums = Query.getBuildNames(mainWindow.dataSourceConnection.connection);
        assert albums != null;
        for (String album : albums) {
            buildList.addItem(album);
        }
    }

    private void createJButton() {
        submitButton = (JButton) mainWindow.createJThing(0, "Añadir");
        submitButton.setBounds(100, 120, 110, 30);
        submitButton.addActionListener(actionEvent -> {
            if (StringUtils.isEmpty(nameTextField.getText())) {
                JOptionPane.showMessageDialog(null, "No puede haber campos en blanco!", "ERROR", JOptionPane.ERROR_MESSAGE);
            } else if (StringUtils.isNotInt((String) buildList.getSelectedItem())) {
                JOptionPane.showMessageDialog(null, "WAT!\nEl ID ha de ser un número", "ERROR", JOptionPane.ERROR_MESSAGE);
            } else {
                String name = nameTextField.getText().trim();
                int build_id = Integer.parseInt((String) Objects.requireNonNull(buildList.getSelectedItem()));

                if (!Query.insertAuthor(mainWindow.dataSourceConnection.connection, name, build_id)) {
                    JOptionPane.showMessageDialog(null, "Error al insertar!", "ERROR", JOptionPane.ERROR_MESSAGE);
                }
                DBUtils.refresh(mainWindow.dataSourceConnection, mainWindow.logger, mainWindow);
                close();
            }
        });
    }

    private void addStuff() {
        add(nameLabel);
        add(buildLabel);

        add(nameTextField);

        add(submitButton);

        add(buildList);
    }

    private void close() {
        dispose();
        mainWindow.switchB(true);
    }
}
