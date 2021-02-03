package windows;

import database.Query;
import utils.DBUtils;
import utils.StringUtils;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

@SuppressWarnings("unchecked")
public class EditAuthorWindow extends JFrame {
    private final MainWindow mainWindow;
    private final int id;
    private final String name;
    private final String album;

    private JLabel idLabel;
    private JLabel nameLabel;
    private JLabel albumLabel;

    private JTextArea idTextArea;
    private JTextArea nameTextArea;

    private JButton submitButton;

    private JComboBox<String> albumList;

    public EditAuthorWindow(MainWindow mainWindow, int id, String name, String album) {
        this.mainWindow = mainWindow;
        this.id = id;
        this.name = name;
        this.album = album;

        createJLabel();

        createJTextArea();

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
        albumLabel = (JLabel) mainWindow.createJThing(2, "Álbum");
        albumLabel.setBounds(20, 120, 80, 30);
    }

    private void createJTextArea() {
        idTextArea = (JTextArea) mainWindow.createJThing(3, "");
        idTextArea.setText(String.valueOf(this.id));
        idTextArea.setBounds(100, 20, 170, 30);
        idTextArea.setEditable(false);
        nameTextArea = (JTextArea) mainWindow.createJThing(3, "");
        nameTextArea.setText(this.name);
        nameTextArea.setBounds(100, 70, 170, 30);
    }

    private void createJButton() {
        submitButton = (JButton) mainWindow.createJThing(0, "Editar");
        submitButton.setBounds(100, 170, 110, 30);
        submitButton.addActionListener(actionEvent -> {
            if (StringUtils.isEmpty(nameTextArea.getText())) {
                JOptionPane.showMessageDialog(null, "No puede haber campos en blanco!", "ERROR", JOptionPane.ERROR_MESSAGE);
            } else {
                String name = nameTextArea.getText().trim();
                String album = (String) albumList.getSelectedItem();

                if (!Query.updateAuthor(mainWindow.dataSourceConnection.connection, id, name, album)) {
                    JOptionPane.showMessageDialog(null, "Error al editar!", "ERROR", JOptionPane.ERROR_MESSAGE);
                }
                DBUtils.refresh(mainWindow.dataSourceConnection, mainWindow.logger, mainWindow);
                close();
            }
        });
    }

    private void createJComboBox() {
        albumList = (JComboBox<String>) mainWindow.createJThing(1, "");
        albumList.setBounds(100, 120, 170, 30);
        List<String> albums = Query.getAlbumNames(mainWindow.dataSourceConnection.connection);
        assert albums != null;
        for (String album : albums) {
            albumList.addItem(album);
        }
        // Selecciono el item que ya había en la base de datos para comodidad.
        albumList.setSelectedItem(this.album);
    }

    private void addStuff() {
        add(idLabel);
        add(nameLabel);
        add(albumLabel);

        add(idTextArea);
        add(nameTextArea);

        add(submitButton);

        add(albumList);
    }

    private void close() {
        dispose();
        mainWindow.switchB(true);
    }
}
