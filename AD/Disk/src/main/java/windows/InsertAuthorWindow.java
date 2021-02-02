package windows;

import database.Query;
import utils.DBUtils;
import utils.StringUtils;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Collections;
import java.util.List;

@SuppressWarnings("unchecked")
public class InsertAuthorWindow extends JFrame {
    private final MainWindow mainWindow;

    private JLabel nameLabel;
    private JLabel albumLabel;

    private JTextArea nameTextArea;

    private JButton submitButton;

    private JComboBox<String> albumList;

    public InsertAuthorWindow(MainWindow mainWindow) {
        this.mainWindow = mainWindow;

        createJLabel();

        createJTextArea();

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
        albumLabel = (JLabel) mainWindow.createJThing(2, "Álbum");
        albumLabel.setBounds(20, 70, 80, 30);
    }

    private void createJTextArea() {
        nameTextArea = (JTextArea) mainWindow.createJThing(3, "");
        nameTextArea.setBounds(100, 20, 170, 30);
    }

    private void createJButton() {
        submitButton = (JButton) mainWindow.createJThing(0, "Añadir");
        submitButton.setBounds(100, 120, 110, 30);
        submitButton.addActionListener(actionEvent -> {
            if (StringUtils.isEmpty(nameTextArea.getText())) {
                JOptionPane.showMessageDialog(null, "No puede haber campos en blanco!", "ERROR", JOptionPane.ERROR_MESSAGE);
            } else {
                String name = nameTextArea.getText();
                String album = (String) albumList.getSelectedItem();

                if (!Query.insertAuthor(mainWindow.dataSourceConnection.connection, name, album)) {
                    JOptionPane.showMessageDialog(null, "Error al insertar!", "ERROR", JOptionPane.ERROR_MESSAGE);
                }
                DBUtils.refresh(mainWindow.dataSourceConnection, mainWindow.logger, mainWindow);
                close();
            }
        });
    }

    private void createJComboBox() {
        albumList = (JComboBox<String>) mainWindow.createJThing(1, "");
        albumList.setBounds(100, 70, 170, 30);
        List<String> albums = Query.getAlbumNames(mainWindow.dataSourceConnection.connection);
        assert albums != null;
        Collections.reverse(albums);
        for (String album : albums) {
            albumList.addItem(album);
        }
    }

    private void addStuff() {
        add(nameLabel);
        add(albumLabel);

        add(nameTextArea);

        add(submitButton);

        add(albumList);
    }

    private void close() {
        dispose();
        mainWindow.switchB(true);
    }
}
