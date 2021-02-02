package windows;

import database.Query;
import org.jdesktop.swingx.JXDatePicker;
import utils.DBUtils;
import utils.StringUtils;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class InsertAlbumWindow extends JFrame {
    private final MainWindow mainWindow;

    private JLabel titleLabel;
    private JLabel dateLabel;

    private JTextArea titleTextArea;

    private JXDatePicker datePicker;

    private JButton submitButton;

    public InsertAlbumWindow(MainWindow mainWindow) {
        this.mainWindow = mainWindow;

        createJLabel();

        createJTextArea();

        createDatePicker();

        createJButton();

        addStuff();

        createJFrame();
    }

    public void createJFrame() {
        setBounds(100, 100, 300, 200);
        setLayout(new GroupLayout(getContentPane()));
        setTitle("Insertar Album");
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
        titleLabel = (JLabel) mainWindow.createJThing(2, "Título");
        titleLabel.setBounds(20, 20, 80, 30);
        dateLabel = (JLabel) mainWindow.createJThing(2, "Fecha");
        dateLabel.setBounds(20, 70, 60, 30);
    }

    private void createJTextArea() {
        titleTextArea = (JTextArea) mainWindow.createJThing(3, "");
        titleTextArea.setBounds(100, 20, 170, 30);
    }

    private void createDatePicker() {
        datePicker = new JXDatePicker();
        datePicker.setDate(Calendar.getInstance().getTime());
        datePicker.setFormats(new SimpleDateFormat("dd-MM-yyyy"));
        datePicker.setBounds(100, 70, 170, 30);
    }

    private void createJButton() {
        submitButton = (JButton) mainWindow.createJThing(0, "Añadir");
        submitButton.setBounds(100, 120, 110, 30);
        submitButton.addActionListener(actionEvent -> {
            if (StringUtils.isEmpty(titleTextArea.getText())) {
                JOptionPane.showMessageDialog(null, "No puede haber campos en blanco!", "ERROR", JOptionPane.ERROR_MESSAGE);
            } else {
                String title = titleTextArea.getText();
                SimpleDateFormat dateTimeFormatter = new SimpleDateFormat("dd-MM-yyyy");
                String date = dateTimeFormatter.format(datePicker.getDate());
                if (!Query.insertAlbum(mainWindow.dataSourceConnection.connection, title, date)) {
                    JOptionPane.showMessageDialog(null, "Error al insertar!\nSeguro que este título no existe?", "ERROR", JOptionPane.ERROR_MESSAGE);
                }
                DBUtils.refresh(mainWindow.dataSourceConnection, mainWindow.logger, mainWindow);
                close();
            }
        });
    }

    private void addStuff() {
        add(titleLabel);
        add(dateLabel);

        add(titleTextArea);

        add(datePicker);

        add(submitButton);
    }

    private void close() {
        dispose();
        mainWindow.switchB(true);
    }
}
