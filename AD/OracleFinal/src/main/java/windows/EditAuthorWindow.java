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
public class EditAuthorWindow extends JFrame {
    private final MainWindow mainWindow;
    private final int id;
    private final String name;
    private final int build_id;

    private JLabel idLabel;
    private JLabel nameLabel;
    private JLabel albumLabel;

    private JTextField idTextField;
    private JTextField nameTextField;

    private JButton submitButton;

    private JComboBox<String> buildList;

    public EditAuthorWindow(MainWindow mainWindow, int id, String name, int build_id) {
        this.mainWindow = mainWindow;
        this.id = id;
        this.name = name;
        this.build_id = build_id;

        createJLabel();

        createJTextField();

        createJButton();

        createJComboBox();

        addStuff();

        createJFrame();
    }

    public void createJFrame() {
        setBounds(100, 100, 300, 250);
        setLayout(new GroupLayout(getContentPane()));
        setTitle("Editar Autor");
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
        nameLabel = (JLabel) mainWindow.createJThing(2, "Nombre");
        nameLabel.setBounds(20, 70, 80, 30);
        albumLabel = (JLabel) mainWindow.createJThing(2, "build_id");
        albumLabel.setBounds(20, 120, 80, 30);
    }

    private void createJTextField() {
        idTextField = (JTextField) mainWindow.createJThing(4, String.valueOf(this.id));
        idTextField.setBounds(100, 20, 170, 30);
        idTextField.setEditable(false);
        nameTextField = (JTextField) mainWindow.createJThing(4, this.name);
        nameTextField.setBounds(100, 70, 170, 30);
    }

    private void createJComboBox() {
        buildList = (JComboBox<String>) mainWindow.createJThing(1, "");
        buildList.setBounds(100, 120, 170, 30);
        List<String> albums = Query.getBuildNames(mainWindow.dataSourceConnection.connection);
        assert albums != null;
        for (String album : albums) {
            buildList.addItem(album);
        }
        buildList.setSelectedItem(String.valueOf(build_id));
    }

    private void createJButton() {
        submitButton = (JButton) mainWindow.createJThing(0, "Editar");
        submitButton.setBounds(100, 170, 110, 30);
        submitButton.addActionListener(actionEvent -> {
            if (StringUtils.isEmpty(nameTextField.getText())) {
                JOptionPane.showMessageDialog(null, "No puede haber campos en blanco!", "ERROR", JOptionPane.ERROR_MESSAGE);
            } else if (StringUtils.isNotInt((String) buildList.getSelectedItem())) {
                JOptionPane.showMessageDialog(null, "WAT!\nEl ID ha de ser un número", "ERROR", JOptionPane.ERROR_MESSAGE);
            } else {
                String name = nameTextField.getText().trim();
                int build_id = Integer.parseInt((String) Objects.requireNonNull(buildList.getSelectedItem()));

                if (!Query.updateAuthor(mainWindow.dataSourceConnection.connection, id, name, build_id)) {
                    JOptionPane.showMessageDialog(null, "Error al editar!", "ERROR", JOptionPane.ERROR_MESSAGE);
                }
                DBUtils.refresh(mainWindow.dataSourceConnection, mainWindow.logger, mainWindow);
                close();
            }
        });
    }

    private void addStuff() {
        add(idLabel);
        add(nameLabel);
        add(albumLabel);

        add(idTextField);
        add(nameTextField);

        add(submitButton);

        add(buildList);
    }

    private void close() {
        dispose();
        mainWindow.switchB(true);
    }
}
